package ovh.ara.workers;

import java.util.concurrent.CountDownLatch;

public class SequentialWorkerWithLatch extends SequantialWorker {

    CountDownLatch latch;

    public SequentialWorkerWithLatch(int offset, int size, double array[], CountDownLatch latch){
       super(offset, size, array);
       this.latch = latch;
    }

    @Override
    public double add(){
        double value = super.add();
        latch.countDown();
        return value;
    }

}
