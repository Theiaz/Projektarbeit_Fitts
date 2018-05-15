package de.hska.iiwi.fittslaw.alerts;

import de.hska.iiwi.fittslaw.Constants;
import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutAlert extends Alert {

	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();

	public AboutAlert() {
		super(AlertType.INFORMATION);
		ImageView image = new ImageView(Constants.LOGO);
		image.setFitWidth(60.0);
		image.setFitHeight(60.0);
		setGraphic(image);
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(Constants.LOGO));
		setTitle(OBSERVABLE_RESOURCES.getStringBinding("about").get() + " " + Constants.TITLE);
		setHeaderText(Constants.TITLE + " " + Constants.VERSION);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(OBSERVABLE_RESOURCES.getStringBinding("writtenby").get());
		sb.append(' ');
		sb.append(Constants.AUTHOR_1);
		sb.append(' ');
		sb.append(OBSERVABLE_RESOURCES.getStringBinding("and").get());
		sb.append(' ');
		sb.append(Constants.AUTHOR_2);
		sb.append('\n');
		
		sb.append(OBSERVABLE_RESOURCES.getStringBinding("iconsby").get());
		sb.append(' ');
		sb.append(Constants.AUTHOR_ICONS);
		sb.append('\n');
		
		setContentText(sb.toString());
	}

}
