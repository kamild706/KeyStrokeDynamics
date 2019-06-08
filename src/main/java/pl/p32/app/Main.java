package pl.p32.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.p32.app.controllers.RootController;
import pl.p32.app.model.repository.PersonRepository;
import pl.p32.app.statstics.ExaminationResult;
import pl.p32.app.statstics.Statistics;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("RootLayout.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("KeystrokeDynamics");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        RootController controller = loader.getController();
        controller.setStage(primaryStage);

        Runnable r = PersonRepository::getInstance;
        new Thread(r).start();
        /*Statistics statistics = new Statistics();
        List<ExaminationResult> results = statistics.statisticsForSystem();
        results.forEach(System.out::println);*/
    }
}
