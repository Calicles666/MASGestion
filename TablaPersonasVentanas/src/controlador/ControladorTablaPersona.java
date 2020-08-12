/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author elena
 */
public class ControladorTablaPersona implements Initializable {

    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<Persona> tablaPersona;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colEdad;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    
     //coleccion especial de JavaFX para poder mostrar los objetos 
    private ObservableList<Persona> personas;
    
      //coleccion para resultados de filtrar
    private ObservableList<Persona> personasFiltradas;
    
    @FXML
    private TextField txtFiltro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         //crear la lista observable vacía
        personas = FXCollections.observableArrayList();
        
        personasFiltradas = FXCollections.observableArrayList();
        
        //vincular la lista de  personas en el visor tabla al inicial la ventana
        tablaPersona.setItems(personas);
        
        //enlazar las columnas de la tabla con los atributos
        
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        // TODO
    }    

    @FXML
    private void agregarPersona(ActionEvent event) {
        
        try {
            //creo un cargador con el xml de la ventana
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ControladorTablaPersona.class.getResource("/vista/vistaEditarPersona.fxml"));
            //creo una base y cargo en ella los componentes
            Parent root = loader.load();
            //instancio en controlador de ésta vista y lo asocio
            ControladorEditarPersona controlador = loader.getController();
            //para enviar la lista de personas a la ventana secundaria
            controlador.inicializarAtributos(personas);
            //creo una escena que viene de root
            Scene escena = new Scene(root);
            //ahora creo un escenario
            Stage escenario = new Stage();
            escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
            escenario.setScene(escena);//cargo la escena en el escenario
            escenario.showAndWait(); //la muestro hasta que se cierre por el usuario
            
            //recupero la persona de la ventana secundaria
            Persona p = controlador.getPersona();
            //si he creado una persona en la ventana secundaria la añado a la lista
            if (p!=null){
                
                personas.add(p);
                //prueba uso el metodo filtrar cada vez que modifico la lista de personas
                filtrar(null);
                //tablaPersona.refresh();
            }
            
        } catch (IOException ex) {
             //feedback al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error de E/S");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void seleccionarPersona(MouseEvent event) {
    }

    @FXML
    private void modificarPersona(ActionEvent event) {
        
        //recuperamos la persona seleccionada de la tabla
        Persona pSeleccionada = tablaPersona.getSelectionModel().getSelectedItem();
        
         //si hay una persona seleccionada
        if (pSeleccionada!=null){
            
            try {
                //creo un cargador con el xml de la ventana
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ControladorTablaPersona.class.getResource("/vista/vistaEditarPersona.fxml"));
                //creo una base y cargo en ella los componentes
                Parent root = loader.load();
                //instancio en controlador de ésta vista y lo asocio
                ControladorEditarPersona controlador = loader.getController();
                //envio la lista de personas y al ser modificar tb la persona seleccionada
                controlador.inicializarAtributos(personas,pSeleccionada);
                //creo una escena que viene de root
                Scene escena = new Scene(root);
                //ahora creo un escenario
                Stage escenario = new Stage();
                escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
                escenario.setScene(escena);//cargo la escena en el escenario
                escenario.showAndWait(); //la muestro hasta que se cierre por el usuario

                //No es necesario como en guardar 
                    //recupero la persona de la ventana secundaria
                    //Persona aux = controlador.getPersona();
                    //...
                //nunca va a ser nulo porque le pasamos y recojemos referencias a persona desde la principal
                //haya modificado una persona en la ventana secundaria 
                //o no refresco igualmente. Si le di a salir no habra cambios.
                
                //tablaPersona.refresh();
                //prueba uso el metodo filtrar cada vez que modifico la lista de personas en lugar de refrescar
                filtrar(null);

            } catch (IOException ex) {
                //feedback al usuario
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Error de E/S");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
            
        } else //si no hay persona seleccionada lanzo el error al usuario
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
        Persona pSeleccionada = tablaPersona.getSelectionModel().getSelectedItem();
        
        //si hay una persona seleccionada
        if (pSeleccionada!=null){
            
            personas.remove(pSeleccionada);
            //feedback al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("Persona borrada con éxito.");
            alert.showAndWait();
            
            //tablaPersona.refresh();
            //prueba uso el metodo filtrar cada vez que modifico la lista de personas en vez de refrescar
                filtrar(null);
            
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
    private void filtrar(KeyEvent event) {
        
        String filtro = txtFiltro.getText().toLowerCase();
        
        //si esta vacio el filtro que aparezcan todas las personas
        if (txtFiltro.getText().isEmpty()) {

            tablaPersona.setItems(personas);
        } else //si hay algo que filtrar
        {
            //limpio la lista de filtrados
            personasFiltradas.clear();
            //recorreo personas pasando a la lista auxiliar personasFiltradas los que coincidan
            for (Persona aux : personas) {
                
                if (aux.getNombre().toLowerCase().contains(filtro) || 
                        aux.getApellidos().toLowerCase().contains(filtro) ) {
                    personasFiltradas.add(aux);
                }
            } //for
            tablaPersona.setItems(personasFiltradas);
        }//else
    }

}
