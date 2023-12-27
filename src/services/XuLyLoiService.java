package services;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class XuLyLoiService {
    public static void xuLyLoi(Label Error, Node Button, String ErrInfo, int dX, int dY) {
        Error.setVisible(true);
        Error.setLayoutX(Button.getLayoutX() + dX);
        Error.setLayoutY(Button.getLayoutY() + dY);
        Error.setText(ErrInfo);
        Button.setStyle("-fx-border-color: linear-gradient(to bottom right, #FF0000, #CC0000);");
    }

    public static void xuLyLoi(Node Button) {
        Button.setStyle("-fx-border-color: linear-gradient(to bottom right, #FF0000, #CC0000);");
    }

    public static void xoaLoi(Label Error, Node Button) {
        Error.setVisible(false);
        Button.setStyle("-fx-border-color: linear-gradient(to bottom right, #272b3f, #256b51);");
    }

    public static void xoaLoi(Node Button) {
        Button.setStyle("-fx-border-color: linear-gradient(to bottom right, #272b3f, #256b51);");
    }
}
