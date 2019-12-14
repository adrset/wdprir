package ovh.ara.Lab1.threads;

import java.util.concurrent.CountDownLatch;

public interface IThreadService {

    public void submit(Runnable r);

    public void await() throws Exception;

    public void init();

    public void init(int num);

    public void shutdown();

    public CountDownLatch getLatch();
}
