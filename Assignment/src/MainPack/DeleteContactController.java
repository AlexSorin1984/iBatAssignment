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
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class DeleteContactController {
    public static List<Contact> foundContacts;
    private static int id;
    public static int sortedOrder;
    public static Stage firstPageControllerStage;

    @FXML
    private Button deleteContactButton;
    @FXML
    private Button cancelDeleteContactButton;

    @FXML
    public void initialize(){

    }

    public static void setId(int id) {
        DeleteContactController.id = id;
    }

    @FXML
    public void deleteContact(ActionEvent event) throws IOException {
        System.out.println("The id to be deleted is "+id);
        int index=0;
        for(Contact contact:foundContacts){
            System.out.println(contact.getFirstName()+contact.getLastName()+contact.getId());
            if(contact.getId()==id){
                index = foundContacts.indexOf(contact);
            }
        }
        Contact contactToBeDeleted = Datasource.getInstance().retrieveContact(id);
        System.out.println("DELETING "+contactToBeDeleted);
        Datasource.getInstance().deleteContact(id);
        System.out.println("Contact with the id "+id+" was deleted.");
        ((Node) (event.getSource())).getScene().getWindow().hide();
        foundContacts.remove(index);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("firstPage.fxml"));
        //Parent root = (Parent)loader.load();
        //FirstPageController firstPageController = loader.getController();
        //Stage stage = (Stage)root.getScene().getWindow();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root, 850, 650));
        //stage.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("firstPage.fxml"));
        Parent root = (Parent)loader.load();
        FirstPageController firstPageController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = firstPageControllerStage;
        stage.setScene(scene);
        stage.show();
        System.out.println(sortedOrder);
        //foundContacts.remove(contactToBeDeleted);
        //System.out.println("!!!!!!!!!!  "+foundContacts.toString());
        //firstPageController.contacts = foundContacts;
        firstPageController.setContactsListView(foundContacts);
        firstPageController.sortedOrder = sortedOrder;
        firstPageController.reSort(sortedOrder);
    }

    @FXML
    public void cancelDeleteContact(ActionEvent event){
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
