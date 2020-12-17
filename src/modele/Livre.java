package modele;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the livre database table.
 * 
 */

// défini que la classe est une entité
@Entity
@NamedQuery(name = "Livre.findAll", query = "SELECT l FROM Livre l")
public class Livre implements Serializable {
	private static final long serialVersionUID = 1L;

	// défini l'attribut qui sert de clef primaire dans la table
	@Id
	// défini la stratégie de génération de la clef
	// lors de l'insertion en base de données
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;

	private int annee;

	private String editeur;

	private String isbn;

	private String titre;

	// bi-directional many-to-one association to Auteur
	
	/*
	 * La jointure entre les tables livre et auteur se fait sur le champs "numAuteur" côté table livre
	 */

	@ManyToOne
	@JoinColumn(name = "numAuteur")
	private Auteur auteur;
	
	/*
	 * Constructeur sans arguments 
	 */

	public Livre() {
		super();
	}

	/*
	 * constructeur avec arguments
	 */

	public Livre(String isbn, String titre, String editeur, int annee, Auteur auteur) {
		this.isbn = isbn;
		this.titre = titre;
		this.editeur = editeur;
		this.annee = annee;
		this.auteur = auteur;

	}

	/*
	 * méthode toString. Cette méthode retourne *@return titre, isbn, annee* du
	 * livre
	 */

	public String toString() {
		return titre + " / " + isbn + " / " + editeur + " / " + annee;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getAnnee() {
		return this.annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getEditeur() {
		return this.editeur;
	}

	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Auteur getAuteur() {
		return this.auteur;
	}

	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

}