package main.java.ovh.ara;

public class SynchronousAdder implements IAdder {
    private double array[];

    private int currentIteration = 0;

    public SynchronousAdder(){

    }

    public void setCurrentIteration(int a){
        this.currentIteration = a;
    }


    public void init(){
        int size = (1 << (currentIteration + 1));
        this.array = new double[size];
    }


    public void clearArray(){
        for (int ii=0; ii<array.length; ii++){
            array[ii] = Math.exp(1);
        }
    }

    public double[] getArray(){
        return array;
    }

    public double add(){
       // System.out.println(array.length);

        double value = 0;
        for (int ii=0; ii<array.length; ii++){
            //System.out.println("el:" + array[ii] + " " + Math.log(array[ii]));

            value += Math.log(array[ii]);
        }

        //System.out.println(array.length);

        return value;
    }
}
