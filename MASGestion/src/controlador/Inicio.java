
package controlador;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modelo.BaseDatosOO;
import modelo.Usuario;
import util.Hash;

/**
 *
 * @author Abraham Garrido
 */
public class Inicio extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        //creo el primer usuario
        /*BaseDatosOO bd = new BaseDatosOO();
        String hashClave = Hash.sha1("123456") ;
        Usuario yo = new Usuario ("root",hashClave,true);
        bd.insertarUsuario(yo);*/
        
       try {
            //Cargo la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inicio.class.getResource("/vista/vistaLogin.fxml"));
 
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
 
            // Cargo el scene
            Scene scene = new Scene(ventana);
 
            // Seteo la scene y la muestro
            primaryStage.setTitle("Login.");
            // Set the application icon.
            primaryStage.getIcons().add(new Image("/vista/logoMAS.png"));

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
