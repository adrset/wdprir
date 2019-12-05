package ovh.ara.adders;

import ovh.ara.threads.IThreadService;
import ovh.ara.threads.SimpleThreadService;
import ovh.ara.workers.IWorker;
import ovh.ara.workers.SequantialWorker;
import ovh.ara.workers.SequentialWorkerWithLatch;

public class ThreaderAdder implements IAdder {

    private double array[];
    private int currentIteration = 0;
    private int processors;
    private IWorker runnables[];
    private IThreadService exectutor;
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
        exectutor = new SimpleThreadService();
    }

    public void clearArray(){
        for (int ii=0; ii<array.length; ii++){
            array[ii] = Math.exp(1);
        }
    }

    public double add(){
        exectutor.init();
        int size = array.length / processors;
        runnables = new SequantialWorker[processors];

        for (int ii =0; ii<processors;ii++ ){
            SequentialWorkerWithLatch sw =  new SequentialWorkerWithLatch(ii* size, size, this.array);
            runnables[ii] = sw;
            sw.setLatch(exectutor.getLatch());
        }
        for (int ii =0; ii<processors;ii++ ){
            exectutor.submit(runnables[ii]);
        }
        double value = 0;
        try {

            exectutor.await();
            for (int ii =0; ii<runnables.length;ii++ ){
                value = runnables[ii].getValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;//value;
    }


}
