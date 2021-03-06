package ovh.ara.Lab1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import ovh.ara.Lab1.adders.*;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    private int retries = 0;
    private double timeAverages[];
    private int iterations = 0;
    private int currentIteration = 0;
    private int maxBlockSize = 0;
    private int choice = 0;

    IAdder adder;

    public Main(){
    }

    public Map<Integer, Double> getMapping(){
        Map<Integer, Double> map = new LinkedHashMap<>();
        for (int ii=0; ii< this.timeAverages.length; ii++) {
            map.put(new Integer((1 << ii)), new Double(this.timeAverages[ii]));
            //System.out.println(ii + "  " + this.timeAverages[ii]);
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
        System.out.println("Sync/Threaded/Race/ConstPoolThreaded/RaceAdderWitBlocks (1/2/3/4/5)");
        s = br.readLine();
        choice = Integer.parseInt(s);
        if (choice == 1) {
            adder = new SynchronousAdder();
        } else if (choice == 2) {
            adder = new ThreaderAdder();
        } else if (choice == 3){
            adder = new RaceAdder();
        } else if (choice == 4){
            adder = new PooledAdder();
        } else if (choice == 5){
            System.out.println("Please specify max block size 2^x where x=?");
            s = br.readLine();
            this.maxBlockSize = Integer.parseInt(s);
            adder = new BlockRaceAdder();
        }else {
            throw new Exception("Choose 1, 2 or 3!");
        }
    }



    public void measure(){
        if (maxBlockSize > 0) {
            measureSameSizeArray();
            return;
        }
        while (currentIteration < iterations) {
            adder.init();
            double average = 0;
            for (int ii=0;ii<this.retries;ii++){
                double startTime = System.nanoTime();
                try {
                    double val = adder.add();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                double elapsed = System.nanoTime() - startTime;
                average += elapsed;
                //System.out.println(currentIteration + " " + elapsed);
            }

            timeAverages[currentIteration++] = average / (this.retries);
            adder.setCurrentIteration(currentIteration);
        }

    }

    public void measureSameSizeArray(){
        // iterations -> max size of array

        try {
            BlockRaceAdder blockAdder = (BlockRaceAdder) adder;
            adder.setCurrentIteration(iterations);
            this.timeAverages = new double[maxBlockSize];
            while (currentIteration < maxBlockSize) {
                double average = 0;

                for (int ii=0;ii<this.retries;ii++){
                    blockAdder.setBlockSize(currentIteration);
                    adder.init();

                    double startTime = System.nanoTime();
                    try {
                        double val = adder.add();
                      // System.out.println(val);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    double elapsed = System.nanoTime() - startTime;
                    average += elapsed;
                }

                timeAverages[currentIteration++] = average / (this.retries);
            }
            System.out.println(timeAverages);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void writeToFile() throws Exception{
        if (adder.getArray() == null) {
            return;
        }

        FileWriter writer = new FileWriter(new File("output" + (choice) + ".txt"));
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(writer);

        for (int ii=0;ii<this.timeAverages.length;ii++){

            outputWriter.write(Integer.toString((1 <<(ii + 1))) + "\t" + Double.toString(this.timeAverages[ii]) + "\n");

        }


        outputWriter.flush();
        outputWriter.close();




    }

    public static void main(String argsd[]){

        Main app = new Main();
        try {

            app.readUserInput();
            app.measure();
            app.writeToFile();
            XYSeriesDemo t = new XYSeriesDemo("title",app.getMapping());
            t.setVisible(true);
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
        for (Integer ii : map.keySet()) {
            series.add(ii.intValue(), map.get(ii).doubleValue());
           // System.out.println(ii.intValue() + "  " + map.get(ii).doubleValue());
        }
        this.setBounds(210, 10, 1280, 720);
        this.toFront();
        this.repaint();
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Time complexity",
                "array size",
                "time (ns)",
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