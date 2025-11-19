package trafficWrapper.controllers;
import trafficWrapper.model.SimulationManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardController implements Initializable {

    @FXML private Label timeLabel;
    @FXML private Label vehicleLabel;
    @FXML private Label statusLabel;
    @FXML private GridPane tlGrid;

    private SimulationManager sim = new SimulationManager();
    private final String[] TL_IDS = {"0", "1", "2", "3"};   // ← change to your real IDs

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createTrafficLightControls();
        startRefreshTask();
    }

    private void createTrafficLightControls() {
        for (int i = 0; i < TL_IDS.length; i++) {
            String id = TL_IDS[i];
            Label lbl = new Label("TL " + id);
            lbl.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

            ComboBox<String> cb = new ComboBox<>();
            cb.getItems().addAll("NS Green", "NS Yellow", "EW Green", "EW Yellow");
            cb.setValue("NS Green");
            cb.setStyle("-fx-font-size: 16px;");

            int finalI = i;
            cb.setOnAction(e -> sim.setTrafficLightPhase(id, cb.getSelectionModel().getSelectedIndex()));

            tlGrid.add(lbl, 0, i);
            tlGrid.add(cb, 1, i);
        }
    }

    @FXML private void startSimulation() {
        status("Starting SUMO...", "#ff9800");
        new Thread(() -> {
            sim.startSimulation();
            Platform.runLater(() -> status("Connected – Running", "#00e676"));
        }).start();
    }

    @FXML private void emergencyStop() {
        sim.stopSimulation();
        status("Stopped", "#757575");
    }

    private void status(String text, String color) {
        Platform.runLater(() -> statusLabel.setText("Status: " + text));
        statusLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 20px; -fx-font-weight: bold;");
    }

    private void startRefreshTask() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            if (sim.isConnected()) {
                Platform.runLater(() -> {
                    timeLabel.setText(String.format("%.1f s", sim.getCurrentTime()));
                    vehicleLabel.setText(String.valueOf(sim.getVehicleCount()));
                });
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
    }
}