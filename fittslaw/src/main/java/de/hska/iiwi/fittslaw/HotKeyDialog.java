package de.hska.iiwi.fittslaw;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HotKeyDialog extends Stage {

	@SuppressWarnings("unchecked")
	public HotKeyDialog(HotKey hotKey, SettingsModel model) {
		super(StageStyle.UTILITY);
		initModality(Modality.APPLICATION_MODAL);
		setResizable(false);
		// setOnCloseRequest(e -> );

		HBox hBox = new HBox();
		hBox.getStyleClass().add("hotkey-dialog");
		if (model.isExperimentIcons()) {
			ImageView icon = new ImageView(hotKey.getIcon());
			hBox.getChildren().add(icon);
		} else {
			Label label = new Label(hotKey.getSimpleName()); // TODO i18n
			label.getStyleClass().add("hotkey-dialog");
			hBox.getChildren().add(label);
		}
		Scene scene = new Scene(hBox);
		scene.getStylesheets().add("/styles.css");
		setScene(scene);
		
		addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler() {

			@Override
			public void handle(Event event) {
				if (hotKey.getKeyCombination().match((KeyEvent) event)) {
					close();
				}
			}

		});
	}

}
