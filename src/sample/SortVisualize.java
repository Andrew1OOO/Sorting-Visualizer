package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Arrays;

public class SortVisualize extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sorting Visualizer");
        SortService service = new SortService();
        int[] x = new int[20];
        for(int j = 0; j < x.length; j++){
            x[j] = j;
        }
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Sorting Visualizer");
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        /*BackgroundFill background_fill = new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        barChart.setBackground(background);*/
        xAxis.setTickLabelFill(Color.BLUE);
        yAxis.setTickLabelFill(Color.BLUE);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for(int i = 0; i < x.length; i++) {
            series.getData().add(new XYChart.Data<>("" +i, x[i]));
            barChart.setMinHeight(barChart.getMinHeight() + 10);
            barChart.setMinWidth(barChart.getMinWidth() + 10);

        }
        System.out.println(series.getData());
       service.valueProperty().addListener((obs, oldValue, newValue) -> {
            // run on JavaFX Application Thread, safe to update GUI

            System.out.println("Hello from JavaFX Application Thread");
            System.out.println(Arrays.toString(x));
        });
        service.sortArray(x);
        barChart.getData().addAll(series);
        Group root = new Group(barChart);
        Scene scene = new Scene(root, barChart.getMaxWidth(), barChart.getMaxHeight());
        primaryStage.setScene(scene);
        scene.getStylesheets().add("sample/color.css");
        primaryStage.show();
    }

}
