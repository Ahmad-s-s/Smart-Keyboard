import Methods.Functions;
import javafx.application.Application;
import Structures.Trie;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

import static Methods.Functions.spellCheck;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DS PROJECT");

        Trie mainTrie = Functions.read();
        Trie reverseTrie = Functions.readRev();
        System.out.println(spellCheck(mainTrie, "a"));

        //top bar
        Label topBarLabel = new Label("SMART KEYBOARD");
        topBarLabel.setStyle("-fx-text-fill : #ffffff; -fx-font-family: fantasy; -fx-font-size : 50px;");
        topBarLabel.setAlignment(Pos.CENTER);
        topBarLabel.setLineSpacing(15);

        VBox topBar = new VBox(topBarLabel);
        topBar.setMaxWidth(500);
        topBar.setMaxHeight(80);
        topBar.setMinHeight(80);
        topBar.setMinWidth(500);
        topBar.setStyle("-fx-background-color: #34373d;");
        topBar.setAlignment(Pos.TOP_CENTER);

        //header label
        Label label = new Label("Enter a word");
        label.setStyle("-fx-text-fill : #ffffff; -fx-font-family: fantasy; -fx-font-size : 25px;");

        //buttons suggestions
        Button suggestion1 = new Button("");
        Button suggestion2 = new Button("");
        Button suggestion3 = new Button("");
        Button suggestion4 = new Button("");
        Button suggestion5 = new Button("");

        suggestion1.setStyle("-fx-background-color : #9b9b9b; -fx-text-fill: #34373d;");
        suggestion2.setStyle("-fx-background-color : #9b9b9b; -fx-text-fill: #34373d;");
        suggestion3.setStyle("-fx-background-color : #9b9b9b; -fx-text-fill: #34373d;");
        suggestion4.setStyle("-fx-background-color : #9b9b9b; -fx-text-fill: #34373d;");
        suggestion5.setStyle("-fx-background-color : #9b9b9b; -fx-text-fill: #34373d;");

        suggestion1.setCursor(Cursor.HAND);
        suggestion2.setCursor(Cursor.HAND);
        suggestion3.setCursor(Cursor.HAND);
        suggestion4.setCursor(Cursor.HAND);
        suggestion5.setCursor(Cursor.HAND);

        GridPane gridPane = new GridPane();
        gridPane.add(suggestion1, 0, 0, 1, 1);
        gridPane.add(suggestion2, 1, 0, 1, 1);
        gridPane.add(suggestion3, 2, 0, 1, 1);
        gridPane.add(suggestion4, 3, 0, 1, 1);
        gridPane.add(suggestion5, 4, 0, 1, 1);
        gridPane.setHgap(8);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        //textfield
        TextField textField = new TextField();
        textField.setMinWidth(320);
        textField.setMinHeight(36);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            boolean spellCheckValidation = spellCheck(mainTrie, newValue).isValid;
            if (spellCheckValidation){
                textField.setStyle("-fx-text-fill : green; -fx-border-color : green;");
            }
            else{
                textField.setStyle("-fx-text-fill : red; -fx-border-color : red;");
            }

        });

        //button check
        Button checkButton = new Button("Check");
        checkButton.setStyle("-fx-background-color : #459285; -fx-text-fill: white;");
        checkButton.setCursor(Cursor.HAND);
        checkButton.setMinHeight(36);
        checkButton.setMinWidth(50);
        checkButton.setOnAction(event -> {
            Trie checkValidNode = spellCheck(mainTrie, (String) textField.getText());
            if (checkValidNode.isValid){
                checkValidNode.frequency +=  1;
            }
        });

        //main box
        HBox textFillAndButton = new HBox(textField,checkButton);
        textFillAndButton.setAlignment(Pos.CENTER);
        textFillAndButton.setSpacing(5);
        textFillAndButton.setMinHeight(36);
        textFillAndButton.setMinWidth(380);

        VBox mainBox = new VBox(label,textFillAndButton,gridPane);
        mainBox.setMaxWidth(500);
        mainBox.setMaxHeight(620);
        mainBox.setMinHeight(620);
        mainBox.setMinWidth(500);
        mainBox.setStyle("-fx-background-color: #171b20;");
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(15);

        // Scene

        VBox container =  new VBox();
        container.getChildren().addAll(topBar, mainBox);
        Scene scene = new Scene(container,500,700);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(400);

        primaryStage.show();






    }
}
