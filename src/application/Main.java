package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.awt.*;
import java.net.URI;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//	    	Parent login = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
//			Scene scene = new Scene(login,800,650);
//			scene.getStylesheets().add("source/login.css");
//
			Parent login = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
			Scene scene = new Scene(login,800,650);
//			scene.getStylesheets().add("source/nhankhau_va_hokhau.css");

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();


		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
