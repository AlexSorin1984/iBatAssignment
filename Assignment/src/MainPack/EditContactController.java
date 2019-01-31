package MainPack;

import MainPack.model.Contact;
import MainPack.model.Datasource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


public class EditContactController {
    public static List<Contact> foundContacts;
    public static int sortedOrder;
    public static Stage firstPageControllerStage;
    private static boolean firstNameChanged = false;
    private static boolean lastNameChanged = false;
    private static boolean phoneChanged = false;
    private static int id;
    private static String firstName;
    private static String lastName;
    private static String phone;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Button editContactSaveChanges;

    public static void setId(int id) {
        EditContactController.id = id;
    }

    public static void setFirstName(String firstName) {
        EditContactController.firstName = firstName;
    }

    public static void setLastName(String lastName) {
        EditContactController.lastName = lastName;
    }

    public static void setPhone(String phone) {
        EditContactController.phone = phone;
    }


    public void initialize(){
        firstNameChanged = false;
        lastNameChanged = false;
        phoneChanged = false;
        System.out.println("Initializing");
        firstNameTextField.setText(firstName);
        lastNameTextField.setText(lastName);
        phoneTextField.setText(phone);
        System.out.println(id);
        firstNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue!=null){
                    EditContactController.firstNameChanged=true;
                }
            }
        });
        lastNameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue!=null){
                    EditContactController.lastNameChanged=true;
                }
            }
        });
        phoneTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue!=null){
                    EditContactController.phoneChanged=true;
                }
            }
        });
    }

/*    private FirstPageController firstPageController;

    public void SetFirstPageController(FirstPageController firstPageController) {
        this.firstName
    }*/

    @FXML
    public void saveChanges(ActionEvent event) throws IOException {
        int index=0;
        for(Contact contact:foundContacts){
            System.out.println(contact.getFirstName()+contact.getLastName()+contact.getId());
            if(contact.getId()==id){
                index = foundContacts.indexOf(contact);
            }
        }

        System.out.println(foundContacts);
        String newFirstName = firstNameTextField.getText();
        System.out.println(newFirstName);
        String newLastName = lastNameTextField.getText();
        String newPhone = phoneTextField.getText();

        if(firstNameChanged || lastNameChanged || phoneChanged){
            Datasource.getInstance().saveChangesEditContactWindow(id, newFirstName, newLastName, newPhone);
            Contact newContact = new Contact(id, newFirstName, newLastName, newPhone);
            foundContacts.set(index, newContact);
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
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
        //firstPageController.contacts = foundContacts;
        firstPageController.setContactsListView(foundContacts);
        firstPageController.sortedOrder = sortedOrder;
        firstPageController.reSort(sortedOrder);
        /*Parent root = FXMLLoader.load(getClass().getResource("firstPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = firstPageControllerStage;
        stage.setScene(scene);
        stage.show();

*/
    }
}
