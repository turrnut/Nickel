package application;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller implements Initializable{
	
	@FXML
	private WebView webview;
	
	@FXML
	private TextField text;
	private WebEngine eng;
	
	@FXML
	private Button refreshbutton;
	
	String url = "";
	String initurl = "https://www.google.com";
	double zoom = 0.10;
	
	@FXML
	void backward(ActionEvent event) {
		System.out.println("backward");
	}

	@FXML
	void forward(ActionEvent event) {
		System.out.println("forward");
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
    }
    
    @FXML
    void history(ActionEvent event) {
    	System.out.println("History");
    }
    
    @FXML
    void script(ActionEvent event) {
    	System.out.println("script");
    }

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.eng = webview.getEngine();
		init(this.initurl);
		
	}
	
	public void init(String initurl) {
		text.setText(initurl);
		eng.load(initurl);
	}
    
}

