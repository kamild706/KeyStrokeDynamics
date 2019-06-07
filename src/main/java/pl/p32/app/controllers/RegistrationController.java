package pl.p32.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Setter;
import pl.p32.app.keystrokes.KeyStrokeCollector;
import pl.p32.app.model.Person;
import pl.p32.app.services.PersonNameUnavailableException;
import pl.p32.app.services.RegistrationService;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML private TextField tfName;
    @FXML private TextField tf1;
    @FXML private TextField tf2;
    @FXML private TextField tf3;
    private TextField[] textFields;
    @Setter private Stage stage;
    private String str = "This is some simple text which we use to collect data about users";
    private KeyStrokeCollector[] collectors = new KeyStrokeCollector[3];

    @FXML
    private void handleRegistration() {
        String message = "Niepoprawnie wprowadzona wartość w polu ";
        boolean error = false;
        for (int i = 0; i < collectors.length && !error; i++) {
            if (!collectors[i].isValid()) {
                message += String.valueOf(i + 1);
                initializeField(i);
                error = true;
            }
        }

        if (error) {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
            alert.showAndWait();
        } else {
            saveUser();
        }
    }

    private void saveUser() {
        String name = tfName.getText();
        Person person = null;
        try {
            person = RegistrationService.register(name,
                    collectors[0].getKeystrokes(), collectors[1].getKeystrokes(), collectors[2].getKeystrokes());
        } catch (PersonNameUnavailableException e) {
            String message = "Podana nazwa jest zajęta";
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
            alert.showAndWait();
        }

        if (person != null) {
            String message = "Użytkownik " + person.getName() + " zarejestrowany";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.CLOSE);
            alert.showAndWait();
            stage.close();
        }
    }

    @FXML
    private void handleCancellation() {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields = new TextField[]{tf1, tf2, tf3};
        initializeField(0);
        initializeField(1);
        initializeField(2);

        tf1.setOnKeyPressed(event -> onKeyPressed(event, 0));
        tf1.setOnKeyReleased(event -> onKeyReleased(event, 0));

        tf2.setOnKeyPressed(event -> onKeyPressed(event, 1));
        tf2.setOnKeyReleased(event -> onKeyReleased(event, 1));

        tf3.setOnKeyPressed(event -> onKeyPressed(event, 2));
        tf3.setOnKeyReleased(event -> onKeyReleased(event, 2));
    }

    private void onKeyPressed(KeyEvent event, int fieldIndex) {
        KeyStrokeCollector collector = collectors[fieldIndex];
        KeyCode keycode = event.getCode();
        if (keycode.equals(KeyCode.SHIFT)) {
            collector.onShiftPressed();
        } else if (keycode.equals(KeyCode.BACK_SPACE)) {
            collector.onBackspacePressed();
        } else if (!keycode.equals(KeyCode.TAB)) {
            collector.onKeyPressed(keycode);
        }
    }

    private void onKeyReleased(KeyEvent event, int fieldIndex) {
        KeyStrokeCollector collector = collectors[fieldIndex];
        KeyCode keycode = event.getCode();
        if (keycode.equals(KeyCode.SHIFT)) {
            collector.onShiftReleased();
        } else if (!keycode.equals(KeyCode.TAB)) {
            collector.onKeyReleased(keycode);
        }
    }

    private void initializeField(int fieldId) {
        collectors[fieldId] = new KeyStrokeCollector(str);
        textFields[fieldId].clear();
    }
}
