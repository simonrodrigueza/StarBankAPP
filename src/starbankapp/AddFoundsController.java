/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starbankapp;

import Account.Account;
import Account.AccountFacade;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
public class AddFoundsController implements Initializable {

    @FXML
    private ChoiceBox<String> cajaTipoCuenta = new ChoiceBox();
    @FXML
    private TextField cajaIDCuenta;
    @FXML
    private TextField cajaCantidad;
    @FXML
    private PasswordField cajaClave;
    @FXML
    private Button botonAñadir;
    @FXML
    private Button botoCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cajaTipoCuenta.getItems().addAll("Ahorros", "Corriente");
    }

    @FXML
    private void botonAñadir(ActionEvent event) throws Exception {
        String tipoCuenta = cajaTipoCuenta.getValue();
        String idCuenta = cajaIDCuenta.getText();
        int cantidad = 0;
        String clave = cajaClave.getText();
        if (cajaCantidad.getText().length() > 0) {
            cantidad = Integer.parseInt(cajaCantidad.getText());
        }
        if (tipoCuenta == null) {
            ventanaEmergente("error", "Elija tipo de cuenta");
            return;
        }
        if (idCuenta.length() <  4 || clave.length() < 4) {
            ventanaEmergente("Error", "Datos incorrectos");
            return;
        }
        AccountFacade accFacade = AccountFacade.getInstance();
        Account AccountSource = accFacade.searchAccount(idCuenta, tipoCuenta);
        boolean correct = AccountSource.addFounds(cantidad, clave);
        if (correct) {
            ventanaEmergente("ok", "Proceso finalizado");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1));
            stage1.setResizable(false);
            stage1.show();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

        } else {
            ventanaEmergente("Error", "Proceso no realizado.");
        }
    }

    @FXML
    private void botonCancelar(ActionEvent event) throws Exception {
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

        cajaCantidad.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 10) {

                return null;
            } else {
                return change;
            }
        }));

        cajaClave.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {

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
        cajaIDCuenta.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 5) {

                return null;
            } else {
                return change;
            }
        }));
        char car = keyEvent.getCharacter().charAt(0);
        if ((car < 'A' || car > 'Z') && (car < '0' || car > '9')) {

            keyEvent.consume();
        }
    }


}
