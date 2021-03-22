/**
 * Created by jordi on 18/11/16.
 */

import control.TresNLiniaControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class tresNLinia extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Tres en Línia");
        String css1 =  getClass().getResource("css/ThemeDark.css").toExternalForm();
        String css2 =  getClass().getResource("css/ThemeLight.css").toExternalForm();
        System.out.println(css1);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/scGame2.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tres en Línia");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css1);

        TresNLiniaControl tresNLiniaControl = loader.getController();
        tresNLiniaControl.setStage(primaryStage);
        tresNLiniaControl.setScene(scene);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}
