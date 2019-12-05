package ovh.ara.threads;

import java.util.concurrent.CountDownLatch;

public interface IThreadService {

    public void submit(Runnable r);

    public void await() throws Exception;

    public void init();

    public CountDownLatch getLatch();
}
