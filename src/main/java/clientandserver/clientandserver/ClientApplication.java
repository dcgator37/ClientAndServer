package clientandserver.clientandserver;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClientApplication extends Application {

    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("client-view.fxml"));

        // Panel p to hold the label and text field
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter a radius: "));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);

        BorderPane mainPane = new BorderPane();
        // Text area to display contents
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);

        Scene scene = new Scene(mainPane, 450, 200);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();

        tf.setOnAction(e -> {
            try {
                int thenumber = Integer.parseInt(tf.getText().trim());
                //double radius = Double.parseDouble(thetextfield.getText().trim());

                toServer.writeInt(thenumber);
                toServer.flush();

                boolean result = fromServer.readBoolean();

                ta.appendText("number is: " + thenumber + '\n');
                if (result) {
                    ta.appendText(thenumber + " is prime." + '\n');
                } else {
                    ta.appendText(thenumber + " is not prime." + '\n');
                }


            } catch (IOException ex) {
                System.err.println(ex);
            }
        });

        try {
            Socket socket = new Socket("localhost", 8000);

            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());


        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    public static void main(String[] args) {
        launch();
    }

}