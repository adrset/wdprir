package ovh.ara.Lab2;

import java.util.List;

public interface IBatchURLFetcher {
	public List<String> getContents(String url);
}
