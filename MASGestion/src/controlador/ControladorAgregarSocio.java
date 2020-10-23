/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import modelo.Socio;
import modelo.Socio.Actividad;
import util.Util;

/**
 * FXML Controller class
 *
 * @author elena
 */
public class ControladorAgregarSocio implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtTelefono;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txtDni;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private TextField txtDireccion;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private DatePicker dpFechaAlta;
    @FXML
    private CheckBox cbActivo;
    @FXML
    private DatePicker dpFechaBaja;
    @FXML
    private ImageView iViewLogo;
    @FXML
    private ChoiceBox<Actividad> cbActividad;
    @FXML
    private Label lbFechaBaja;
    @FXML
    private Label etiqFeedback;

    //socio que voy a crear y que le pasare a la venta principal
    private Socio socio;
    //referencia a la lista de socios para controlar preexistencia de un socio 
    //desde aqui
    private ObservableList<Socio> socios; 
    @FXML
    private DatePicker dpFechaFinMembresia;
    
    /**Método para tener la referencia a la lista de socios se podría meter dentro 
     sw initialize pero al ser variables del modelo es mas correcto separarlo de
     initialize que es mas para la interfaz grafica.
     @param socios -  referencia a la lista de socios con la que estamos trabajando*/
    public void recuperarSocios(ObservableList<Socio> socios) {
    
        this.socios = socios; //referencio mi socios al de la ventana principal
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbActividad.setItems(FXCollections.observableArrayList(
                Socio.Actividad.values()));
    }    

    /**Método que recupera la informacion que ha introducido el usuario en las 
     * correspondientes casillas y construye un objeto tipo Socio que se recupera
     * desde la ventana principal
     * @param event - El evento generado en la ventana que lo dispara*/
    @FXML
    private void guardarSocio(ActionEvent event) {
        
        try {
            //tomo los valores de los campos de texto
            String dni = txtDni.getText().toUpperCase();
            if (!Util.validarNif(dni)) throw new DataFormatException();
            String nombre = txtNombre.getText();
            //si esta vacio alguno de los campos requeridos lanzo la excepcion
            if (nombre.isEmpty()) throw new DataFormatException();
            String apellidos = txtApellidos.getText();
            if (apellidos.isEmpty()) throw new DataFormatException();
            String telefono = txtTelefono.getText();
            if (!Util.esNumeroTlf(telefono)) throw new DataFormatException();
            String direccion = txtDireccion.getText(); 
            LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
            LocalDate fechaAlta = dpFechaAlta.getValue();
            LocalDate fechaBaja = dpFechaBaja.getValue();
            LocalDate fechaFinMembresia = dpFechaFinMembresia.getValue();
            Actividad actividad = cbActividad.getValue();
            boolean activo = cbActivo.isSelected();
            String observaciones = txtObservaciones.getText();


            Socio aux = new Socio(dni, nombre, apellidos, telefono, direccion,
                    fechaNacimiento, fechaAlta, fechaBaja,fechaFinMembresia, 
                    actividad , activo, observaciones);

            if (socios.contains(aux)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("El dni del socio ya existe");
                alert.showAndWait();

            } else //si el socio no existe procedo a guardar
            {
               //ahora ya vuelco el socio una vez verificado que no existe 
               //para pasarlo a la ventana principal
                socio = aux;
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Guardado");
                alert.setContentText("El socio ha sido añadido con éxito.");
                alert.showAndWait();

               //recupero el escenario actual para cerrarlo
               Stage escenario = (Stage) btnGuardar.getScene().getWindow();
               escenario.close();

            }
        } catch (DataFormatException e) {

            //primero compruebo que no ha saltado por el DNI incorrecto
            if (!Util.validarNif(txtDni.getText())) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Formato de DNI incorrecto.");
                alert.showAndWait();

            } else { //si el DNI es correcto continuo comprobando 
                //si ha saltado por el telefono nombre y apellidos no están vacíos
                if (!txtApellidos.getText().isEmpty()
                        && !txtNombre.getText().isEmpty()
                        && !Util.esNumeroTlf(txtTelefono.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Formato de teléfono erroneo");
                    alert.showAndWait();
                    //si ha saltado por campos vacios requeridos.
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Introduzca valores en campos requeridos");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Método que cierra la ventana agregar socio y pone socio a null
     * @param event - El evento generado en la ventana que lo dispara*/
    @FXML
    private void salir(ActionEvent event) {
        
        //por si he creado el socio y luego me arrepiento y doy a salir
        socio = null;
        //recupero el escenario actual para cerrarlo
        Stage escenario = (Stage) btnGuardar.getScene().getWindow();
        escenario.close();
    }

    /**Método que controla la visualizacion correcta del datapicker fecha baja
     * @param event - El evento generado en la ventana que lo dispara*/
    @FXML
    private void mostrarFechaBaja(ActionEvent event) {
        
        /*Cuando desmarque un socio como activo debe aparecer el control
        y desaparecer cuando se marque de nuevo
        */
        if (cbActivo.isSelected()) {
            lbFechaBaja.setVisible(false);
            dpFechaBaja.setVisible(false);
        } else {
            lbFechaBaja.setVisible(true);
            dpFechaBaja.setVisible(true);
        }
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    

}
