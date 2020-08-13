/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Abraham
 */
public class BaseDatosOO {
    
    EntityManager em ;
    EntityManagerFactory emf;

    public BaseDatosOO() {

        // abre una conexion a la BDOO (crea una BD nueva si no existe)
        emf = Persistence.createEntityManagerFactory("db/MAS.odb");
        em = emf.createEntityManager();
    }
    
    public void insertarUsuario(Usuario u){
    
        em.getTransaction().begin();

        em.persist(u);

        em.getTransaction().commit();

    }
    /**Método para buscar un usuario en la BDOO por nombre de usuario
     @param usuario - nombre de usuario del que estamos buscando
     @return - el usuario que coincide con el nombre de usuario
     o Devuelve null si no existe en la BD*/
    public Usuario buscarUsuario(String usuario) {
        Usuario resultado = null;
        try {
            TypedQuery<Usuario> query
                    = em.createQuery("SELECT u FROM Usuario u WHERE usuario='"
                            + usuario + "'", Usuario.class);
            //aqui salta una NoResultException si no hay ningun usuario con ese nombre
            resultado = query.getSingleResult();

        } catch (NoResultException e) {
            resultado = null;

        }

        return resultado;

    
    }
    
    public void cerrarBD(){
    
        em.close();
        emf.close();
     
    }
}
