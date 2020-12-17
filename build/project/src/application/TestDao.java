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

		// r�cup�re la liste des auteurs pr�sents dans la base de donn�es

		TypedQuery<Auteur> query = em.createQuery("SELECT g FROM Auteur g", Auteur.class);
		List<Auteur> liste = query.getResultList();
		liste.stream().forEach(i -> System.out
				.println("Nom:" + i.getNom() + ", pr�nom:" + i.getPrenom() + ", nationalit�:" + i.getNationalite()));

		/*
		 * ajouter un nouvel auteur dans la base de donn�es
		 */

		/*
		 Auteur auteur = new Auteur ("Bussi" , "Michel", "Fran�ais");
		 em.getTransaction().begin(); em.persist(auteur);
		 em.getTransaction().commit();
		 */

		/*
		 * m�thode qui permet de supprimer un auteur
		 */

		/*
		 Auteur i = liste.get(0); em.getTransaction().begin(); em.remove(i);
		 em.getTransaction().commit();
		 */

		System.out.println("");
		System.out.println("Liste des livres dans la biblioth�que : ");
		System.out.println("");

		/*
		 * r�cup�re la liste des livres dans la biblioth�que
		 */

		TypedQuery<Livre> query1 = em.createQuery("SELECT g FROM Livre g", Livre.class);
		List<Livre> liste1 = query1.getResultList();
		liste1.stream().forEach(i -> System.out.println("Edition :" + i.getEditeur() + ", Isbn :" + i.getIsbn()
				+ ", Titre :" + i.getTitre() + ", Auteur : " + i.getAuteur()));

		/*
		 * ajoute un livre dans la base de donn�es livre
		 */

		/*
		 Livre livre = new Livre ("Folio", "9-123-456-777", "Il �tait une fois");
		 em.getTransaction().begin(); em.persist(livre); em.getTransaction().commit();
		 */

		/*
		 * supprimer un livre dans la base de donn�es
		 */

		/*
		 Livre i = liste1.get(0); em.getTransaction().begin(); em.remove(i);
		 em.getTransaction().commit();
		 */
	}
}