package ovh.ara.Lab1.workers;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class RaceWorker implements IWorker  {

    protected double array[];
    protected AtomicIntegerArray locks;
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
           if(locks.getAndSet(ii, 1) != 1){

                value += array[ii];
           }
        }
        return value;
    }
}
