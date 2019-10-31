package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Random;

public class Main extends Application {
//Skapar upp rutnät,knapparna och nytt spelknapp, själva spelet
    GridPane gridPane;
    Button[][] buttons = new Button[4][4];
    Button nyttSpel;

    //Programmet börjar här
    @Override
    public void start(Stage primaryStage) throws Exception {
        Handler handler = new Handler();

        primaryStage.setTitle("Pusselspel");
        gridPane = new GridPane();

        nyttSpel = new Button("Nytt spel");
        nyttSpel.setOnAction(handler);
        nyttSpel.setMinSize(720, 60);

        /*
            Skapar alla knappar
            Lägger till en event handler
            Lägger till knapparna till gridpane
         */
        int numb = 1;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Button button;
                if (x == 3 && y == 3) {
                    button = new Button("");
                }
                else {
                    button = new Button(Integer.toString(numb));
                }
                button.setMinSize(180, 180);
                button.setOnAction(handler);
                buttons[x][y] = button;
                numb++;
                gridPane.add(button, y, x);
            }
        }

        gridPane.add(nyttSpel, 0, 4, 4, 1);

        Scene scene = new Scene(gridPane, 720 , 780);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Skapar en intern klass fär att hantera events
     */
    private class Handler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button button = (Button) actionEvent.getSource();

            /*
             *Löper igenom tvådimensionella arrayen
             *Kollar att indexvärdet inte är på sin gräns
             * Byter textvärden på knapparna
             */
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (buttons[x][y].equals(button)) {
                        String tempText = button.getText();
                        if (x > 0) {
                            if (buttons[x-1][y].getText().equals("")) {
                                buttons[x][y].setText(buttons[x-1][y].getText());
                                buttons[x-1][y].setText(tempText);
                            }
                        }
                        if (x < 3) {
                            if (buttons[x+1][y].getText().equals("")) {
                                buttons[x][y].setText(buttons[x+1][y].getText());
                                buttons[x+1][y].setText(tempText);
                            }
                        }
                        if (y > 0) {
                            if (buttons[x][y-1].getText().equals("")) {
                                buttons[x][y].setText(buttons[x][y-1].getText());
                                buttons[x][y-1].setText(tempText);
                            }
                        }
                        if (y < 3) {
                            if (buttons[x][y+1].getText().equals("")) {
                                buttons[x][y].setText(buttons[x][y+1].getText());
                                buttons[x][y+1].setText(tempText);
                            }
                        }
                    }
                }
            }

            if (button.equals(nyttSpel)) {
                shuffle();
            }

            if (gameFinished()) {
                JOptionPane.showMessageDialog(null, "Grattis du vann!");
            }
        }
    }

    /**
     * Löper igenom 2darrayen
     * kollar om allt är i följd
     * @return
     */
    public boolean gameFinished() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int expectedText = x * 4 + y + 1;
                String actualText = buttons[x][y].getText();

                if (actualText.equals("")) {
                    if (expectedText != 16) {
                        return false;
                    }
                }
                else {
                    if (expectedText != Integer.parseInt(actualText))
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Slumpar fram spelplan med indexsiffror
     */
    public void shuffle() {
        Random random = new Random();

        for (int x = buttons.length - 1; x > 0; x--) {
            for (int y = buttons[x].length - 1; y > 0; y--) {
                int x2 = random.nextInt(x+1);
                int y2 = random.nextInt(y+1);

                String tempText = buttons[x][y].getText();
                buttons[x][y].setText(buttons[x2][y2].getText());
                buttons[x2][y2].setText(tempText);
            }
        }
    }
}
