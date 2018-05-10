package de.hska.iiwi.fittslaw;

import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

	private static final Logger LOG = Logger.getRootLogger();

	// Creating a static root to pass to the controller
	private static BorderPane root = new BorderPane();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		root = FXMLLoader.load(getClass().getResource(Constants.SCREEN_MAIN));

		// Initialize app with settings screen
		BorderPane settings = FXMLLoader.load(getClass().getResource(Constants.SCREEN_SETTINGS));
		root.setCenter(settings);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles.css");

		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	/**
	 * Called by the controller to switch scenes
	 * @return
	 */
	public static BorderPane getRoot() {
		return root;
	}
}