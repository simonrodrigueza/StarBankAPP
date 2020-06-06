
package starbankapp;

import Account.CurrentAccount;
import Account.SavingAccount;
import Client.Client;
import Client.ClientFacade;
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
public class AccountCreationController implements Initializable {

    @FXML
    private Button botonCrear;
    @FXML
    private TextField cajaTextoID;
    @FXML
    private PasswordField cajaClave;
    @FXML
    private ChoiceBox<String> cajaTipoCliente = new ChoiceBox();
    @FXML
    private ChoiceBox<String> cajaTipoCuenta = new ChoiceBox();
    Cashier cashier = new Cashier();
    ClientFacade clientFacade = ClientFacade.getInstance();

    @FXML
    private void botonConsultarAccionado(ActionEvent event) {
        String ID = cajaTextoID.getText();
        String tipoCliente = cajaTipoCliente.getValue();
        if (tipoCliente == null) {
            ventanaEmergente("Error", "Seleccione tipo de cliente");
            return;
        }
        Client client = clientFacade.searchClient(ID, tipoCliente);
        if (client == null) {
            ventanaEmergente("Error", "No existe cliente con ese ID");
        } else {
            cajaClave.setDisable(false);
            botonCrear.setDisable(false);

        }
    }

    @FXML
    private void botonCrear(ActionEvent event) throws Exception {
        if (cajaClave.getText().length() != 4) {
            ventanaEmergente("Error", "Mínimo 4 dígitos de contraseña.");
            return;
        }
        String tipoCuenta = cajaTipoCuenta.getValue();
        if (tipoCuenta == null) {
            ventanaEmergente("Error", "Seleccione tipo de cliente");
            return;
        }

        String ID = cajaTextoID.getText();
        String tipoCliente = cajaTipoCliente.getValue();
        Client client = clientFacade.searchClient(ID, tipoCliente);
        cashier.addNumberOfAccountToClient(client);
        String idCuenta = "";
        if (tipoCuenta.equalsIgnoreCase("ahorros")) {
            SavingAccount newAccount = new SavingAccount(0, false, cajaTextoID.getText(), cajaTipoCliente.getValue(), cajaClave.getText());
            cashier.addSavignAccount(newAccount);
            idCuenta = newAccount.getId();
        } else if (tipoCuenta.equalsIgnoreCase("corriente")) {
            CurrentAccount newAccount = new CurrentAccount(0, false, cajaTextoID.getText(), cajaTipoCliente.getValue(), cajaClave.getText());
            cashier.addCurrentAccount(newAccount);
            idCuenta = newAccount.getId();
        }

        ventanaEmergente("OK", "El ID de su cuenta es: " + idCuenta);
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
    public void hileraID(javafx.scene.input.KeyEvent keyEvent) {
        cajaTextoID.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
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
    public void hileraClave(javafx.scene.input.KeyEvent keyEvent) {
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cajaTipoCliente.getItems().addAll("Natural", "Compañia");
        cajaTipoCuenta.getItems().addAll("Ahorros", "Corriente");
    }



}
