package ovh.ara.Lab1.adders;

import ovh.ara.Lab1.threads.IThreadService;
import ovh.ara.Lab1.threads.ThreadPoolService;
import ovh.ara.Lab1.workers.IWorker;
import ovh.ara.Lab1.workers.SequantialWorker;
import ovh.ara.Lab1.workers.SequentialWorkerWithLatch;


public class PooledAdder implements IAdder{
    private double array[];
    private int currentIteration = 0;
    private int processors;
    private IWorker runnables[];
    private IThreadService executor;
    public void init(){
        int size = (1 << (currentIteration + 1));
        this.array = new double[size];
        executor.init();
        clearArray();
    }

    public void setCurrentIteration(int a){
        this.currentIteration = a;
    }

    @Override
    public double[] getArray() {
        return array;
    }


    public PooledAdder() {
        processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU cores: " + processors);
        runnables = new SequantialWorker[processors];
        executor = new ThreadPoolService();

    }

    public void clearArray(){
        for (int ii=0; ii<array.length; ii++){
            array[ii] = Math.exp(1);
        }
    }

    public double add(){
        int size = array.length / processors;

        double value = 0;
        try {
            for (int ii =0; ii<processors;ii++ ){
                SequentialWorkerWithLatch sw = new SequentialWorkerWithLatch(ii* size, size, this.array);
                runnables[ii] = sw;
                sw.setLatch(executor.getLatch());
            }
            for (int ii =0; ii<processors;ii++ ){
                executor.submit(runnables[ii]);
            }
            executor.await();
            for (int ii =0; ii<runnables.length;ii++ ){
                value = runnables[ii].getValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;//value;
    }
}
