/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Objects;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue
    private long id;
    
    /**nombre de usuario a efectos de credenciales*/
    private String usuario; //    VARCHAR(10) NOT NULL PRIMARY KEY,
    /**Contraseña del socio*/
    private String clave; // VARCHAR(50) NOT NULL,
    /**Variable que indica si el socio es administrador*/
    private boolean administrador; 

    public Usuario(String usuario, String clave, boolean administrador) {
        this.usuario = usuario;
        this.clave = clave;
        this.administrador = administrador;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.clave);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
    
    
}
