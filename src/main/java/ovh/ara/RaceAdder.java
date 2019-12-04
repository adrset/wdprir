package main.java.ovh.ara;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class RaceAdder implements IAdder{
    private double array[];
    private AtomicIntegerArray atomics;
    private int currentIteration = 0;
    private int processors;
    private Worker runnables[];

    public void init(){
        int size = (1 << (currentIteration + 1));
        this.array = new double[size];
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
        runnables = new Worker[processors];
        for (int ii =0; ii<processors;ii++ ){
            runnables[ii] = new Worker(0, 0, this.array);
        }
    }



    public void clearArray(){
        for (int ii=0; ii<array.length; ii++){
            array[ii] = Math.exp(1);
        }
    }

    public double add(){
        int size = array.length / processors;

        for (int ii =0; ii<processors;ii++ ){
            runnables[ii].start();
        }
        //double value = 0;

        try {

            for (int ii =0; ii<processors;ii++ ){
                runnables[ii].join();
            }

//            for (int ii =0; ii<processors;ii++ ){
//                value += runnables[ii].getValue();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;//value;
    }
}
