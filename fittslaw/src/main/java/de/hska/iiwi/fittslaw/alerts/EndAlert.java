package de.hska.iiwi.fittslaw.alerts;

import de.hska.iiwi.fittslaw.Constants;
import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EndAlert extends Alert {
	
	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();

	public EndAlert() {
		super(AlertType.CONFIRMATION);
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Constants.LOGO));
		setHeaderText(OBSERVABLE_RESOURCES.getStringBinding("restartquestion").get());
//		setContentText(OBSERVABLE_RESOURCES.getStringBinding("restartquestion").get());
	}

}
