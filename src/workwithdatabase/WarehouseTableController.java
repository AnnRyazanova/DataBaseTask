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
import workwithdatabase.models.Warehouse;

public class WarehouseTableController extends Pane implements Initializable {
    
    @FXML
    private TableView<Warehouse> warehouseTable;
    @FXML
    private TableColumn<Warehouse, String> nameColumn;
    @FXML
    private TableColumn<Warehouse, String> cityColumn;
    @FXML
    private TextField name;
    @FXML
    private TextField city;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    
    private DatabaseConnection connection;
    
    public static WarehouseTableController create() throws IOException {
        FXMLLoader deliveryWindowLoader
                = new FXMLLoader(AgentsTableController.class.getResource("warehouse_table.fxml"));
        GridPane root = new GridPane();
        deliveryWindowLoader.setRoot(root);
        deliveryWindowLoader.load();
        WarehouseTableController controller = deliveryWindowLoader.<WarehouseTableController>getController();
        controller.getChildren().add(root);
        return controller;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warehouseTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable) -> {
                    delete.setDisable(null == warehouseTable.getSelectionModel().getSelectedItem());
                });
        connection = WorkWithDatabase.getConnection();
        updateWarehouseTable();
    }
    
    private void updateWarehouseTable() {
        connection.getAllWarehouses((warehouse) -> {
            warehouseTable.setItems(FXCollections.observableArrayList(warehouse));
        });
    }
    
    
    @FXML
    private void onAddWarehouse() {
        String nameValue = name.getText();
        if (nameValue.isEmpty()) {
            new Alert(AlertType.WARNING, "Поле \"Название\" не должно быть пустым", ButtonType.OK)
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
        city.clear();
        add.setDisable(true);
        connection.addWarehouse(nameValue, cityValue, (error) -> {
            add.setDisable(false);
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось добавить склад", ButtonType.OK)
                        .showAndWait();
                return;
            }
            updateWarehouseTable();
        });
    }
    
    @FXML
    private void onDeleteWarehouse() {
        Warehouse warehouseToDelete = warehouseTable.getSelectionModel().getSelectedItem();
        add.setDisable(true);
        delete.setDisable(true);
        connection.removeWarehouse(warehouseToDelete, (error) -> {
            add.setDisable(false);
            delete.setDisable(false);
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось удалить склад", ButtonType.OK)
                        .showAndWait();
                return;
            }
            updateWarehouseTable();
        });
    }
    
    @FXML
    private void onEditName(CellEditEvent<Warehouse, String> event) {
        Warehouse changedWarehouse = event.getRowValue();
        changedWarehouse.setName(event.getNewValue());
        connection.changeWarehouseName(changedWarehouse, (error) -> {
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось внести изменения", ButtonType.OK)
                        .showAndWait();
            }
            updateWarehouseTable();
        });
    }
   
    
    @FXML
    private void onEditCity(CellEditEvent<Warehouse, String> event) {
        Warehouse changedWarehouse = event.getRowValue();
        changedWarehouse.setCity(event.getNewValue());
        connection.changeWarehouseCity(changedWarehouse, (error) -> {
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось внести изменения", ButtonType.OK)
                        .showAndWait();
            }
            updateWarehouseTable();
        });
    }
    
}
