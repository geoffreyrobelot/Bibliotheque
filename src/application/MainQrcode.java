package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Livre;
import vue.ControleurBibliotheque;
import vue.ControleurBibliothequeQRcode;

public class MainQrcode {

	// déclaration de l'attribut primaryStage qui sera la fenetre principale
	private Stage primaryStage;
	
	// constructeur de la classe avec arguments 

	public MainQrcode(Stage stage) {
		primaryStage = stage;
	}
	
	/*
	 * méthode appelée dans le controleur principal pour charger la nouvelle
	 * fenêtre "créer un QRCode"
	 */

	public void montrerLaVueQrcode(Livre livre1) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ControleurBibliotheque.class.getResource("CreerQRcode.fxml"));
			GridPane page = (GridPane) loader.load();

			Stage qrcodeStage = new Stage();
			ControleurBibliothequeQRcode controleur = loader.getController();
			controleur.setLivre(livre1);

			qrcodeStage.setTitle("Créer QRCode");
			qrcodeStage.initModality(Modality.WINDOW_MODAL);
			qrcodeStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			qrcodeStage.setScene(scene);
			qrcodeStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
