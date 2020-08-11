/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import modelo.Socio;
import modelo.Socio.Actividad;

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
    //referencia a la lista de socios para controlar preexistencia de un socio desde aqui
    private ObservableList<Socio> socios;

    
    /**Método en éste caso para que al modificar podamos obtener 
     * el socio seleccionado desde la ventana principal 
     @param referencia a la lista de personas con la que estamos trabajando*/
    public void recuperarSocios (ObservableList<Socio> socios , Socio s) {
    
        this.socios = socios; //referencio mi socios al de la ventana principal
        
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
            String dni = txtDni.getText().toUpperCase();
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String telefono = txtTelefono.getText();
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
                //vuelco los valores de aux en el socio seleccionado
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
               

                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Guardado");
                alert.setContentText("El socio ha sido modificado con éxito.");
                alert.showAndWait();

               //recupero el escenario actual para cerrarlo
               Stage escenario = (Stage) btnGuardar.getScene().getWindow();
               escenario.close();
               
                
            }
        } catch (NumberFormatException e) {
 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato incorrecto");
            alert.showAndWait();
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
