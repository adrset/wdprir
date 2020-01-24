package ovh.ara.Lab1.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolService implements IThreadService {
    private ExecutorService executor;
    private CountDownLatch latch;
    private int processors;

    public ThreadPoolService(){
        this ( Runtime.getRuntime().availableProcessors());
    }

    public ThreadPoolService(int processors){
        executor = Executors.newFixedThreadPool(processors);
        System.out.println("Creating thread pool of size "+ processors);
    }

    public void init() {
        latch = new CountDownLatch(processors);
    }
    public void init(int a) {
        latch = new CountDownLatch(a);
    }

    public void shutdown(){
        executor.shutdown();
    }
    public void submit(Runnable r) {
        executor.submit(r);
    }

    public void await() throws Exception{
        latch.await();
       // System.out.println("count 0");
    }


    public CountDownLatch getLatch(){
        return latch;
    }


}
