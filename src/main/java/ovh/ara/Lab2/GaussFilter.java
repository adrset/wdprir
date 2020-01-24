package ovh.ara.Lab2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class GaussFilter implements ImageOperation {


    static final float[] matrix = {
            1 / 16f, 1 / 8f, 1 / 16f,
            1 / 8f, 1 / 4f, 1 / 8f,
            1 / 16f, 1 / 8f, 1 / 16f };

    public BufferedImage processImage(Image a) {


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
