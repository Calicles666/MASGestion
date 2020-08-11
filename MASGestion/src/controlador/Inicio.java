
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

/**
 *
 * @author Abraham Garrido
 */
public class Inicio extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
       try {
            //Cargo la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inicio.class.getResource("/vista/vistaPrincipal.fxml"));
 
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
 
            // Cargo el scene
            Scene scene = new Scene(ventana);
 
            // Seteo la scene y la muestro
            primaryStage.setTitle("MAS Gestión.");
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
