package de.hska.iiwi.fittslaw.experiment;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import de.hska.iiwi.fittslaw.ObservableResourcesSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ExperimentController implements Initializable {
	
	private static final Logger LOG = Logger.getRootLogger();

	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();

	@FXML
	private Label labelHello;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindI18NText();
	}
	
	private void bindI18NText() {
		labelHello.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelHello"));
	}
}
