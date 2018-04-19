package de.hska.iiwi.fittslaw;

import java.net.URL; 
import java.util.ResourceBundle; 

import javafx.fxml.FXML; 
import javafx.fxml.Initializable; 
import javafx.scene.control.Label; 

public class SettingsController implements Initializable { 

//    @FXML 
//    private Label label; 

    private SettingsModel model; 

    @Override 
    public void initialize(URL location, ResourceBundle resources) { 
        model = new SettingsModel(); 
    } 

//    public void manageButton() { 
//        label.setText(model.getHello()); 
//    } 
} 
