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
import workwithdatabase.models.Goods;

public class GoodsTableController extends Pane implements Initializable {

    @FXML
    private TableView<Goods> goodsTable;
    @FXML
    private TableColumn<Goods, String> nomenclatureColumn;
    @FXML
    private TableColumn<Goods, String> measureColumn;
    @FXML
    private TextField nomenclature;
    @FXML
    private TextField measure;
    @FXML
    private Button add;
    @FXML
    private Button delete;

    private DatabaseConnection connection;
    
    public static GoodsTableController create() throws IOException {
        FXMLLoader deliveryWindowLoader
                = new FXMLLoader(GoodsTableController.class.getResource("goods_table.fxml"));
        GridPane root = new GridPane();
        deliveryWindowLoader.setRoot(root);
        deliveryWindowLoader.load();
        GoodsTableController controller = deliveryWindowLoader.<GoodsTableController>getController();
        controller.getChildren().add(root);
        return controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goodsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable) -> {
                    delete.setDisable(null == goodsTable.getSelectionModel().getSelectedItem());
                });
        connection = WorkWithDatabase.getConnection();
        updateGoodsTable();
    }

    private void updateGoodsTable() {
        connection.getAllGoods((goods) -> {
            goodsTable.setItems(FXCollections.observableArrayList(goods));
        });
    }

    @FXML
    private void onAddGoods() {
        String nomenclatureValue = nomenclature.getText();
        if (nomenclatureValue.isEmpty()) {
            new Alert(AlertType.WARNING, "Поле \"Имя\" не должно быть пустым", ButtonType.OK)
                    .showAndWait();
            return;
        }
        String measureValue = measure.getText();
        if (measureValue.isEmpty()) {
            new Alert(AlertType.WARNING, "Поле \"Единицы\" не должно быть пустым", ButtonType.OK)
                    .showAndWait();
            return;
        }
        nomenclature.clear();
        measure.clear();
        add.setDisable(true);
        connection.addGoods(nomenclatureValue, measureValue, (error) -> {
            add.setDisable(false);
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось добавтиь товар", ButtonType.OK)
                        .showAndWait();
                return;
            }
            updateGoodsTable();
        });
    }

    @FXML
    private void onDeleteGoods() {
        Goods goodsToDelete = goodsTable.getSelectionModel().getSelectedItem();
        add.setDisable(true);
        delete.setDisable(true);
        connection.removeGoods(goodsToDelete, (error) -> {
            add.setDisable(false);
            delete.setDisable(false);
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось удалить товар", ButtonType.OK)
                        .showAndWait();
                return;
            }
            updateGoodsTable();
        });
    }

    @FXML
    private void onEditNomenclature(CellEditEvent<Goods, String> event) {
        Goods changedGoods = event.getRowValue();
        changedGoods.setNomenclature(event.getNewValue());
        connection.changeGoodsNomenclature(changedGoods, (error) -> {
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось внести изменения", ButtonType.OK)
                        .showAndWait();
            }
            updateGoodsTable();
        });
    }

    @FXML
    private void onEditMeasure(CellEditEvent<Goods, String> event) {
        Goods changedGoods = event.getRowValue();
        changedGoods.setMeasure(event.getNewValue());
        connection.changeGoodsMeasure(changedGoods, (error) -> {
            if (null != error) {
                error.printStackTrace(System.out);
                new Alert(AlertType.ERROR, "Не удалось внести изменения", ButtonType.OK)
                        .showAndWait();
            }
            updateGoodsTable();
        });
    }
}
