/**
 * Sample Skeleton for 'Home.fxml' Controller Class
 */

package it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model.Club;
import it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model.Footballer;
import it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;

public class HomeController {

	private Model model;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxChampionship"
    private ChoiceBox<String> boxChampionship; // Value injected by FXMLLoader

    @FXML // fx:id="boxTeam"
    private ChoiceBox<Club> boxTeam; // Value injected by FXMLLoader

    @FXML // fx:id="btnBuyPage"
    private Button btnBuyPage; // Value injected by FXMLLoader

    @FXML // fx:id="btnChampionship"
    private Button btnChampionship; // Value injected by FXMLLoader

    @FXML // fx:id="btnMarketPage"
    private Button btnMarketPage; // Value injected by FXMLLoader

    @FXML // fx:id="btnTeam"
    private Button btnTeam; // Value injected by FXMLLoader
    
    @FXML // fx:id="lblError"
    private Label lblError; // Value injected by FXMLLoader

    @FXML
    void doChargeTeam(ActionEvent event) {

    	String championship = null;
    	championship = boxChampionship.getValue();
    	
    	if(championship!=null) {
        	boxTeam.getItems().clear();
        	for(Club ci: model.getAllClubsObjectByChampionship(championship)) {
            	boxTeam.getItems().add(ci);
        	}

        	lblError.setText(null);
            this.btnTeam.setDisable(false);
    	} else {
    		lblError.setText("Perfavore selezionare un campionato!");
    	}

    	
    }
    
    @FXML
    void setTeam(ActionEvent event) {
    	
    	Club team = null;
    	team = boxTeam.getValue();
    	
    	if(team!=null) {

    		model.setSelectedTeam(team);
    		        	
        	/* Attivo i bottoni */
        	lblError.setText(null);
            this.btnBuyPage.setDisable(false);
            this.btnMarketPage.setDisable(false);

    	} else {
    		lblError.setText("Perfavore selezionare una squadra!");
    	}
    	
    }


    @FXML
    void goToBuyPage(ActionEvent event) {

		try {
			
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Buy.fxml"));
	    	root = loader.load();
	        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

	   
	        Scene scene = new Scene(root);
	        BuyController controller = loader.getController();
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

    @FXML
    void goToMarketPage(ActionEvent event) {
    	
    	try {
			
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Market.fxml"));
	    	root = loader.load();
	        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

	   
	        Scene scene = new Scene(root);
	        MarketController controller = loader.getController();
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
        assert boxChampionship != null : "fx:id=\"boxChampionship\" was not injected: check your FXML file 'Home.fxml'.";
        assert boxTeam != null : "fx:id=\"boxTeam\" was not injected: check your FXML file 'Home.fxml'.";
        assert btnBuyPage != null : "fx:id=\"btnBuyPage\" was not injected: check your FXML file 'Home.fxml'.";
        assert btnChampionship != null : "fx:id=\"btnChampionship\" was not injected: check your FXML file 'Home.fxml'.";
        assert btnMarketPage != null : "fx:id=\"btnMarketPage\" was not injected: check your FXML file 'Home.fxml'.";
        assert btnTeam != null : "fx:id=\"btnTeam\" was not injected: check your FXML file 'Home.fxml'.";
        assert lblError != null : "fx:id=\"lblError\" was not injected: check your FXML file 'Home.fxml'.";

        /* Disattivo i bottoni */
        this.btnTeam.setDisable(true);
        this.btnBuyPage.setDisable(true);
        this.btnMarketPage.setDisable(true);

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	/* Aggiungo i campionati */
    	this.boxChampionship.getItems().addAll(model.getAllChampionships());   	
    }

}
