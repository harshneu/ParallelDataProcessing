// Main Package
package com.main

//importing spark context and parser.
import com.parser.PageParsers
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf


object PageRankDriver {
  def main(args: Array[String]) {
    // pageRank Value
    val pageRankValue: Double = 0.15
    // Remaining Delta value
    val pageRankProb: Double = 0.85
    val zero: Int = 0
    // initialises the context
    val conf = new SparkConf().setAppName("PageRank using Spark/Scala")
    val sc = new SparkContext(conf)

    // The step to parse the lines
    var node = sc.textFile(args(zero)).
      map { line => PageParsers.parseLine(line).split("\\s") }.
    // filtering for the invalid nodes.
      filter { row => row(zero) != null  }.
      map { row => {
        try {
          var page = row(zero)
          var rank = row(1).toDouble
          var adjacencyList = List[String]()
          // checks for row length and adds to the adjacency list
          if (row.length >= 3)
            adjacencyList = row(2).split("~").toList
          (page, rank, adjacencyList)
          //exception handling in scala.
        } catch {
          case e: Throwable => ("", 0.0d, List())
        }
        // Pagename is taken in as the key.
        // Spark has lazy loading and therefore we persist with lazy loading.
      }}.keyBy(row => row._1).persist().mapValues(row => (row._2, row._3))


    // combines all the nodes in the graph as in adjacency list
    val adjacencyLists = node.flatMap(row => row._2._2)
   //  make an RDD using the combining in the adjacency list and  union of the RDD with completeGraph RDD.
    val adjacencyListNodesRDD = adjacencyLists.map(row => (row, 0.0, List[String]())).keyBy(row => row._1)
      .mapValues(row => (row._2, row._3));
    // creates an RDD
    val graph = node.union(adjacencyListNodesRDD).reduceByKey((p, q) => (p._1, p._2 ++ q._2))

    var totalNodes = graph.count;
    var graphCombined = graph.mapValues(row => (1.0 / totalNodes, row._2))
    // Page rank initialization We also checkfor the iterations here.
    var i: Int = 1;
    while( i < 11 ) {
      // counter implemented here
      val danglingCounter = sc.doubleAccumulator("dangling")
      var contributionGraph = graphCombined.map(row => (row._2._1, row._2._2)).flatMap { row => {
        if (row._2.length == zero) {
          danglingCounter.add(row._1)
          List()
        }
        else {
          var contribution = row._1 / row._2.length
          row._2.map { outlink => (outlink, contribution) }
        }
      }}.keyBy(row => row._1).mapValues(row => row._2)
      contributionGraph = contributionGraph.reduceByKey((p, q) => p + q)

      // creates a new RDD and update valuse for next iterations.
      // run loops for 10 iterations..
      val graphOne = graphCombined.join(contributionGraph); // Perform inner join on page names
      totalNodes = graphOne.count()
      graphCombined = graphOne.map { row => {
        var pageRank = (pageRankValue / totalNodes) + (pageRankProb * (row._2._2 + danglingCounter.value))
        (row._1, pageRank, row._2._1._2)
      }}.keyBy(row => row._1).mapValues(row => (row._2, row._3))
      i= i+1
    }

    // Create a RDD for top 100 pages ,take top 100 pages from each iterations,
    // creates one partition to get top 100 pages globally.
    val output = graphCombined.map(row => ( row._2._1, row._1))
      .takeOrdered(100)(Ordering[Double].reverse.on { x => x._1 })
    sc.parallelize(output, 1).saveAsTextFile(args(1))
  }
}