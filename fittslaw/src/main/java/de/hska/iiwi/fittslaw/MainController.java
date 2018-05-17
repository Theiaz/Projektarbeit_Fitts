package de.hska.iiwi.fittslaw;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import de.hska.iiwi.fittslaw.alerts.AboutAlert;
import de.hska.iiwi.fittslaw.settings.SettingsController;
import de.hska.iiwi.fittslaw.util.FileNameCreator;
import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	private static final Logger LOG = Logger.getRootLogger();
	
	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();
	
	@FXML
	private BorderPane rootPane;
	@FXML
	private Menu menuFile;
	@FXML
	private MenuItem menuItemClose;
	@FXML
	private Menu menuLanguage;
	@FXML
	private RadioMenuItem menuItemEnglish;
	@FXML
	private ToggleGroup toggleLanguage;
	@FXML
	private RadioMenuItem menuItemGerman;
	@FXML
	private Menu menuHelp;
	@FXML
	private MenuItem menuItemAbout;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		OBSERVABLE_RESOURCES.setResources(ResourceBundle.getBundle(Constants.I18N_MAIN_EN));
		bindI18NText();

		menuItemEnglish.setSelected(true);
		menuItemEnglish.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					LOG.info("Selected english language");
					OBSERVABLE_RESOURCES.setResources(ResourceBundle.getBundle(Constants.I18N_MAIN_EN));
				}
			}
		});

		menuItemGerman.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					LOG.info("Selected german language");
					OBSERVABLE_RESOURCES.setResources(ResourceBundle.getBundle(Constants.I18N_MAIN_DE));
				}
			}
		});
		
		menuItemAbout.setOnAction(e -> {
			LOG.info("showing about alert");
			new AboutAlert().showAndWait();
		});
		
		ImageView ienus = new ImageView(new Image(Constants.ICON_EN_UK));
		menuItemEnglish.setGraphic(ienus);
		ImageView idede = new ImageView(new Image(Constants.ICON_DE_DE));
		idede.setFitWidth(16.0);
		idede.setFitHeight(16.0);
		menuItemGerman.setGraphic(idede);
	}
	
	private void bindI18NText() {
		//menu
		menuFile.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("menuFile"));
		menuItemClose.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("menuItemClose"));
		menuLanguage.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("menuLanguage"));
		menuItemEnglish.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("menuItemEnglish"));
		menuItemGerman.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("menuItemGerman"));
		menuHelp.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("menuHelp"));
		menuItemAbout.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("menuItemAbout"));
	}
	
	@FXML
	protected void onCloseItemClicked() {
		abort();
	}
	
	public static void abort() {
		LOG.info("Closing application...");
		
		// Delete if not yet finished
		if (SettingsController.getModel().isExperimentAborted()) {
			File file = new File(FileNameCreator.getFileName(SettingsController.getModel().getTimestamp()));
			file.delete();
			LOG.info("Deleted outputfile.");
		}
		
		System.exit(0);
	}
}
