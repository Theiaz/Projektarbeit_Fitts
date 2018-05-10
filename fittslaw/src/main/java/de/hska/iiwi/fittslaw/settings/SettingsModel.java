package de.hska.iiwi.fittslaw.settings;

import java.util.Date;

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

	@CsvBindByPosition(position = 0, required = true)
	private boolean deviceMouse;

	@CsvBindByPosition(position = 1, required = true)
	private boolean deviceTouch;

	@CsvBindByPosition(position = 2, required = true)
	private boolean devicePen;

	@CsvBindByPosition(position = 3, required = true)
	private String deviceType;

	@CsvBindByPosition(position = 4, required = true)
	private String userName;

	@CsvBindByPosition(position = 5, required = true)
	private int userAge;

	@CsvBindByPosition(position = 6, required = true)
	private Gender userGender;

	@CsvBindByPosition(position = 7, required = true)
	private WritingHand userWritingHand;

	@CsvBindByPosition(position = 8, required = true)
	private WritingDirection userWritingDirection;

	@CsvBindByPosition(position = 9)
	private boolean userTenFingerSystem;

	@CsvBindByPosition(position = 10)
	private String userComment;

	@CsvBindByPosition(position = 11, required = true)
	private ExperimentType experimentType;

	@CsvBindByPosition(position = 12, required = true)
	private int experimentRounds;

	@CsvBindByPosition(position = 13)
	private boolean experimentIcons;

	@CsvBindByPosition(position = 14)
	private boolean experimentAborted;

	@CsvBindByPosition(position = 15)
	private String version;

	@CsvBindByPosition(position = 16, required = true)
	private String computerName;

	@CsvBindByPosition(position = 17, required = true)
	private String operatingSystem;

	@CsvBindByPosition(position = 18, required = true)
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