package workwithdatabase;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WorkWithDatabase extends Application {

    private static DatabaseConnection connection;

    @Override
    public void init() throws Exception {
        super.init();
        connection = new DatabaseConnection();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader mainWindowLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            primaryStage.setScene(new Scene((Parent) mainWindowLoader.load()));
            primaryStage.setTitle("Действия");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public static DatabaseConnection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
