/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author elena
 */
public class ControladorGestionUsuarios implements Initializable {

    @FXML
    private ImageView iViewLogo;
    @FXML
    private Button btnAltaUsuario;
    @FXML
    private Button btnEliminarUsuario;
    @FXML
    private Button btnCambiarClave;
    @FXML
    private Button btnVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void altaUsuario(ActionEvent event) {
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {
    }

    @FXML
    private void cambiarClave(ActionEvent event) {
    }

    @FXML
    private void volver(ActionEvent event) {
    }
    
}
