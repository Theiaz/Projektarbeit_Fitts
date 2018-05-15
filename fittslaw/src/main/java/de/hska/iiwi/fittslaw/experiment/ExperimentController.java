package de.hska.iiwi.fittslaw.experiment;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import de.hska.iiwi.fittslaw.Constants;
import de.hska.iiwi.fittslaw.MainWindow;
import de.hska.iiwi.fittslaw.settings.SettingsController;
import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

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
		command.setVisible(!SettingsController.getModel().isExperimentIcons());
		icon.setVisible(SettingsController.getModel().isExperimentIcons());

		try {
			FileWriter writer = new FileWriter(Constants.OUTPUT, true);
			writer.append('\n');
			writer.append("expected;");
			writer.append("pressed;");
			writer.append("time;");
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
		MainWindow.getStage().removeEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
		new Alert(AlertType.INFORMATION).showAndWait();
	}

}
