package workwithdatabase;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import workwithdatabase.models.Agent;

public class AgentsTableController extends Pane implements Initializable {
    
    @FXML
    private TableView<Agent> agentsTable;
    @FXML
    private TableColumn<Agent, String> nameColumn;
    @FXML
    private TableColumn<Agent, String> phoneColumn;
    @FXML
    private TableColumn<Agent, String> cityColumn;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField city;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    
    private DatabaseConnection connection;
    
    public static AgentsTableController create() throws IOException {
        FXMLLoader deliveryWindowLoader
                = new FXMLLoader(AgentsTableController.class.getResource("agents_table.fxml"));
        GridPane root = new GridPane();
        deliveryWindowLoader.setRoot(root);
        deliveryWindowLoader.load();
        AgentsTableController controller = deliveryWindowLoader.<AgentsTableController>getController();
        controller.getChildren().add(root);
        return controller;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        agentsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable) -> {
                    delete.setDisable(null == agentsTable.getSelectionModel().getSelectedItem());
                });
        connection = WorkWithDatabase.getConnection();
        updateAgentsTable();
    }
    
    private void updateAgentsTable() {
        connection.getAllAgents((agents) -> {
            agentsTable.setItems(FXCollections.observableArrayList(agents));
        });
    }
    
    
    @FXML
    private void onAddAgents() {
        String nameValue = name.getText();
        if (nameValue.isEmpty()) {
            new Alert(AlertType.WARNING, "Поле \"Имя\" не должно быть пустым", ButtonType.OK)
                    .showAndWait();
            return;
        }
        String phoneValue = phone.getText();
        if (phoneValue.isEmpty()) {
            new Alert(AlertType.WARNING, "Поле \"Номер телефона\" не должно быть пустым", ButtonType.OK)
                    .showAndWait();
            return;
        }
        String cityValue = city.getText();
        if (cityValue.isEmpty()) {
            new Alert(AlertType.WARNING, "Поле \"Город\" не должно быть пустым", ButtonType.OK)
                    .showAndWait();
            return;
        }
        name.clear();
        phone.clear();
        city.clear();
        add.setDisable(true);
        connection.addAgents(nameValue, phoneValue, cityValue, (error) -> {
            add.setDisable(false);
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось добавтиь поставщика", ButtonType.OK)
                        .showAndWait();
                return;
            }
            updateAgentsTable();
        });
    }
    
    @FXML
    private void onDeleteAgents() {
        Agent agentsToDelete = agentsTable.getSelectionModel().getSelectedItem();
        add.setDisable(true);
        delete.setDisable(true);
        connection.removeAgents(agentsToDelete, (error) -> {
            add.setDisable(false);
            delete.setDisable(false);
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось удалить поставщика", ButtonType.OK)
                        .showAndWait();
                return;
            }
            updateAgentsTable();
        });
    }
    
    @FXML
    private void onEditName(CellEditEvent<Agent, String> event) {
        Agent changedAgent = event.getRowValue();
        changedAgent.setName(event.getNewValue());
        connection.changeAgentName(changedAgent, (error) -> {
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось внести изменения", ButtonType.OK)
                        .showAndWait();
            }
            updateAgentsTable();
        });
    }
    
    @FXML
    private void onEditPhone(CellEditEvent<Agent, String> event) {
        Agent changedAgent = event.getRowValue();
        changedAgent.setPhone(event.getNewValue());
        connection.changeAgentPhone(changedAgent, (error) -> {
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось внести изменения", ButtonType.OK)
                        .showAndWait();
            }
            updateAgentsTable();
        });
    }
    
    @FXML
    private void onEditCity(CellEditEvent<Agent, String> event) {
        Agent changedAgent = event.getRowValue();
        changedAgent.setCity(event.getNewValue());
        connection.changeAgentCity(changedAgent, (error) -> {
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось внести изменения", ButtonType.OK)
                        .showAndWait();
            }
            updateAgentsTable();
        });
    }
    
}
