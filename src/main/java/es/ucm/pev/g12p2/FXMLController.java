package es.ucm.pev.g12p2;

import es.ucm.pev.g12p2.crossover.Crossover;
import es.ucm.pev.g12p2.crossover.CrossoverFactory;
import es.ucm.pev.g12p2.mutation.Mutation;
import es.ucm.pev.g12p2.mutation.MutationFactory;
import es.ucm.pev.g12p2.selection.Selection;
import es.ucm.pev.g12p2.selection.SelectionFactory;
import java.awt.Dimension;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private TextField txtPorcentajeInv;
    @FXML
    private TextField txtPuntoIniInv;
    @FXML
    private TextField txtPuntoFinInv;
    
    @FXML
    private TextField txtMejorAbs;
    
    @FXML
    private TextField txtIndividuo;
    
    @FXML
    private TextField txtPeorAbs;
    
    @FXML
    private TextField txtTotalC;
    
    @FXML
    private TextField txtTotalM;
    
    @FXML
    private TextField txtTotalI;
     
    @FXML
    private ComboBox cboFuncion;
    @FXML
    private ComboBox cboCruce;
    @FXML
    private ComboBox cboSeleccion;
    @FXML
    private ComboBox cboMutacion;

    @FXML
    private TabPane tabPane;

    @FXML
    private javafx.scene.control.CheckBox chbElitism;

    @FXML
    private Label lblN;
    @FXML
    private TextField txtN;

    @FXML
    private void handleMutationSelected(ActionEvent event) {

        String function = cboMutacion.getSelectionModel().getSelectedItem().toString();
        
        switch (function) {
            case "Insercion":
                this.lblN.setText("Nº Inserciones:");
                this.lblN.setVisible(true);
                this.txtN.setVisible(true);
                break;
            case "Heuristica":
                this.lblN.setText("Nº Seleccionados:");
                this.lblN.setVisible(true);
                this.txtN.setVisible(true);
                break;
            default:
                this.lblN.setVisible(false);
                this.txtN.setVisible(false);
                break;
        }
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
        double inversionPercentage = Double.parseDouble(txtPorcentajeInv.getText());
        int inversionInitialP = Integer.parseInt(txtPuntoIniInv.getText());
        int inversionFinalP = Integer.parseInt(txtPuntoFinInv.getText());
        int seed = Integer.parseInt(txtSemilla.getText());
        boolean elitism = chbElitism.isSelected();
        int nF4 = 4;
        if (!txtN.getText().isEmpty()) {
            nF4 = Integer.parseInt(txtN.getText());
        }
        Selection selection = SelectionFactory.getSelectionAlgorithm(cboSeleccion.getSelectionModel().getSelectedItem().toString());
        Crossover crossover = CrossoverFactory.getCrossoverAlgorithm(cboCruce.getSelectionModel().getSelectedItem().toString());
        Mutation mutation = MutationFactory.getMutationAlgorithm(cboMutacion.getSelectionModel().getSelectedItem().toString(),
                prob_mut,populationSize,nF4);

        AG newAG = new AG(function, populationSize, max_generations, prob_cross,
                prob_mut, tolerance, seed, selection, crossover, elitism, mutation,
                inversionPercentage, inversionInitialP, inversionFinalP);
    
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
        plot.addLinePlot("Mejor de la generacion", numbers, genBest);
        plot.addLinePlot("Media de la generacion", numbers, genAvg);

         DecimalFormat noDecim = new DecimalFormat("0");
        Pane pane = new Pane();
        pane.getChildren().add(swingNode);
        txtMejorAbs.setText(noDecim.format(absBest[max_generations - 1]));
        txtIndividuo.setText(viewInfo.getBestIndividual());
        this.txtPeorAbs.setText(noDecim.format(viewInfo.getAbsoluteWorst()));
        this.txtTotalC.setText(noDecim.format(viewInfo.getNumCrossovers()));
        this.txtTotalM.setText(noDecim.format(viewInfo.getNumMutations()));
        this.txtTotalI.setText(noDecim.format(viewInfo.getNumInversions()));
        tab.setContent(pane);
        tabPane.getTabs().add(tab);
        count++;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        count = 1;
        cboFuncion.getItems().addAll(
                "ajuste.dat",
                "datos12.dat",
                "datos15.dat",
                "datos30.dat"
        );
        
        cboFuncion.getSelectionModel().selectFirst();

        cboCruce.getItems().addAll(
                "PMX",
                "CX",
                "ERX",
                "OX",
                "Codificacion Ordinal",
                "OX Prioridad Orden",
                "OX Prioridad Posicion",
                "OX Modificado Propio"
        );
        cboCruce.getSelectionModel().selectFirst();

        cboSeleccion.getItems().addAll(
                "Ruleta",
                "Estocastico Universal",
                "Truncamiento",
                "Torneo Determinista",
                "Torneo Probabilistico"
        );
        cboSeleccion.getSelectionModel().selectFirst();

        this.lblN.setVisible(false);
        this.txtN.setVisible(false);
        
        cboMutacion.getItems().addAll(
                "Insercion",
                "Intercambio",
                "Inversion",
                "Heuristica",
                "Intercambio Multiple"
        );
        
        cboMutacion.getSelectionModel().selectFirst();
    }

    private ObservableList<XYChart.Series<Double, Double>> getChartData(AGView viewInfo) {

        ObservableList<XYChart.Series<Double, Double>> data = FXCollections
                .observableArrayList();

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Mejor absoluto");
        //List<Double> absoluteBest = viewInfo.getAbsoluteBest();

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Mejor de la generacion");
        //List<Double> generationBest = viewInfo.getGenerationBest();

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Media de la generacion");
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
