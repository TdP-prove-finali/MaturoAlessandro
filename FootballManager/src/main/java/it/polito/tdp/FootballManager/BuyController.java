/**
 * Sample Skeleton for 'Buy.fxml' Controller Class
 */

package it.polito.tdp.FootballManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.FootballManager.model.Footballer;
import it.polito.tdp.FootballManager.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BuyController {

	private Model model;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnHome"
    private Button btnHome; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulate"
    private Button btnSimulate; // Value injected by FXMLLoader

    @FXML // fx:id="cmbIndexes"
    private ChoiceBox<String> cmbIndexes; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtValue"
    private TextField txtValue; // Value injected by FXMLLoader

    @FXML // fx:id="txtWage"
    private TextField txtWage; // Value injected by FXMLLoader  

    public BuyController() {
		super();
	}

	@FXML
    void doBuy(ActionEvent event) {

    	// Dovrai mettere tipo selectedTeam in model e prenderlo
    	String team = "";
    	
    	txtResult.clear();
    	
    	//OK CI ENTRA ENTRA
    	String index = cmbIndexes.getValue();
    	
    	if(index!=null) {
    		
    		String wageStr = txtWage.getText();
    		String valueStr = txtValue.getText();
    		
    		try {
    			
    			int wage = Integer.parseInt(wageStr);
    			int value = Integer.parseInt(valueStr);
    			
    	    	List<Footballer> result = model.getFootballersMaxSalaryAndMaxValueAndBetterIndex(index, model.getSelectedTeam(), wage, value);
    	    	
    	    	txtResult.appendText("I 5 migliori calciatori che migliorano l'indice selezionato con salario e costo all'interno dei valori inseriti sono:\n");
    	    	for(Footballer fi: result) {
    	    		txtResult.appendText(fi.getName()+"\n");
    	    	}
    			
    		} catch (NumberFormatException e) {
    			txtResult.appendText("Perfavore inserire valori numerici per stipendio e valore massimi!");
    		}
    		
    	} else {
    		txtResult.appendText("Perfavore selezionare un indice!");
    	}
    	
    }

    @FXML
    void goBackToHomePage(ActionEvent event) {

    	try {
			
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
	    	root = loader.load();
	        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

	   
	        Scene scene = new Scene(root);
	        HomeController controller = loader.getController();
	        controller.setModel(model);
	        	        
	        scene.getStylesheets().add("/styles/Styles.css");
	        scene.getRoot().setStyle("-fx-font-family: 'serif'");

	        stage.setScene(scene);
	        stage.show();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnHome != null : "fx:id=\"btnHome\" was not injected: check your FXML file 'Buy.fxml'.";
        assert btnSimulate != null : "fx:id=\"btnSimulate\" was not injected: check your FXML file 'Buy.fxml'.";
        assert cmbIndexes != null : "fx:id=\"cmbIndexes\" was not injected: check your FXML file 'Buy.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Buy.fxml'.";
        assert txtValue != null : "fx:id=\"txtValue\" was not injected: check your FXML file 'Buy.fxml'.";
        assert txtWage != null : "fx:id=\"txtWage\" was not injected: check your FXML file 'Buy.fxml'.";
        
    }

	public void setModel(Model model) {
		this.model = model;		
		
        /* Carico indici nella combo box */
    	this.cmbIndexes.getItems().addAll(model.getAllIndexes());
	}

}
