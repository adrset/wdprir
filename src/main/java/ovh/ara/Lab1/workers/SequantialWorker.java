package ovh.ara.Lab1.workers;
public class SequantialWorker implements IWorker {
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


    public SequantialWorker(int offset, int size, double array[]){
        this.array = array;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public void run() {
        value = add();
    }

    public double add(){
        double value = 0;
        for (int ii=offset; ii<size + offset; ii++){
            array[ii] = Math.log(array[ii]);
            value += array[ii];
        }
        return value;
    }
}
