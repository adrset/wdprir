package ovh.ara.Lab2;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;

import ovh.ara.Lab1.threads.ILatchable;

public class ImageFetch implements Runnable, ILatchable {
	private ImageOperation operation;
	private String uri;
	private Image img;
	private static final String FOLDER = "img-output";

	CountDownLatch latch;

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public ImageFetch(String uri, ImageOperation operation) {
		this.uri = uri;
		this.operation = operation;
	}

	@Override
	public void run() {

		fetchImage();
		//System.out.println("countdown");

		latch.countDown();
	}

	public Image getImage() {
		return this.img;
	}

	public void fetchImage() {

		try {
			URL url = new URL(uri);
			img = ImageIO.read(url.openStream());
			String[] split = url.getFile().split("/");

//			File f = new File(FOLDER + "/" + split[split.length - 1]);
//			f.mkdirs();
////			for (int ii=0;ii<10;ii++){
////				img = operation.processImage(img);
////			}
//			ImageIO.write(operation.processImage(img), "jpg", f);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



}
