package MainPack;

import MainPack.model.Datasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;


public class LoginPageController {
    @FXML
    public static String loggedInUser; //MUST BE STATIC BECAUSE WHEN NEW STAGE IS CREATED A NEW CONTROLLER INSTANCE IS CREATED AND THE VALUE IS REINITIATED TI ZZZ
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginbutton;
    @FXML
    private Label loginerror;


    @FXML
    public void initialize() {
        System.out.println("Initializing LoginPageController.");
        //dataSource = Datasource.getInstance();
    }

    @FXML
    public void onLoginButtonClicked(ActionEvent event) {
        loggedInUser = username.getText();
        String typedPassword = password.getText();
        boolean succes = Datasource.getInstance().loginSuccessful(loggedInUser, typedPassword);

        if (!succes) {
            loginerror.setText("Login unsuccessful. Try again.");
        } else {
            System.out.println("Welcome " + loggedInUser);
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("firstPage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("My New Stage Title");
                stage.setScene(new Scene(root, 850, 650));
                stage.show();
                FirstPageController.setLoggedInUser(loggedInUser);
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}






