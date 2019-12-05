package ovh.ara.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool implements IThreadService {
    private ExecutorService executor;
    private CountDownLatch latch;
    private int processors;

    public ThreadPool(){
        processors = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(processors);
    }

    public void init() {
        latch = new CountDownLatch(processors);
    }

    public void submit(Runnable r) {
        executor.submit(r);
    }

    public void await() throws Exception{
        latch.await();
    }
}
