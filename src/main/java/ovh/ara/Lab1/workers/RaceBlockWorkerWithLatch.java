package ovh.ara.Lab1.workers;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class RaceBlockWorkerWithLatch extends RaceWorkerWithLatch {
    private int blockSize;
    private int size;
    public RaceBlockWorkerWithLatch(double array[], AtomicIntegerArray atomics, int blockSize){
        super(array, atomics);
        this.blockSize = blockSize;
    }

    public double add(){
        double value = 0;

        for (int ii=0; ii<array.length; ii+=blockSize){
            if(locks.getAndSet(ii, 1) != 1){

                for (int jj=ii; jj< ii + blockSize; jj++){
                    value += array[jj];
                }
            }
        }
        latch.countDown();
        return value;
    }

}
