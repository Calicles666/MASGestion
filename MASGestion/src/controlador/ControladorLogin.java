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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.BaseDatosOO;
import modelo.Usuario;
import util.Hash;

/**
 * FXML Controller class
 *
 * @author Abraham Garrido
 */
public class ControladorLogin implements Initializable {

    @FXML
    private ImageView iViewLogo;
    @FXML
    private Label txtFeedback;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtClave;
    @FXML
    private Button btnAcceder;
    @FXML
    private Button btnGestionUsuarios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //para no escribir en las pruebas
        //txtUsuario.setText("root");
        //txtClave.setText("123456");
        
    }    

    @FXML
    private void acceder(ActionEvent event) {

        //String hashClave = Hash.sha1("123456") ;
        //Usuario yo = new Usuario ("root",hashClave,true);
        
        //control de salida a la aplicacion 
        boolean acceso ;
              
        String nombreU = txtUsuario.getText();
        String clave = txtClave.getText();
        String hashClave = Hash.sha1(clave);
        
        //conexion con bd
        BaseDatosOO bd = new BaseDatosOO();
        //busco el usuario en la bd
        Usuario yo = bd.buscarUsuario(nombreU);
        //cierre
        bd.cerrarBD();

        if (yo!=null && nombreU.equals(yo.getUsuario())
                && hashClave.equals(yo.getClave())) {
            acceso = true;
        } else {
            txtFeedback.setText("Credenciales erroneas.");
            acceso = false;
        }

        if (acceso) {
            //Una vez identificado cargo la ventana principal
            try {
                //Cargo la vista
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ControladorLogin.class.getResource("/vista/vistaPrincipal.fxml"));

                //creo una base y cargo en ella los componentes
                Parent root = loader.load();

                //creo una escena que viene de root
                Scene escena = new Scene(root);

                //ahora creo un escenario
                Stage escenario = new Stage();
                // Seteo la scene y la muestro
                escenario.setTitle("MAS Gestión.");
                // Set the application icon.
                escenario.getIcons().add(new Image("/vista/logoMAS.png"));
                escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
                escenario.setScene(escena);//cargo la escena en el escenario
                //la muestro con show para poder cerrar login
                //con showandwait se queda abierta login
                escenario.show(); 
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            //recupero el escenario actual para cerrarlo
            Stage escenario = (Stage) btnAcceder.getScene().getWindow();
            escenario.close();
        }

    }

    @FXML
    private void gestionUsuarios(ActionEvent event) {
        
        //control de salida a la aplicacion 
        boolean acceso ;
              
        String nombreU = txtUsuario.getText();
        String clave = txtClave.getText();
        String hashClave = Hash.sha1(clave);
        
        //conexion con bd
        BaseDatosOO bd = new BaseDatosOO();
        //busco el usuario en la bd
        Usuario yo = bd.buscarUsuario(nombreU);
        //cierre
        bd.cerrarBD();

        if (yo!=null && nombreU.equals(yo.getUsuario())
                && hashClave.equals(yo.getClave())) {
            acceso = true;
        } else {
            txtFeedback.setText("Credenciales erroneas.");
            acceso = false;
        }
        
        if (acceso) {
            //Una vez identificado cargo la ventana principal
            try {
                //Cargo la vista
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/vista/vistaGestionUsuarios.fxml"));
                
                Parent root = loader.load();
                
               
                 ControladorGestionUsuarios controladorGU = loader.getController();
                //para enviar el usuario al controlador Gestion de Usuarios
                controladorGU.recuperarUsuario(yo);
                //creo una base y cargo en ella los componentes

                //creo una escena que viene de root
                Scene escena = new Scene(root);

                //ahora creo un escenario
                Stage escenario = new Stage();
                // Seteo la scene y la muestro
                escenario.setTitle("Gestión Usuarios.");
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
    }

}
