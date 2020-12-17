package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import vue.ControleurBibliotheque;

public class Main extends Application {

	/*
	 * Cette m�thode est le point d'entr� de l'application Elle est appel�e apr�s
	 * compilation du projet, lorsque l'application est pr�te � d�marrer
	 * 
	 * @param primaryStage primaryStage est la fen�tre principale de l'application
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../vue/Vue.fxml"));
			GridPane root = (GridPane) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Greta - Projet Biblioth�que");
			primaryStage.setScene(scene);
			primaryStage.show();

			// instanciation de la classe MainAuteur
			MainAuteur mainauteur = new MainAuteur(primaryStage);
			
			// donne la r�f�rence de l'objet au controleur principal
			ControleurBibliotheque controleur = loader.getController();
			controleur.setMainAuteur(mainauteur);

			// instanciation de la classe MainQrcode
			MainQrcode mainqrcode = new MainQrcode(primaryStage);

			// donne la r�f�rence de l'objet au controleur principal
			@SuppressWarnings("unused")
			ControleurBibliotheque controleurqrcode = loader.getController();
			controleur.setMainQrcode(mainqrcode);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/*
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
