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

import static Methods.Functions.*;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DS PROJECT");

        Trie mainTrie = Functions.read();
      //  System.out.println(spellCheck(mainTrie, "a"));

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

        //textfield
        HBox suggestGrid = new HBox();
        suggestGrid.setSpacing(8);
        suggestGrid.setAlignment(Pos.CENTER);
        TextField textField = new TextField();
        textField.setMinWidth(320);
        textField.setMinHeight(36);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<Button> suggestButtons = new ArrayList<>();
            Trie endNode = spellCheck(mainTrie, newValue);
            HBox gridPane = new HBox();
            gridPane.setSpacing(8);
            gridPane.setAlignment(Pos.CENTER);
            if (endNode.isValid){
                textField.setStyle("-fx-text-fill : green; -fx-border-color : green;");
            }
            else{
                textField.setStyle("-fx-text-fill : red; -fx-border-color : red;");
                endNode = findRoot(mainTrie,newValue);
            }
            System.out.println(endNode.val);
            ArrayList<String> suggestions = AutoComplete(endNode,newValue);
            for (String suggest:suggestions) {
                Button button = new Button(suggest);
                button.setOnAction(event -> {
                    textField.setText(button.getText());
                });
                button.setStyle("-fx-background-color : #9b9b9b; -fx-text-fill: #34373d;");
                button.setCursor(Cursor.HAND);
                suggestButtons.add(button);
            }
            System.out.println(suggestions);
            for (int i = 0 ; i < suggestButtons.size() ; i++){
                gridPane.getChildren().add(suggestButtons.get(i));
            }
            suggestGrid.getChildren().clear();
            suggestGrid.getChildren().add(gridPane);
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

        VBox mainBox = new VBox(label,textFillAndButton,suggestGrid);
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
