package vue;

import application.Dao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import modele.Auteur;

public class ControleurBibliothequeAuteur {
	private Dao dao;

	@SuppressWarnings("unused")
	private Stage dialogStage;

	public ControleurBibliothequeAuteur() {

		// le controleur crée l'objet dao

		dao = new Dao();

	}

	@FXML
	private TextField nom;

	@FXML
	private TextField prenom;

	@FXML
	private TextField nationalite;

	@FXML
	private Button annuler;

	@FXML
	private void okFormulaireAuteur() {
		// si la méthode isEmpty retourne false alors la méthode okFormulaireAuteur
		// s'execute
		if (isEmpty()) {

			// récupère le texte du champs nationalite
			String Nationalite = nationalite.getText();
			String Nom = nom.getText();
			String Prenom = prenom.getText();
			// création d'un nouvel objet Auteur
			Auteur i = new Auteur(Nom, Prenom, Nationalite);
			dao.ajouteurAuteur(i);
			System.out.println("Vous avez ajouté l'auteur :" + i.getPrenom() + " " + i.getNom());
			// vide les champs du formulaire
			nom.setText("");
			prenom.setText("");
			nationalite.setText("");
			Stage dialogStage = (Stage) annuler.getScene().getWindow();
			// ferme la fenêtre du formulaire
			dialogStage.close();
		}
	}

	@FXML
	private void annulerFormulaireAuteur() {

		Stage dialogStage = (Stage) annuler.getScene().getWindow();
		// ferme la fenêtre du formulaire
		dialogStage.close();
	}

	/*
	 * méthode boolean qui vérifie si les champs du formulaire sont remplis ou non
	 * 
	 * @return false si les champs sont remplis
	 * 
	 * @return true si les champs sont vides
	 */

	private boolean isEmpty() {

		String Nom = nom.getText();
		String Prenom = prenom.getText();
		String Nationalite = nationalite.getText();
		if (Nom.length() < 1 || Prenom.length() < 1 || Nationalite.length() < 1) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Champs vide");
			alert.setHeaderText("Veuillez saisir tous les champs du formulaire");
			if (Nom.length() < 1)
				nom.setText("Remplir le champs");
			nom.setText("Saisir");
			prenom.setText("");
			nationalite.setText("");

			if (Prenom.length() < 1)
				nom.setText("Saisir");
			prenom.setText("");
			nationalite.setText("");

			if (Nationalite.length() < 1)
				nom.setText("");
			prenom.setText("");
			nationalite.setText("Saisir");

			alert.showAndWait();
			return false;
		} else
			return true;
	}

}
