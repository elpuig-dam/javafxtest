package control;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
    @FXML
    Label lblDescripcio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDescripcio.setText("Aconsegueix una filera de tres símbols, en horitzontal, vertical o diagonal");
    }
}
