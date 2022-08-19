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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> cmbIndexes; // Value injected by FXMLLoader
    
    @FXML // fx:id="cmbRoles"
    private ComboBox<String> cmbRoles; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtValue"
    private TextField txtValue; // Value injected by FXMLLoader

    @FXML // fx:id="txtWage"
    private TextField txtWage; // Value injected by FXMLLoader  
    
    @FXML // fx:id="grafico"
    private BarChart<String, Number> grafico; // Value injected by FXMLLoader
    
    @FXML // fx:id="assex"
    private CategoryAxis assex; // Value injected by FXMLLoader

    @FXML // fx:id="assey"
    private NumberAxis assey; // Value injected by FXMLLoader
    
    

    public BuyController() {
		super();
	}


	@FXML
    void doBuy(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	String index = cmbIndexes.getValue();
    	String role = cmbRoles.getValue();
    	
    	if(index!=null && role!=null) {
    		
    		String wageStr = txtWage.getText();
    		String valueStr = txtValue.getText();
    		
    		try {
    			
    			double wage = Double.parseDouble(wageStr);
    			double value = Double.parseDouble(valueStr);
    			
    	    	List<Footballer> result = model.getFootballersMaxSalaryAndMaxValueAndBetterIndex(index, role, model.getSelectedTeam(), wage, value);
    	    	
    	    	int i = 0;
    	    	
    	    	if(result.size()!=0) {
    	    		txtResult.appendText("I 5 migliori calciatori acquistabili sono in ordine:\n");
        	    	for(Footballer fi: result) {
        	    		i++;
        	    		txtResult.appendText(+i+" - "+fi.getName()+"\n");
        	    	}
    	    	} else {
    	    		txtResult.appendText("Non sono presenti calciatori che migliorano gli indici e rispettano i vincoli inseriti.");
    	    	}
    	    	
    	    	
    			
    		} catch (NumberFormatException e) {
    			txtResult.appendText("Perfavore inserire valori numerici per stipendio e valore massimo!");
    		}
    		
    	} else {
    		txtResult.appendText("Perfavore selezionare un indice e/o un ruolo!");
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
        assert assex != null : "fx:id=\"assex\" was not injected: check your FXML file 'Buy.fxml'.";
        assert assey != null : "fx:id=\"assey\" was not injected: check your FXML file 'Buy.fxml'.";
        assert btnHome != null : "fx:id=\"btnHome\" was not injected: check your FXML file 'Buy.fxml'.";
        assert btnSimulate != null : "fx:id=\"btnSimulate\" was not injected: check your FXML file 'Buy.fxml'.";
        assert cmbIndexes != null : "fx:id=\"cmbIndexes\" was not injected: check your FXML file 'Buy.fxml'.";
        assert cmbRoles != null : "fx:id=\"cmbRoles\" was not injected: check your FXML file 'Buy.fxml'.";
        assert grafico != null : "fx:id=\"grafico\" was not injected: check your FXML file 'Buy.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Buy.fxml'.";
        assert txtValue != null : "fx:id=\"txtValue\" was not injected: check your FXML file 'Buy.fxml'.";
        assert txtWage != null : "fx:id=\"txtWage\" was not injected: check your FXML file 'Buy.fxml'.";
        
    }

	public void setModel(Model model) {
		this.model = model;		
		
        /* Carico indici e ruoli nella combo box */
    	this.cmbIndexes.getItems().addAll(model.getAllIndexes());
    	this.cmbRoles.getItems().addAll(model.getAllRoles());
    	
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
