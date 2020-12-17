package application;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import modele.Auteur;
import modele.Livre;

/*
 * classe test de la classe Dao 
 */

public class TestDao {
	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProjetBibliJpa");
		EntityManager em;
		em = factory.createEntityManager();

		// récupère la liste des auteurs présents dans la base de données

		TypedQuery<Auteur> query = em.createQuery("SELECT g FROM Auteur g", Auteur.class);
		List<Auteur> liste = query.getResultList();
		liste.stream().forEach(i -> System.out
				.println("Nom:" + i.getNom() + ", prénom:" + i.getPrenom() + ", nationalité:" + i.getNationalite()));

		/*
		 * ajouter un nouvel auteur dans la base de données
		 */

		/*
		 Auteur auteur = new Auteur ("Bussi" , "Michel", "Français");
		 em.getTransaction().begin(); em.persist(auteur);
		 em.getTransaction().commit();
		 */

		/*
		 * méthode qui permet de supprimer un auteur
		 */

		/*
		 Auteur i = liste.get(0); em.getTransaction().begin(); em.remove(i);
		 em.getTransaction().commit();
		 */

		System.out.println("");
		System.out.println("Liste des livres dans la bibliothèque : ");
		System.out.println("");

		/*
		 * récupère la liste des livres dans la bibliothèque
		 */

		TypedQuery<Livre> query1 = em.createQuery("SELECT g FROM Livre g", Livre.class);
		List<Livre> liste1 = query1.getResultList();
		liste1.stream().forEach(i -> System.out.println("Edition :" + i.getEditeur() + ", Isbn :" + i.getIsbn()
				+ ", Titre :" + i.getTitre() + ", Auteur : " + i.getAuteur()));

		/*
		 * ajoute un livre dans la base de données livre
		 */

		/*
		 Livre livre = new Livre ("Folio", "9-123-456-777", "Il était une fois");
		 em.getTransaction().begin(); em.persist(livre); em.getTransaction().commit();
		 */

		/*
		 * supprimer un livre dans la base de données
		 */

		/*
		 Livre i = liste1.get(0); em.getTransaction().begin(); em.remove(i);
		 em.getTransaction().commit();
		 */
	}
}