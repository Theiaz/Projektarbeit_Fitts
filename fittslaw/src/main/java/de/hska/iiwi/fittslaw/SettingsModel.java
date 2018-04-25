package de.hska.iiwi.fittslaw;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class SettingsModel {

	public enum Gender {
		MALE, FEMALE
	}

	public enum WritingHand {
		LEFT, RIGHT
	}

	public enum WritingDirection {
		LEFTTORIGHT, RIGHTTOLEFT
	}

	public enum ExperimentType {
		Type1, Type2
	}

	@CsvBindByName(column = "DeviceMouse", required = true)
	@CsvBindByPosition(position = 0)
	private boolean deviceMouse;

	@CsvBindByName(column = "DeviceTouch", required = true)
	@CsvBindByPosition(position = 1)
	private boolean deviceTouch;

	@CsvBindByName(column = "DevicePen", required = true)
	@CsvBindByPosition(position = 2)
	private boolean devicePen;

	@CsvBindByName(column = "DeviceType")
	@CsvBindByPosition(position = 3)
	private String deviceType;

	@CsvBindByName(column = "UserName", required = true)
	@CsvBindByPosition(position = 4)
	private String userName;

	@CsvBindByName(column = "UserAge", required = true)
	@CsvBindByPosition(position = 5)
	private int userAge;

	@CsvBindByName(column = "UserGender", required = true)
	@CsvBindByPosition(position = 6)
	private Gender userGender;

	@CsvBindByName(column = "UserWritingHand", required = true)
	@CsvBindByPosition(position = 7)
	private WritingHand userWritingHand;

	@CsvBindByName(column = "UserWritingDirection", required = true)
	@CsvBindByPosition(position = 8)
	private WritingDirection userWritingDirection;

	@CsvBindByName(column = "UserTenFingerSystem")
	@CsvBindByPosition(position = 9)
	private boolean userTenFingerSystem;

	@CsvBindByName(column = "UserComment")
	@CsvBindByPosition(position = 10)
	private String userComment;

	@CsvBindByName(column = "ExperimentType", required = true)
	@CsvBindByPosition(position = 11)
	private ExperimentType experimentType;

	@CsvBindByName(column = "ExperimentRounds", required = true)
	@CsvBindByPosition(position = 12)
	private int experimentRounds;

	@CsvBindByName(column = "ExperimentIcons")
	@CsvBindByPosition(position = 13)
	private boolean experimentIcons;

	@CsvBindByName(column = "ExperimentAborted")
	@CsvBindByPosition(position = 14)
	private boolean experimentAborted;

	@CsvBindByName(column = "Version")
	@CsvBindByPosition(position = 15)
	private String version;

	@CsvBindByName(column = "ComputerName", required = true)
	@CsvBindByPosition(position = 16)
	private String computerName;

	@CsvBindByName(column = "OS", required = true)
	@CsvBindByPosition(position = 17)
	private String operatingSystem;

	@CsvBindByName(column = "Timestamp", required = true)
	@CsvBindByPosition(position = 18)
	private Date timestamp;

	public boolean isDeviceMouse() {
		return deviceMouse;
	}

	public void setDeviceMouse(boolean deviceMouse) {
		this.deviceMouse = deviceMouse;
	}

	public boolean isDeviceTouch() {
		return deviceTouch;
	}

	public void setDeviceTouch(boolean deviceTouch) {
		this.deviceTouch = deviceTouch;
	}

	public boolean isDevicePen() {
		return devicePen;
	}

	public void setDevicePen(boolean devicePen) {
		this.devicePen = devicePen;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getName() {
		return userName;
	}

	public void setName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public Gender getUserGender() {
		return userGender;
	}

	public void setUserGender(Gender userGender) {
		this.userGender = userGender;
	}

	public WritingHand getUserWritingHand() {
		return userWritingHand;
	}

	public void setUserWritingHand(WritingHand userWritingHand) {
		this.userWritingHand = userWritingHand;
	}

	public WritingDirection getUserWritingDirection() {
		return userWritingDirection;
	}

	public void setUserWritingDirection(WritingDirection userWritingDirection) {
		this.userWritingDirection = userWritingDirection;
	}

	public boolean isUserTenFingerSystem() {
		return userTenFingerSystem;
	}

	public void setUserTenFingerSystem(boolean userTenFingerSystem) {
		this.userTenFingerSystem = userTenFingerSystem;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public ExperimentType getExperimentType() {
		return experimentType;
	}

	public void setExperimentType(ExperimentType experimentType) {
		this.experimentType = experimentType;
	}

	public int getExperimentRounds() {
		return experimentRounds;
	}

	public void setExperimentRounds(int experimentRounds) {
		this.experimentRounds = experimentRounds;
	}

	public boolean isExperimentIcons() {
		return experimentIcons;
	}

	public void setExperimentIcons(boolean experimentIcons) {
		this.experimentIcons = experimentIcons;
	}

	public boolean isExperimentAborted() {
		return experimentAborted;
	}

	public void setExperimentAborted(boolean experimentAborted) {
		this.experimentAborted = experimentAborted;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}