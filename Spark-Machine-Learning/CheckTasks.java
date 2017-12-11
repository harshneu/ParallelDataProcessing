package general;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CheckTasks {
	private static final String FILENAME = "stderr1.txt";

	public static void main(String[] args) {

		BufferedReader br = null;
		FileReader fr = null;
		int cnt = 0;
		for (int i = 0; i <= 20; i++) {
			try {

				fr = new FileReader(FILENAME);
				br = new BufferedReader(fr);

				String sCurrentLine;

				while ((sCurrentLine = br.readLine()) != null) {
					if (sCurrentLine.contains("TaskSetManager: Starting task")
							&& sCurrentLine.contains("stage " + i + ".0")) {
						cnt++;
					}
				}
			} catch (

			IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (br != null)
						br.close();

					if (fr != null)
						fr.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
			System.out.println("Stage :" + i + ".0 \tTasks:" + cnt);
			cnt = 0;
		}
	}

}