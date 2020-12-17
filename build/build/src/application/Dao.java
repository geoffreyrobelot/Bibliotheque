package application;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import modele.Auteur;

import modele.Livre;

/*
 * Classe qui permet d'isoler la couche métier de la couche de persistance.
 * Ici la base de données relationnelle "bibliotheque"
 */

public class Dao {

	private EntityManager em;

	/*
	 * Initialisation de JPA
	 * 
	 */

	public Dao() {
		// Spécifie le nom de l'unité de la persistence en paramètre
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjetBibliJpa");
		em = factory.createEntityManager();
	}

	/*
	 * méthode pour ajouter un auteur dans la base de données
	 * 
	 * @param auteur
	 */

	public void ajouteurAuteur(Auteur auteur) {
		// demande d'insertion dans la base de données
		em.getTransaction().begin();
		em.persist(auteur);
		em.getTransaction().commit();
	}

	/*
	 * méthode pour ajouter un livre dans la base de données
	 * 
	 * @param livre
	 */

	public void ajouterLivre(Livre livre) {

		em.getTransaction().begin();
		em.persist(livre);
		em.getTransaction().commit();
	}

	/*
	 * méthode pour supprimer un auteur dans la base de données
	 * 
	 * @param auteur
	 */

	public void supprimerAuteur(Auteur auteur) {
		em.getTransaction().begin();
		em.remove(auteur);
		em.getTransaction().commit();
	}

	/*
	 * méthode pour supprimer un livre dans la base de données
	 * 
	 * @param livre
	 */

	public void supprimerLivre(Livre livre) {
		em.getTransaction().begin();
		em.remove(livre);
		em.getTransaction().commit();
	}

	/*
	 * méthode pour afficher la liste des auteurs création d'une requête sur la
	 * table auteur sélectionne tous les auteurs de la table auteur
	 * 
	 * @return retourne une liste de tous les auteurs de la table auteur
	 */

	public List<Auteur> afficherListeAuteur() {

		TypedQuery<Auteur> query = em.createQuery("SELECT g FROM Auteur g", Auteur.class);
		List<Auteur> liste = query.getResultList();
		return liste;

	}

	/*
	 * méthode pour afficher la liste des livres création d'une requête sur la table
	 * livre sélectionne tous les livres de la table livre
	 * 
	 * @return retourne une liste de tous les livres de la table livre
	 */

	public List<Livre> afficherListeLivre() {

		TypedQuery<Livre> query = em.createQuery("SELECT g FROM Livre g", Livre.class);
		List<Livre> listelivre = query.getResultList();
		return listelivre;

	}

	/*
	 * méthode pour afficher les livres en fonction de l'auteur séléctionné création
	 * d'une requête qui sélectionne le(s) livre(s) d'un auteur Instanciation de la
	 * requête création d'une liste vide
	 * 
	 * @param auteur
	 * 
	 * @return retourne une liste où le champs numAuteur de la table livre est égal
	 * au champs num de la table livre
	 */

	@SuppressWarnings("unchecked")
	public List<Livre> afficherleslivresdunauteur(Auteur auteur) {

		String requete = "SELECT l from Livre l WHERE l.auteur =?1";
		Query query = em.createQuery(requete);
		List<Livre> list = null;
		query.setParameter(1, auteur);
		list = query.getResultList();
		return list;

	}

}
