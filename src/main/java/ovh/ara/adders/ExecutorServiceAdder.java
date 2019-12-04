package ovh.ara.adders;

import ovh.ara.workers.IWorker;
import ovh.ara.workers.RaceWorker;
import ovh.ara.workers.SequantialWorker;
import ovh.ara.workers.SequentialWorkerWithLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ExecutorServiceAdder implements IAdder{
    private double array[];
    private int currentIteration = 0;
    private int processors;
    private IWorker runnables[];
    private CountDownLatch latch;
    public void init(){
        int size = (1 << (currentIteration + 1));
        this.array = new double[size];
        latch = new CountDownLatch(processors);
        clearArray();
    }

    public void setCurrentIteration(int a){
        this.currentIteration = a;
    }

    @Override
    public double[] getArray() {
        return array;
    }


    public ExecutorServiceAdder() {
        processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + processors);
        runnables = new SequantialWorker[processors];

    }

    public void clearArray(){
        for (int ii=0; ii<array.length; ii++){
            array[ii] = Math.exp(1);
        }
    }

    public double add(){
        int size = array.length / processors;

        for (int ii =0; ii<processors;ii++ ){
            runnables[ii] = new SequentialWorkerWithLatch(ii* size, size, this.array, latch);
        }
        double value = 0;
        try {

            latch.await();
            for (int ii =0; ii<runnables.length;ii++ ){
                value = runnables[ii].getValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;//value;
    }
}
