package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	
	//jpackage --name "Nickel Browser" --description "a free, open source, easy-to-use browser" --app-version 1.0 --input .\out --dest .\out --main-jar test.jar --module-path C:\javafx-jmods-19 --add-modules javafx.web,javafx.swing,javafx.graphics,javafx.base,javafx.controls,javafx.media,javafx.fxml --win-shortcut --win-menu
	double version = 1.0;
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/UI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			stage.setScene(scene);
			stage.setTitle("Nickel Browser " + String.valueOf(version));
			stage.getIcons().add(new Image("/nickel.png"));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}	
	
}
