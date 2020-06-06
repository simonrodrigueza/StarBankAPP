
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
public class ActivateController implements Initializable {

    @FXML
    private ChoiceBox<String> cajaTipoCuenta = new ChoiceBox();
    @FXML
    private TextField cajaIDCuenta;
    @FXML
    private TextField cajaCantidad;
    @FXML
    private PasswordField cajaClave;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cajaTipoCuenta.getItems().addAll("Ahorros", "Corriente");
    }

    @FXML
    private void botonActivar(ActionEvent event) throws Exception {
        Cashier cashier = new Cashier();
        String tipoCuenta = cajaTipoCuenta.getValue();
        String clave = cajaClave.getText();
        String cuentaID = cajaIDCuenta.getText();
        if (tipoCuenta == null) {
            ventanaEmergente("error", "Elija tipo cuenta.");
            return;
        }
        int cantidad = 0;
        if (cajaCantidad.getText().length() > 0) {
            cantidad = Integer.parseInt(cajaCantidad.getText());
        } else {
            ventanaEmergente("error", "Ingrese cantidad");
            return;
        }
        if (clave.length() < 4 || cuentaID.length() < 4 || cajaCantidad.getText().length() < 5) {
            ventanaEmergente("error", "Ingrese datos correctamente");
            return;
        }
        AccountFacade accFacade = AccountFacade.getInstance();
        Account account = accFacade.searchAccount(cuentaID, tipoCuenta);
        if (account == null) {
            ventanaEmergente("error", "Cuenta no encontrada");
            return;
        }
        boolean correcto = cashier.activateAccount(cuentaID, tipoCuenta, cantidad);
        if (!correcto) {
            ventanaEmergente("error", "Proceso no finalizado.");
            return;
        } else {
            ventanaEmergente("OK","Cuenta Activada");
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
        cajaIDCuenta.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
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
