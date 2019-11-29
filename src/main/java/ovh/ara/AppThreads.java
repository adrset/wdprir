package main.java.ovh.ara;
import java.io.*;

public class AppThreads {
    private double array[];
    private int retries = 0;
    private double timeAverages[];
    private int iterations = 0;
    private int currentIteration = 0;
    private int processors;
    private Worker runnables[];

    public AppThreads(){
        processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + processors);
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

    private void addThreaded() throws Exception{
        int size = array.length / processors;
        runnables = new Worker[processors];
        for (int ii =0; ii<processors;ii++ ){
            runnables[ii] = new Worker(ii* size, size, this.array);
        }
        for (int ii =0; ii<processors;ii++ ){
            runnables[ii].start();
        }
        for (int ii =0; ii<processors;ii++ ){
            runnables[ii].join();
        }
    }



    public void measure(){
        while (currentIteration < iterations) {
            init();
            clearArray();
            double average = 0;
            for (int ii=0;ii<this.retries;ii++){
                double startTime = System.nanoTime();
                try {
                    addThreaded();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                double elapsed = System.nanoTime() - startTime;
                average += elapsed;
            }

            average/=this.retries;
            timeAverages[currentIteration++] = average;
        }

    }


    public void writeToFile() throws Exception{
        if (array == null) {
            return;
        }

        FileWriter writer = new FileWriter(new File("output2.txt"));
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(writer);

        for (int ii=0;ii<this.timeAverages.length;ii++){

            outputWriter.write(Integer.toString((1 <<(ii + 1))) + "\t" + Double.toString(this.timeAverages[ii]) + "\n");

        }


        outputWriter.flush();
        outputWriter.close();




    }

    public static void main(String argsd[]){

        AppThreads app = new AppThreads();
        try {

            app.readUserInput();
            app.measure();
            app.writeToFile();
        } catch (Exception e) {

        }
    }
}
