package ovh.ara.Lab1.workers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class RaceWorkerWithLatch extends RaceWorker {
    CountDownLatch latch;

    public RaceWorkerWithLatch(double array[], AtomicIntegerArray atomics){
        super(array, atomics);
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
