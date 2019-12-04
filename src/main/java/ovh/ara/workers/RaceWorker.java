package ovh.ara.workers;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class RaceWorker implements IWorker  {
    private int offset;
    private int size;
    private double array[];
    private AtomicIntegerArray locks;
    private double value = 0;

    public double getValue() {
        return value;
    }

    public void reset(){
        value = 0;
    }


    public RaceWorker(double array[], AtomicIntegerArray locks){
        this.array = array;
        this.locks = locks;
    }

    @Override
    public void run() {
        this.value = add();
    }

    public double add(){
        double value = 0;
        for (int ii=0; ii<array.length; ii++){
           if(locks.get(ii) != 1){
                value += array[ii];
                locks.set(ii, 1);
           }
        }
        return value;
    }
}
