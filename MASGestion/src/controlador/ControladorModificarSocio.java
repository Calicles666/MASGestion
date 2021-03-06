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
import javafx.stage.Stage;
import modelo.BaseDatosOO;
import modelo.Socio;
import modelo.Socio.Actividad;
import util.Util;

/**
 * FXML Controller class
 *
 * @author elena
 */
public class ControladorModificarSocio implements Initializable {

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
    private Label lbFechaBaja;
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
    private Label etiqFeedback;
    @FXML
    private ImageView iViewLogo;
    @FXML
    private ChoiceBox<Actividad> cbActividad;
    @FXML
    private DatePicker dpFechaFinMembresia;
    // para almacenar el socio seleccionado en la  venta principal y modificarlo
    private Socio socio;
    // lista de socios para controlar preexistencia de un socio desde aqui
    private ObservableList<Socio> socios;
    //referencia a la bd
    private BaseDatosOO bd;

    
    /**Método en éste caso para que al modificar podamos obtener 
     * el socio seleccionado desde la ventana principal 
     @param bd referencia a la base de datos con que estamos trabajando
     @param s socio seleccionado en ventana principal*/
    public void recuperarSocios (BaseDatosOO bd , Socio s) {
    
        this.bd = bd;
        //recupero los socios desde la base de datos
        this.socios = FXCollections.observableArrayList(bd.queryAllSocios()); 
        
        this.socio = s ;
        
        //prefiero hacerlo al inicializar la ventana creo es mas correcto pero no funciona
        //si he pasado una persona a modificar en la ventana secundaria la muestro
        if (socio!=null) {
            
             //vuelco los valores del socio seleccionado en los campos de texto
            txtDni.setText(socio.getDni());
            txtNombre.setText(socio.getNombre());
            txtApellidos.setText(socio.getApellidos());
            txtTelefono.setText(socio.getTelefono());
            txtDireccion.setText(socio.getDireccion()); 
            dpFechaNacimiento.setValue(socio.getFechaNacimiento());
            dpFechaAlta.setValue(socio.getFechaAlta());
            dpFechaBaja.setValue(socio.getFechaBaja());
            dpFechaFinMembresia.setValue(socio.getFechaFinMembresia());
            cbActividad.setValue(socio.getActividad());
            txtObservaciones.setText(socio.getObservaciones());
            
            if (socio.isActivo()) {
                cbActivo.setSelected(true);
            } else {
                cbActivo.setSelected(false);
                lbFechaBaja.setVisible(true);
                dpFechaBaja.setVisible(true);
            }

            
        }
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         cbActividad.setItems(FXCollections.observableArrayList(Socio.Actividad.values()));
    }    

    @FXML
    private void guardarSocio(ActionEvent event) {
        try {
            //tomo los valores de los campos de texto
            //el DNI define al socio por lo que no permite modificarlo por tanto no hay que controlarlo
            String dni = txtDni.getText().toUpperCase();
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

            if (socios.contains(aux) &
                    !(socio.equals(aux)) //y he modificado el DNI --compara por DNI
                    ) 
                    //si estoy modificando el socio lógicamente tendra el mismo DNI
                    //luego tengo que desechar ese caso particular para que me deje 
                    //guardar
                   
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("El dni del socio ya existe");
                alert.showAndWait();

            } else //si el socio no existe procedo a modificar
            {
                
                //ahora ya modifico el socio seleccionado una vez verificado que  
                // no he modificado incorrectamente el DNI
                
                //con la persistencia en ObjectDB ha de realizarse en una transaccion con
                //entity manager por eso lo realizo en la clase BaseDatosOO
                /*//vuelco los valores de aux en el socio seleccionado
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
                */
                
                bd.modificarSocio(socio,aux);
               

                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Guardado");
                alert.setContentText("El socio ha sido modificado con éxito.");
                alert.showAndWait();

               //recupero el escenario actual para cerrarlo
               Stage escenario = (Stage) btnGuardar.getScene().getWindow();
               escenario.close();
               
                
            }
        } catch (DataFormatException e) {

            //si ha saltado por el telefono nombre y apellidos no están vacíos
            if (!txtApellidos.getText().isEmpty()
                    && !txtNombre.getText().isEmpty()
                    && !Util.esNumeroTlf(txtTelefono.getText())) 
            {
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

    @FXML
    private void salir(ActionEvent event) {
        //por si he creado el socio y luego me arrepiento y doy a salir
        socio = null;
        //recupero el escenario actual para cerrarlo
        Stage escenario = (Stage) btnGuardar.getScene().getWindow();
        escenario.close();
    }

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
    
    
    
}
