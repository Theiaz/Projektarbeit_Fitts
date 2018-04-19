package de.hska.iiwi.fittslaw;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainWindow extends Application {
	
	private static final Logger LOG = Logger.getRootLogger();
	private static final String SCREEN_SETTINGS = "/fxml/settings.fxml";
	
	private Stage window;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(SCREEN_SETTINGS));
		Parent root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			LOG.error("Unable to load FXML files: " + e.getMessage());
			e.printStackTrace();
		}
		
		Scene scene = new Scene(root, 300, 250, Color.WHITE);

		window.setScene(scene);
		window.show();
	}

}