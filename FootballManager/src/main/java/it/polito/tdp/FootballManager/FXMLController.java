/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.FootballManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.FootballManager.model.Club;
import it.polito.tdp.FootballManager.model.Footballer;
import it.polito.tdp.FootballManager.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
/*import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;*/
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxChampionship"
    private ChoiceBox<String> boxChampionship; // Value injected by FXMLLoader

    @FXML // fx:id="boxTeam"
    private ChoiceBox<String> boxTeam; // Value injected by FXMLLoader
    
    @FXML // fx:id="boxIndexes"
    private ChoiceBox<String> boxIndexes; // Value injected by FXMLLoader

    @FXML // fx:id="btnPt1"
    private Button btnPt1; // Value injected by FXMLLoader
    
    @FXML // fx:id="btnChampionship"
    private Button btnChampionship; // Value injected by FXMLLoader

    @FXML // fx:id="btnTeam"
    private Button btnTeam; // Value injected by FXMLLoader
    
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

    @FXML // fx:id="tabPlayers"
    private TableView<Footballer> tabPlayers; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtMaxValue"
    private TextField txtMaxValue; // Value injected by FXMLLoader

    @FXML // fx:id="txtMaxWage"
    private TextField txtMaxWage; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtDate"
    private TextArea txtDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML // fx:id="lblStats"
    private Label lblStats; // Value injected by FXMLLoader
    
   /* @FXML // fx:id="chartStats"
    private BarChart<?, ?> chartStats; // Value injected by FXMLLoader
    
    @FXML // fx:id="x"
    private CategoryAxis x; // Value injected by FXMLLoader

    @FXML // fx:id="y"
    private NumberAxis y; // Value injected by FXMLLoader
    */
    

    @FXML
    void doChargeTeam(ActionEvent event) {
    	
    	//FAI I CONTROLLI
    	
    	String championship = boxChampionship.getValue();
    	
    	boxTeam.getItems().clear();
    	boxTeam.getItems().addAll(model.getAllClubsByChampionship(championship));
    }


    @FXML
    void doTable(ActionEvent event) {
    	
    	//FAI I CONTROLLI
    	
    	String team = boxTeam.getValue();
    	
    	List<Footballer> footballers = model.getFootballerByTeam(team);
    	
    	tabPlayers.setItems(FXCollections.observableArrayList(footballers));
    	
    	//INSERIMENTO NEL txtDate
    	
    	//txtDate.appendText(model.averageStatistics(team));
    	
    	//INSERIMENTO NELLA LABEL
    	this.lblStats.setText(model.averageStatistics(team));
    	
    	//CREO GRAFICO
    	/*XYChart.Series set1 = new XYChart.Series<>();
    	
    	set1.getData().add(new XYChart.Data("Marking", model.averageMar(team)));
    	set1.getData().add(new XYChart.Data("Positioning", model.averagePos(team)));
    	set1.getData().add(new XYChart.Data("Passing", model.averagePas(team)));
    	set1.getData().add(new XYChart.Data("Strenght", model.averageStr(team)));
    	set1.getData().add(new XYChart.Data("Technique", model.averageTec(team)));
	
    	this.chartStats.getData().addAll(set1);*/
    	
    	
    }
    
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
    	
    	List<Footballer> result = model.init(selected);
    	    	
    	txtResult.appendText("La soluzione migliore trovata a partire dai giocatori selezionati per la vendita è:\n");
    	for(Footballer fi: result) {
    		txtResult.appendText(fi.getName()+"\n");
    	}
    	
    }
    
    @FXML
    void doPt1(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	//OK CI ENTRA ENTRA
    	
    	List<Footballer> result = model.getFootballersMaxSalaryAndMaxValueAndBetterIndex(boxIndexes.getValue(), boxTeam.getValue(), Integer.parseInt(this.txtMaxWage.getText()), Integer.parseInt(this.txtMaxValue.getText()));
    	txtResult.appendText("I calciatori che migliorano l'indice selezionato con salarioe costo che stanno all'interno dei valori inseriti sono:\n");
    	
    	for(Footballer fi: result) {
    		txtResult.appendText(fi.getName()+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxIndexes != null : "fx:id=\"boxIndexes\" was not injected: check your FXML file 'Scene.fxml'.";
    	assert boxChampionship != null : "fx:id=\"boxChampionship\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxTeam != null : "fx:id=\"boxTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnChampionship != null : "fx:id=\"btnChampionship\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTeam != null : "fx:id=\"btnTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPt1 != null : "fx:id=\"btnPt1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colNationality != null : "fx:id=\"colNationality\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colRole != null : "fx:id=\"colRole\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colValue != null : "fx:id=\"colValue\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colWage != null : "fx:id=\"colWage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colYear != null : "fx:id=\"colYear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tabPlayers != null : "fx:id=\"tabPlayers\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxValue != null : "fx:id=\"txtMaxValue\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxWage != null : "fx:id=\"txtMaxWage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDate != null : "fx:id=\"txtDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblStats != null : "fx:id=\"lblStats\" was not injected: check your FXML file 'Scene.fxml'.";
        /*assert x != null : "fx:id=\"x\" was not injected: check your FXML file 'Scene.fxml'.";
        assert y != null : "fx:id=\"y\" was not injected: check your FXML file 'Scene.fxml'.";
        assert chartStats != null : "fx:id=\"chartStats\" was not injected: check your FXML file 'Scene.fxml'.";*/

        
        this.colName.setCellValueFactory(new PropertyValueFactory<Footballer, String>("name"));
        //this.colNationality.setCellValueFactory(new PropertyValueFactory<Footballer, String>("nationality"));
        this.colRole.setCellValueFactory(new PropertyValueFactory<Footballer, String>("best_role"));
        this.colYear.setCellValueFactory(new PropertyValueFactory<Footballer, Integer>("age"));
        this.colValue.setCellValueFactory(new PropertyValueFactory<Footballer, Double>("value"));
        this.colWage.setCellValueFactory(new PropertyValueFactory<Footballer, Integer>("wage"));

        tabPlayers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.boxChampionship.getItems().addAll(model.getAllChampionships());
    	this.boxIndexes.getItems().addAll(model.getAllIndexes());
    }

}
