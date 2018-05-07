package de.hska.iiwi.fittslaw;

import javafx.scene.control.Alert;

public class AboutAlert extends Alert {

	public AboutAlert() {
		super(AlertType.INFORMATION);
		setTitle("About"); // TODO i18n
		setHeaderText("Header Text"); // TODO i18n
		setContentText("Context Text"); // TODO i18n
	}
	
}
