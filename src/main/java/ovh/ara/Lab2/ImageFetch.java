package ovh.ara.Lab2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;

import ovh.ara.Lab1.threads.ILatchable;

public class ImageFetch implements Runnable, ILatchable {
	private String uri;
	private Image img;
	private static final String FOLDER = "img-output";

	CountDownLatch latch;

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public ImageFetch(String uri) {
		this.uri = uri;
	}

	@Override
	public void run() {

		fetchImage();
		System.out.println("countdown");

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

			File f = new File(FOLDER + "/" + split[split.length - 1]);
			f.mkdirs();
			img = filterImage(img);
			ImageIO.write(filterImage(img), "jpg", f);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static BufferedImage filterImage(Image a) {
		float[] matrix = { 1 / 16f, 1 / 8f, 1 / 16f, 1 / 8f, 1 / 4f, 1 / 8f,
				1 / 16f, 1 / 8f, 1 / 16f, };

		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
		BufferedImage blurredImage = op.filter(toBufferedImage(a), null);
		blurredImage = op.filter(blurredImage, null);
		blurredImage = op.filter(blurredImage, null);
		return blurredImage;

	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		return bimage;
	}

}
