package ovh.ara.Lab2;

import ovh.ara.Lab1.threads.IThreadService;
import ovh.ara.Lab1.threads.ThreadPoolService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab2 {

	IThreadService service = new ThreadPoolService(512);
	IBatchURLFetcher imageService = new SoupImageLinkFetcher();

	public Lab2() {

		List<String> images = imageService
				.getContents("http://if.pw.edu.pl/~wierzba/zdjecia/erice/index.html");
		List<ImageFetch> fetchers = new ArrayList<>();
		for (int ii = 0; ii < images.size(); ii++) {
			fetchers.add(new ImageFetch(images.get(ii), new GaussFilter()));
		}

		float averages[] = new float[10];
		for (int i=0;i<10;i++){

			service.init(images.size());
			for (int ii = 0; ii < images.size(); ii++) {
				fetchers.get(ii).setLatch(service.getLatch());
				service.submit(fetchers.get(ii));
			}
			double startTime = System.nanoTime();

			try {
				service.await();

				double timeElapsed = System.nanoTime() - startTime;
				System.out.println( String.format("%.02f", timeElapsed * Math.pow(10,-9)) + " s");


				averages[i] = (float) (timeElapsed * Math.pow(10,-9));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Average" + String.format("%.02f", getAverage(averages)) + " s");
		service.shutdown();
	}

	public float getAverage(float tab[]){
		float av = 0;
		for (float f: tab){
			av+=f;
		}
		return av / ((float) tab.length);
	}

	public static void main(String[] args) throws Exception {
		new Lab2();

	}

}
