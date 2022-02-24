/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizador.de.rutas;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Eloy Barca
 */
public class FormularioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnBuscar;
    @FXML
    private Label lbRuta;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnCargar;
    @FXML
    private Spinner spin;
    @FXML
    private Button btnCalcular;
    @FXML
    private Label lbMostrarRuta;
    @FXML
    private Label lbMostrarTiempo;
    @FXML
    private Label lbCarga;
    File selectedFile;
    Calculos calc = new Calculos();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void botonArchivo(ActionEvent event) {

        FileChooser file = new FileChooser();

        selectedFile = file.showOpenDialog(null);
        file.setTitle("Buscar...");

        file.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*"),
                new FileChooser.ExtensionFilter("XLS", "*.xls"));

        String ruta = selectedFile.getAbsolutePath();
        lbRuta.setText(ruta);

    }

    public void botonSalir(ActionEvent event) {
        Platform.exit();
        System.exit(0);

    }

    public void botonCargar(ActionEvent event) throws Exception {

        if (calc.iniciarMatriz(selectedFile) == true) {
            lbCarga.setVisible(true);
            lbCarga.setText("Datos Cargados");
        } else {
            lbCarga.setVisible(true);
            lbCarga.setText("Fallo al cargar los datos");
        }
    }

    public void botonCalcular(ActionEvent event) {
        int num = (Integer) spin.getValue();
        String mostrar = "";

        int[] ruta = calc.calcularRuta(num);
        for (int i = 0; i < ruta.length; i++) {
            if (i == ruta.length - 1) {
                mostrar = mostrar + ruta[i];
            } else {
                mostrar = mostrar + ruta[i] + " - ";
            }
        }
        double tiempo = calc.calcularTiempo(num);
        String formato = "%.3f";
        String t = String.format(formato, tiempo);

        lbMostrarRuta.setVisible(true);
        lbMostrarRuta.setText(mostrar);
        lbMostrarTiempo.setVisible(true);
        lbMostrarTiempo.setText(t);

    }

}
