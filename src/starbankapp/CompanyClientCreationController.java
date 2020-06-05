/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starbankapp;

import Client.Company;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static starbankapp.WithdrawalController.ventanaEmergente;

/**
 * FXML Controller class
 *
 * @author SIMON
 */
public class CompanyClientCreationController implements Initializable {

    @FXML
    private TextField cajatTextoID;
    @FXML
    private TextField cajaTextoNombre;
    @FXML
    private TextField cajaTextoCelular;
    @FXML
    private TextField cajaTextoDireccion;
    @FXML
    private TextField cajaTextoOcupacion;
    @FXML
    private TextField cajaTextoNIT;
    @FXML
    private TextField cajaTextoNEmpresa;
    @FXML
    private TextField cajaTextoSector;
    @FXML
    private Button botonGuardar;
    Cashier cashier = new Cashier();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void botonGuardarAccionado(ActionEvent event) {
        if (obtenerDatosEmpresa() != null) {
            cashier.addCompanyClient(obtenerDatosEmpresa());
        }

    }

    private Company obtenerDatosEmpresa() {
        String ID = cajatTextoID.getText();
        String nombre = cajaTextoNombre.getText();
        String cel = cajaTextoCelular.getText();
        String direc = cajaTextoDireccion.getText();
        String ocupa = cajaTextoOcupacion.getText();
        String NIT = cajaTextoNIT.getText();
        String nEmpresa = cajaTextoNEmpresa.getText();
        String sector = cajaTextoSector.getText();

        if (ID.length() < 10 || cel.length() < 10 || NIT.length() < 10) {
            ventanaEmergente("error", "Campos con menos de 10 dígitos");
            return null;
        }
        if (nombre.length() < 10 || direc.length() < 10 || ocupa.length() < 10 || nEmpresa.length() < 10 || sector.length() < 10) {
            ventanaEmergente("error", "Campos incompletos.");
        }
        Company company = new Company(NIT, nEmpresa, sector, ID, nombre, cel, direc, ocupa, false);
        return company;
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
        cajatTextoID.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
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
                cajaTextoNIT.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
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
