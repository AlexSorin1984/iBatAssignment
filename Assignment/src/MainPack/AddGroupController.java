package MainPack;

import MainPack.model.Datasource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;


public class AddGroupController {
    @FXML
    private TextField groupNameTextField;

    @FXML
    public void saveGroup(ActionEvent event){
        String groupName = groupNameTextField.getText();
        Datasource.getInstance().addGroup(groupName);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
