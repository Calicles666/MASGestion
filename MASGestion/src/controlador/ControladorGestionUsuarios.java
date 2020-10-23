/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Usuario;

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
    
    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void altaUsuario(ActionEvent event) {
        try {
                //Cargo la vista
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/vista/vistaAltaUsuario.fxml"));
                
                Parent root = loader.load();
                
               
                
                //creo una escena que viene de root
                Scene escena = new Scene(root);

                //ahora creo un escenario
                Stage escenario = new Stage();
                // Seteo la scene y la muestro
                escenario.setTitle("Alta Nuevo Usuario.");
                // Set the application icon.
                escenario.getIcons().add(new Image("/vista/logoMAS.png"));
                escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
                escenario.setScene(escena);//cargo la escena en el escenario
                //l
                //con showandwait se queda abierta login
                escenario.show(); 
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {
        try {
            //Cargo la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/vista/vistaEliminarUsuario.fxml"));

            Parent root = loader.load();

            //creo una escena que viene de root
            Scene escena = new Scene(root);

            //ahora creo un escenario
            Stage escenario = new Stage();
            // Seteo la scene y la muestro
            escenario.setTitle("Eliminar usuario.");
            // Set the application icon.
            escenario.getIcons().add(new Image("/vista/logoMAS.png"));
            escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
            escenario.setScene(escena);//cargo la escena en el escenario
            //l
            //con showandwait se queda abierta login
            escenario.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void cambiarClave(ActionEvent event) {
        
        try {
            //Cargo la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/vista/vistaCambiarClave.fxml"));

            Parent root = loader.load();

            //instancio en controlador de ésta vista y lo asocio
            ControladorCambiarClave controladorCC = loader.getController();
            //para enviar la lista de personas a la ventana agregar Socio
            controladorCC.recuperarUsuario(this.usuario);

            //creo una escena que viene de root
            Scene escena = new Scene(root);

            //ahora creo un escenario
            Stage escenario = new Stage();
            // Seteo la scene y la muestro
            escenario.setTitle("Modificacion contraseña usuario.");
            // Set the application icon.
            escenario.getIcons().add(new Image("/vista/logoMAS.png"));
            escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
            escenario.setScene(escena);//cargo la escena en el escenario
            //l
            //con showandwait se queda abierta login
            escenario.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void volver(ActionEvent event) {
        //recupero el escenario actual para cerrarlo
        Stage escenario = (Stage) btnVolver.getScene().getWindow();
        escenario.close();
    }

    /**
     * Metodo que recupera el usuario inroducido en la ventana login y desactiva
     * la creacion/eliminacion de socios para usuarios no administradores.
     */
    public void recuperarUsuario(Usuario usuario) {

        this.usuario = usuario;
        if (!usuario.isAdministrador()) {
            btnAltaUsuario.setDisable(true);
            btnEliminarUsuario.setDisable(true);
        }
    }
}
