package de.hska.iiwi.fittslaw;

import javafx.scene.control.Alert;

public class EndExperimentAlert extends Alert {

	public EndExperimentAlert() {
		super(AlertType.INFORMATION);
		setTitle("Info"); // TODO i18n
		setHeaderText("Experiment successfully recorded"); // TODO i18n
		setContentText(null);
	}
	
}
