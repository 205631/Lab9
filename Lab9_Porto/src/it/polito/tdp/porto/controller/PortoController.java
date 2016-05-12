package it.polito.tdp.porto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Creator;
import it.polito.tdp.porto.model.PortoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	PortoModel model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Creator> cmbAutore1;

    @FXML
    private ComboBox<Creator> cmbAutore2;

    @FXML
    private Button btnCoautori;

    @FXML
    private Button btnCluster;

    @FXML
    private Button btnArticoli;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    public void setModel(PortoModel m){
    	model=m;
    	//aggiungere alle cmb gli autori
    	model.generaGrafo();
    	cmbAutore1.getItems().addAll(model.getCreators());
    	cmbAutore2.getItems().addAll(model.getCreators());
    	
    }
    @FXML
    void doReset(ActionEvent event) {
    	txtResult.setText("");
    	cmbAutore1.setValue(null);
    	cmbAutore2.setValue(null);
    }

    @FXML
    void doVisArticoli(ActionEvent event) {

    }

    @FXML
    void doVisCluster(ActionEvent event) {

    	StringBuilder s=model.getCluster();
    	txtResult.setText(s.toString());
    	
    }

    @FXML
    void doVisCoautori(ActionEvent event) {
    	
    	txtResult.setText("");
    	
    	if(cmbAutore1.getValue()!=null){
	    	StringBuilder s=model.getCoautori(cmbAutore1.getValue());
	    	txtResult.setText(s.toString());
    	}
    	else
    		txtResult.setText("ERRORE: inserire un autore!");
    }

    @FXML
    void initialize() {
        assert cmbAutore1 != null : "fx:id=\"cmbAutore1\" was not injected: check your FXML file 'Porto.fxml'.";
        assert cmbAutore2 != null : "fx:id=\"cmbAutore2\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnCoautori != null : "fx:id=\"btnCoautori\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnCluster != null : "fx:id=\"btnCluster\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnArticoli != null : "fx:id=\"btnArticoli\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Porto.fxml'.";

    }
}
