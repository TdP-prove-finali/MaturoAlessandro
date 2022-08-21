/**
 * Sample Skeleton for 'Market.fxml' Controller Class
 */

package it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model.Footballer;
import it.polito.tdp.ToolPerLeSimulazioniDiMercatoDiUnaRosaCalcistica.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    
    @FXML // fx:id="assex"
    private CategoryAxis assex; // Value injected by FXMLLoader

    @FXML // fx:id="assey"
    private NumberAxis assey; // Value injected by FXMLLoader

    @FXML // fx:id="grafico"
    private BarChart<String, Number> grafico; // Value injected by FXMLLoader

    @FXML
    void doRecursion(ActionEvent event) {

    	txtResult.clear();

    	List<Footballer> selected = tabPlayers.getSelectionModel().getSelectedItems();
    	
    	if(selected.size()!=0) {
        	if(selected.size()<=4) {
        		
            	/* Da qua faccio partire la ricorsione */
            	List<Footballer> result = model.init(selected);
            	
            	if(result.size()>0) {
                	txtResult.appendText("La soluzione migliore trovata a partire dai giocatori selezionati per la vendita è:\n");
                	for(Footballer fi: result) {
                		txtResult.appendText(fi.getName()+" ("+fi.getBest_pos()+")\n");
                	}
            	} else {
            		txtResult.appendText("Non sono presenti soluzioni che migliorano gli indici della squadra.");
            	}
        	} else {
        		txtResult.appendText("Perfavore selezionare un massimo di 4 giocatori.");
        	}
    	} else {
    		txtResult.appendText("Perfavore selezionare almeno un giocatore.");
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
        assert grafico != null : "fx:id=\"grafico\" was not injected: check your FXML file 'Market.fxml'.";
        assert assex != null : "fx:id=\"assex\" was not injected: check your FXML file 'Market.fxml'.";
        assert assey != null : "fx:id=\"assey\" was not injected: check your FXML file 'Market.fxml'.";

		/* Per la tabella */
        this.colName.setCellValueFactory(new PropertyValueFactory<Footballer, String>("name"));
        this.colRole.setCellValueFactory(new PropertyValueFactory<Footballer, String>("best_pos"));
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
    	
    	/* Imposto la label con i valori medi */
    	lblStats.setText(model.averageStatistics(model.getSelectedTeam()));
		
    	 /* Imposto il grafico */
        XYChart.Series<String, Number> set1= new XYChart.Series<>();
        
        set1.getData().add(new XYChart.Data<String, Number>("TEC", model.averageTec(model.getSelectedTeam())));
        set1.getData().add(new XYChart.Data<String, Number>("FOR", model.averageStr(model.getSelectedTeam())));
        set1.getData().add(new XYChart.Data<String, Number>("MAR", model.averageMar(model.getSelectedTeam())));
        set1.getData().add(new XYChart.Data<String, Number>("POS", model.averagePos(model.getSelectedTeam())));
        set1.getData().add(new XYChart.Data<String, Number>("PAS", model.averagePas(model.getSelectedTeam())));

        
        grafico.getData().add(set1);
	}

}