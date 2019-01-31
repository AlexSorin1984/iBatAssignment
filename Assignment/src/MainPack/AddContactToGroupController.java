package MainPack;

import MainPack.model.Datasource;
import MainPack.model.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.util.List;

public class AddContactToGroupController {
    private static int contactIdToBeAdded;
    @FXML
    private ListView<Group> showGroupsListView;

    private static List<Group> groupsToShow;

    public static List<Group> getGroupsToShow() {
        return groupsToShow;
    }

    public static int getContactIdToBeAdded() {
        return contactIdToBeAdded;
    }

    public static void setContactIdToBeAdded(int contactIdToBeAdded) {
        AddContactToGroupController.contactIdToBeAdded = contactIdToBeAdded;
    }

    public static void setGroupsToShow(List<Group> groupsToShow) {
        AddContactToGroupController.groupsToShow = groupsToShow;
    }


    @FXML
    public void initialize(){
        showGroupsListView.getItems().setAll(groupsToShow);
        showGroupsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void addContactToGroup(ActionEvent event){
        System.out.println("Something");
        int groupId = showGroupsListView.getSelectionModel().getSelectedItem().getId();
        Datasource.getInstance().addContactToGroup(contactIdToBeAdded, groupId);
    }
}
