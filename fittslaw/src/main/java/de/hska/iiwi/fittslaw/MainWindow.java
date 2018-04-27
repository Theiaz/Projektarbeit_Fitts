package de.hska.iiwi.fittslaw;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {
	
	private static final Logger LOG = Logger.getRootLogger();
	private static final String SCREEN_SETTINGS = "/fxml/settings.fxml";
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(SCREEN_SETTINGS));
		Parent root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			LOG.error("Unable to load FXML files: " + e.getMessage());
			e.printStackTrace();
		}
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles.css");

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}