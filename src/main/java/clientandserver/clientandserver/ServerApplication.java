package clientandserver.clientandserver;

import javafx.application.Application;
import javafx.application.Platform;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.chart.PieChart;
//import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.*;
import java.net.*;
import java.util.Date;


public class ServerApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        TextArea ta = new TextArea();

        //FXMLLoader fxmlLoader = new FXMLLoader(ServerApplication.class.getResource("server-view.fxml"));
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();

        new Thread( () -> {
            try {

               ServerSocket serverSocket = new ServerSocket(8000);
               Platform.runLater(() ->
                   ta.appendText("Server started at " + new Date() + '\n')
               );

               Socket socket = serverSocket.accept();

               DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
               DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

               while (true) {
                   int thenumber = inFromClient.readInt();

                   if(isPrime(thenumber)) {
                       outToClient.writeBoolean(true);
                   } else {
                       outToClient.writeBoolean(false);
                   }

                   Platform.runLater(() -> {
                       ta.appendText("Number received from client: " + thenumber + '\n');
                   });
               }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch();
    }
}