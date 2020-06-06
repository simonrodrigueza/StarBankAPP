package starbankapp;

import Account.Account;
import Account.AccountFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import static starbankapp.WithdrawalController.ventanaEmergente;

/**
 * FXML Controller class
 *
 * @author SIMON
 */
public class ConsignController implements Initializable {

    @FXML
    private ChoiceBox<String> cajaCuentaOrigen = new ChoiceBox();
    @FXML
    private TextField cajaIDOrigen;
    @FXML
    private TextField cajaCantidad;
    @FXML
    private ChoiceBox<String> cajaCuentaDestino = new ChoiceBox();
    @FXML
    private TextField cajaIDDestino;
    @FXML
    private PasswordField cajaClave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cajaCuentaOrigen.getItems().addAll("Ahorros", "Corriente");
        cajaCuentaDestino.getItems().addAll("Ahorros", "Corriente");
    }

    @FXML
    public void botonConsignar(ActionEvent event) throws Exception {
        AccountFacade accFacade = AccountFacade.getInstance();
        String tipoCuentaOrigen = cajaCuentaOrigen.getValue();
        String tipoCuentaDestino = cajaCuentaDestino.getValue();
        String idOrigen = cajaIDOrigen.getText();
        String idDestino = cajaIDDestino.getText();
        String clave = cajaClave.getText();

        if (tipoCuentaDestino == null || tipoCuentaOrigen == null) {
            ventanaEmergente("error", "Seleccione tipo de cuenta.");
            return;
        }
        int cantidad = 0;
        if (cajaCantidad.getText().length() > 0) {
            Integer.parseInt(cajaCantidad.getText());
        } else {
            ventanaEmergente("error", "Ingrese cantidad");
            return;
        }
        if (idOrigen.length() < 4 || clave.length() < 4 || cajaCantidad.getText().length() < 5 || idDestino.length() < 4) {
            ventanaEmergente("error", "Datos incorrectos.");
            return;
        }

        Account SourceAccount = accFacade.searchAccount(idOrigen, tipoCuentaOrigen);
        if (SourceAccount != null) {
            boolean correct = SourceAccount.consign(cantidad, clave, idDestino, tipoCuentaDestino);
            if (correct) {
                ventanaEmergente("OK", "EXITO");
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

                ventanaEmergente("error", "Proceso no finalizado");

            }

        } else {
            ventanaEmergente("Error", "Cuenta no encontrada.");
            return;
        }

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

        cajaClave.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {

                return null;
            } else {
                return change;
            }
        }));

        cajaCantidad.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
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
        cajaIDOrigen.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {

                return null;
            } else {
                return change;
            }
        }));
        cajaIDDestino.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 4) {

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
