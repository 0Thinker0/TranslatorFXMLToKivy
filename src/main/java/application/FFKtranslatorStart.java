package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FFKtranslatorStart extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Grafica Progetto.fxml"));
		Pane root = (Pane) loader.load();
		Scene scene = new Scene(root, 1000, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("FKKTranslator");
		primaryStage.initStyle(StageStyle.TRANSPARENT);	
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
