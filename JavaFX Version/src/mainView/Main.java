package mainView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;
    public static Stage getStage(){
        return stage;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = primaryStage;
        primaryStage.setTitle("Simple Poerty Creator");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root,500 , 420));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
