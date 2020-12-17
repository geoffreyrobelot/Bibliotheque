package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vue.ControleurBibliotheque;

public class MainAuteur {

	// déclaration de l'attribut primaryStage fenêtre principale de l'application
	private Stage primaryStage;

	/*
	 * constructeur de la classe avec argument
	 */
	public MainAuteur(Stage stage) {
		primaryStage = stage;
	}

	/*
	 * méthode appelée dans le controleur principal pour charger la nouvelle
	 * fenêtre "ajouter un auteur"
	 */

	public void montrerLaVue() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ControleurBibliotheque.class.getResource("VueAuteur.fxml"));
			GridPane page = (GridPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajout d'un auteur");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
