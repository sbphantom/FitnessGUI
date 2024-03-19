package ppl.recreation.fitnessgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main model of the application
 *
 * @author Danny Onuorah
 */
public class StudioManagerMain extends Application {
    private static MemberList memberlist = new MemberList();
    private static Schedule schedule = new Schedule();

    /**
     * Returns the MemberList of the program
     *
     * @return MemberList
     */
    public static MemberList getMemberlist() {
        return memberlist;
    }

    /**
     * Returns the schedule of the program
     *
     * @return Schedule
     */
    public static Schedule getSchedule() {
        return schedule;
    }

    /**
     * Starts the program
     */
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

    /**
     * Program entry point
     */
    public static void main(String[] args) throws IOException {
        launch();
    }
}