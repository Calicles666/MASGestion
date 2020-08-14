/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.copy;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.BaseDatosOO;
import modelo.Socio;
import modelo.Socio.Actividad;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Abraham Garrido
 */
public class ControladorPrincipal implements Initializable {

    @FXML
    private Button btnAgregar;
    
    @FXML
    private TableView<Socio> tablaSocios;
    @FXML
    private TableColumn colDNI;
    @FXML
    private TableColumn colActividad;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colFechaNa;
    
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtFiltro;
    @FXML
    private ToggleButton tbtnSociosActivos;
    @FXML
    private ToggleButton tbtnPendientesPago;
    @FXML
    private Button btnInformeMorosos;
    @FXML
    private ImageView iViewLogo;
    
    @FXML
    private Button btnMostrarSocio;
    @FXML
    private TextField txtFeedback;
    
    
     //coleccion especial de JavaFX para poder mostrar los objetos. En socios tendremos 
    // almacenados en memoria todos los socios del club.
    private ObservableList<Socio> socios;
    
      //coleccion para resultados de filtrar por texto
    private ObservableList<Socio> sociosFiltrados;
    
      //coleccion para resultados de filtrar por fecha Membresia
    private ObservableList<Socio> sociosFiltradosMembresia;
    
      //coleccion para resultados de filtrar por activo
    private ObservableList<Socio> sociosFiltradosActivo;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //ingresarSociosPruebas();
        //crear la lista observable con todos los socios
         //conexion con bd
        BaseDatosOO bd = new BaseDatosOO();
        //extraigo los socios de la bd
        socios = FXCollections.observableArrayList(bd.queryAll());
        //cierre
        bd.cerrarBD();
        
        
        sociosFiltrados = FXCollections.observableArrayList();
        
        sociosFiltradosMembresia  = FXCollections.observableArrayList();
        
        sociosFiltradosActivo = FXCollections.observableArrayList();
        //texto personalizado para la tabla sin elementos
        tablaSocios.setPlaceholder(new Label("Ningún socio con los criterios seleccionados."));
        
        //enlazar las columnas de la tabla con los atributos
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        colFechaNa.setCellValueFactory(new PropertyValueFactory("fechaNacimiento"));
        colDNI.setCellValueFactory(new PropertyValueFactory("dni"));
        colActividad.setCellValueFactory(new PropertyValueFactory("actividad"));   
        
        //para hacer pruebas
        //inicializarSocios();
        
        // filtarSociosActivos va a llamar a filtrar
        //y éste hara la carga inicial correspondiente 
        filtrarSociosActivos(null); //el boton esta accionado por defecto llamo al método
        
        
        
    }//initialize    
    
    /**Método que aporta la funcionalidad al boton correspondiente cuando el usuario
     pulsa el mismo. Se encarga de cargar la ventana agregar socio y pasar al controlador
     de la misma la lista de socios
     @param event - El evento generado en la ventana que lo dispara*/
    @FXML
    private void agregarSocio(ActionEvent event) {
        
        try {
            //creo un cargador con el xml de la ventana
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/vista/vistaAgregarSocio.fxml"));
            //creo una base y cargo en ella los componentes
            Parent root = loader.load();
            //instancio en controlador de ésta vista y lo asocio
            ControladorAgregarSocio controladorAS = loader.getController();
            //para enviar la lista de personas a la ventana agregar Socio
            controladorAS.recuperarSocios(socios);
            //creo una escena que viene de root
            Scene escena = new Scene(root);
            
            
            //ahora creo un escenario
            Stage escenario = new Stage();
             // Seteo la scene y la muestro
            escenario.setTitle("Alta de Socio.");
            // Set the application icon.
            escenario.getIcons().add(new Image("/vista/logoMAS.png"));
            escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
            escenario.setScene(escena);//cargo la escena en el escenario
            escenario.showAndWait(); //la muestro hasta que se cierre por el usuario
            
            //recupero el socio creado en la ventana agregar socio
            Socio s = controladorAS.getSocio();
            //si he creado una persona en la ventana secundaria la añado a la lista
            if (s!=null){
                
                //podria añadirlo desde el Controlador AS pero creo mas 
                //corecto manipular los socios en el principal
                socios.add(s); 
                
                filtrar(null);
                
            }

        } catch (IOException ex) {
            //feedback al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error de E/S");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    /**Método que aporta la funcionalidad al boton correspondiente cuando el usuario
     pulsa el mismo. Se encarga de cargar la ventana modificar socio y pasar al 
     * controlador de la misma la lista de socios y el socio seleccionado
     @param event - El evento generado en la ventana que lo dispara*/
    @FXML
    private void modificarSocio(ActionEvent event) {

        //recuperamos el socio seleccionado de la tabla
        Socio sSeleccionado = tablaSocios.getSelectionModel().getSelectedItem();

        //si hay una persona seleccionada
        if (sSeleccionado != null) {

            try {
                //creo un cargador con el xml de la ventana
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/vista/vistaModificarSocio.fxml"));
                //creo una base y cargo en ella los componentes
                Parent root = loader.load();
                //instancio en controlador de ésta vista y lo asocio
                ControladorModificarSocio controladorMS = loader.getController();
                //para enviar la lista de personas y la persona seleccionada
                //a la ventana ModificarSocio
                controladorMS.recuperarSocios(socios ,sSeleccionado);
                //creo una escena que viene de root
                Scene escena = new Scene(root);

                //ahora creo un escenario
                Stage escenario = new Stage();
                // Seteo la scene y la muestro
                escenario.setTitle("Modificación de Socio.");
                // Set the application icon.
                escenario.getIcons().add(new Image("/vista/logoMAS.png"));
                escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
                escenario.setScene(escena);//cargo la escena en el escenario
                escenario.showAndWait(); //la muestro hasta que se cierre por el usuario

                //no necesito recuperar la persona la he modificado desde controladorMS
                tablaSocios.refresh();
                filtrar(null);

            } catch (IOException ex) {
                //feedback al usuario
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Error de E/S");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

           

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Ningún socio seleccionado.");
            alert.showAndWait();
        }

    }

    /**Método que aporta la funcionalidad al boton correspondiente cuando el usuario
     pulsa el mismo. Se encarga de eliminar al socio seleccionado en el TableView
     @param event - El evento generado en la ventana que lo dispara*/
    @FXML
    private void eliminarSocio(ActionEvent event) {
        
         //recuperamos el socio seleccionado de la tabla
        Socio sSeleccionado = tablaSocios.getSelectionModel().getSelectedItem();
        
        //si hay una persona seleccionada
        if (sSeleccionado!=null){
            
            socios.remove(sSeleccionado);
            //feedback al usuario
            txtFeedback.setText("Usuario borrado con éxito.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmacion");
            alert.setContentText("Usuario borrado con éxito.");
            alert.showAndWait();
                        
            //uso el metodo filtrar cada vez que modifico la lista de personas en vez de refrescar
             filtrar(null);
            
            
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Ningún socio seleccionado.");
            alert.showAndWait(); 
        }
    }

    /**Método que forma la lista sociosFiltrados en funcion de lo que haya escrito 
     en la casilla y los toglebutons que haya activos. Es el que controla la visualizacion
     correcta del TableView despues de cada evento. Todos los eventos lo llaman
     sin evento al concluir su trabajo si modifican los datos de socios
     @param even - Evento que lo dispara*/
    @FXML
    private void filtrar(KeyEvent event) {
        
        String filtro = txtFiltro.getText().toLowerCase();
        
        //limpio la lista de filtrados
        sociosFiltrados.clear();
        //filtro 1 por texto
        //si esta vacio el filtro que aparezcan todas las personas
        if (txtFiltro.getText().isEmpty()) {

            sociosFiltrados.addAll(socios);

        } else //si hay algo que filtrar
        {
            //recorreo personas pasando a la lista auxiliar personasFiltradas los que coincidan
            for (Socio aux : socios) {

                if (aux.getNombre().toLowerCase().contains(filtro)
                        || aux.getApellidos().toLowerCase().contains(filtro)
                        || aux.getDni().toLowerCase().contains(filtro)) {
                    sociosFiltrados.add(aux);
                }
            } //for
            

        }//else
        //filtro 2 por activos
        //si he creado una lista de sociosFiltradosActivo
        if (tbtnSociosActivos.isSelected()) //tambien !sociosFiltradosActivo.isEmpty()
        {
            //que queden en sociosFiltrados los comunes
            sociosFiltrados.retainAll(sociosFiltradosActivo); 
            
        }
        //filtro 3 por membresia expirada
        //si he creado una lista de sociosFiltradosMembresia
        if (tbtnPendientesPago.isSelected()) //tambien !sociosFiltradosMembresía.isEmpty()
        {
            //que queden en sociosFiltrados los comunes
            sociosFiltrados.retainAll(sociosFiltradosMembresia); 
           
        }
        
        //ahora sociosFiltrados contiene los socios correctos a mostrar
        tablaSocios.setItems(sociosFiltrados);
       
    }

    /**Método que crea la lista de sociosFiltradosActivos si está el boton 
     seleccionado y pasa el flujo a filtrar
     @param event - Evento que lo dispara*/
    @FXML
    private void filtrarSociosActivos(ActionEvent event) {
        
        //limpio la lista de filtrados
        sociosFiltradosActivo.clear();
        
        if (tbtnSociosActivos.isSelected()) {
            //recorro socios pasando a la lista auxiliar los activos
            for (Socio aux : socios) {

                if (aux.isActivo()) {
                    sociosFiltradosActivo.add(aux);
                }
            } //for
            
        } //si esta apagado paso a filtrar con la lista sociosFiltradosActivo vacia
        filtrar(null);

    }

    /**Método que crea la lista de sociosFiltradosMembresía si está el boton 
     seleccionado y pasa el flujo a filtrar*/
    @FXML
    private void filtrarPendientesPago(ActionEvent event) {

        //limpio la lista de filtrados
        sociosFiltradosMembresia.clear();
        if (tbtnPendientesPago.isSelected()) {

            //recorro socios pasando a la lista auxiliar los activos
            for (Socio aux : socios) {

                if ((aux.getFechaFinMembresia() != null)
                        && aux.getFechaFinMembresia().isBefore(LocalDate.now())) {
                    sociosFiltradosMembresia.add(aux);
                }
            } //for

        } //si esta apagado paso a filtrar con la lista sociosFiltradosMEmbresía vacia
        filtrar(null);
    }

    @FXML
    private void generarInformeMorosos(ActionEvent event) {
    }

    /**Método que aporta la funcionalidad al boton correspondiente cuando el usuario
     pulsa el mismo. Se encarga de cargar la ventana detalles de socio y pasar al 
     * controlador de la misma el socio seleccionado para que lo visualice
     @param event - El evento generado en la ventana que lo dispara*/
    @FXML
    private void mostarDetalleSocio(ActionEvent event) {
        
        //recuperamos el socio seleccionado de la tabla
        Socio sSeleccionado = tablaSocios.getSelectionModel().getSelectedItem();

        //si hay una persona seleccionada
        if (sSeleccionado != null) {

            try {
                //creo un cargador con el xml de la ventana
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/vista/vistaDetalleSocio.fxml"));
                //creo una base y cargo en ella los componentes
                Parent root = loader.load();
                //instancio en controlador de ésta vista y lo asocio
                ControladorDetalleSocio controladorDS = loader.getController();
                //no necesito enviar la lista de personas, solo la persona seleccionada
                //a la ventana ModificarSocio para su visualizacion
                controladorDS.recuperarSocio(sSeleccionado);
                //creo una escena que viene de root
                Scene escena = new Scene(root);

                //ahora creo un escenario
                Stage escenario = new Stage();
                // Seteo la scene y la muestro
                escenario.setTitle("Detalles de Socio.");
                // Set the application icon.
                escenario.getIcons().add(new Image("/vista/logoMAS.png"));
                escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
                escenario.setScene(escena);//cargo la escena en el escenario
                escenario.showAndWait(); //la muestro hasta que se cierre por el usuario

                
                

            } catch (IOException ex) {
                //feedback al usuario
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Error de E/S");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Ningún socio seleccionado.");
            alert.showAndWait();
        }
    }

    /**Método que inicia la ventana de login*/
    @FXML
    private void solicitarCredenciales() {
        
                try {
            //creo un cargador con el xml de la ventana
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/vista/vistaLogin.fxml"));
            //creo una base y cargo en ella los componentes
            Parent root = loader.load();
            /*
            //instancio en controlador de ésta vista y lo asocio
            ControladorDetalleSocio controladorDS = loader.getController();
            //no necesito enviar la lista de personas, solo la persona seleccionada
            //a la ventana ModificarSocio para su visualizacion
            controladorDS.recuperarSocio(sSeleccionado);*/
            //creo una escena que viene de root
            Scene escena = new Scene(root);

            //ahora creo un escenario
            Stage escenario = new Stage();
            // Seteo la scene y la muestro
            escenario.setTitle("Solicitud Credenciales.");
            // Set the application icon.
            escenario.getIcons().add(new Image("/vista/logoMAS.png"));
            escenario.initModality(Modality.APPLICATION_MODAL); //ventana modal
            escenario.setScene(escena);//cargo la escena en el escenario
            escenario.showAndWait(); //la muestro hasta que se cierre por el usuario

        } catch (IOException ex) {
            //feedback al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error de E/S");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

            
        
    }//fin solicitarCredenciales
    /*Metodo para que la lista de socios tenga datos para probar*/
    private void inicializarSocios() {
        
        
        Socio s1 = new Socio ("75795734Q","Abraham","Garrido Rosillo",
                Actividad.MUAYTHAI, LocalDate.of(1982, Month.FEBRUARY, 10),true);
        Socio s2 = new Socio ("75756437A","Elena","Gutierrez Barrios",
                Actividad.YOGA, LocalDate.of(1978, Month.JUNE, 26),false);
        s2.setFechaBaja(LocalDate.of(2020,Month.MARCH,1));
        Socio s3 = new Socio ("79412892X","Jimena","Garrido Gutierrez",
                Actividad.TAEKWONDO, LocalDate.of(2016, Month.JANUARY, 16),true);
        Socio s4 = new Socio ("79211892F","Pancho","Garrido Gutierrez",
                Actividad.MUAYTHAI, LocalDate.of(2013, Month.DECEMBER, 4),true);
        Socio s5 = new Socio ("79211891Y","Jose","Garrido Gutierrez",
                Actividad.MUAYTHAI, LocalDate.of(2009, Month.MARCH, 10),LocalDate.of(2020, Month.MARCH, 10));
        
        socios.add(s1);
        socios.add(s2);
        socios.add(s3);
        socios.add(s4);
        socios.add(s5);
        
        
    }

    /**Método que introduce varios socio en la BD para testear
     */
    private void ingresarSociosPruebas() {
        
        Socio s1 = new Socio ("75795734Q","Abraham","Garrido Rosillo",
                Actividad.MUAYTHAI, LocalDate.of(1982, Month.FEBRUARY, 10),true);
        Socio s2 = new Socio ("75756437A","Elena","Gutierrez Barrios",
                Actividad.YOGA, LocalDate.of(1978, Month.JUNE, 26),false);
        s2.setFechaBaja(LocalDate.of(2020,Month.MARCH,1));
        Socio s3 = new Socio ("79412892X","Jimena","Garrido Gutierrez",
                Actividad.TAEKWONDO, LocalDate.of(2016, Month.JANUARY, 16),true);
        Socio s4 = new Socio ("79211892F","Pancho","Garrido Gutierrez",
                Actividad.MUAYTHAI, LocalDate.of(2013, Month.DECEMBER, 4),true);
        Socio s5 = new Socio ("79211891Y","Jose","Garrido Gutierrez",
                Actividad.MUAYTHAI, LocalDate.of(2009, Month.MARCH, 10),LocalDate.of(2020, Month.MARCH, 10));
         //conexion con bd
        BaseDatosOO bd = new BaseDatosOO();
        //ingreso los socios creados en la  bd
                
        bd.insertarSocio(s1);
        bd.insertarSocio(s2);
        bd.insertarSocio(s3);
        bd.insertarSocio(s4);
        bd.insertarSocio(s5);
        //cierre
        bd.cerrarBD();
        
    }
    
}
