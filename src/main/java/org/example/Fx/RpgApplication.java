package org.example.Fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.Core.Game;

public class RpgApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Game game = new Game(stage);
        Group root = new Group();
        Scene menuScene = new Scene(root);
        int width = 1300;
        int height = 720;
        stage.setTitle( "Harry Potter at Home" );
        stage.setScene(menuScene);

        Canvas canvas = new Canvas( width, height);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = new Image( "images/menuBackground.jpg" );
        gc.drawImage(background, 0, 0 );
        gc.setFill( Color.DARKSEAGREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "verdana", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText( "Harry Potter At Home", width/2 -290, 100 );
        gc.strokeText( "Harry Potter At Home", width/2 - 290, 100 );
        Button startButton = new Button("Start");
        startButton.setLayoutX(width/2 - startButton.getWidth()/2-35);
        startButton.setLayoutY(400);
        startButton.setStyle("-fx-font-size: " + 20);
        //startButton.setStyle("-fx-font-family: comic sans ms");
        root.getChildren().add(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Code à exécuter lorsque le bouton est cliqué
                game.run();
            }
        });
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
