package main.java.ovh.ara;
public class Worker extends Thread {
    private int offset;
    private int size;
    private double array[];

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
        double value = 0;
        for (int ii=0; ii<size; ii++){
            value += Math.log(array[offset + ii]);
        }
    }
}
