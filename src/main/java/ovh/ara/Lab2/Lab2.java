package ovh.ara.Lab2;

import ovh.ara.Lab1.threads.IThreadService;
import ovh.ara.Lab1.threads.ThreadPoolService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab2 {

	IThreadService service = new ThreadPoolService();
	IBatchURLFetcher imageService = new SoupImageLinkFetcher();

	public Lab2() {
		HttpFetcher fetcher = HttpFetcher.getInstance();

		List<String> images = imageService
				.getContents("http://www.if.pw.edu.pl/~akraw/gallery/holidays2006.html");
		List<ImageFetch> fetchers = new ArrayList<>();
		for (int ii = 0; ii < images.size(); ii++) {
			fetchers.add(new ImageFetch(images.get(ii)));
		}

		service.init(images.size());
		for (int ii = 0; ii < images.size(); ii++) {
			fetchers.get(ii).setLatch(service.getLatch());
			service.submit(fetchers.get(ii));
		}

		try {
			service.await();
			service.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		new Lab2();

	}

}
