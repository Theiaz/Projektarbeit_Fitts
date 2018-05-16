package de.hska.iiwi.fittslaw.experiment;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import de.hska.iiwi.fittslaw.Constants;
import de.hska.iiwi.fittslaw.MainWindow;
import de.hska.iiwi.fittslaw.alerts.EndAlert;
import de.hska.iiwi.fittslaw.settings.SettingsController;
import de.hska.iiwi.fittslaw.settings.SettingsModel.ExperimentType;
import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class ExperimentController implements Initializable {

	private static final Logger LOG = Logger.getRootLogger();

	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();

	@FXML
	private Label command;

	@FXML
	private ImageView icon;

	private HotKey hotKey;

	private long time = System.currentTimeMillis();

	private int counter = 1;

	@SuppressWarnings("rawtypes")
	private EventHandler eventHandler;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindI18NText();
		command.setVisible(SettingsController.getModel().getExperimentType().equals(ExperimentType.TEXT));
		icon.setVisible(SettingsController.getModel().getExperimentType().equals(ExperimentType.ICON));

		try {
			FileWriter writer = new FileWriter(Constants.OUTPUT, true);
			writer.append('\n');
			writer.append("Expected Key;");
			writer.append("Pressed Key;");
			writer.append("Time in ms;");
			writer.append('\n');
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LOG.error("can't write file " + Constants.OUTPUT + " : " + e.getMessage());
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
						FileWriter writer = new FileWriter(Constants.OUTPUT, true);
						writer.append(hotKey.getSimpleName() + ";");
						writer.append(pressed.getSimpleName() + ";");
						writer.append(System.currentTimeMillis() - time + ";");
						writer.append('\n');
						writer.flush();
						writer.close();
					} catch (IOException e) {
						LOG.error("can't write file " + Constants.OUTPUT + " : " + e.getMessage());
						e.printStackTrace();
					}

					time = System.currentTimeMillis();
					
					if (pressed.equals(hotKey)) {
						next();
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
		MainWindow.getStage().removeEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
		SettingsController.getModel().setExperimentAborted(false);
		LOG.info("showing end alert");
		Optional<ButtonType> result = new EndAlert().showAndWait();
		if (result.get() == ButtonType.OK) {
			// TODO start new experiment
		} else if (result.get() == ButtonType.CANCEL) {
			System.exit(0);
		} else {
			String message = "unkown ButtonType";
			LOG.error("message");
			throw new InternalError(message);
		}
	}

}
