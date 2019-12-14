package ovh.ara.Lab2;

import ovh.ara.Lab1.threads.ILatchable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ImageFetch implements Runnable, ILatchable {
    private String uri;
    private Image img;

    CountDownLatch latch;

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public ImageFetch(String uri){
        this.uri = uri;
    }

    @Override
    public void run(){

        fetchImage();
        System.out.println("countdown");

        latch.countDown();
    }

    public Image getImage(){
        return this.img;
    }


    public void fetchImage(){

        try {
            URL url = new URL(uri);
            img = ImageIO.read( url.openStream());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void filterImage(Image a){
        float[] matrix = {
                1/16f, 1/8f, 1/16f,
                1/8f, 1/4f, 1/8f,
                1/16f, 1/8f, 1/16f,
        };

        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix) );

    }


}
