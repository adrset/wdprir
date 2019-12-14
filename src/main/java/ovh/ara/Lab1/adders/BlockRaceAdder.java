package ovh.ara.Lab1.adders;

import ovh.ara.Lab1.workers.RaceBlockWorkerWithLatch;

public class BlockRaceAdder extends RaceAdder {
    private int blockSize;

    public BlockRaceAdder(){}

    public void setBlockSize(int size){
        this.blockSize = (1 << size);
    }
    public double add(){
        super.executor.init();

        for (int ii =0; ii<processors;ii++ ){
            RaceBlockWorkerWithLatch sw =  new RaceBlockWorkerWithLatch(this.array, atomics, 8);
            runnables[ii] = sw;
            sw.setLatch(executor.getLatch());
        }
        for (int ii =0; ii<processors;ii++ ){
            executor.submit(runnables[ii]);
        }
        double value = 0;

        try {

            executor.await();

            for (int ii =0; ii<runnables.length;ii++ ){
                value += runnables[ii].getValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
