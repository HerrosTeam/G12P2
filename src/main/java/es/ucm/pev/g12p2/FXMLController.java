package es.ucm.pev.g12p2;

import es.ucm.pev.g12p2.crossover.Crossover;
import es.ucm.pev.g12p2.crossover.CrossoverFactory;
import es.ucm.pev.g12p2.selection.Selection;
import es.ucm.pev.g12p2.selection.SelectionFactory;
import java.awt.Dimension;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JPanel;
import org.math.plot.Plot2DPanel;

public class FXMLController implements Initializable {

    private int count;

    @FXML
    private TextField txtPrecision;
    @FXML
    private TextField txtPoblacion;
    @FXML
    private TextField txtIteraciones;
    @FXML
    private TextField txtCruces;
    @FXML
    private TextField txtMutacion;
    @FXML
    private TextField txtSemilla;
    @FXML
    private TextField txtSeleccion1;
    @FXML
    private TextField txtSeleccion2;

    @FXML
    private Label lblMejorAbs;
    @FXML
    private TextField txtMejorAbs;

    @FXML
    private ComboBox cboFuncion;
    @FXML
    private ComboBox cboCruce;
    @FXML
    private ComboBox cboSeleccion;

    @FXML
    private TabPane tabPane;

    @FXML
    private javafx.scene.control.CheckBox chbElitism;

    @FXML
    private Label lblN;
    @FXML
    private TextField txtN;

    @FXML
    private void onFuncionAction(ActionEvent event) {
/*
        String function = cboFuncion.getSelectionModel().getSelectedItem().toString();
        if (function.equals("Función 4") || function.equals("Función 4 reales")) {
            this.lblN.setVisible(true);
            this.txtN.setVisible(true);
        } else {
            this.lblN.setVisible(false);
            this.txtN.setVisible(false);
        }*/
    }

    @FXML
    private void onCrearAGButton(ActionEvent event) {

        txtMejorAbs.setText("");
                
        String function = cboFuncion.getSelectionModel().getSelectedItem().toString();
        int populationSize = Integer.parseInt(txtPoblacion.getText());
        int max_generations = Integer.parseInt(txtIteraciones.getText());
        double prob_cross = Double.parseDouble(txtCruces.getText());
        double prob_mut = Double.parseDouble(txtMutacion.getText());
        double tolerance = Double.parseDouble(txtPrecision.getText());
        int seed = Integer.parseInt(txtSemilla.getText());
        Selection selection = SelectionFactory.getSelectionAlgorithm(cboSeleccion.getSelectionModel().getSelectedItem().toString());
        Crossover crossover = CrossoverFactory.getCrossoverAlgorithm(cboCruce.getSelectionModel().getSelectedItem().toString());
        boolean elitism = chbElitism.isSelected();
        int nF4 = 4;
        if (!txtN.getText().isEmpty()) {
            nF4 = Integer.parseInt(txtN.getText());
        }

        AG newAG = new AG(function, populationSize, max_generations, prob_cross,
                prob_mut, tolerance, seed, selection, crossover, elitism, nF4);

        AGView viewInfo = newAG.executeAlgorithm();

        /*
        Tab tab = new Tab();
        tab.setText("AG " + count);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Generación");
        yAxis.setLabel("Evaluación");

        yAxis.setAutoRanging(true);
        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(max_generations);
        xAxis.setLowerBound(0);
        xAxis.setTickUnit(20);
        yAxis.setForceZeroInRange(false);
        
        yAxis.setAnimated(true);

        LineChart lineChart = new LineChart(xAxis, yAxis);
        
        lineChart.setAnimated(false);
        lineChart.layout();
        
        lineChart.setPrefSize(600, 450);
        lineChart.setCreateSymbols(false);

        lineChart.setMaxSize(550, 515);
        lineChart.setData(getChartData(viewInfo));
        tab.setContent(lineChart);
        tabPane.getTabs().add(tab);
        count++;
         */
        Tab tab = new Tab();
        tab.setText("AG " + count);

        final SwingNode swingNode = new SwingNode();
        JPanel graphPanel = new JPanel();
        Plot2DPanel plot = new Plot2DPanel();
        plot.addLegend("SOUTH");
        plot.setPreferredSize(new Dimension(610, 460));
        graphPanel.add(plot);
        swingNode.setContent(graphPanel);

        double[] absBest = viewInfo.getAbsoluteBest();
        double[] genBest = viewInfo.getGenerationBest();
        double[] genAvg = viewInfo.getGenerationAverage();
        double[] numbers = new double[max_generations];

        for (int i = 0; i < max_generations; i++) {
            numbers[i] = i;
        }

        plot.removeAllPlots();
        plot.addLinePlot("Mejor absoluto", numbers, absBest);
        plot.addLinePlot("Mejor de la generación", numbers, genBest);
        plot.addLinePlot("Media de la generación", numbers, genAvg);

        Pane pane = new Pane();
        pane.getChildren().add(swingNode);
        DecimalFormat decim = new DecimalFormat("0.00000");
        txtMejorAbs.setText(decim.format(absBest[max_generations - 1]));
        tab.setContent(pane);
        tabPane.getTabs().add(tab);
        count++;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        count = 1;
        cboFuncion.getItems().addAll(
                "Función 1"
        );
        
        cboFuncion.getSelectionModel().selectFirst();

        cboCruce.getItems().addAll(
                "Monopunto",
                "Multipunto",
                "CX",
                "ERX",
                "PMX",
                "OX",
                "Ordinal Codification"
        );
        cboCruce.getSelectionModel().selectFirst();

        cboSeleccion.getItems().addAll(
                "Ruleta",
                "Estocástico Universal",
                "Truncamiento",
                "Torneo Determinista",
                "Torneo Probabilístico"
        );
        cboSeleccion.getSelectionModel().selectFirst();

        this.lblN.setVisible(true);
        this.txtN.setVisible(true);
    }

    private ObservableList<XYChart.Series<Double, Double>> getChartData(AGView viewInfo) {

        ObservableList<XYChart.Series<Double, Double>> data = FXCollections
                .observableArrayList();

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Mejor absoluto");
        //List<Double> absoluteBest = viewInfo.getAbsoluteBest();

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Mejor de la generación");
        //List<Double> generationBest = viewInfo.getGenerationBest();

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Media de la generación");
        //List<Double> generationAvg = viewInfo.getGenerationAverage();
        /*
        for (int i = 0; i < absoluteBest.size(); i++) {
            series1.getData().add(new XYChart.Data(i, absoluteBest.get(i)));
            series2.getData().add(new XYChart.Data(i, generationBest.get(i)));
            series3.getData().add(new XYChart.Data(i, generationAvg.get(i)));
        }*/

        data.add(series3);
        data.add(series2);
        data.add(series1);

        return data;
    }
}
