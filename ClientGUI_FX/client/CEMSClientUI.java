package client;
import gui.CemsHomeController;
import javafx.application.Application;

import javafx.stage.Stage;

public class CEMSClientUI extends Application {
	public CemsHomeController chc;

	public static void main(String[] args) throws Exception {
        CEMSClientUI.launch(args);
    }
	 
	@Override
    public void start(Stage primaryStage) throws Exception {
        this.chc = new CemsHomeController();
        this.chc.initCemsHomeController(primaryStage);
    }
	
	
}
