package application;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class Controller implements Initializable{
	
	@FXML
	private WebView webview;
	
	@FXML
	private TextField text;
	
	@FXML
	private Button refreshbutton;
	
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView historytable;
	
	private WebEngine eng;
	private WebHistory his;
	private final String initurl = "https://turrnut.github.io/nickel/";
	private double zoom = 0.10;
	
	@FXML
	private Tab tab;
	
	@FXML
	private TabPane tabpane;
	
	@FXML
	void backward(ActionEvent event) {
		try {
			eng.getHistory().go(-1);
			text.setText(eng.getLocation());
		} catch (IndexOutOfBoundsException e){
			
		}
	}

	@FXML
	void forward(ActionEvent event) {
		try {
			eng.getHistory().go(1);
			text.setText(eng.getLocation());
		} catch (IndexOutOfBoundsException e){
			
		}
	}
	
	@FXML
	void zoomin(ActionEvent event) {
		webview.setZoom(webview.getZoom() + this.zoom);
	}

	@FXML
	void zoomout(ActionEvent event) {
		webview.setZoom(webview.getZoom() - this.zoom);
	}
	
    @FXML
    void refresh(ActionEvent event) {
    	eng.reload();
    	text.setText(eng.getLocation());
    	tab.setText(eng.getLocation());
    }
    
    @FXML
    void newtab(ActionEvent event) {
    	System.out.println("New Tab");
    }
    
    @FXML
    void bookmarks(ActionEvent event) {
    	System.out.println("Bookmarks");
    }
    
    @FXML
    void go(ActionEvent event) {
    	eng.load(text.getText());
    	tab.setText(eng.getLocation());
    }
    
	@FXML
    void history(ActionEvent event) throws IOException {
		text.setText(eng.getLocation());
    	this.his = eng.getHistory();
    	ObservableList<WebHistory.Entry> ent = his.getEntries();
    	String display = "";
    	for (WebHistory.Entry entry : ent) {
    		display += entry.getUrl() + " : " + new SimpleDateFormat("E yyyy:mm:dd hh:M:s").format(entry.getLastVisitedDate()) + "\n";
    	}
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("History");
    	alert.setWidth(900);
    	alert.setContentText("Browser History");
    	alert.setContentText(display);
    	alert.show();
    }
    
    @FXML
    void script(ActionEvent event) {
    	System.out.println(text.getText());
    	eng.executeScript(text.getText());
    }

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.eng = webview.getEngine();
		init(this.initurl);
		
	}
	
	public void init(String initurl) {
		text.setText(initurl);
		eng.load(initurl);
		tab.setText(initurl);
	}
    
}

