/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.Socio;
import modelo.Socio.Actividad;

/**
 * FXML Controller class
 *
 * @author Abraham Garrido Rosillo
 */
public class ControladorDetalleSocio implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtTelefono;
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
    //socio que voy a visualizar seleccionado en la venta principal
    private Socio socio;

    /**
     * Método recuperarSocios en éste caso para que al modificar podamos obtener
     * el socio seleccionado desde la ventana principal
     *
     * @param referencia a la lista de personas con la que estamos trabajando
     */
    public void recuperarSocio(Socio s) {
        this.socio = s;
        //prefiero hacerlo al inicializar la ventana creo es mas correcto pero no funciona
        //por la forma en que paso la variable socio con instancia del controlador
        //si he pasado una persona a modificar en la ventana secundaria la muestro
        if (socio != null) {

            //vuelco los valores del socio seleccionado en los campos de texto
            txtDni.setText(socio.getDni());
            txtNombre.setText(socio.getNombre());
            txtApellidos.setText(socio.getApellidos());
            txtTelefono.setText(socio.getTelefono());
            txtDireccion.setText(socio.getDireccion()); 
            
            dpFechaNacimiento.setValue(socio.getFechaNacimiento());
            dpFechaNacimiento.setDisable(true);
            dpFechaAlta.setValue(socio.getFechaAlta());
            dpFechaAlta.setDisable(true);
            dpFechaBaja.setValue(socio.getFechaBaja());
            dpFechaBaja.setDisable(true);
            dpFechaFinMembresia.setValue(socio.getFechaFinMembresia());
            dpFechaFinMembresia.setDisable(true);
            //solo incluyo la actividad del socio a mostrar no se puede cambiar desde aqui
            cbActividad.setItems(FXCollections.observableArrayList(socio.getActividad()));
            cbActividad.setValue(socio.getActividad());
            cbActividad.setDisable(true);


            txtObservaciones.setText(socio.getObservaciones());
            
            if (socio.isActivo()) {
                cbActivo.setSelected(true);
            } else {
                cbActivo.setSelected(false);
                lbFechaBaja.setVisible(true);
                dpFechaBaja.setVisible(true);
            }
            cbActivo.setDisable(true);

            
        }
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void salir(ActionEvent event) {
        
        //recupero el escenario actual para cerrarlo
        Stage escenario = (Stage) btnSalir.getScene().getWindow();
        escenario.close();
    }
    

    

    
    
}
