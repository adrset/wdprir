package ovh.ara.adders;

import ovh.ara.workers.RaceBlockWorkerWithLatch;
import ovh.ara.workers.RaceWorkerWithLatch;

public class BlockRaceAdder extends RaceAdder {
    private int blockSize;
    public BlockRaceAdder(){
        this(8);
    }

    public BlockRaceAdder(int blockSize){ this.blockSize = blockSize;}
    public double add(){
        int size = array.length / processors;
        super.exectutor.init();

        for (int ii =0; ii<processors;ii++ ){
            RaceBlockWorkerWithLatch sw =  new RaceBlockWorkerWithLatch(this.array, atomics, 8);
            runnables[ii] = sw;
            sw.setLatch(exectutor.getLatch());
        }
        for (int ii =0; ii<processors;ii++ ){
            exectutor.submit(runnables[ii]);
        }
        double value = 0;

        try {

            exectutor.await();

            for (int ii =0; ii<runnables.length;ii++ ){
                value += runnables[ii].getValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
