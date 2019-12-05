package ovh.ara.threads;

public interface IThreadService {

    public void submit(Runnable r);

    public void await() throws Exception;

    public void init();
}
