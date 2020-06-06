
package starbankapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Client.NaturalPerson;
import java.awt.event.KeyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import static starbankapp.WithdrawalController.ventanaEmergente;

/**
 * FXML Controller class
 *
 * @author SIMON
 */
public class NaturalClientCreationController implements Initializable {

    @FXML
    private TextField cajaTextoID;
    @FXML
    private TextField cajaTextoNombre;
    @FXML
    private TextField cajaTextoCelular;
    @FXML
    private TextField cajaTextoDireccion;
    @FXML
    private TextField cajaTextoOcupacion;
    Cashier cashier = new Cashier();

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void botonGuardarAccionado(ActionEvent event) throws Exception {
        if (obtenerDatosCliente() != null) {
            cashier.addNaturalClient(obtenerDatosCliente());
            ventanaEmergente("OK", "Cliente Creado.");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1));
            stage1.setResizable(false);
            stage1.show();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

        }

    }

    private NaturalPerson obtenerDatosCliente() {
        String id = cajaTextoID.getText();

        String nombre = cajaTextoNombre.getText();
        String cel = cajaTextoCelular.getText();
        String direcc = cajaTextoDireccion.getText();
        String ocupa = cajaTextoOcupacion.getText();

        if (id.length() < 10 || cel.length() < 10) {
            ventanaEmergente("ERROR", "ID  o número mínimo de 10 dígitos");
            return null;
        }
        if (nombre.length() < 10 || direcc.length() < 10 || ocupa.length() < 5) {
            ventanaEmergente("ERROR", "Llene los campos correctamente.");
            return null;
        }
        NaturalPerson natPerson = new NaturalPerson(id, nombre, cel, direcc, ocupa, false);
        return natPerson;
    }

    @FXML
    public void botonCancelar(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root1));
        stage1.setResizable(false);
        stage1.show();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void hileraNumeros(javafx.scene.input.KeyEvent keyEvent) {
        cajaTextoID.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 10) {

                return null;
            } else {
                return change;
            }
        }));
        cajaTextoCelular.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 10) {

                return null;
            } else {
                return change;
            }
        }));
        char car = keyEvent.getCharacter().charAt(0);
        if ((car < '0' || car > '9')) {
            keyEvent.consume();
        }
    }

    @FXML
    public void hileraLetras(javafx.scene.input.KeyEvent keyEvent) {

        char car = keyEvent.getCharacter().charAt(0);
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z')
                && car != 'á' //Minúsculas
                && car != 'é'
                && car != 'í'
                && car != 'ó'
                && car != 'ú'
                && car != 'Á' //Mayúsculas
                && car != 'É'
                && car != 'Í'
                && car != 'Ó'
                && car != 'Ú'
                && car != 'ñ'
                && car != 'Ñ'
                && (car != (char) KeyEvent.VK_SPACE)) {
            keyEvent.consume();
        }
    }


}
