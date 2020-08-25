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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.BaseDatosOO;
import modelo.Usuario;
import util.Hash;

/**
 * FXML Controller class
 *
 * @author Abraham
 */
public class ControladorCambiarClave implements Initializable {

    @FXML
    private ImageView iViewLogo;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtClave;
    @FXML
    private Button btnModificarClave;
    @FXML
    private Button btnCancelar;
    @FXML
    private Label txtFeedback;
    @FXML
    private PasswordField txtClaveConfirmar;
    
    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modificarClave(ActionEvent event) {
        String claveH = Hash.sha1(txtClave.getText());
        //String claveH = txtClave.getText();
        //si el campo clave nueva no están vacíos
        if (txtClave.getText().replaceAll(" ", "").length() != 0) {

            
            BaseDatosOO bd = new BaseDatosOO();

            //si las contraseñas coinciden
            if (Hash.sha1(txtClaveConfirmar.getText()).equals(claveH)) {
                    
                    Usuario aux = new Usuario(usuario.getUsuario(),claveH,usuario.isAdministrador());

                    bd.modificarClaveUsuario(usuario,aux);
                    bd.cerrarBD();
                    txtFeedback.setText("Contraseña modificada con éxito");
                    limpiarCampos();
               
              //si las contraseñas no coinciden.
            } else {
                txtFeedback.setText("Las contraseñas no coinciden.");
            }
            //si hay campos en blanco o solo con espacios
        } else {

            txtFeedback.setText("No puede haber campos vacíos o solo con espacios.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        
          //recupero el escenario actual para cerrarlo
            Stage escenario = (Stage) btnCancelar.getScene().getWindow();
            escenario.close();
    }
    /**Metodo que recupera el usuario inroducido en la ventana login y desactiva
     la creacion/eliminacion de socios para usuarios no administradores.*/
    public void recuperarUsuario(Usuario usuario) {

        this.usuario = usuario;
        txtUsuario.setText(usuario.getUsuario());
    }
    
     private void limpiarCampos() {
        
       
        txtClave.clear();
        txtClaveConfirmar.clear();
        
    }
}
