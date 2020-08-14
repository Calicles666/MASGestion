/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
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
    /**Método que inserta un Usuario en la base de datos
     @param u - usuario a insertar*/
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
    /**Método para obtener de la BDOO una lista
     @return - lista  con todos los socios
    */
    public List<Socio> queryAll() {
        List<Socio> socios ;
        try {
            TypedQuery<Socio> query = 
                    em.createQuery("SELECT s FROM Socio s", Socio.class);
            
            socios = query.getResultList();
        
        } catch (NoResultException e) {
            socios = null;

        }

        return socios;

    
    }
    /**Método que inserta un Socio en la base de datos
     @param s - socio a insertar*/
    public void insertarSocio(Socio s){
    
        em.getTransaction().begin();

        em.persist(s);

        em.getTransaction().commit();

    }
    
    /**Método que borra un Socio preexistente de la base de datos
     @param s - socio a borrar*/
    public void borrarSocio(Socio s){
    
        em.getTransaction().begin();

        em.remove(s);

        em.getTransaction().commit();

    }
    public void cerrarBD(){
    
        em.close();
        emf.close();
     
    }

    /**Método que modifica un socio en la base de datos. He de traer los valores
     * mediente un socio auxiliar ya que ObjectDB realiza así las modificaciones
     @param socio referncia al socio a modificar
     @param aux instancia con los valores a establecer*/
    public void modificarSocio(Socio socio , Socio aux) {
       
        em.getTransaction().begin();

        socio.setDni(aux.getDni());
        socio.setNombre(aux.getNombre());
        socio.setApellidos(aux.getApellidos());
        socio.setTelefono(aux.getTelefono());
        socio.setDireccion(aux.getDireccion());
        socio.setFechaNacimiento(aux.getFechaNacimiento());
        socio.setFechaAlta(aux.getFechaAlta());
        socio.setFechaBaja(aux.getFechaBaja());
        socio.setFechaFinMembresia(aux.getFechaFinMembresia());
        socio.setActividad(aux.getActividad());
        socio.setObservaciones(aux.getObservaciones());
        socio.setActivo(aux.isActivo());

        em.getTransaction().commit();
    }
}
