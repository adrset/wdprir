package main.java.ovh.ara;
public class Worker extends Thread {
    private int offset;
    private int size;
    private double array[];
    private double value = 0;

    public double getValue() {
        return value;
    }

    public void reset(){
        value = 0;
    }


    public Worker(int offset, int size, double array[]){
        this.array = array;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public void run() {
        addSequentional();
    }

    private void addSequentional(){
        for (int ii=offset; ii<size + offset; ii++){
            array[ii] = Math.log(array[ii]);
            value += array[ii];
        }
    }
}
