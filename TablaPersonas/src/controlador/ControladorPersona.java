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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author elena
 */
public class ControladorPersona implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtEdad;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colEdad;
    @FXML
    private TableView<Persona> tablaPersona;
    
    //coleccion especial de JavaFX para poder mostrar los objetos 
    private ObservableList<Persona> personas;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //crear la lista observable vacía
        personas = FXCollections.observableArrayList();
        
        //enlazar las columnas de la tabla con los atributos
        
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        // TODO
    }    

    @FXML
    private void agregarPersona(ActionEvent event) {
        
        try {
            //tomo los valores de los campos de texto
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            int edad = Integer.parseInt(txtEdad.getText());

            Persona p = new Persona(nombre, apellidos, edad);

            if (personas.contains(p)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("La persona ya existe");
                alert.showAndWait();

            } else //empiezo a trabajar si la persona no existe 
            {
                personas.add(p);
                tablaPersona.setItems(personas);
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
    private void modificarPersona(ActionEvent event) {
        
        //recuperamos la persona seleccionada de la tabla
        Persona p = tablaPersona.getSelectionModel().getSelectedItem();
        
        //si hay una persona seleccionada
        if (p!=null){
            
             try {
                //tomo los valores de los campos de texto
                String nombre = txtNombre.getText();
                String apellidos = txtApellidos.getText();
                int edad = Integer.parseInt(txtEdad.getText());

                //los vuelco en un objeto persona auxiliar
                Persona aux = new Persona(nombre, apellidos, edad);

                if (personas.contains(aux)) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("La persona ya existe");
                    alert.showAndWait();

                } else //vuelco aux en p  si la persona no existe 
                {
                    p.setNombre(aux.getNombre());
                    p.setApellidos(aux.getApellidos());
                    p.setEdad(aux.getEdad());
                    
                    //feedback al usuario
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Confirmacion");
                    alert.setContentText("Persona modificada con éxito.");
                    alert.showAndWait();
           
                    tablaPersona.refresh();
                }
            } catch (NumberFormatException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Formato incorrecto");
                alert.showAndWait();
            }
            
    
            
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Ninguna persona seleccionada.");
            alert.showAndWait(); 
        }
        
    }

    @FXML
    private void eliminarPersona(ActionEvent event) {
       
         //recuperamos la persona seleccionada de la tabla
        Persona p = tablaPersona.getSelectionModel().getSelectedItem();
        
        //si hay una persona seleccionada
        if (p!=null){
            
            personas.remove(p);
            //feedback al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("Persona borrada con éxito.");
            alert.showAndWait(); 
            tablaPersona.refresh();
            
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Ninguna persona seleccionada.");
            alert.showAndWait(); 
        }
        
    }

    @FXML
    private void seleccionarPersona(MouseEvent event) {
        
        //recuperamos la persona seleccionada de la tabla
        Persona p = tablaPersona.getSelectionModel().getSelectedItem();
        
        //si hay una persona seleccionada
        if (p!=null){
            
            txtNombre.setText(p.getNombre());
            txtApellidos.setText(p.getApellidos());
            txtEdad.setText( Integer.toString(p.getEdad()) );
        }
    }
    
}
