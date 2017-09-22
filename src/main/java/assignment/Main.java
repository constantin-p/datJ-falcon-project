package assignment;

import assignment.core.RootController;
import assignment.util.CacheEngine;
import assignment.util.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import store.db.Database;

import java.io.IOException;

public class Main extends Application {

    private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        Config.loadConfig("cache", "config/cache.properties");
        Config.loadConfig("store", "config/store_secret.properties");
        CacheEngine.configInstance(Config.getConfig("cache"));
        Database.configInstance(Config.getConfig("store"));

        initRootLayout();
    }

    // App entry
    public static void main(String[] args) {
        launch(args);
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(classLoader
                .getResource("templates/root.fxml"));

            RootController controller = new RootController(primaryStage);
            loader.setController(controller);

            Parent layout = loader.load();

            primaryStage.setScene(new Scene(layout, 600, 500));
            primaryStage.setTitle("-- REPLACE THIS --");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
