package workwithdatabase;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private <T> void runDialog(String title, Parent root) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(stage);
        window.setTitle(title);
        window.setResizable(false);
        window.setScene(new Scene(root));
        window.showAndWait();
    }

    @FXML
    private void onGoodsTable() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(stage);
        window.setTitle("Справочник товаров");
        window.setResizable(false);
        window.setScene(new Scene(GoodsTableController.create()));
        window.showAndWait();
    }
    
    @FXML
    private void onAgentsTable() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(stage);
        window.setTitle("Справочник поставщиков");
        window.setResizable(false);
        window.setScene(new Scene(AgentsTableController.create()));
        window.showAndWait();
    }
    
    
    @FXML
    private void onWarehouseTable() throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(stage);
        window.setTitle("Справочник складов");
        window.setResizable(false);
        window.setScene(new Scene(WarehouseTableController.create()));
        window.showAndWait();
    }
}
