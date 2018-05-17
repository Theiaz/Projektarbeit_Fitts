package de.hska.iiwi.fittslaw.experiment;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.log4j.Logger;

import de.hska.iiwi.fittslaw.Constants;
import de.hska.iiwi.fittslaw.MainWindow;
import de.hska.iiwi.fittslaw.alerts.EndAlert;
import de.hska.iiwi.fittslaw.settings.SettingsController;
import de.hska.iiwi.fittslaw.settings.SettingsModel.ExperimentType;
import de.hska.iiwi.fittslaw.util.FileNameCreator;
import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

	private HotKey hotKey;

	private long time = System.currentTimeMillis();

	private int counter = 1;

	@SuppressWarnings("rawtypes")
	private EventHandler eventHandler;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindI18NText();
		switch (SettingsController.getModel().getExperimentType()) {
			case TEXT :
				container.getChildren().remove(icon);
				break;
			case ICON :
				container.getChildren().remove(command);
				break;
			default :
				String message = "unknown enum " + SettingsController.getModel().getExperimentType().toString();
				LOG.error(message);
				throw new InternalError(message);
		}
		command.setVisible(SettingsController.getModel().getExperimentType().equals(ExperimentType.TEXT));
		icon.setVisible(SettingsController.getModel().getExperimentType().equals(ExperimentType.ICON));
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
			LOG.error("can't write file " + filename + " : " + e.getMessage());
			e.printStackTrace();
		}

		eventHandler = new EventHandler<Event>() {
			@Override
			public void handle(Event event) {

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
			}
		};

		MainWindow.getStage().addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
		next();
	}

	private void bindI18NText() {
		command.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("command"));
	}

	private void next() {
		if (counter > SettingsController.getModel().getExperimentRounds()) {
			end();
		} else {
			hotKey = HotKey.getRandom();
			command.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding(hotKey.getSimpleName()));
			icon.setImage(hotKey.getIcon());
			LOG.info("round " + counter + " of " + SettingsController.getModel().getExperimentRounds() + " : "
					+ hotKey.getSimpleName());
			counter++;
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

}
