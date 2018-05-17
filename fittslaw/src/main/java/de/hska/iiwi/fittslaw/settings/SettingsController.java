package de.hska.iiwi.fittslaw.settings;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import de.hska.iiwi.fittslaw.Constants;
import de.hska.iiwi.fittslaw.MainWindow;
import de.hska.iiwi.fittslaw.settings.SettingsModel.ExperimentType;
import de.hska.iiwi.fittslaw.settings.SettingsModel.Gender;
import de.hska.iiwi.fittslaw.settings.SettingsModel.WritingDirection;
import de.hska.iiwi.fittslaw.settings.SettingsModel.WritingHand;
import de.hska.iiwi.fittslaw.util.FileNameCreator;
import de.hska.iiwi.fittslaw.util.ObservableResourcesSingleton;
import de.hska.iiwi.fittslaw.util.ValueHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SettingsController implements Initializable {

	private static final Logger LOG = Logger.getRootLogger();

	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();

	private static final SettingsModel model = new SettingsModel();

	@FXML
	private HBox parentHBox;
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
	private Label labelMouseHand;
	@FXML
	private RadioButton radioMouseHandL;
	@FXML
	private RadioButton radioMouseHandR;
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
	private TextField textfieldRounds;
	@FXML
	private Text captionAttempts;
	@FXML
	private Label labelRounds;
	@FXML
	private Button buttonStart;
	@FXML
	private Label labelType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindI18NText();
		
		fillMockData();
	}

	@FXML
	protected void saveButtonClicked(ActionEvent event) {
		if (isInputValid()) {
			LOG.info("Input successfully validated!");
			bindDataToModel();
			// bindMockupData();
			writeToCsv();
			
			startExperiment();
		}
	}

	private void startExperiment() {
		BorderPane experimentPane;
		try {
			experimentPane = FXMLLoader.load(getClass().getResource(Constants.SCREEN_EXPERIMENT));
			BorderPane root = MainWindow.getRoot();
			root.setCenter(experimentPane);
		} catch (IOException e) {
			LOG.error("Cannot load experiment screen " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void writeToCsv() {
		try {
			List<ValueHolder> content = new ArrayList<ValueHolder>();
			for (Field field : model.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (!Modifier.isPrivate(field.getModifiers())) {
					continue;
				}
				String s1 = field.getName().substring(0, 1).toUpperCase();
				String nameCapitalized = s1 + field.getName().substring(1);
				// Skip ExperimentAborted field (not necessary inside csv)
				if (nameCapitalized.contains("ExperimentAborted"))
				{
					continue;
				}
				content.add(new ValueHolder(nameCapitalized, String.valueOf(field.get(model))));
			}

			final ColumnPositionMappingStrategy<ValueHolder> strategy = new ColumnPositionMappingStrategy<>();
			strategy.setType(ValueHolder.class);
			strategy.setColumnMapping("key", "value");

			LOG.info("Start generating csv file...");
			FileWriter writer = new FileWriter(FileNameCreator.getFileName(model.getTimestamp()));
			StatefulBeanToCsvBuilder<ValueHolder> csvBuilder = new StatefulBeanToCsvBuilder<>(writer);
			StatefulBeanToCsv<ValueHolder> beanWriter = csvBuilder.withSeparator(';')
					.withLineEnd(";" + System.lineSeparator()).withMappingStrategy(strategy).build();
			beanWriter.write(content);
			writer.close();
			LOG.info("Generated csv file.");
		} catch (CsvBadConverterException | SecurityException | IllegalArgumentException | IllegalAccessException
				| CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e) {
			LOG.error("Cannot generate csv file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void fillMockData() {
		radioInputDeviceMouse.setSelected(true);
		textfieldName.setText("Jon");
		textfieldAge.setText("25");
		textfieldRounds.setText("5");
		radioSexMale.setSelected(true);
		radioWritingDirectionLR.setSelected(true);
		radioWritingHandL.setSelected(true);
		radioMouseHandL.setSelected(true);
		radioTypeFirst.setSelected(true);
	}

	private void bindI18NText() {
		captionInputDevice.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("captionInputDevice"));
		labelInputDevice.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelInputDevice"));
		radioInputDeviceMouse.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioInputDeviceMouse"));
		radioInputDeviceTouch.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioInputDeviceTouch"));
		radioInputDevicePen.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioInputDevicePen"));
		labelDeviceType.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelDeviceType"));

		captionExperimentee.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("captionExperimentee"));
		labelName.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelName"));
		labelAge.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelAge"));
		labelSex.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelSex"));
		radioSexFemale.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioSexFemale"));
		radioSexMale.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioSexMale"));
		labelWritingDirection.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelWritingDirection"));
		radioWritingDirectionLR.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioWritingDirectionLR"));
		radioWritingDirectionRL.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioWritingDirectionRL"));
		labelWritingHand.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelWritingHand"));
		radioWritingHandL.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioWritingHandL"));
		radioWritingHandR.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioWritingHandR"));
		labelMouseHand.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelMouseHand"));
		radioMouseHandL.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioWritingHandL"));
		radioMouseHandR.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioWritingHandR"));
		labelWriting10Finger.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelWriting10Finger"));
		checkboxWriting10Finger.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("checkboxWriting10Finger"));
		labelComment.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelComment"));

		captionAttempts.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("captionAttempts"));
		labelType.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelType"));
		radioTypeFirst.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioTypeFirst"));
		radioTypeSecond.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioTypeSecond"));
		labelRounds.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("labelRounds"));

		buttonStart.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("buttonStart"));
	}

	private void bindDataToModel() {
		// input device
		model.setDeviceMouse(radioInputDeviceMouse.isSelected());
		model.setDeviceTouch(radioInputDeviceTouch.isSelected());
		model.setDevicePen(radioInputDevicePen.isSelected());
		model.setDeviceType(textfieldDeviceType.getText());

		// users
		model.setName(textfieldName.getText());
		model.setUserAge(Integer.parseInt(textfieldAge.getText()));
		if (radioSexMale.isSelected()) {
			model.setUserGender(Gender.MALE);
		} else if (radioSexFemale.isSelected()) {
			model.setUserGender(Gender.FEMALE);
		}

		if (radioWritingDirectionLR.isSelected()) {
			model.setUserWritingDirection(WritingDirection.LEFTTORIGHT);
		} else if (radioWritingDirectionRL.isSelected()) {
			model.setUserWritingDirection(WritingDirection.RIGHTTOLEFT);
		}

		if (radioWritingHandL.isSelected()) {
			model.setUserWritingHand(WritingHand.LEFT);
		} else if (radioWritingHandR.isSelected()) {
			model.setUserWritingHand(WritingHand.RIGHT);
		}
		
		if (radioMouseHandL.isSelected()) {
			model.setUserMouseHand(WritingHand.LEFT);
		} else if (radioMouseHandR.isSelected()) {
			model.setUserMouseHand(WritingHand.RIGHT);
		}

		model.setUserTenFingerSystem(checkboxWriting10Finger.isSelected());
		model.setUserComment(textfieldComment.getText());

		// experiment
		if (radioTypeFirst.isSelected()) {
			model.setExperimentType(ExperimentType.TEXT);
		} else if (radioTypeSecond.isSelected()) {
			model.setExperimentType(ExperimentType.ICON);
		}

		model.setExperimentRounds(Integer.parseInt(textfieldRounds.getText()));
		model.setExperimentAborted(true);

		// others
		model.setVersion(Constants.VERSION);
		try {
			model.setComputerName(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			LOG.error("Cannot determine computer name: " + e.getMessage());
		}
		model.setOperatingSystem(System.getProperty("os.name"));
		model.setTimestamp(new Date());
	}

	private void bindMockupData() {
		// input device
		model.setDeviceMouse(true);
		model.setDeviceTouch(false);
		model.setDevicePen(false);
		model.setDeviceType("Laptop");

		// users
		model.setName("Julian");
		// model.setName(textfieldName.getText());
		model.setUserAge(25);
		model.setUserGender(Gender.MALE);
		model.setUserWritingDirection(WritingDirection.LEFTTORIGHT);
		model.setUserWritingHand(WritingHand.RIGHT);
		model.setUserMouseHand(WritingHand.RIGHT);
		model.setUserTenFingerSystem(false);
		model.setUserComment("Nice comment");

		// experiment
		model.setExperimentType(ExperimentType.TEXT);
		model.setExperimentRounds(100);
		model.setExperimentAborted(true);

		// others
		model.setVersion("1.0.0");
		model.setComputerName("Zenbook");
		model.setOperatingSystem(System.getProperty("os.name"));
		model.setTimestamp(new Date());
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		LOG.info("Validating input...");
		boolean b = true;

		// Remove all styles to avoid duplicates
		radioInputDeviceMouse.getStyleClass().remove("error");
		radioInputDeviceTouch.getStyleClass().remove("error");
		radioInputDevicePen.getStyleClass().remove("error");

		textfieldName.getStyleClass().remove("error");
		textfieldAge.getStyleClass().remove("error");
		radioSexMale.getStyleClass().remove("error");
		radioSexFemale.getStyleClass().remove("error");
		radioWritingDirectionLR.getStyleClass().remove("error");
		radioWritingDirectionRL.getStyleClass().remove("error");
		radioWritingHandL.getStyleClass().remove("error");
		radioWritingHandR.getStyleClass().remove("error");
		radioMouseHandL.getStyleClass().remove("error");
		radioMouseHandR.getStyleClass().remove("error");

		radioTypeFirst.getStyleClass().remove("error");
		radioTypeSecond.getStyleClass().remove("error");
		textfieldRounds.getStyleClass().remove("error");

		if (!radioInputDeviceMouse.isSelected() && !radioInputDeviceTouch.isSelected()
				&& !radioInputDeviceTouch.isSelected()) {
			radioInputDeviceMouse.getStyleClass().add("error");
			radioInputDeviceTouch.getStyleClass().add("error");
			radioInputDevicePen.getStyleClass().add("error");
			b = false;
		}

		if (textfieldName.getText().length() <= 0) {
			textfieldName.getStyleClass().add("error");
			b = false;
		}

		if (textfieldAge.getText().length() <= 0 || textfieldAge.getText().length() > 2
				|| !textfieldAge.getText().matches("[0-9]+")) {
			textfieldAge.getStyleClass().add("error");
			b = false;
		}

		if (!radioSexMale.isSelected() && !radioSexFemale.isSelected()) {
			radioSexMale.getStyleClass().add("error");
			radioSexFemale.getStyleClass().add("error");
			b = false;
		}

		if (!radioWritingDirectionLR.isSelected() && !radioWritingDirectionRL.isSelected()) {
			radioWritingDirectionLR.getStyleClass().add("error");
			radioWritingDirectionRL.getStyleClass().add("error");
			b = false;
		}

		if (!radioWritingHandL.isSelected() && !radioWritingHandR.isSelected()) {
			radioWritingHandL.getStyleClass().add("error");
			radioWritingHandR.getStyleClass().add("error");
			b = false;
		}
		
		if (!radioMouseHandL.isSelected() && !radioMouseHandR.isSelected()) {
			radioMouseHandL.getStyleClass().add("error");
			radioMouseHandR.getStyleClass().add("error");
			b = false;
		}

		if (!radioTypeFirst.isSelected() && !radioTypeSecond.isSelected()) {
			radioTypeFirst.getStyleClass().add("error");
			radioTypeSecond.getStyleClass().add("error");
			b = false;
		}

		if (textfieldRounds.getText().length() <= 0 || !textfieldRounds.getText().matches("[0-9]+")) {
			textfieldRounds.getStyleClass().add("error");
			b = false;
		}

		return b;
	}
	
	public static SettingsModel getModel() {
		return model;
	}

}
