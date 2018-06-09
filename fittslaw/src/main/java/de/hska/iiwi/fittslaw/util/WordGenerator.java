package de.hska.iiwi.fittslaw.util;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import de.hska.iiwi.fittslaw.Constants;

public class WordGenerator {

	private static final Logger LOG = Logger.getRootLogger();

	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();

	private static int lastInt = -1;

	/**
	 * Returns a random word. The returned word will always be a different one
	 * than the last one returned by this method.
	 * 
	 * @return random word
	 */
	public static String getWord() {
		int random;
		do {
			random = ThreadLocalRandom.current().nextInt(0, Constants.DICT_LNGTH);
		} while (lastInt == random);
		LOG.info("generated word" + random);
		return OBSERVABLE_RESOURCES.getStringBinding("word" + random).get();
	}

}
