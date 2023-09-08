package republic.turrnut.browser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Controller implements Initializable, EventHandler<Event> {

	@FXML
	private Button goButton;
	@FXML
	private Button refreshButton;
	@FXML
	private Button zoomInButton;
	@FXML
	private Button zoomOutButton;
	@FXML
	private TextField urlField;
	@FXML
	private TabPane tabPane;
	
	private String currentUrl;
	private final double zoomRate = .10;
	private Stage stage;
	private Scene scene;
	private Map<Integer, Tab> pageMap;
	private List<WebView> views;
	private List<Boolean> tablogs;
	public static final String logPath = "log" + File.separator + "log.nickel";
	public String defaultPage = "https://www.google.com";
	public int currentTab = 0;
//	private ObservableList<WebHistory.Entry> history;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		urlField.setText(defaultPage);
		tabPane.getTabs().remove(0);
		pageMap = new HashMap<>();
		views = new ArrayList<>();
		tablogs = new ArrayList<>();
	}

	@Override
	public void handle(Event ev) {
		for(int i = 0; i < pageMap.size(); i ++) {
			Tab theTab = pageMap.get(i);
			if (theTab.isSelected()) {
				currentTab = i;
				urlField.setText((String) theTab.getUserData());
				theTab.setText((String) theTab.getUserData());
				if ((boolean) tabNew(currentTab)) {
					views.get(currentTab).getEngine().load((String) theTab.getUserData());
					views.get(currentTab).setLayoutX(0);
					views.get(currentTab).setLayoutY(404);
					views.get(currentTab).setPrefSize(this.tabPane.getPrefHeight(), 593);
					tablogs.set(currentTab, false);
				} else {
					views.get(currentTab).setLayoutX(0);
					views.get(currentTab).setLayoutY(404);
					views.get(currentTab).setPrefSize(this.tabPane.getPrefHeight(), 593);
					
				}
			}
		}
	}
	
	public boolean tabNew(int idx) {
		return tablogs.get(idx);
	}

	public void newTab() {
		createTab(tabPane.getTabs().size());
	}

	public void closeTab() {
		for(int i = 0; i < pageMap.size(); i ++) {
			Tab theTab = pageMap.get(i);
			if (theTab.isSelected()) {
				if (tabPane.getTabs().size() > 1) {
					tabPane.getTabs().remove(i);
					views.remove(i);
					tablogs.remove(i);
				}
			}
		}
	}
	
	public void createTab(int index) {
		Tab thisTab = new Tab();
		WebView wv = new WebView();
		tabPane.getTabs().add(thisTab);
		thisTab.setContent(wv);
		thisTab.setUserData(defaultPage);
		thisTab.setText(defaultPage);
		pageMap.put(index, thisTab);
		wv.setStyle("-fx-min-width: 100%; -fx-min-height: 100%;");
        wv.maxWidthProperty().bind(this.scene.widthProperty());
        wv.maxHeightProperty().bind(this.scene.heightProperty());
		views.add(wv);
		tablogs.add(true);
		thisTab.setOnSelectionChanged(this);
	}

	public void zoomIn() {
		views.get(currentTab).setZoom(views.get(currentTab).getZoom() + zoomRate);
	}

	public void zoomOut() {
		views.get(currentTab).setZoom(views.get(currentTab).getZoom() - zoomRate);
	}

	public void refresh() {
		views.get(currentTab).getEngine().reload();
	}

	public void setStageWhenInit(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
		this.stage.setMinWidth(1070);
//		this.stage.heightProperty().addListener((observable, oldValue, newValue) -> {
//			views.get(currentTab).setPrefHeight(newValue.doubleValue());
//		});
//		this.stage.widthProperty().addListener((observable, oldValue, newValue) -> {
//			views.get(currentTab).setPrefWidth(newValue.doubleValue());
//		});

		
		createTab(0);

		goPage();

		updateHistory();
		views.get(currentTab).getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
			if (Worker.State.SUCCEEDED.equals(newValue)) {
				urlField.setText(views.get(currentTab).getEngine().getLocation());
				pageMap.get(currentTab).setUserData(views.get(currentTab).getEngine().getLocation());
				if (views.get(currentTab).getEngine().getLocation().length() > 30)
					pageMap.get(currentTab).setText(views.get(currentTab).getEngine().getLocation().substring(0, 30));
			}
		});

		tabPane.setStyle("-fx-min-width: 100%; -fx-min-height: 100%;");
		tabPane.prefWidthProperty().bind(this.scene.widthProperty());
		tabPane.prefHeightProperty().bind(this.scene.heightProperty());
	}

	public void goPage() {
		currentUrl = urlField.getText();
		views.get(currentTab).getEngine().load(currentUrl);
		views.get(currentTab).setLayoutX(0);
		views.get(currentTab).setLayoutY(404);
		views.get(currentTab).setPrefSize(this.tabPane.getPrefHeight(), 593);		
		
		pageMap.get(currentTab).setUserData(currentUrl);
		if (currentUrl.length() > 30)
			pageMap.get(currentTab).setText(currentUrl.substring(0, 30));
		
		tablogs.set(currentTab, false);
	}

	public void updateHistory() {
		views.get(currentTab).getEngine().getHistory().getEntries();
	}

	public void go(int idx) {
		try {
			views.get(currentTab).getEngine().getHistory().go(idx);
		} catch (IndexOutOfBoundsException e) {
			return;
		}
	}

	public void goBack() {
		
		updateHistory();
		go(-1);
	}

	public void goNext() {
		updateHistory();
		go(1);
	}

	// TODO
	public void getHistory() {
	}

}
