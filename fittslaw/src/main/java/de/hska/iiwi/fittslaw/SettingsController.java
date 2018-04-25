package de.hska.iiwi.fittslaw;

import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import de.hska.iiwi.fittslaw.SettingsModel.ExperimentType;
import de.hska.iiwi.fittslaw.SettingsModel.Gender;
import de.hska.iiwi.fittslaw.SettingsModel.WritingDirection;
import de.hska.iiwi.fittslaw.SettingsModel.WritingHand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SettingsController {
	
	private static final Logger LOG = Logger.getRootLogger();
	
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
	private Label labelIcons; // TODO labels nicht notwendig?! nur textinputs?
								// oder labelname als erste spalte fuer titel
	@FXML
	private Button buttonStart;
	@FXML
	private Label labelType;

	private final SettingsModel model = new SettingsModel();

	@FXML
	protected void saveButtonClicked(ActionEvent event) {
		System.out.println("click");
//		bindDataToModel(); TODO 
		bindMockupData();

		FileWriter writer;
		try {
			writer = new FileWriter("./settings6.csv");
			
			final CustomMappingStrategy<SettingsModel> mappingStrategy = new CustomMappingStrategy<>();
			mappingStrategy.setType(SettingsModel.class);

			StatefulBeanToCsvBuilder<SettingsModel> beanToCsv = new StatefulBeanToCsvBuilder<SettingsModel>(writer);
//			 beanToCsv.withLineEnd("\t"); //settings1
			// beanToCsv.withSeparator(";".charAt(0)); //settings2
			// beanToCsv.withEscapechar(";".charAt(0)); //settings3

			StatefulBeanToCsv<SettingsModel> beanWriter = beanToCsv.withMappingStrategy(mappingStrategy).build();
			beanWriter.write(model);
			writer.close();

//			String[] strings = new String[10];
//			for (int i = 0; i < 10; i++) {
//				strings[i] = "Testing";
//			}
//			String[] wrap = new String[1]; // probably saving on GC
//			
//			CSVWriter writer2 = new CSVWriter(new FileWriter("testing.csv"), '\t');
//			for (String s : strings) {
//				wrap[0] = s;
//				writer2.writeNext(wrap);
//			}
//			 writer2.close();

		} catch (Exception e) {
			// TODO log
		}

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
		model.setUserGender(Gender.MALE); //TODO
		model.setUserWritingDirection(WritingDirection.LEFTTORIGHT); //TODO
		model.setUserWritingHand(WritingHand.RIGHT); //TODO
		model.setUserTenFingerSystem(checkboxWriting10Finger.isSelected());
		model.setUserComment(textfieldComment.getText());
		
		// experiment
		model.setExperimentType(ExperimentType.Type1); //TODO
		model.setExperimentRounds(Integer.parseInt(textfieldRounds.getText()));
		model.setExperimentIcons(checkboxIcons.isSelected());
		model.setExperimentAborted(false); //TODO
		
		// others
		model.setVersion("1.0.0"); //TODO
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

}
