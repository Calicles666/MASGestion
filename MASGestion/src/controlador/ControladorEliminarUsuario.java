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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.BaseDatosOO;
import modelo.Socio;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author elena
 */
public class ControladorEliminarUsuario implements Initializable {

    @FXML
    private ImageView iViewLogo;
    @FXML
    private Button btnEliminarUsuario;
    @FXML
    private Button btnCancelar;
    @FXML
    private Label txtFeedback;
    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colAdministrador;
    
    //coleccion especial de JavaFX para poder mostrar los objetos. En usuarios tendremos 
    // almacenados en memoria todos los usuarios de la aplicacion.
    private ObservableList<Usuario> usuarios;
    
    //conexion con bd
    BaseDatosOO bd = new BaseDatosOO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        usuarios = FXCollections.observableArrayList((bd.queryAll(new Usuario())));
        //cierre
        //bd.cerrarBD();
        
        
        
        //texto personalizado para la tabla sin elementos
        tablaUsuarios.setPlaceholder(new Label("Ningún usuario"));
        
        //enlazar las columnas de la tabla con los atributos
        colNombre.setCellValueFactory(new PropertyValueFactory("usuario"));        
        colAdministrador.setCellValueFactory(new PropertyValueFactory("administrador")); 
        tablaUsuarios.setItems(usuarios);
    }    

    @FXML
    private void eliminarUsuario(ActionEvent event) {
        //recuperamos el usuario seleccionado de la tabla
        Usuario uSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        
        //si hay un usuario seleccionado
        if (uSeleccionado!=null){
            
            bd.borrarElemento(uSeleccionado);
            //feedback al usuario
            txtFeedback.setText("Usuario borrado con éxito.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("Usuario borrado con éxito.");
            alert.showAndWait();
              
            usuarios = FXCollections.observableArrayList((bd.queryAll(uSeleccionado)));
            tablaUsuarios.setItems(usuarios);
            
            
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Ningún socio seleccionado.");
            alert.showAndWait(); 
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        //recupero el escenario actual para cerrarlo
        Stage escenario = (Stage) btnCancelar.getScene().getWindow();
        escenario.close();
    }
    
}
