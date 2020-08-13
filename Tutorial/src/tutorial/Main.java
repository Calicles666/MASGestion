package tutorial;

import java.time.LocalDate;
import javax.persistence.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("db/p2.odb");
        EntityManager em = emf.createEntityManager();

        // Store 1000 Point objects in the database:
        em.getTransaction().begin();
        for (int i = 0; i < 20; i++) {
            
            
            Usuario u = new Usuario(Integer.toString(i), Integer.toString(i),true, LocalDate.now());
            em.persist(u);
        }
        em.getTransaction().commit();

        

                // Retrieve all the Point objects from the database:
        TypedQuery<Point> query =
            em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            System.out.println(p);
        }
        
        // Find the number of Point objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + q1.getSingleResult());
        
        TypedQuery<Usuario> query2 =
            em.createQuery("SELECT u FROM Usuario u", Usuario.class);
        List<Usuario> results2 = query2.getResultList();
        for (Usuario u : results2) {
            System.out.println(u);
        }

        // Close the database connection:
        em.close();
        emf.close();
    }
}