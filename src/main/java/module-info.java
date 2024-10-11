module org.example.java_project_iii {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.java_project_iii to javafx.fxml;
    exports org.example.java_project_iii;
}