package de.hska.iiwi.fittslaw;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
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
}
