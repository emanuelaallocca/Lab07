/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.*;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	try {
    		int maxOre = Integer.parseInt(this.txtHours.getText());
    		int maxAnni =Integer.parseInt(this.txtYears.getText());
    		Nerc n = this.cmbNerc.getValue();
    		
    		List<PowerOutages> blackout = model.trovaEventi(maxOre, maxAnni, n);
    		
    		if(blackout == null) {
    			txtResult.appendText("Non ho trovato soluzioni\n");
    			return ;
    		}
    		txtResult.setStyle("-fx-font-family: monospace");
    		StringBuilder sb = new StringBuilder();
    		for (PowerOutages p: blackout) {
   
    			sb.append(String.format("%-8s ", p.getId()));
        	    sb.append(String.format("%-4d", p.getNerc_id()));
        	    sb.append(String.format("%-4ds ", p.getInizio()));
        	    sb.append(String.format("%-4d\n", p.getFine()));
    		}
    		txtResult.appendText(sb.toString());
    				

    	} 
    	catch (NumberFormatException e) {
    		txtResult.setText("Inserire un numero di crediti > 0");
    	}
    	catch (ArithmeticException e) {
    		txtResult.setText("Inserire un numero di crediti > 6 e <167");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbNerc.getItems().addAll(model.getNercList());
    }
}
