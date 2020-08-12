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
public class PruebaInterfazGestion extends Application {
    
    @Override
    public void start(Stage gStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Gestion.fxml"));
        
        gStage.setTitle("Gestion de Socios");
        gStage.initModality(Modality.WINDOW_MODAL);
        
        Scene scene = new Scene(root);
        
        gStage.setScene(scene);
        gStage.show();
        
       
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    

    
}
