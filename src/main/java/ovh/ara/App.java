package main.java.ovh.ara;

import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class App {
    private double array[];
    private int retries = 0;
    private double timeAverages[];
    private int iterations = 0;
    private int currentIteration = 0;

    public App(){

    }

    public Map<Integer, Double> getMapping(){
        Map<Integer, Double> map = new HashMap<>();
        for (int ii=0; ii< this.timeAverages.length; ii++) {
            map.put(new Integer((1 << ii)), new Double(this.timeAverages[ii]));
        }

        return map;
    }


    public void readUserInput() throws Exception{

        System.out.println("length = 2^");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        iterations = Integer.parseInt(s);
        System.out.println("retires = ^");
        timeAverages = new double[iterations];
        s = br.readLine();
        this.retries = Integer.parseInt(s);
    }

    private void init(){
        int size = (1 << (currentIteration + 1));
        this.array = new double[size];
    }


    private void clearArray(){
        for (int ii=0; ii<array.length; ii++){
            array[ii] = Math.exp(1);
        }
    }

    private void addSequentional(){
        double value = 0;
        System.out.println(array.length);
        for (int ii=0; ii<array.length; ii++){
            value += Math.log(array[ii]);
        }

    }



    public void measure(){
        while (currentIteration < iterations) {
            init();
            clearArray();
            double average = 0;
            for (int ii=0;ii<this.retries;ii++){
                double startTime = System.nanoTime();
                addSequentional();

                double elapsed = System.nanoTime() - startTime;
                average += (elapsed / Math.pow(10, 6));
            }

            average/=this.retries;
            timeAverages[currentIteration++] = average;
        }

    }


    public void writeToFile() throws Exception{
        if (array == null) {
            return;
        }

        FileWriter writer = new FileWriter(new File("output.txt"));
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(writer);

        for (int ii=0;ii<this.timeAverages.length;ii++){

            outputWriter.write(Integer.toString((1 <<(ii + 1))) + "\t" + Double.toString(this.timeAverages[ii]) + "\n");

        }


        outputWriter.flush();
        outputWriter.close();




    }

    public static void main(String argsd[]){

        App app = new App();
        try {
            app.readUserInput();
            app.measure();
            app.writeToFile();
            new XYSeriesDemo("title",app.getMapping());
        } catch (Exception e) {

        }
    }
}

class XYSeriesDemo extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series containing a null value.
     *
     * @param title the frame title.
     */
    public XYSeriesDemo(final String title, Map<Integer, Double> map) {

        super(title);
        final XYSeries series = new XYSeries("Random Data");
        for (Integer ii : map.keySet()){
            series.add(ii.intValue(), map.get(ii).doubleValue());
        }
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Series Demo",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
}

