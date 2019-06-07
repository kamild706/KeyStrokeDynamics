package pl.p32.app.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.Setter;
import pl.p32.app.keystrokes.KeyStrokeCollector;
import pl.p32.app.metrics.Chebyshev;
import pl.p32.app.metrics.Euclidean;
import pl.p32.app.metrics.Manhattan;
import pl.p32.app.metrics.Metric;
import pl.p32.app.model.KeyStroke;
import pl.p32.app.model.Person;
import pl.p32.app.model.repository.PersonRepository;
import pl.p32.app.services.VerificationService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VerificationController implements Initializable {

    @FXML private TextField tf1;
    @FXML private ChoiceBox<Metric> cbmetric;
    @FXML private ChoiceBox<Person> cbuser;
    @FXML private TextField tfk;
    @Setter private Stage stage;
    private String str = "This is some simple text which we use to collect data about users";
    private KeyStrokeCollector collector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeField();
        tf1.setOnKeyPressed(this::onKeyPressed);
        tf1.setOnKeyReleased(this::onKeyReleased);

        cbmetric.setItems(FXCollections.observableArrayList(
                new Chebyshev(),
                new Euclidean(),
                new Manhattan()
        ));
        cbmetric.getSelectionModel().select(1);
        tfk.setText("1");

        PersonRepository repository = PersonRepository.getInstance();
        List<Person> people = repository.findAll();
        cbuser.setItems(FXCollections.observableArrayList(people));
        cbuser.getSelectionModel().select(0);
        cbuser.setConverter(new StringConverter<Person>() {
            @Override
            public String toString(Person object) {
                return object.getName();
            }

            @Override
            public Person fromString(String string) {
                return null;
            }
        });
    }

    private void onKeyPressed(KeyEvent event) {
        KeyCode keycode = event.getCode();
        if (keycode.equals(KeyCode.SHIFT)) {
            collector.onShiftPressed();
        } else if (keycode.equals(KeyCode.BACK_SPACE)) {
            collector.onBackspacePressed();
        } else if (!keycode.equals(KeyCode.TAB)) {
            collector.onKeyPressed(keycode);
        }
    }

    private void onKeyReleased(KeyEvent event) {
        KeyCode keycode = event.getCode();
        if (keycode.equals(KeyCode.SHIFT)) {
            collector.onShiftReleased();
        } else if (!keycode.equals(KeyCode.TAB)) {
            collector.onKeyReleased(keycode);
        }
    }

    private void initializeField() {
        collector = new KeyStrokeCollector(str);
    }

    @FXML
    private void handleVerification() {
        if (collector.isValid()) {
            List<KeyStroke> keyStrokes = collector.getKeystrokes();
            int k = Integer.valueOf(tfk.getText());
            Metric metric = cbmetric.getSelectionModel().getSelectedItem();
            Person person = cbuser.getSelectionModel().getSelectedItem();

            boolean success = VerificationService.verifyPerson(person, keyStrokes, metric, k);
            if (success) {
                String message = "Pomyślnie zweryfikowano jako " + person.getName();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.CLOSE);
                alert.showAndWait();
            } else {
                String message = "Nie udało się zweryfikować użytkownika";
                Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Niepoprawnie przepisany tekst", ButtonType.CLOSE);
            alert.showAndWait();
            initializeField();
        }
    }

    @FXML
    private void handleCancellation() {
        stage.close();
    }
}
