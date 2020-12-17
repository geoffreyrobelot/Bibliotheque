package modele;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the auteur database table.
 * 
 */

//défini que la classe est une entité
@Entity
@NamedQuery(name = "Auteur.findAll", query = "SELECT a FROM Auteur a")
public class Auteur implements Serializable {
	private static final long serialVersionUID = 1L;

	// défini l'attribut qui sert de clef primaire dans la table
	@Id

	/*
	 * Défini la stratégie de génération de la clef lors de l'insertion en base de
	 * données
	 * "IDENTITY" car utilisation de MySQL et auto-increment 
	 */

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;

	private String nationalite;

	private String nom;

	private String prenom;

	// bi-directional many-to-one association to Livre
	@OneToMany(mappedBy = "auteur")
	private List<Livre> livres;

	/*
	 * La jointure entre les tables livre et auteur se fait sur le champs "num" côté
	 * table auteur
	 */

	public Auteur() {
		super();
	}

	/*
	 * constructeur avec arguments
	 */

	public Auteur(String nom, String prenom, String nationalite) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.nationalite = nationalite;
	}

	/*
	 * méthode to String Cette méthode retourne *@return nom, prenom, nationalite*
	 * de l'auteur
	 */

	public String toString() {
		return nom + " " + prenom + " / " + nationalite;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getNationalite() {
		return this.nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Livre> getLivres() {
		return this.livres;
	}

	public void setLivres(List<Livre> livres) {
		this.livres = livres;
	}

	public Livre addLivre(Livre livre) {
		getLivres().add(livre);
		livre.setAuteur(this);

		return livre;
	}

	public Livre removeLivre(Livre livre) {
		getLivres().remove(livre);
		livre.setAuteur(null);

		return livre;
	}

}