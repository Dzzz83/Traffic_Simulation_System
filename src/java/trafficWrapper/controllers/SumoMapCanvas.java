// src/main/java/your/package/SumoMapCanvas.java
package trafficWrapper.controllers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SumoMapCanvas extends Canvas {

    private double zoom = 1.0;
    private double offsetX = 0;
    private double offsetY = 0;

    private final List<LaneShape> lanes = new ArrayList<>();

    public SumoMapCanvas(double width, double height) {
        super(width, height);
        loadNetwork("src/sumo/demo.net.xml"); // ← your exact file
        drawMap();

        // Enable zoom & pan with mouse wheel + drag
        setOnScroll(e -> {
            double delta = e.getDeltaY() > 0 ? 1.2 : 0.8;
            zoom *= delta;
            drawMap();
        });
        setOnMouseDragged(e -> {
            offsetX += e.getX() - getWidth() / 2;
            offsetY += e.getY() - getHeight() / 2;
            drawMap();
        });
    }

    private void loadNetwork(String netFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(netFilePath));
            doc.getDocumentElement().normalize();

            NodeList laneNodes = doc.getElementsByTagName("lane");
            for (int i = 0; i < laneNodes.getLength(); i++) {
                Element lane = (Element) laneNodes.item(i);
                String shapeStr = lane.getAttribute("shape");
                String[] points = shapeStr.split(" ");
                List<double[]> coords = new ArrayList<>();
                for (String p : points) {
                    String[] xy = p.split(",");
                    double x = Double.parseDouble(xy[0]);
                    double y = Double.parseDouble(xy[1]);
                    coords.add(new double[]{x, y});
                }
                lanes.add(new LaneShape(coords, lane.getParentNode() instanceof Element edge ?
                        ((Element) lane.getParentNode()).getAttribute("priority") : "-1"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawMap() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.web("#1e1e1e"));
        gc.fillRect(0, 0, getWidth(), getHeight());

        Affine transform = new Affine();
        transform.appendScale(zoom, zoom, getWidth() / 2, getHeight() / 2);
        transform.appendTranslation(offsetX, offsetY);
        gc.setTransform(transform);

        // Auto-center the network
        double minX = lanes.stream().flatMap(l -> l.points.stream()).mapToDouble(p -> p[0]).min().orElse(0);
        double maxX = lanes.stream().flatMap(l -> l.points.stream()).mapToDouble(p -> p[0]).max().orElse(1);
        double minY = lanes.stream().flatMap(l -> l.points.stream()).mapToDouble(p -> p[1]).min().orElse(0);
        double maxY = lanes.stream().flatMap(l -> l.points.stream()).mapToDouble(p -> p[1]).max().orElse(1);

        double centerX = (minX + maxX) / 2;
        double centerY = (minY + maxY) / 2;

        gc.translate(-centerX, -centerY);

        // Draw all lanes
        for (LaneShape lane : lanes) {
            gc.setStroke(Color.web("#444444"));
            gc.setLineWidth(6.0 / zoom); // thicker highways
            if ("-1".equals(lane.priority)) {
                gc.setStroke(Color.web("#666666"));
                gc.setLineWidth(8.0 / zoom);
            }
            gc.setLineCap(javafx.scene.shape.StrokeLineCap.ROUND);

            double[] xPoints = lane.points.stream().mapToDouble(p -> p[0]).toArray();
            double[] yPoints = lane.points.stream().mapToDouble(p -> p[1]).toArray();
            gc.strokePolyline(xPoints, yPoints, xPoints.length);

            // Lane markings (white dashes)
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(0.8 / zoom);
            gc.setLineDashes(5, 5);
            gc.strokePolyline(xPoints, yPoints, xPoints.length);
            gc.setLineDashes(null);
        }

        // Draw junctions (small circles)
        gc.setFill(Color.ORANGE);
        for (LaneShape lane : lanes) {
            if (!lane.points.isEmpty()) {
                double[] first = lane.points.get(0);
                double[] last = lane.points.get(lane.points.size() - 1);
                gc.fillOval(first[0] - 3, first[1] - 3, 6, 6);
                gc.fillOval(last[0] - 3, last[1] - 3, 6, 6);
            }
        }
    }

    private static class LaneShape {
        final List<double[]> points;
        final String priority;

        LaneShape(List<double[]> points, String priority) {
            this.points = points;
            this.priority = priority;
        }
    }
}