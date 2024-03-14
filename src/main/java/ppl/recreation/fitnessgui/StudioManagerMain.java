package ppl.recreation.fitnessgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class StudioManagerMain extends Application {
    private static MemberList memberlist = new MemberList();
    private static Schedule schedule = new Schedule();

    public static MemberList getMemberlist() {
        return memberlist;
    }

    public static Schedule getSchedule() {
        return schedule;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudioManagerMain.class.getResource("studioManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        stage.setTitle("Recreation Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}