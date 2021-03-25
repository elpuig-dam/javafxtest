package control;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * Created by jordi on 22/11/16.
 */

public class TresNLiniaControl {
    //tresNLinia parent;
    private boolean torn, acabat;
    private int tirades;
    private char[][] tauler;
    @FXML private Button btn00,btn01,btn02,btn10,btn11,btn12,btn20,btn21,btn22;
    @FXML private Button btnInici;
    @FXML private RadioButton rbtnCC, rbtnHH, rbtnHC;
    @FXML private Label lblTorn;
    @FXML private AnchorPane apaneEsquerra, apaneDret;
    @FXML private GridPane gridPane1;
    @FXML private VBox vbox1;
    @FXML private  MenuItem menuItemTheme, menuItemIntruccions;
    List<Button> llista_buttons;
    private Scene scene;
    private Stage stage;

    public TresNLiniaControl() {

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML protected void clickInici(ActionEvent event) {
        //Triar aleatòriament qui comença, incialitzar panell i tauler
        llista_buttons.forEach(System.out::println);
        torn = triaTorn();
        lblTorn.setText(torn?"Tira X":"Tira O");
        acabat = false;
        btnInici.setDisable(true);
        tirades = 0;
        cleanButtons();
        tauler = new char[][]{  {'-', '-', '-'},
                                {'-', '-', '-'},
                                {'-', '-', '-'} };

        if(rbtnHH.isSelected() || rbtnHC.isSelected()) {
            //activar panell per que interaccioni l'usuari
            apaneEsquerra.setDisable(false);
            apaneDret.setDisable(true);
            if(rbtnHC.isSelected()) {
                //triar qui comença Human o Computer
                if (Math.random() < 0.5) jocComputer();
            }
        }else {
            new Thread(() -> {
                while (!acabat) {
                    Platform.runLater(()->jocComputer());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    @FXML public void initialize() {
        llista_buttons = Arrays.asList(btn00, btn10, btn20,
                                        btn01, btn11, btn21,
                                        btn02, btn12, btn22);
        tirades = 0;
    }

    @FXML protected void tirada(ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.setText(torn?"X":"O");
        //Agafar la fila i la columna del button per actualitzar el tauler de joc
        int c = Integer.parseInt(btn.getId().substring(3,4));
        int f = Integer.parseInt(btn.getId().substring(4,5 ));
        //guardar la tirada
        tauler[c][f] = torn?'X':'O';
        btn.setDisable(true);
        tirades++;
        //Comprovar l'estat del joc
        if(comprovaJoc(torn) < 1) acabar();
        else {
            canviTorn();
            if(rbtnHC.isSelected()) jocComputer();
        }
    }


    /***
     *
     * @param torn
     * @return -1 si hi ha un guanyador, 0 si hi ha empat, 1 si cal continuar
     */
    private int comprovaJoc(boolean torn) {
        //comprovar si hi ha algun guanyador
        int t = 3 * ((torn) ? 'X' : 'O');

        if( tauler[0][0] + tauler[1][0] + tauler[2][0] == t || //tresNLinia a la fila0
            tauler[0][1] + tauler[1][1] + tauler[2][1] == t || //tresNLinia a la fila1
            tauler[0][2] + tauler[1][2] + tauler[2][2] == t || ////tresNLinia a la fila2
            tauler[0][0] + tauler[0][1] + tauler[0][2] == t || //tresNLinia a la col0
            tauler[1][0] + tauler[1][1] + tauler[1][2] == t || //tresNLinia a la col1
            tauler[2][0] + tauler[2][1] + tauler[2][2] == t || //tresNLinia a la col2
            tauler[0][0] + tauler[1][1] + tauler[2][2] == t || //tresNLinia diagonal1
            tauler[2][0] + tauler[1][1] + tauler[0][2] == t    //tresNLinia diagonal2
         ) {
            acabat = true;
            return -1;
        } else if (tirades==9) { //empat
            acabat = true;
            return 0;
        }
        else return 1;
    }

    private void acabar() {
        //anunciar guanaydors i
        //restaurar el joc per tornar a començar
        System.out.println("ACABAT!");
        //tornar a inicialitzar
        if(tirades < 9) lblTorn.setText("Campió " + ((torn) ? "X" : "O"));
        else {
            if(comprovaJoc(torn) == 0) lblTorn.setText("Empat");
            else lblTorn.setText("Campió " + ((torn) ? "X" : "O"));
        }
        apaneEsquerra.setDisable(true);
        apaneDret.setDisable(false);
        btnInici.setDisable(false);
    }

    private void cleanButtons() {
        for(Button b: llista_buttons) {
            b.setText("");
            b.setDisable(false);
        }
    }

    private boolean triaTorn() {
        return Math.random() < 0.5;
    }

    private void canviTorn() {
        if(torn) torn=false;
        else torn=true;
        lblTorn.setText(torn?"Tira X":"Tira O");
    }

    public boolean jocComputer() {
        boolean posat = false;

        //triar casella
        while(!posat) {
            //Triar Button aleatòtiament

            Button b = llista_buttons.get((int) Math.floor(Math.random() * 9));
            int c = Integer.parseInt(b.getId().substring(3,4));
            int f = Integer.parseInt(b.getId().substring(4,5));
            if (tauler[c][f] == '-') {
                tauler[c][f] = (torn) ? 'X' : 'O';
                b.setText((torn) ? "X" : "O");
                posat = true;
                tirades++;
                b.setDisable(true);
               // printTauler();

            }
        }
        if(comprovaJoc(torn) < 1) {
            acabar();
            return true;
        } else {
            canviTorn();
            return false;
        }

    }

    private void printTauler() {
        for (int fil = 0; fil < 3; fil++) {
            for (int col = 0; col < 3; col++) System.out.print(tauler[col][fil]);
            System.out.println();
        }
    }

    public void clickMenuItemTheme(ActionEvent actionEvent) {
        String mode;
        scene.getStylesheets().clear();
        if(menuItemTheme.getText().equals("Dark mode")) {
            mode = "Light mode";
            scene.getStylesheets().add("css/ThemeDark.css");
        } else {
            mode = "Dark mode";
            scene.getStylesheets().add("css/ThemeLight.css");
        }
        menuItemTheme.setText(mode);
    }

    public void clickMenuItemClose(ActionEvent actionEvent) {
        stage.close();
    }

    public void clickMenuItemInstruccions(ActionEvent actionEvent) {
        vbox1.getChildren().forEach(p -> System.out.println(p.getId()));
        if(menuItemIntruccions.getText().equals("Instruccions")) {

            //Eliminem el Node a partir del Objecte que està instanciat en aquest controller
            vbox1.getChildren().remove(gridPane1);
            try {
                VBox vBoxPane = FXMLLoader.load(getClass().getResource("/fxml/Help.fxml"));
                vbox1.getChildren().add(vBoxPane);
                menuItemIntruccions.setText("Joc");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Eliminem el node perquè sabem que és a la posició 1 dels Childrens
            vbox1.getChildren().remove(1);
            vbox1.getChildren().add(gridPane1);
            menuItemIntruccions.setText("Instruccions");
        }


    }

   /* private void tira(Button btn) {
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                btn.setText(torn?"X":"O");
                //Agafar la fila i la columna del button per actualitzar el tauler de joc
                int c = Integer.parseInt(btn.getId().substring(3,4));
                int f = Integer.parseInt(btn.getId().substring(4,5 ));
                //guardar la tirada
                tauler[c][f] = torn?'X':'O';
                btn.setDisable(true);
                tirades++;
                //Comprovar l'estat del joc
                if(comprovaJoc(torn) < 1) acabar();
                else {
                    canviTorn();
                    if(rbtnHC.isSelected()) jocComputer();
                }

            }
        });

    }*/
}
