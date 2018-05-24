package de.hska.iiwi.fittslaw.experiment;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.log4j.Logger;

import de.hska.iiwi.fittslaw.Constants;
import de.hska.iiwi.fittslaw.MainWindow;
import de.hska.iiwi.fittslaw.alerts.EndAlert;
import de.hska.iiwi.fittslaw.settings.SettingsController;
import de.hska.iiwi.fittslaw.util.FileNameCreator;
import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ExperimentController implements Initializable {

	private static final Logger LOG = Logger.getRootLogger();

	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();

	@FXML
	private Label command;

	@FXML
	private ImageView icon;

	@FXML
	private VBox container;

	@FXML
	private VBox textInput;

	@FXML
	private Label textInputWord;

	@FXML
	private TextField textInputField;

	private HotKey hotKey;

	private long time = System.currentTimeMillis();

	private int round = 1;

	private boolean hotKeyInput = false;

	private String word;

	@SuppressWarnings("rawtypes")
	private EventHandler eventHandler;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindI18NText();
		setContent();
		String filename = FileNameCreator.getFileName(SettingsController.getModel().getTimestamp());

		try {
			FileWriter writer = new FileWriter(filename, true);
			writer.append('\n');
			writer.append("Expected Key;");
			writer.append("Pressed Key;");
			writer.append("Time in ms;");
			writer.append('\n');
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LOG.error("can't write file " + filename + ": " + e.getMessage());
			e.printStackTrace();
		}

		eventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event event) {

				if (hotKeyInput) {

					// find out which hotkey has been pressed
					HotKey pressed = null;
					for (HotKey hotKey : HotKey.VALUES) {
						if (hotKey.getKeyCombination().match((KeyEvent) event)) {
							pressed = hotKey;
						}
					}

					if (pressed != null) {
						LOG.info("hotkey " + pressed.getSimpleName() + " pressed");
						try {
							FileWriter writer = new FileWriter(filename, true);
							writer.append(hotKey.getSimpleName() + ";");
							writer.append(pressed.getSimpleName() + ";");
							writer.append(System.currentTimeMillis() - time + ";");
							writer.append('\n');
							writer.flush();
							writer.close();
						} catch (IOException e) {
							LOG.error("can't write file " + filename + " : " + e.getMessage());
							e.printStackTrace();
						}

						time = System.currentTimeMillis();

						if (pressed.equals(hotKey)) {
							next();
						} else {
							playErrorSound();
						}

					}
				}
			}
		};

		textInputField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.equals(word)) {
				next();
			} else if (newValue.length() > word.length() || !word.substring(0, newValue.length()).equals(newValue)) {
				playErrorSound();
				textInputField.setText(oldValue);
			}
		});

		MainWindow.getStage().addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
		next();
	}

	private void bindI18NText() {
		command.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("command"));
	}

	private void next() {
		if (round > SettingsController.getModel().getExperimentRounds()) {
			end();
		} else {
			hotKeyInput = !hotKeyInput;
			setContent();
			if (hotKeyInput) {
				hotKey = HotKey.getRandom();
				command.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding(hotKey.getSimpleName()));
				icon.setImage(hotKey.getIcon());
				LOG.info("round " + round + " of " + SettingsController.getModel().getExperimentRounds() + ": "
						+ hotKey.getSimpleName());
				time = System.currentTimeMillis();
				round++;
			} else {
				int randomNum = ThreadLocalRandom.current().nextInt(0, Constants.DICT_LNGTH);
				word = OBSERVABLE_RESOURCES.getStringBinding("word" + randomNum).get();
				textInputWord.setText(word);
				textInputField.clear();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void end() {
		LOG.info("end of experiment");
		MainWindow.getRoot().setCenter(null);
		MainWindow.getStage().removeEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
		SettingsController.getModel().setExperimentAborted(false);
		LOG.info("showing end alert");
		new EndAlert().showAndWait();
		BorderPane settings = null;
		try {
			settings = FXMLLoader.load(getClass().getResource(Constants.SCREEN_SETTINGS));
			MainWindow.getRoot().setCenter(settings);
		} catch (IOException e) {
			LOG.error("Cannot load settings screen " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void setContent() {
		container.getChildren().clear();
		if (hotKeyInput) {
			switch (SettingsController.getModel().getExperimentType()) {
				case TEXT :
					container.getChildren().add(command);
					break;
				case ICON :
					container.getChildren().add(icon);
					break;
				default :
					String message = "unknown enum " + SettingsController.getModel().getExperimentType().toString();
					LOG.error(message);
					throw new InternalError(message);
			}
		} else {
			container.getChildren().add(textInput);
		}
	}

	private void playErrorSound() {
		try {
			Clip clip = AudioSystem.getClip();

			// read audio data from whatever source
			// (file/classloader/etc.)
			InputStream audioSrc = getClass().getResourceAsStream(Constants.SOUND_ERROR);
			// add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

			clip.open(audioStream);
			clip.start();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
