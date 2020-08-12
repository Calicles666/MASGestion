/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebainterfaz;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author elena
 */
public class PruebaInterfaz extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
       /**
 * Opens a dialog to edit details for the specified person. If the user
 * clicks OK, the changes are saved into the provided person object and true
 * is returned.
 * 
 * @param 
 * @return true if the user clicked OK, false otherwise.
 */
/*public void mostrarGestion() {
    try {
        Parent rootG = FXMLLoader.load(getClass().getResource("Gestion.fxml"));
        
            
        
        
        // Create the dialog Stage.
        Stage gestionStage = new Stage();
        gestionStage.setTitle("Gestion de Socios");
        gestionStage.initModality(Modality.WINDOW_MODAL);
        
        Scene sceneG = new Scene(rootG);
        gestionStage.setScene(sceneG);

        // Set the person into the controller.
        //PersonEditDialogController controller = loader.getController();
        //controller.setDialogStage(dialogStage);
        //controller.setPerson(person);

        // Show the dialog and wait until the user closes it
        gestionStage.showAndWait();

        
    } catch (IOException e) {
        e.printStackTrace();
        
    }
   }*/
    
}
