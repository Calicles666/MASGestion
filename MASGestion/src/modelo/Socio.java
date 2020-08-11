/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Clase que representa a los socios del club MAS
 *
 * @author Abraham Garrido
 */
public class Socio {
    
   /**Dni del socio*/
    private String dni; //    VARCHAR(10) NOT NULL PRIMARY KEY,
    /**Nombre del socio*/
    private String nombre; // VARCHAR(50) NOT NULL,
    /**Apellidos del socio*/
    private String apellidos; // VARCHAR(100) NOT NULL,
    /**Teléfono de contacto*/
    private String telefono; // VARCHAR(10)
    /**Direccion*/
    private String direccion; //VARCHAR(100)
    /**Fecha de nacimiento del socio*/
    private LocalDate fechaNacimiento; // DATE , /* formato yyyy-MM-dd */
    /**Fecha de alta en club*/
    private LocalDate fechaAlta; // DATE , /* formato yyyy-MM-dd */
    /**Fecha de baja en club*/
    private LocalDate fechaBaja; // DATE , /* formato yyyy-MM-dd */
    /**Fecha fin de membresia - Indica cuando tiene que volver a abonar su cuota*/
    private LocalDate fechaFinMembresia; // DATE , /* formato yyyy-MM-dd */
    
    /**Actividad del socio en el club*/
    public enum Actividad {MUAYTHAI,TAEKWONDO,WINGCHUNG,YOGA}
    private Actividad actividad; // VARCHAR(15) NOT NULL
    /**Buleana para identificar los socios activos de los históricos*/
    private boolean activo;
    /**campo para observaciones adicionales*/
    private String observaciones; //TEXT
    
    //constructores
    /**Constructor con todos los atributos*/
    public Socio(String dni, String nombre, String apellidos, String telefono, 
            String direccion, LocalDate fechaNacimiento, LocalDate fechaAlta, 
            LocalDate fechaBaja,LocalDate fechaFinMembresia ,Actividad actividad
            ,boolean activo, String observaciones) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.fechaFinMembresia = fechaFinMembresia;
        this.actividad = actividad;
        this.activo = activo;
        this.observaciones = observaciones;
    }
    
     /**Constructor con todos los atributos menos activo y fecha de baja que se
      * establecen por defecto*/
    public Socio(String dni, String nombre, String apellidos, String telefono,
            String direccion, LocalDate fechaNacimiento, LocalDate fechaAlta,
            LocalDate fechaFinMembresia,Actividad actividad 
            ,String observaciones) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = null;
        this.fechaFinMembresia = fechaFinMembresia;
        this.actividad = actividad;
        this.activo = true;
        this.observaciones = observaciones;
    }
    
     /**Constructor con todos los atributos menos activo y observaciones que se 
      * establece por defecto*/
    public Socio(String dni, String nombre, String apellidos, String telefono,
            String direccion, LocalDate fechaNacimiento, LocalDate fechaAlta,
            LocalDate fechaFinMembresia ,Actividad actividad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = null;
        this.activo = true;
        this.observaciones = "";
        this.fechaFinMembresia = fechaFinMembresia;
        this.actividad = actividad;
    }

    /**Constructor que recibe los datos del resumen de la vista principal*/
    public Socio(String dni, String nombre, String apellidos, Actividad actividad , LocalDate fechaNacimiento, boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.actividad = actividad;
        this.activo = activo;
    }
    
    /**Constructor que recibe los datos del resumen de la vista principal*/
    public Socio(String dni, String nombre, String apellidos, 
            Actividad actividad , LocalDate fechaNacimiento,
            LocalDate fechaFinMembresia) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.actividad = actividad;
        this.activo = true;
        this.fechaFinMembresia = fechaFinMembresia;
    }

    //Getters Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFechaFinMembresia() {
        return fechaFinMembresia;
    }

    public void setFechaFinMembresia(LocalDate fechaFinMembresia) {
        this.fechaFinMembresia = fechaFinMembresia;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
    
    @Override
    public String toString() {
        return "Socio{" + "dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Socio other = (Socio) obj;
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }
        return true;
    }
   
    
    
    
    
    
}
