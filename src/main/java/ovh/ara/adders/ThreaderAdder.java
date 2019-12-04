package ovh.ara.adders;

import ovh.ara.workers.IWorker;
import ovh.ara.workers.SequantialWorker;

public class ThreaderAdder implements IAdder {

    private double array[];
    private int currentIteration = 0;
    private int processors;
    private IWorker runnables[];
    private Thread threads[];
    public void init(){
        int size = (1 << (currentIteration + 1));
        this.array = new double[size];
        clearArray();
    }

    public void setCurrentIteration(int a){
        this.currentIteration = a;
    }

    @Override
    public double[] getArray() {
        return array;
    }


    public ThreaderAdder() {
        processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + processors);
    }

    public void clearArray(){
        for (int ii=0; ii<array.length; ii++){
            array[ii] = Math.exp(1);
        }
    }

    public double add(){
        int size = array.length / processors;
        runnables = new SequantialWorker[processors];
        threads = new Thread[processors];

        for (int ii =0; ii<processors;ii++ ){
            runnables[ii] = new SequantialWorker(ii* size, size, this.array);
        }
        for (int ii =0; ii<processors;ii++ ){
            threads[ii] = new Thread(runnables[ii]);
            threads[ii].start();
        }
        double value = 0;
        try {

            for (int ii =0; ii<processors;ii++ ){
                threads[ii].join();
            }
            for (int ii =0; ii<runnables.length;ii++ ){
                value = runnables[ii].getValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;//value;
    }


}
