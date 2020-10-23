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
import javafx.scene.control.CheckBox;
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
 * @author Abraham Garrido
 */
public class ControladorAltaUsuario implements Initializable {

    @FXML
    private ImageView iViewLogo;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtClave;
    @FXML
    private Button btnCrearUsuario;
    @FXML
    private Button btnCancelar;
    @FXML
    private Label txtFeedback;
    @FXML
    private PasswordField txtClaveConfirmar;
    @FXML
    private CheckBox cbAdministrador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void crearUsuario(ActionEvent event) {

        //cojo la info de los cuadros de texto
        String nombre = txtUsuario.getText();
        String claveH = Hash.sha1(txtClave.getText());

        //si los campos no están vacíos
        if (nombre.replaceAll(" ", "").length() != 0
                && txtClave.getText().replaceAll(" ", "").length() != 0) {

            boolean administrador = cbAdministrador.isSelected();
            BaseDatosOO bd = new BaseDatosOO();

            //si las contraseñas coinciden
            if (Hash.sha1(txtClaveConfirmar.getText()).equals(claveH)) {

                Usuario nuevo = new Usuario(nombre, claveH, administrador);
                //si no existe lo inserto
                if (!bd.existeUsuario(nuevo)) {

                    bd.insertarUsuario(nuevo);
                    bd.cerrarBD();
                    txtFeedback.setText("Usuario creado con éxito");
                    limpiarCampos();
                } else {
                    //si ya existe no lo creo e informo
                    txtFeedback.setText("Usuario ya existente en la BD");

                }
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

    private void limpiarCampos() {
        
        txtUsuario.clear();
        txtClave.clear();
        txtClaveConfirmar.clear();
        cbAdministrador.setSelected(false);
    }
    
}
