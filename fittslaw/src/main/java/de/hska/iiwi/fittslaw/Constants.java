package de.hska.iiwi.fittslaw;

public class Constants {

	/**
	 * If built by Maven, this will be set to the current version, for example
	 * {@code 1.0.0} or {@code 1.0.0-SNAPSHOT}. Otherwise, this may be {@code
	 * null}.
	 */
	public static final String VERSION = Constants.class.getPackage().getImplementationVersion();

	public static final String TITLE = "Fitts O1";
	public static final String TITLE_FILES = "FittsO1";
	public static final String AUTHOR_1 = "Julian Schäfer";
	public static final String AUTHOR_2 = "Torben Krüger";
	public static final String AUTHOR_ICONS = "Freepik (www.freepik.com)";
	public static final Object AUTHOR_SOUND_ERROR = "lluiset7 (www.freesound.org/s/141334)";
	public static final String SUPERVISOR = "Prof. Dr. rer. nat. Ulrich Bröckl";

	public static final String SCREEN_MAIN = "/fxml/main.fxml";
	public static final String SCREEN_SETTINGS = "/fxml/settings.fxml";
	public static final String SCREEN_EXPERIMENT = "/fxml/experiment.fxml";

	public static final String I18N_MAIN_EN = "i18n.MainI18N_en_US";
	public static final String I18N_MAIN_DE = "i18n.MainI18N_de_DE";

	public static final String ICON_COPY = "/icons/hotkeys/copy.png";
	public static final String ICON_CUT = "/icons/hotkeys/cut.png";
	public static final String ICON_PASTE = "/icons/hotkeys/paste.png";
	public static final String ICON_PRINT = "/icons/hotkeys/print.png";
	public static final String ICON_SAVE = "/icons/hotkeys/save.png";
	
	public static final String ICON_EN_US = "/icons/i18n/en_US.png";
	public static final String ICON_EN_UK = "/icons/i18n/en_UK.png";
	public static final String ICON_DE_DE = "/icons/i18n/de_DE.png";

	public static final String LOGO = "/icons/logo.png";
	
	public static final String SOUND_ERROR = "/sound/error.aiff";
	
	public static final int DICT_LNGTH = 30;
	
	public static final int DEVICETYPE_LENGTH_MIN = 0;
	public static final int DEVICETYPE_LENGTH_MAX = 100; // TODO find good value
	public static final int NAME_LENGTH_MIN = 1;
	public static final int NAME_LENGTH_MAX = 100; // TODO find good value
	public static final int AGE_MIN = 0;
	public static final int AGE_MAX = 99;
	public static final int COMMENT_LENGTH_MIN = 0;
	public static final int COMMENT_LENGTH_MAX = 1000; // TODO find good value
	public static final int ROUNDS_MIN = 5; // TODO find good value
	public static final int ROUNDS_MAX = 100; // TODO find good value

}
