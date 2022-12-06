package clientandserver.clientandserver;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.io.*;
import java.net.*;

// Controller not used

public class ClientController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField thetextfield;
    @FXML
    private TextArea thetextarea;

    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @FXML
    protected void onSubmitButtonClick() {

        try {
            Socket socket = new Socket("localhost", 8000);

            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            System.err.println(ex);
        }

        try {

            int thenumber = Integer.parseInt(thetextfield.getText().trim());
            //double radius = Double.parseDouble(thetextfield.getText().trim());

            toServer.writeInt(thenumber);
            toServer.flush();

            boolean result = fromServer.readBoolean();

            thetextarea.appendText("number is: " + thenumber + '\n');
            if (result) {
                thetextarea.appendText(thenumber + " is prime." + '\n');
            } else {
                thetextarea.appendText(thenumber + " is not prime." + '\n');
            }


        } catch (IOException ex) {
            System.err.println(ex);
        }
        //welcomeText.setText("Welcome to JavaFX Application!");
    }
}