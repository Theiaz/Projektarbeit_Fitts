package de.hska.iiwi.fittslaw;

import javafx.fxml.FXML;

import javafx.scene.text.Text;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

import javafx.scene.control.CheckBox;

public class SettingsController {
	@FXML
	private Label labelAge;
	@FXML
	private TextField textfieldAge;
	@FXML
	private Label labelSex;
	@FXML
	private RadioButton radioSexFemale;
	@FXML
	private TextField textfieldName;
	@FXML
	private RadioButton radioSexMale;
	@FXML
	private Label labelWritingDirection;
	@FXML
	private RadioButton radioWritingDirectionLR;
	@FXML
	private RadioButton radioWritingDirectionRL;
	@FXML
	private Label labelWritingHand;
	@FXML
	private RadioButton radioWritingHandL;
	@FXML
	private RadioButton radioWritingHandR;
	@FXML
	private Label labelComment;
	@FXML
	private TextField textfieldComment;
	@FXML
	private RadioButton radioInputDeviceMouse;
	@FXML
	private RadioButton radioInputDeviceTouch;
	@FXML
	private RadioButton radioInputDevicePen;
	@FXML
	private TextField textfieldDeviceType;
	@FXML
	private Text captionExperimentee;
	@FXML
	private Text captionInputDevice;
	@FXML
	private CheckBox checkboxWriting10Finger;
	@FXML
	private Label labelWriting10Finger;
	@FXML
	private Label labelName;
	@FXML
	private Label labelInputDevice;
	@FXML
	private Label labelDeviceType;
	@FXML
	private RadioButton radioTypeFirst;
	@FXML
	private RadioButton radioTypeSecond;
	@FXML
	private RadioButton radioTypeThird;
	@FXML
	private TextField textfieldRounds;
	@FXML
	private Text captionAttempts;
	@FXML
	private Label labelRounds;
	@FXML
	private CheckBox checkboxIcons;
	@FXML
	private Label labelIcons;
	@FXML
	private Button buttonStart;
	@FXML
	private Label labelType;

}
