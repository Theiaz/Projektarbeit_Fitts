package de.hska.iiwi.fittslaw.experiment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.hska.iiwi.fittslaw.Constants;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public enum HotKey {

	COPY("copy", Constants.ICON_COPY, KeyCode.C),

	CUT("cut", Constants.ICON_CUT, KeyCode.X),

	PASTE("paste", Constants.ICON_PASTE, KeyCode.V),

	PRINT("print", Constants.ICON_PRINT,KeyCode.P),

	SAVE("save", Constants.ICON_SAVE, KeyCode.S);

	private final Image icon;

	private final String simpleName;

	private final KeyCode keyCode;

	private HotKey(String simpleName, String iconURL, KeyCode keyCode) {
		this.simpleName = simpleName;
		this.icon = new Image(iconURL);
		this.keyCode = keyCode;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public Image getIcon() {
		return icon;
	}

	public KeyCode getKeyCode() {
		return keyCode;
	}

	public static final List<HotKey> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

	private static final int SIZE = VALUES.size();

	private static final Random RANDOM = new Random();

	private static int lastInt = -1;

	/**
	 * Returns a random HotKey. The returned HotKey will always be a different
	 * one than the last one returned by this method.
	 * 
	 * @return random HotKey
	 */
	public static HotKey getRandom() {
		int random;
		do {
			random = RANDOM.nextInt(SIZE);
		} while (lastInt == random);
		lastInt = random;
		return VALUES.get(random);
	}

}
