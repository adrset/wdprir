package ovh.ara.adders;

import ovh.ara.threads.IThreadService;
import ovh.ara.threads.ThreadPoolService;
import ovh.ara.workers.IWorker;
import ovh.ara.workers.RaceWorker;
import ovh.ara.workers.RaceWorkerWithLatch;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class RaceAdder implements IAdder{
    protected double array[];
    protected AtomicIntegerArray atomics;
    protected int currentIteration = 0;
    protected int processors;
    protected IWorker[] runnables;
    protected IThreadService executor;

    public void init(){
        int size = (1 << (currentIteration + 1));
        this.array = new double[size];
        clearArray();
        // default zeros

        atomics = new AtomicIntegerArray(size);
    }

    public void setCurrentIteration(int a){
        this.currentIteration = a;
    }

    @Override
    public double[] getArray() {
        return array;
    }


    public RaceAdder() {
        processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + processors);
        runnables = new RaceWorker[processors];
        executor = new ThreadPoolService();

    }



    public void clearArray(){
        for (int ii=0; ii<array.length; ii++){
            array[ii] = Math.exp(1);
        }
    }

    public double add(){
        executor.init();

        for (int ii =0; ii<processors;ii++ ){
            RaceWorkerWithLatch sw =  new RaceWorkerWithLatch(this.array, atomics);
            runnables[ii] = sw;
            sw.setLatch(executor.getLatch());
        }
        for (int ii =0; ii<processors;ii++ ){
            executor.submit(runnables[ii]);
        }
        double value = 0;

        try {

            executor.await();

            for (int ii =0; ii<runnables.length;ii++ ){
                value += runnables[ii].getValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
