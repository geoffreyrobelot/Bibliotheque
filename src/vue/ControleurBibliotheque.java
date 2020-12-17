package vue;

import java.util.List;
import java.util.Optional;

import application.Dao;
import application.MainAuteur;
import application.MainQrcode;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modele.Auteur;
import modele.Livre;

public class ControleurBibliotheque {

	// attribut dao

	private Dao dao;

	// attribut mainqrcode

	private MainQrcode mainqrcode;

	// attribut mainauteur

	private MainAuteur mainauteur;

	public ControleurBibliotheque() {

		// le controleur cr�e l'objet dao

		dao = new Dao();

	}

	@FXML
	private ComboBox<Auteur> choixauteur;

	@FXML
	private TextField isbn;

	@FXML
	private TextField titre;

	@FXML
	private TextField annee;

	@FXML
	private TextField editeur;

	@FXML
	private Label nom;

	@FXML
	private Label prenom;

	@FXML
	private Label nationalite;

	@FXML
	private ListView<Auteur> auteur;
	private ObservableList<Auteur> listeauteur = FXCollections.observableArrayList();

	@FXML
	private ListView<Livre> livre;
	private ObservableList<Livre> listelivre = FXCollections.observableArrayList();

	/*
	 * m�thode instanci�e au d�marrage de l'application
	 */

	@FXML
	private void initialize() {

		// affecte la liste des auteurs de l'ObservableList listeauteur � la ListView
		// auteur
		auteur.setItems(listeauteur);
		voirtouslesauteurs();
		livre.setItems(listelivre);
		// affecte la liste des auteurs de l'ObservableList listeauteur � la ComboBox
		// choixauteur
		choixauteur.setItems(listeauteur);

		/*
		 * m�thode appel�e lors du clic sur un item de la ListView auteur
		 */
		auteur.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// vide l'observableList listelivre avant d'afficher les livres de l'auteur
				listelivre.clear();
				// cr�ation d'une liste qui permet d'afficher les livres de l'auteur
				List<Livre> list = dao.afficherleslivresdunauteur(auteur.getSelectionModel().getSelectedItem());
				for (Livre i : list)
					listelivre.add(i);

				// permet de r�cup�rer l'auteur de la liste sur lequel on clic
				Auteur auteur1 = auteur.getSelectionModel().getSelectedItem();
				System.out.println(auteur1);

			}

		});

	}

	@FXML
	private void voirtouslesauteurs() {
		listeauteur.clear();
		List<Auteur> lesauteurs = dao.afficherListeAuteur();
		for (Auteur i : lesauteurs)
			listeauteur.add(i);
	}

	@FXML
	private void voirtousleslivres() {
		listelivre.clear();
		List<Livre> leslivres = dao.afficherListeLivre();
		for (Livre i : leslivres)
			listelivre.add(i);
	}

	@FXML
	private void ajouterLivre() {
		// appel de la methode qui permet de controler que la cha�ne de caractere est de
		// 13
		if (nbIsbn()) {
			// si le nombre de caract�res entr� lors de la saisie du champs Isbn est �gal �
			// 13 alors la m�thode ajouterLivre est executee

			// r�cup�re les donn�es entr�es dans les textfield

			String Isbn = isbn.getText();
			String Titre = titre.getText();
			String Editeur = editeur.getText();
			int Annee = Integer.parseInt(annee.getText());

			// r�cup�re l'item de la combobox "choixauteur"
			// et rempli la colonne "numAuteur" dans la table livre en r�cup�rant "num" de
			// la table auteur

			Auteur Auteur = choixauteur.getSelectionModel().getSelectedItem();

			System.out.println("Vous avez s�lectionn� l'auteur : " + choixauteur.getSelectionModel().getSelectedItem());

			Livre i = new Livre(Isbn, Titre, Editeur, Annee, Auteur);
			dao.ajouterLivre(i);
			listelivre.add(i);

			// reset des champs textfield + Combobox
			titre.setText("");
			isbn.setText("");
			annee.setText("");
			editeur.setText("");
			choixauteur.setItems(listeauteur);

		}
	}

	/*
	 * accesseur r�f�rence sur l'objet de type MainAuteur
	 */

	public void setMainAuteur(MainAuteur mainauteur) {
		this.mainauteur = mainauteur;
	}

	@FXML
	private void ajouterAuteur() {

		mainauteur.montrerLaVue();
		voirtouslesauteurs();

	}

	@FXML
	private void supprimerLivre() {
		Livre sel = livre.getSelectionModel().getSelectedItem();
		if (sel != null) {
			// affichage d'une fenetre de type "confirmation"
			Alert alert = new Alert(AlertType.CONFIRMATION);
			// titre de la fenetre
			alert.setTitle("Attention !");
			// texte affich� dans la fenetre
			alert.setHeaderText("Vous allez supprimer un livre");
			alert.setContentText("Etes-vous s�r de vouloir continuer ?");
			// affichage de boutons de s�lection et attente de r�ponse de l'utilisateur
			Optional<ButtonType> result = alert.showAndWait();
			// Si bouton "ok" selectionn�
			if (result.get() == ButtonType.OK) {
				// appelle de la m�thode supprimerLivre
				dao.supprimerLivre(sel);
				// affichage de la liste des livres � jour
				listelivre.clear();
				
				}
			} else {
				// la liste des livres est vid�e sans suppresion du livre
				listelivre.clear();
			}

		}
	

	@FXML
	private void supprimerAuteur() {
		Auteur sel = auteur.getSelectionModel().getSelectedItem();
		if (sel != null) {
			// affichage d'une fenetre de type "confirmation"
			Alert alert = new Alert(AlertType.CONFIRMATION);
			// titre de la fenetre
			alert.setTitle("Attention !");
			// texte affich� dans la fenetre
			alert.setHeaderText("Vous �tes sur le point de supprimer un auteur");
			alert.setContentText("Etes-vous s�r de vouloir continuer ?");

			// Actions r�alis�es en fonction du clic sur le bouton "ok" ou "annuler"
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// si "ok" alors l'auteur est supprim�
				dao.supprimerAuteur(sel);
				// la liste des livres associ�e � l'auteur est vid�e
				listelivre.clear();
				// affichage de tous les auteurs dans la liste actualis�e
				voirtouslesauteurs();

				// si bouton "annuler"
			} else {
				// affichage de tous les auteurs dans la liste inchang�e
				voirtouslesauteurs();
			}

		}
	}

	/*
	 * m�thode qui permet de cont�ler que la saisie de l'isbn soit correcte cad : 13
	 * caract�res
	 */

	private boolean nbIsbn() {
		String Isbn = isbn.getText();
		if (Isbn.length() < 13 || Isbn.length() > 13) {
			// affichage d'une fen�tre d'information 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ISBN invalide");
			alert.setHeaderText("ISBN doit contenir 13 caract�res");
			if (Isbn.length() < 13)
				// vide le champs texte 
				isbn.setText("");
				// indique dans le champs texte 
				isbn.setPromptText("Isbn trop court");
			//titre.setText("");
			//editeur.setText("");
			//annee.setText("");
			if (Isbn.length() > 13)
				isbn.setText("");
				isbn.setPromptText("Isbn trop long");
			//titre.setText("");
			//editeur.setText("");
			//annee.setText("");
			alert.showAndWait();
			return false;
		} else
			return true;
	}

	/*
	 * accesseur r�f�rence sur l'objet de type MainQrcode
	 */

	public void setMainQrcode(MainQrcode mainqrcode) {
		this.mainqrcode = mainqrcode;
	}

	@FXML
	private void creerQRCode() {
		Livre livre1 = livre.getSelectionModel().getSelectedItem();
		if (livre1 != null) {
			mainqrcode.montrerLaVueQrcode(livre1);

		}

	}

}
