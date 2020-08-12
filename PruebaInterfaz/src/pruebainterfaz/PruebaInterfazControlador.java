/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebainterfaz;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author elena
 */
public class PruebaInterfazControlador {
    
 /**
 * Called when the user clicks the new button. Opens a dialog to edit
 * details for a new person.
 */
@FXML
private void manejadorbGestion() throws IOException {
        Stage gStage = new Stage();
    
        Parent root = FXMLLoader.load(getClass().getResource("Gestion.fxml"));
        
        gStage.setTitle("Gestion de Socios");
        gStage.initModality(Modality.WINDOW_MODAL);
        
        Scene scene = new Scene(root);
        
        gStage.setScene(scene);
        gStage.show();
    
   
}

    
}
