package MainPack;

import MainPack.model.Contact;
import MainPack.model.Datasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddContactController {
    public static List<Contact> foundContacts;
    public static int sortedOrder;
    public static Stage firstPageControllerStage;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Button addContactSaveButton;

    public void addContactSave(ActionEvent event) throws IOException {
        System.out.println("The following contact will be added: "+firstNameTextField.getText()+" / "+lastNameTextField.getText()+" / "+phoneTextField.getText());
        Datasource.getInstance().addContact(firstNameTextField.getText(), lastNameTextField.getText(), phoneTextField.getText());
        Contact justCreatedContact = Datasource.getInstance().retrieveContact(firstNameTextField.getText(), lastNameTextField.getText(), phoneTextField.getText());
        ((Node) (event.getSource())).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("firstPage.fxml"));
        Parent root = (Parent)loader.load();
        FirstPageController firstPageController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = firstPageControllerStage;
        stage.setScene(scene);
        stage.show();
        System.out.println(sortedOrder);
        foundContacts.add(justCreatedContact);
        //firstPageController.contacts = foundContacts;
        firstPageController.setContactsListView(foundContacts);
        firstPageController.sortedOrder = sortedOrder;
        firstPageController.reSort(sortedOrder);
    }

}
