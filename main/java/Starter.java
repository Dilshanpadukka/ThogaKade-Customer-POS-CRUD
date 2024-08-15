//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Starter extends Application {
//    public static void main(String[] args) {
//launch();
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/dash_form.fxml"))));
//        stage.show();
//    }
//}
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/dash_form.fxml"))));
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
