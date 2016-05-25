package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.db.Statistiche;
import it.polito.tdp.rivers.model.River;
import it.polito.tdp.rivers.model.Simulatore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RiversController {
	private Simulatore simulatore;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtFMed;

    @FXML
    private Button btnSimula;

    @FXML
    private TextField txtK;

    public void riempi(){
    	//riempire campi dell'interfaccia
    	RiversDAO r= new RiversDAO();
    	Statistiche s= r.statistiche(boxRiver.getValue());
    	this.txtStartDate.setText(s.getPrima().toString());
    	this.txtEndDate.setText(s.getUltima().toString());
    	this.txtFMed.setText(s.getFmed()+"");
    	this.txtNumMeasurements.setText(s.getMisurazioni()+"");
    	
    }
    
    public void simula(){
    	//simulazione
    	Double fmed= Double.parseDouble(this.txtFMed.getText());
    	int k= Integer.parseInt(this.txtK.getText());
    	Double Q = 30*fmed*k;
    	Double c=Q/2;
    	double min=0.8*fmed;
    	simulatore.simula(Q, c, min,boxRiver.getValue());
    	this.txtResult.setText("Giorni senza erogazione: "+ simulatore.getMancati()+"\n"+"Occupazione media: "+simulatore.getcMedia());
    }
    public void setSimulatore(Simulatore s){
    	this.simulatore=s;
    	RiversDAO r= new RiversDAO();
    	boxRiver.getItems().addAll(r.getAllRivers());
    }
    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Rivers.fxml'.";

    }
}
