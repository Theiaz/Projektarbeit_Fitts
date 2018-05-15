package de.hska.iiwi.fittslaw.experiment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.hska.iiwi.fittslaw.Constants;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public enum HotKey {

	COPY("copy", Constants.ICON_COPY, new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN)),

	CUT("cut", Constants.ICON_CUT, new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN)),

	PASTE("paste", Constants.ICON_PASTE, new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN)),

	PRINT("print", Constants.ICON_PRINT, new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN)),

	SAVE("save", Constants.ICON_SAVE, new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

	private final Image icon;

	private final String simpleName;

	private final KeyCombination keyCombination;

	private HotKey(String simpleName, String iconURL, KeyCombination keyCombination) {
		this.simpleName = simpleName;
		this.icon = new Image(iconURL);
		this.keyCombination = keyCombination;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public Image getIcon() {
		return icon;
	}

	public KeyCombination getKeyCombination() {
		return keyCombination;
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
