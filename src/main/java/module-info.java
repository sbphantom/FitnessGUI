module ppl.recreation.fitnessgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.testng;
    requires junit;

    opens ppl.recreation.fitnessgui to javafx.fxml;
    exports ppl.recreation.fitnessgui;
}