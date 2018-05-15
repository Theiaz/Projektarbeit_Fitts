package de.hska.iiwi.fittslaw;

import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

	private static final Logger LOG = Logger.getRootLogger();

	// Creating a static root to pass to the controller
	private static BorderPane root = new BorderPane();
	
	private static Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		LOG.info("Initializing main screen.");
		try {
			root = FXMLLoader.load(getClass().getResource(Constants.SCREEN_MAIN));
		} catch (IOException e) {
			LOG.error("Cannot load main screen " + e.getMessage());
			e.printStackTrace();
		}

		// Initialize app with settings screen
		BorderPane settings = null;
		try {
			settings = FXMLLoader.load(getClass().getResource(Constants.SCREEN_SETTINGS));
			root.setCenter(settings);
		} catch (IOException e) {
			LOG.error("Cannot load settings screen " + e.getMessage());
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles.css");

		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(Constants.LOGO));
		primaryStage.setTitle(Constants.TITLE);
		primaryStage.show();
		stage = primaryStage;
		LOG.info("Initialized main screen.");

	}
	
	/**
	 * Called by the controller to switch scenes
	 * @return
	 */
	public static BorderPane getRoot() {
		return root;
	}
	
	public static Stage getStage() {
		return stage;
	}
}