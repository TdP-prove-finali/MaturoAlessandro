/**
 * Sample Skeleton for 'Market.fxml' Controller Class
 */

package it.polito.tdp.FootballManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.FootballManager.model.Footballer;
import it.polito.tdp.FootballManager.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MarketController {

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

    @FXML // fx:id="btnRecursion"
    private Button btnRecursion; // Value injected by FXMLLoader

    @FXML // fx:id="colName"
    private TableColumn<Footballer, String> colName; // Value injected by FXMLLoader

    @FXML // fx:id="colNationality"
    private TableColumn<Footballer, String> colNationality; // Value injected by FXMLLoader

    @FXML // fx:id="colRole"
    private TableColumn<Footballer, String> colRole; // Value injected by FXMLLoader

    @FXML // fx:id="colValue"
    private TableColumn<Footballer, Double> colValue; // Value injected by FXMLLoader

    @FXML // fx:id="colWage"
    private TableColumn<Footballer, Integer> colWage; // Value injected by FXMLLoader

    @FXML // fx:id="colYear"
    private TableColumn<Footballer, Integer> colYear; // Value injected by FXMLLoader

    @FXML // fx:id="lblStats"
    private Label lblStats; // Value injected by FXMLLoader

    @FXML // fx:id="tabPlayers"
    private TableView<Footballer> tabPlayers; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doRecursion(ActionEvent event) {

    	txtResult.clear();

    	List<Footballer> selected = tabPlayers.getSelectionModel().getSelectedItems();
    	
    	/*for(Footballer fi: f) {
        	txtResult.appendText(fi.toString());
        	txtResult.appendText("\n");
    	}
    	OK LI PRENDE
    	*/
    	
    	/* Da qua faccio partire la ricorsione */
    	List<Footballer> result = model.init(selected);
    	    	
    	txtResult.appendText("La soluzione migliore trovata a partire dai giocatori selezionati per la vendita è:\n");
    	for(Footballer fi: result) {
    		txtResult.appendText(fi.getName()+"\n");
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
    	
    	/* Parte copiata da SceneBuilder */
        assert btnHome != null : "fx:id=\"btnHome\" was not injected: check your FXML file 'Market.fxml'.";
        assert btnRecursion != null : "fx:id=\"btnRecursion\" was not injected: check your FXML file 'Market.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'Market.fxml'.";
        assert colRole != null : "fx:id=\"colRole\" was not injected: check your FXML file 'Market.fxml'.";
        assert colValue != null : "fx:id=\"colValue\" was not injected: check your FXML file 'Market.fxml'.";
        assert colWage != null : "fx:id=\"colWage\" was not injected: check your FXML file 'Market.fxml'.";
        assert colYear != null : "fx:id=\"colYear\" was not injected: check your FXML file 'Market.fxml'.";
        assert lblStats != null : "fx:id=\"lblStats\" was not injected: check your FXML file 'Market.fxml'.";
        assert tabPlayers != null : "fx:id=\"tabPlayers\" was not injected: check your FXML file 'Market.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Market.fxml'.";

		/* Per la tabella */
        this.colName.setCellValueFactory(new PropertyValueFactory<Footballer, String>("name"));
        this.colRole.setCellValueFactory(new PropertyValueFactory<Footballer, String>("best_role"));
        this.colYear.setCellValueFactory(new PropertyValueFactory<Footballer, Integer>("age"));
        this.colValue.setCellValueFactory(new PropertyValueFactory<Footballer, Double>("value"));
        this.colWage.setCellValueFactory(new PropertyValueFactory<Footballer, Integer>("wage"));
        
        /* Per permettere la selezione multipla */
        tabPlayers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

	public void setModel(Model model) {
		this.model = model;
		
		/* Carico la tabella */
    	List<Footballer> footballers = model.getFootballerByTeam(model.getSelectedTeam()); 	
    	tabPlayers.setItems(FXCollections.observableArrayList(footballers));
		
	}

}