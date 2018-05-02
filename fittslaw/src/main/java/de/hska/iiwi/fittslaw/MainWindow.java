package de.hska.iiwi.fittslaw;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {

	private static final Logger LOG = Logger.getRootLogger();

	private static final ObservableResourceSingleton RESOURCE_FACTORY = ObservableResourceSingleton.getInstance();

	static {
		RESOURCE_FACTORY.setResources(ResourceBundle.getBundle(Constants.I18N_SETTINGS_EN));
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL fxmlURL = classLoader.getResource("fxmls/myview.fxml");

		FXMLLoader loader = new FXMLLoader(fxmlURL, RESOURCE_FACTORY.getResources());
		loader.setLocation(getClass().getResource(Constants.SCREEN_SETTINGS));
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