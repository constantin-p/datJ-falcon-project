package ui;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.Map;

public final class DetailTooltip {
    private static final int SEPARATOR_WIDTH = 10;

    private Popup popup = new Popup();
    private Pane layout = new Pane();
    private Stage primaryStage;

    public DetailTooltip(Stage primaryStage) {
        this.primaryStage = primaryStage;
        layout.setStyle("-fx-background-color: rgb(192, 194, 196), rgb(239, 240, 241); -fx-background-insets: 0, 1;");
        popup.getContent().addAll(layout);
    }

    public void show(ReadOnlyBooleanProperty hover, Bounds bounds, Map<String, String> detail) {
        setPosition(bounds);
        setData(detail);

        hover.or(layout.hoverProperty()).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    if (!hover.getValue() && !layout.hoverProperty().getValue()) {
                        popup.hide();
                    }
                }
            }
        });
        popup.show(primaryStage);
    }

    private void setData(Map<String, String> data) {
        VBox root = new VBox();
        root.setStyle("-fx-padding: 2 4 2 4;");
        for (Map.Entry<String, String> set : data.entrySet()) {
            root.getChildren().add(getRowNode(set.getKey(), set.getValue()));
        }
        this.layout.getChildren().setAll(root);
    }

    private HBox getRowNode(String key, String value) {
        Label keyLabel = new Label(key + ":");
        Label valueLabel = new Label(value);
        HBox root = new HBox(keyLabel, valueLabel);
        root.setSpacing(SEPARATOR_WIDTH);
        return root;
    }

    private void setPosition(Bounds bounds) {
        this.popup.setX((int) bounds.getMinX() + 3);
        this.popup.setY((int) bounds.getMaxY() - 3);
    }
}
