package republic.turrnut.browser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Browser extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui.fxml"));
			Parent root = loader.load();	
			Controller controller = loader.getController();
			Scene scene = new Scene(root);		
			controller.setStageWhenInit(primaryStage, scene);
			primaryStage.getIcons().add(new Image("icon.png"));
			primaryStage.setTitle("Nickel Web Browser 1.0.0");
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
