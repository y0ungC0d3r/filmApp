package org.student.filmApp.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    private static final int THUMBNAIL_WIDTH = 400;
    private static final int THUMBNAIL_HEIGHT = 300;

    static void convert() throws IOException {
        //BufferedImage img = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
        //img.createGraphics().drawImage(ImageIO.read(new File("C:\\Users\\wowow\\Desktop\\test.jpg")).getScaledInstance(400, 300, Image.SCALE_SMOOTH),0,0,null);
        //ImageIO.write(img, "jpg", new File("C:\\Users\\wowow\\Desktop\\test_thumb.jpg"));

        BufferedImage inImage = ImageIO.read(new File("C:\\Users\\wowow\\Desktop\\test.jpg"));
        BufferedImage outImage = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, BufferedImage.TYPE_INT_RGB);;
        int width = inImage.getWidth();
        int height = inImage.getHeight();
        float ratio = (float) width / height;

        int scaledWidth;
        int scaledHeight;
        if(ratio > 1) {
            scaledWidth = Math.round((float) width * ((float) THUMBNAIL_HEIGHT / height));
            scaledHeight = THUMBNAIL_HEIGHT;
            outImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
            outImage.createGraphics().drawImage(inImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH),0,0,null);
            float toCrop = (scaledWidth - THUMBNAIL_WIDTH) / 2;
        } else {
            scaledHeight = Math.round((float) height * ((float) THUMBNAIL_WIDTH / width));
            scaledWidth = THUMBNAIL_WIDTH;
            float toCrop = (scaledHeight - THUMBNAIL_HEIGHT) / 2.f;
            Image scaledImage = inImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            outImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
            outImage = outImage.getSubimage(0, Math.round(toCrop), scaledWidth, outImage.getHeight() - 2 * Math.round(toCrop));
            outImage.createGraphics().drawImage(scaledImage, 0, 0,null);
            //outImage = outImage.getSubimage(0, Math.round(toCrop), scaledWidth, height - 2 * Math.round(toCrop) -2);
            System.out.println(outImage.getHeight() + "  " + outImage.getWidth());
        }

        ImageIO.write(outImage, "jpg", new File("C:\\Users\\wowow\\Desktop\\test_thumb.jpg"));

    }



    public static void main(String[] args) throws IOException {
        convert();
    }
}
