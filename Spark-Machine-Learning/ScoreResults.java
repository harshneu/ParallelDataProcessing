{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf100
{\fonttbl\f0\fmodern\fcharset0 Courier;}
{\colortbl;\red255\green255\blue255;\red0\green0\blue0;}
{\*\expandedcolortbl;;\cssrgb\c0\c0\c0;}
\paperw11900\paperh16840\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\deftab720
\pard\pardeftab720\sl280\partightenfactor0

\f0\fs24 \cf2 \expnd0\expndtw0\kerning0
\outl0\strokewidth0 \strokec2 package score;\
\
import java.io.BufferedReader;\
import java.io.File;\
import java.io.FileInputStream;\
import java.io.IOException;\
import java.io.InputStreamReader;\
\
/** Calculates accuracy of bird sighting predictions for Fall 2016 CS6240 course project. */\
public class ScoreResults \{\
	public static void main(String[] args) \{\
		if (args.length != 2) \{\
			System.err.println("Usage:\\nscore.ScoreResults <result file> <truth file>");\
			System.exit(1);\
		\}\
		File resultFile = new File(args[0]);\
		if (!(resultFile.exists() && resultFile.isFile())) \{\
			System.err.println("Result file does not exist: " + args[0]);\
			System.exit(1);\
		\}\
		File truthFile = new File(args[1]);\
		if (!(truthFile.exists() && truthFile.isFile())) \{\
			System.err.println("Truth file does not exist: " + args[1]);\
			System.exit(1);\
		\}\
\
		BufferedReader resultReader = null, truthReader = null;\
		try \{\
			resultReader = new BufferedReader(new InputStreamReader(new FileInputStream(resultFile)));\
			truthReader = new BufferedReader(new InputStreamReader(new FileInputStream(truthFile)));\
			// Skip headers.\
			resultReader.readLine(); truthReader.readLine();\
			int count = 0, correct = 0;\
			String resultLine, truthLine;\
			while ((resultLine = resultReader.readLine()) != null && (truthLine = truthReader.readLine()) != null) \{\
				// Remove whitespace & parse.\
				//String[] resultFields = resultLine.split(",");\
				//String[] truthFields = truthLine.split(",");\
				//if (resultFields.length != 1 || truthFields.length != 1) \{\
				//	throw new Exception("Wrong number of fields; result:\\n" + resultLine + "\\nor truth:\\n" + truthLine);\
				//\}\
				if (resultLine.equals(truthLine)) \{\
					correct++;\
				\}\
				count++;\
			\}\
\
			System.out.format("Accuracy= %.6f", (float)correct / (float)count);\
\
		\} catch (Exception e) \{\
			System.err.println(e.getMessage());\
		\}\
		finally \{\
			try \{if (resultReader != null) resultReader.close();\} catch (IOException e) \{\}\
			try \{if (truthReader != null) truthReader.close();\} catch (IOException e) \{\}\
		\}\
	\}\
\}\
}