package ovh.ara.Lab1.threads;

import java.util.concurrent.CountDownLatch;

public class SimpleThreadService implements IThreadService {
    private CountDownLatch latch;
    private int processors;
    private int lastIndex = 0;
    private Thread threads[];

    public SimpleThreadService(){
        processors = Runtime.getRuntime().availableProcessors();
    }

    public void init() {
        threads = new Thread[processors];
        latch = new CountDownLatch(processors);
        lastIndex = 0;
    }

    public void shutdown(){

    }

    public void submit(Runnable r) {
        threads[lastIndex] = new Thread(r);
        threads[lastIndex++].start();
    }

    public void init(int a) {
        latch = new CountDownLatch(a);
    }


    public void await() throws Exception{
        latch.await();
    }


    public CountDownLatch getLatch(){
        return latch;
    }


}
