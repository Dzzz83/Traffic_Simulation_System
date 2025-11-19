import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    private SumoConnection sumo;
    private VehicleService vehicleService;
    private TrafficLightService tlService;
    private SimulationLoop loop;

    private Pane canvas;

    @Override
    public void start(Stage stage) {
        Button startBtn = new Button("Start Simulation");
        startBtn.setLayoutX(10);
        startBtn.setLayoutY(10);

        canvas = new Pane();
        canvas.setPrefSize(800, 600);
        canvas.setStyle("-fx-background-color: #333;");
        canvas.setLayoutY(50);

        startBtn.setOnAction(e -> startSimulation());

        Pane root = new Pane(startBtn, canvas);
        stage.setScene(new Scene(root, 900, 700));
        stage.setTitle("Traffic Simulation UI");
        stage.show();
    }

    private void startSimulation() {
        new Thread(() -> {
            try {
                sumo = new SumoConnection();
                sumo.startSUMO();

                vehicleService = new VehicleService(sumo.getConn());
                tlService = new TrafficLightService(sumo.getConn());

                loop = new SimulationLoop(sumo.getConn(), () -> updateUI());
                new Thread(loop).start();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void updateUI() {
        Platform.runLater(() -> {
            canvas.getChildren().clear();
            try {
                List<String> ids = vehicleService.getVehicleIDs();
                for (String id : ids) {
                    double[] pos = vehicleService.getVehiclePosition(id);
                    drawVehicle(pos[0], pos[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void drawVehicle(double x, double y) {
        Circle c = new Circle(4, Color.RED);
        c.setTranslateX(x / 2); // scale down to fit canvas
        c.setTranslateY(y / 2);
        canvas.getChildren().add(c);
    }

    public static void main(String[] args) {
        launch();
    }
}
