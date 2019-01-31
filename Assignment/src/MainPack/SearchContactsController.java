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
import java.util.Collections;
import java.util.List;

public class SearchContactsController {
    private List<Contact> foundContacts;
    public static int sortedOrder;
    public static Stage firstPageControllerStage;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;


    @FXML
    public void searchContactStart(ActionEvent event) throws IOException {
        if(firstNameTextField.getText()!=null||lastNameTextField.getText()!=null||phoneTextField.getText()!=null){
            foundContacts = Datasource.getInstance().searchContacts(firstNameTextField.getText(), lastNameTextField.getText(), phoneTextField.getText());
            //sortFoundContacts(foundContacts, sortedOrder);
            //Collections.sort(foundContacts);
            System.out.println("A: "+foundContacts.toString());
            ((Node) (event.getSource())).getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("firstPage.fxml"));
            System.out.println(1111111111);
            Parent root = (Parent)loader.load();
            System.out.println(222222222);
            FirstPageController firstPageController = loader.getController();

            firstPageController.setContactsListView(foundContacts);
            //firstPageController.contacts = foundContacts;
            System.out.println("B: "+firstPageController.contacts.toString());
            System.out.println(sortedOrder);
            firstPageController.sortedOrder = sortedOrder;
            firstPageController.reSort(sortedOrder);

            Scene scene = new Scene(root);
            Stage stage = firstPageControllerStage;
            stage.setScene(scene);
            stage.show();
            System.out.println(3333333);


            //firstPageController.contacts = foundContacts;

        }
    }

    public void sortFoundContacts(List<Contact> foundContacts, int sortOrder){
        if(sortOrder==1){

        }
        else if(sortOrder==2){

        }
        else if(sortOrder==3){

        }
        else if(sortOrder==4){

        }
    }
}
