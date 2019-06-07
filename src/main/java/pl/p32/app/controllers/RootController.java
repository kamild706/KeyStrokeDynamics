package pl.p32.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class RootController {

    @Setter private Stage stage;

    @FXML
    private void openRegistrationWindow() {
        Stage dialog = new Stage();
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("RegistrationLayout.fxml"));
            Parent root = loader.load();
            dialog.setScene(new Scene(root));

            RegistrationController controller = loader.getController();
            controller.setStage(dialog);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openIdentificationWindow() {
        Stage dialog = new Stage();
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("IdentificationLayout.fxml"));
            Parent root = loader.load();
            dialog.setScene(new Scene(root));

            IdentificationController controller = loader.getController();
            controller.setStage(dialog);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openVerificationWindow() {
        Stage dialog = new Stage();
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("VerificationLayout.fxml"));
            Parent root = loader.load();
            dialog.setScene(new Scene(root));

            VerificationController controller = loader.getController();
            controller.setStage(dialog);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
