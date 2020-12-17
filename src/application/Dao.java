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
 * Classe qui permet d'isoler la couche m�tier de la couche de persistance.
 * Ici la base de donn�es relationnelle "bibliotheque"
 */

public class Dao {

	private EntityManager em;

	/*
	 * Initialisation de JPA
	 * 
	 */

	public Dao() {
		// Sp�cifie le nom de l'unit� de la persistence en param�tre
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjetBibliJpa");
		em = factory.createEntityManager();
	}

	/*
	 * m�thode pour ajouter un auteur dans la base de donn�es
	 * 
	 * @param auteur
	 */

	public void ajouteurAuteur(Auteur auteur) {
		// demande d'insertion dans la base de donn�es
		em.getTransaction().begin();
		em.persist(auteur);
		em.getTransaction().commit();
	}

	/*
	 * m�thode pour ajouter un livre dans la base de donn�es
	 * 
	 * @param livre
	 */

	public void ajouterLivre(Livre livre) {

		em.getTransaction().begin();
		em.persist(livre);
		em.getTransaction().commit();
	}

	/*
	 * m�thode pour supprimer un auteur dans la base de donn�es
	 * 
	 * @param auteur
	 */

	public void supprimerAuteur(Auteur auteur) {
		em.getTransaction().begin();
		em.remove(auteur);
		em.getTransaction().commit();
	}

	/*
	 * m�thode pour supprimer un livre dans la base de donn�es
	 * 
	 * @param livre
	 */

	public void supprimerLivre(Livre livre) {
		em.getTransaction().begin();
		em.remove(livre);
		em.getTransaction().commit();
	}

	/*
	 * m�thode pour afficher la liste des auteurs cr�ation d'une requ�te sur la
	 * table auteur s�lectionne tous les auteurs de la table auteur
	 * 
	 * @return retourne une liste de tous les auteurs de la table auteur
	 */

	public List<Auteur> afficherListeAuteur() {

		TypedQuery<Auteur> query = em.createQuery("SELECT g FROM Auteur g", Auteur.class);
		List<Auteur> liste = query.getResultList();
		return liste;

	}

	/*
	 * m�thode pour afficher la liste des livres cr�ation d'une requ�te sur la table
	 * livre s�lectionne tous les livres de la table livre
	 * 
	 * @return retourne une liste de tous les livres de la table livre
	 */

	public List<Livre> afficherListeLivre() {

		TypedQuery<Livre> query = em.createQuery("SELECT g FROM Livre g", Livre.class);
		List<Livre> listelivre = query.getResultList();
		return listelivre;

	}

	/*
	 * m�thode pour afficher les livres en fonction de l'auteur s�l�ctionn� cr�ation
	 * d'une requ�te qui s�lectionne le(s) livre(s) d'un auteur Instanciation de la
	 * requ�te cr�ation d'une liste vide
	 * 
	 * @param auteur
	 * 
	 * @return retourne une liste o� le champs numAuteur de la table livre est �gal
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
