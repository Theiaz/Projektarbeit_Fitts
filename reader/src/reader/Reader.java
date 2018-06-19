package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Reader {

	public static final int OFFSET = 5;

	public static final String SEPERATOR = ";";

	public static void main(String[] args) throws IOException {

		final File folder = new File(
				System.getProperty("user.dir") + "/../results/");

		final PrintWriter writer = new PrintWriter("analysis.csv");
		writer.println(
				"type;age;percentCorrect;percentIncorrect;meridian;standardDeviation;");

		for (final File fileEntry : folder.listFiles()) {
			Info info = readFile(fileEntry);
			if (info != null) {
				writer.println(info.type + ";" + info.age + ";"
						+ info.percentCorrect + ";" + info.percentIncorrect + ";"
						+ info.meridian + ";" + info.standardDeviation);
			}
		}

		readFile(folder.listFiles()[7]);

		writer.close();
	}

	public static Info readFile(File file) throws IOException {
		BufferedReader br = null;
		String line = null;
		int rounds = 0;
		br = new BufferedReader(new FileReader(file));
		Type type = null;
		int age = 0;

		for (int i = 0; i < 20; i++) {
			line = br.readLine();

			// use semicolon as separator
			String[] splitLine = line.split(SEPERATOR);
			
			if (i == 5) { // ExperimentType
				age = Integer.parseInt((splitLine[1].replace("\"", "")));
			}
			
			if (i == 12) { // ExperimentType
				type = Type.valueOf(splitLine[1].replace("\"", ""));
			}
			
			if (i == 13) { // ExperimentRounds
				rounds = Integer.parseInt(splitLine[1].replace("\"", ""));
				rounds -= OFFSET;
				if (rounds == 0) {
					br.close();
					return null;
				}
			}
		}

		for (int i = 0; i < OFFSET; i++) {
			br.readLine();
		}

		int correct = 0;
		boolean lastCorrect = true;
		List<Long> times = new ArrayList<>();
		while ((line = br.readLine()) != null) {

			// use semicolon as separator
			String[] splitLine = line.split(SEPERATOR);

			if (splitLine[0].equals(splitLine[1])) {
				if (lastCorrect) {
					correct++;
					times.add(
							Long.parseLong(splitLine[2].replaceAll("\"", "")));
				}
				lastCorrect = true;
			} else {
				lastCorrect = false;
			}

			// System.out.println(Arrays.toString(splitLine));

		}

		double percentCorrect = 100.0 * correct / rounds;
		double percentIncorrect = 100.0 * (rounds - correct) / rounds;
		final long meridian = times.stream().mapToLong(Long::longValue).sum()
				/ correct;
		final long standardDeviation = times.stream().mapToLong(Long::longValue)
				.map(l -> Math.abs(l - meridian)).sum() / correct;

		br.close();

		return new Info(type, age, percentCorrect, percentIncorrect, meridian,
				standardDeviation);
	}

	public static class Info {

		public final Type type;

		public final int age;

		public final double percentCorrect;

		public final double percentIncorrect;

		public final long meridian;

		public final long standardDeviation;

		public Info(Type type, int age, double percentCorrect,
				double percentIncorrect, long meridian,
				long standardDeviation) {
			this.type = type;
			this.age = age;
			this.percentCorrect = percentCorrect;
			this.percentIncorrect = percentIncorrect;
			this.meridian = meridian;
			this.standardDeviation = standardDeviation;
		}

	}

	public enum Type {

		TEXT,

		ICON;
		
	}

}
