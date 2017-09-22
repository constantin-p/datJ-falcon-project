package assignment;

import assignment.core.auth.AuthManager;
import assignment.core.RootController;
import assignment.core.auth.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public Stage primaryStage;
    public AuthManager authManager = new AuthManager(() -> initRootLayout(), () -> initLoginLayout());

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        initLoginLayout();
    }
    // App entry
    public static void main(String[] args) {
        launch(args);
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(classLoader
                    .getResource("templates/root.fxml"));

            RootController controller = new RootController(authManager, primaryStage);
            loader.setController(controller);

            Parent layout = loader.load();

            primaryStage.setScene(new Scene(layout, 600, 500));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initLoginLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(classLoader
                    .getResource("templates/login.fxml"));

            LoginController controller = new LoginController(authManager);
            loader.setController(controller);

            Parent layout = loader.load();

            primaryStage.setScene(new Scene(layout, 300, 400));
            primaryStage.setTitle("Login");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
