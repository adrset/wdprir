package ovh.ara.Lab1.workers;

import ovh.ara.Lab1.threads.ILatchable;

import java.util.concurrent.CountDownLatch;

public class SequentialWorkerWithLatch extends SequantialWorker implements ILatchable {

    CountDownLatch latch;

    public SequentialWorkerWithLatch(int offset, int size, double array[]){
       super(offset, size, array);
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }


    @Override
    public double add(){
        double value = super.add();
        latch.countDown();
        return value;
    }



}
