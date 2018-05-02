package de.hska.iiwi.fittslaw;

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

import de.hska.iiwi.fittslaw.SettingsModel.ExperimentType;
import de.hska.iiwi.fittslaw.SettingsModel.Gender;
import de.hska.iiwi.fittslaw.SettingsModel.WritingDirection;
import de.hska.iiwi.fittslaw.SettingsModel.WritingHand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SettingsController implements Initializable {

	private static final Logger LOG = Logger.getRootLogger();
	
	private static final ObservableResourcesSingleton OBSERVABLE_RESOURCES = ObservableResourcesSingleton.getInstance();

	private final SettingsModel model = new SettingsModel();

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
	@FXML
	private MenuItem menuItemClose;
	@FXML
	private RadioMenuItem menuItemEnglish;
	@FXML
	private RadioMenuItem menuItemGerman;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindI18NText();

		menuItemEnglish.setSelected(true);

		menuItemEnglish.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					LOG.info("Selected english language");
					OBSERVABLE_RESOURCES.setResources(ResourceBundle.getBundle(Constants.I18N_SETTINGS_EN));
				}
			}
		});

		menuItemGerman.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				if (isNowSelected) {
					LOG.info("Selected german language");
					OBSERVABLE_RESOURCES.setResources(ResourceBundle.getBundle(Constants.I18N_SETTINGS_DE));
				}
			}
		});
	}

	@FXML
	protected void saveButtonClicked(ActionEvent event) {
		if (isInputValid()) {
			LOG.info("Input successfully validated!");
			bindDataToModel();
			// bindMockupData();

			try {
				List<ValueHolder> content = new ArrayList<ValueHolder>();
				for (Field field : model.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if (!Modifier.isPrivate(field.getModifiers())) {
						continue;
					}
					String s1 = field.getName().substring(0, 1).toUpperCase();
					String nameCapitalized = s1 + field.getName().substring(1);
					content.add(new ValueHolder(nameCapitalized, String.valueOf(field.get(model))));
				}

				final ColumnPositionMappingStrategy<ValueHolder> strategy = new ColumnPositionMappingStrategy<>();
				strategy.setType(ValueHolder.class);
				strategy.setColumnMapping("key", "value");

				LOG.info("Start generating csv file...");
				FileWriter writer = new FileWriter("./settings.csv");
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
	}
	
	private void bindI18NText() {
		OBSERVABLE_RESOURCES.setResources(ResourceBundle.getBundle(Constants.I18N_SETTINGS_EN));
		menuItemClose.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("menuItemClose"));
		radioSexFemale.textProperty().bind(OBSERVABLE_RESOURCES.getStringBinding("radioSexFemale"));
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

		model.setUserTenFingerSystem(checkboxWriting10Finger.isSelected());
		model.setUserComment(textfieldComment.getText());

		// experiment
		if (radioTypeFirst.isSelected()) {
			model.setExperimentType(ExperimentType.Type1);
		} else if (radioTypeSecond.isSelected()) {
			model.setExperimentType(ExperimentType.Type2);
		}

		model.setExperimentType(ExperimentType.Type1);

		model.setExperimentRounds(Integer.parseInt(textfieldRounds.getText()));
		model.setExperimentIcons(checkboxIcons.isSelected());
		model.setExperimentAborted(false); // TODO

		// others
		model.setVersion("1.0.0"); // TODO
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
		model.setUserTenFingerSystem(false);
		model.setUserComment("Nice comment");

		// experiment
		model.setExperimentType(ExperimentType.Type1);
		model.setExperimentRounds(100);
		model.setExperimentIcons(false);
		model.setExperimentAborted(false);

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

}
