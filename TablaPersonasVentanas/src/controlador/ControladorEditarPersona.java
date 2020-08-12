/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author elena
 */
public class ControladorEditarPersona implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtEdad;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;
    //persona que voy a editar/crear y que le pasare a la venta principal
    private Persona persona;
    //referencia a la lista de personas para controlar existencia de una persona desde aqui
    private ObservableList<Persona> personas; 
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*NO CARGA LOS DATOS AL APARECER LA VENTANA
        //si he pasado una persona a modificar en la ventana secundaria la muestro
        if (persona!=null) {
            txtNombre.setText(persona.getNombre());
            txtApellidos.setText(persona.getApellidos());
            txtEdad.setText( Integer.toString(persona.getEdad()) );
            
        }
        */
    }  
    
    /**Método que incializara los atributos de la persona se podría meter dentro 
     sw initialize pero al ser variables del modelo es mas correcto separarlo de
     initialize que es mas para la interfaz grafica.
     @param referencia a la lista de personas con la que estamos trabajando*/
    public void inicializarAtributos (ObservableList<Persona> personas) {
    
        this.personas = personas; //referencio mi personas al de la ventana principal
    }
    
    /**Método sobrecargado en éste caso para que al modificar podamos obtener la persona 
     * seleccionada desde la ventana principal 
     @param referencia a la lista de personas con la que estamos trabajando*/
    public void inicializarAtributos (ObservableList<Persona> personas , Persona p) {
    
        this.personas = personas; //referencio mi personas al de la ventana principal
        
        this.persona = p ;
        
        //prefiero hacerlo al inicializar la ventana creo es mas correcto pero no funciona
        //si he pasado una persona a modificar en la ventana secundaria la muestro
        if (persona!=null) {
            txtNombre.setText(persona.getNombre());
            txtApellidos.setText(persona.getApellidos());
            txtEdad.setText( Integer.toString(persona.getEdad()) );
            
        }
        
    }

    @FXML
    private void guardarPersona(ActionEvent event) {
        
         try {
            //tomo los valores de los campos de texto
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            int edad = Integer.parseInt(txtEdad.getText());

            Persona aux = new Persona(nombre, apellidos, edad);

            if (personas.contains(aux)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("La persona ya existe");
                alert.showAndWait();

            } else //si la persona no existe
            {
               //tengo que diferenciar entre modificar y guardar uno nuevo
               //si le he pasado una persona estoy modificando (metodo inicializarAtributos sobrecargado)
               //si es null es porque estoy creando y repito el codigo de guardad
                if (persona == null) {
                    persona = aux;

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Guardado");
                    alert.setContentText("La persona ha sido añadida con éxito.");
                    alert.showAndWait();
                } else //estoy modificando porque persona tiene un contenido que le hepasado desde la ventana principal
                {
                    //modifico los valores de atributos de la persona
                    persona.setNombre(nombre); //persona.setNombre(aux.getNombre);
                    persona.setApellidos(apellidos);
                    persona.setEdad(edad);
                }
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
        
        //por si he creado la persona y luego me arrepiento y doy a salir
        persona = null;
        //recupero el escenario actual para cerrarlo
        Stage escenario = (Stage) btnGuardar.getScene().getWindow();
        escenario.close();
    }

    public Persona getPersona() {
        return persona;
    }

    public ObservableList<Persona> getPersonas() {
        return personas;
    }
    
}
