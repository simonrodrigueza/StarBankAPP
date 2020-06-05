package starbankapp;

import Account.Account;
import Account.AccountFacade;
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

/**
 * FXML Controller class
 *
 * @author SIMON
 */
public class WithdrawalController implements Initializable {

    @FXML
    private TextField cajaCuentaID;
    @FXML
    private ChoiceBox<String> cajaTipoCuenta = new ChoiceBox();
    @FXML
    private TextField cajaCantidadRetiro;
    @FXML
    private PasswordField cajaClave;
    @FXML
    private Button botonRetiro;
    @FXML
    private Button botonCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cajaTipoCuenta.getItems().addAll("Ahorros", "Corriente");
    }

    @FXML
    public void botonRetiro(ActionEvent event) throws Exception {
        String cuenta = cajaTipoCuenta.getValue();
        String cuentaID = cajaCuentaID.getText();
        String claveCuenta = cajaClave.getText();
        int cantidad;
        if (cajaCantidadRetiro.getText().length() > 0) {
            cantidad = Integer.parseInt(cajaCantidadRetiro.getText());
        } else {
            ventanaEmergente("Error", "Ingrese datos correctamente");
            return;
        }
        if (cuenta == null) {
            ventanaEmergente("Error", "Ingrese tipo cuenta");
            return;
        }
        if (cuentaID.length() < 4 || claveCuenta.length() < 4) {
            ventanaEmergente("Error", "ID o cuenta incorrecto");
            return;
        }

        AccountFacade accFacade = AccountFacade.getInstance();
        Account AccountSource = accFacade.searchAccount(cuentaID, cuenta);
        if(AccountSource == null){
            ventanaEmergente("Error", "Cuenta no encontrada.");
            return;
        }
        boolean correct = AccountSource.withdraw(cantidad, claveCuenta);
        if (correct) {
            
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
            ventanaEmergente("Error", "No se realizó el proceso.");
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

        cajaCantidadRetiro.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
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
        cajaCuentaID.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
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

    /**
     * Genera una ventana emergente con un mensaje de error.
     *
     * @param texto
     * @param mensaje
     */
    public static void ventanaEmergente(String texto, String mensaje) {
        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle(texto);
        ventana.setMinWidth(250);
        ventana.setMinHeight(250);
        Label label = new Label();
        label.setText(mensaje);
        Button botonCerrar = new Button("Cerrar");
        botonCerrar.setOnAction(e -> ventana.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, botonCerrar);
        layout.setAlignment(Pos.CENTER);
        Scene escena = new Scene(layout);
        ventana.setScene(escena);
        ventana.showAndWait();
    }

}
