package MainPack;

import MainPack.model.Contact;
import MainPack.model.Datasource;
import MainPack.model.Group;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.*;

public class FirstPageController{
    public List<Contact> contacts;
    public static String loggedInUser;
    public int sortedOrder;
    @FXML
    private TextField newpassword;
    @FXML
    private ListView<Contact> contactsListView;
    @FXML
    private TextArea contactsTextArea;
    @FXML
    private MenuItem sortAscFirstNameMenuItem;
    @FXML
    private MenuItem sortDescFirstNameMenuItem;
    @FXML
    private MenuItem sortAscLastNameMenuItem;
    @FXML
    private MenuItem sortDescLastNameMenuItem;
    @FXML
    private MenuItem addContactMenuItem;
    @FXML
    private MenuItem searchContactsMenuItem;
    @FXML
    private MenuItem addGroupMenuItem;
    @FXML
    private BorderPane mainBorderPane;



    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(String user) {
        loggedInUser = user;
    }


    @FXML
    public void initialize() {

        System.out.println("Initializing FirstPageController.");
        contacts = Datasource.getInstance().queryAllContacts();
        contactsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observable, Contact oldValue, Contact newValue) {
                if(newValue!=null){
                    Contact contact = contactsListView.getSelectionModel().getSelectedItem();
                    contactsTextArea.setText(contact+" / Phone: "+contact.getTelephone());
                }
            }
        });
        ObservableList<Contact> obsContacts = FXCollections.observableList(contacts);
        contactsListView.setItems(obsContacts);
        //contactsListView.getItems().setAll(contacts);
        contactsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        contactsListView.getSelectionModel().selectFirst();

    }

    public void setContactsListView(List<Contact> contactList) {
        ObservableList<Contact> foundContacts = FXCollections.observableList(contactList);
        contactsListView.setItems(foundContacts);
        contactsListView.getSelectionModel().selectFirst();
        this.contacts = contactList;
    }



    @FXML
    public void showAllContacts(){
        contacts = Datasource.getInstance().queryAllContacts();
        ObservableList<Contact> foundContacts = FXCollections.observableList(contacts);
        contactsListView.setItems(foundContacts);
        contactsListView.getSelectionModel().selectFirst();
        reSort(sortedOrder);
    }

    //sorts after edit button is pressed in the EditContactController !
    public void reSort(int sortedOrder){
        if(sortedOrder==1){
            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            Collections.sort(tempList);
            //Collections.sort(contacts);
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();
        }
        else if(sortedOrder==2){
/*            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            //Collections.reverse(tempList);
            Collections.reverse(tempList);
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();*/
            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            Collections.sort(tempList, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    if(contact.getFirstName().compareTo(t1.getFirstName())<0){
                        return 1;
                    }
                    else if(contact.getFirstName().compareTo(t1.getFirstName())>0){
                        return -1;
                    }
                    else {
                        return 0;
                    }
                }
            });
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();
        }
        else if(sortedOrder==3){
            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            Collections.sort(tempList, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    if(contact.getLastName().compareTo(t1.getLastName())<0){
                        return -1;
                    }
                    else if(contact.getLastName().compareTo(t1.getLastName())>0){
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            });
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();
        }
        else if(sortedOrder==4){
            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            Collections.sort(tempList, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    if(contact.getLastName().compareTo(t1.getLastName())<0){
                        return 1;
                    }
                    else if(contact.getLastName().compareTo(t1.getLastName())>0){
                        return -1;
                    }
                    else {
                        return 0;
                    }
                }
            });
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();
        }
    }

/*    @FXML
    public void handleClickListView(){
        Contact contact = contactsListView.getSelectionModel().getSelectedItem();
        contactsTextArea.setText(contact+" / Phone: "+contact.getTelephone());
    }*/

    @FXML
    public void sortContactsListView(Event event){
        if(event.getSource()== sortAscFirstNameMenuItem){
            sortedOrder =1;
            System.out.println("Sort order is "+ sortedOrder);
            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            Collections.sort(tempList);
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();
        }
        else if(event.getSource()== sortDescFirstNameMenuItem) {
            sortedOrder =2;
            System.out.println("Sort order is "+ sortedOrder);
            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            Collections.reverse(tempList);
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();
        }
        else if(event.getSource()== sortAscLastNameMenuItem) {
            sortedOrder =3;
            System.out.println("Sort order is "+ sortedOrder);
            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            Collections.sort(tempList, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    if(contact.getLastName().compareTo(t1.getLastName())<0){
                        return -1;
                    }
                    else if(contact.getLastName().compareTo(t1.getLastName())>0){
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            });
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();
        }
        else if(event.getSource()== sortDescLastNameMenuItem) {
            sortedOrder =4;
            System.out.println("Sort order is "+ sortedOrder);
            List<Contact> tempList = new ArrayList<Contact>(contactsListView.getItems());
            Collections.sort(tempList, new Comparator<Contact>() {
                @Override
                public int compare(Contact contact, Contact t1) {
                    if(contact.getLastName().compareTo(t1.getLastName())<0){
                        return 1;
                    }
                    else if(contact.getLastName().compareTo(t1.getLastName())>0){
                        return -1;
                    }
                    else {
                        return 0;
                    }
                }
            });
            contactsListView.getItems().setAll(tempList);
            contactsListView.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void editContact(ActionEvent event){
        Parent root;
        EditContactController.setId(contactsListView.getSelectionModel().getSelectedItem().getId());
        EditContactController.setFirstName(contactsListView.getSelectionModel().getSelectedItem().getFirstName());
        EditContactController.setLastName(contactsListView.getSelectionModel().getSelectedItem().getLastName());
        EditContactController.setPhone(contactsListView.getSelectionModel().getSelectedItem().getTelephone());

        try {
            Stage firstPageControllerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            EditContactController.firstPageControllerStage = firstPageControllerStage;
            EditContactController.sortedOrder = sortedOrder;
            EditContactController.foundContacts = contacts;

            root = FXMLLoader.load(getClass().getResource("editContact.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 350, 160));
            stage.show();
            /*Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            FirstPageController.setLoggedInUser(loggedInUser);*/


            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void deleteContact(ActionEvent event){
        Parent root;
        DeleteContactController.setId(contactsListView.getSelectionModel().getSelectedItem().getId());
        DeleteContactController.foundContacts = contacts;

        try {
            Stage firstPageControllerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            DeleteContactController.firstPageControllerStage = firstPageControllerStage;
            DeleteContactController.sortedOrder = sortedOrder;

            root = FXMLLoader.load(getClass().getResource("deleteContact.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Delete contact window");
            stage.setScene(new Scene(root, 310, 125));
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void addContact(ActionEvent event){
        try {
            Stage firstPageControllerStage = (Stage)mainBorderPane.getScene().getWindow();
            AddContactController.firstPageControllerStage = firstPageControllerStage;
            AddContactController.sortedOrder = sortedOrder;
            AddContactController.foundContacts = contacts;

            Parent root = FXMLLoader.load(getClass().getResource("addContact.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add a contact window..");
            stage.setScene(new Scene(root, 310, 165));
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchContacts(){
        try {
            Stage firstPageControllerStage = (Stage)mainBorderPane.getScene().getWindow();
            SearchContactsController.firstPageControllerStage = firstPageControllerStage;
            SearchContactsController.sortedOrder = sortedOrder;

            Parent root = FXMLLoader.load(getClass().getResource("searchContacts.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Search contacts window..");
            stage.setScene(new Scene(root, 310, 165));
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addGroup(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("addGroup.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add group window..");
            stage.setScene(new Scene(root, 350, 100));
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showGroups(){
        Stage firstPageControllerStage = (Stage)mainBorderPane.getScene().getWindow();
        ShowGroupsController.firstPageControllerStage = firstPageControllerStage;
        ShowGroupsController.sortedOrder = sortedOrder;
        try {
            List<Group> groupList =  Datasource.getInstance().showAllGroups();
            ShowGroupsController.setGroupsToShow(groupList);
            Parent root = FXMLLoader.load(getClass().getResource("showGroups.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Show groups window..");
            stage.setScene(new Scene(root, 350, 200));
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addContactToGroup(){
        int selectedContactId = contactsListView.getSelectionModel().getSelectedItem().getId();
        AddContactToGroupController.setContactIdToBeAdded(selectedContactId);
        try {
            List<Group> groupList =  Datasource.getInstance().showAllGroups();
            AddContactToGroupController.setGroupsToShow(groupList);
            Parent root = FXMLLoader.load(getClass().getResource("addContactToGroup.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add contact to group window.");
            stage.setScene(new Scene(root, 440, 280));
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




/*    public static void updateContactsListView(){
        contactsListView.getItems().setAll(Datasource.getInstance().queryAllContacts());
    }*/


/*    @FXML
    public void onChangePasswordButtonClicked() {
        String newPassword = newpassword.getText();
        Datasource.getInstance().changePassword(loggedInUser, newPassword);
    }*/

}

