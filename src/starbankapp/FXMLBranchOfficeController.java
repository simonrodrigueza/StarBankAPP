
package starbankapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SIMON
 */
public class FXMLBranchOfficeController implements Initializable {

    @FXML
    private ChoiceBox<String> cajaSucursales = new ChoiceBox();

    private static String branch;
    private static String branchID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BranchOffice bo = new BranchOffice();
        ArrayList<String> bn = bo.createOffices();
        for (String branch : bn) {
            cajaSucursales.getItems().add(branch);
        }

    }

    @FXML
    private void botonIngresar(ActionEvent event) throws Exception {
        branch = cajaSucursales.getValue();
        assignBranchID(branch);
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

    private void assignBranchID(String b) {
        b = branch;
        if(b.equalsIgnoreCase("StarBank Centro")){
            branchID = "AAA0";
        } else if(b.equalsIgnoreCase("StarBank Carrera 23A")){
            branchID = "AAA1";
        } else{
            branchID = "AAA2";
        }

    }

    public static String getBranch() {
        return branch;
    }

    public static String getBranchID() {
        return branchID;
    }

}
