package MainPack;

import MainPack.model.Contact;
import MainPack.model.Datasource;
import MainPack.model.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ShowGroupsController {
    public static Stage firstPageControllerStage;
    public static int sortedOrder;
    @FXML
    public ListView<Group> showGroupsListView;

    private static List<Group> groupsToShow;

    public static List<Group> getGroupsToShow() {
        return groupsToShow;
    }

    public static void setGroupsToShow(List<Group> groupsToShow) {
        ShowGroupsController.groupsToShow = groupsToShow;
    }

    @FXML
    public void initialize(){
        showGroupsListView.getItems().setAll(groupsToShow);
        showGroupsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount()==2){
                        System.out.println("Double clicked");
                        int selectedGroupId = showGroupsListView.getSelectionModel().getSelectedItem().getId();
                        String selectedGroupName = showGroupsListView.getSelectionModel().getSelectedItem().getName();
                        try {
                            handleDoubleClick(selectedGroupId, new ActionEvent());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void handleDoubleClick(int id, ActionEvent event) throws IOException {
        List<Contact> foundContacts = Datasource.getInstance().showContactsForClickedGroup(id);
        //((Node) (event.getSource())).getScene().getWindow().hide();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("firstPage.fxml"));
        Parent root = (Parent)loader.load();
        FirstPageController firstPageController = loader.getController();

        firstPageController.setContactsListView(foundContacts);
        //firstPageController.contacts = foundContacts;
        firstPageController.sortedOrder = sortedOrder;
        firstPageController.reSort(sortedOrder);

        Scene scene = new Scene(root);
        Stage stage = firstPageControllerStage;
        stage.setScene(scene);
        stage.show();
    }
}
