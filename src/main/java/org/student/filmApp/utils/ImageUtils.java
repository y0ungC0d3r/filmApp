package org.student.filmApp.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ImageUtils {

    private static final int THUMBNAIL_WIDTH = 400;
    private static final int THUMBNAIL_HEIGHT = 300;

    private static final String JPG_FILE_EXTENSION = "jpg";

    private static final String FILMS_IMAGES_BASE_FOLDER = "";

    /*public static List<String> getAllFilmImagePaths(Long filmId) {
        new File(FILMS_IMAGES_BASE_FOLDER);
    }*/

    public static void createThumbnails(String directory) throws IOException {
        File dir = new File(directory);
        if(!dir.isDirectory()) {
            throw new IllegalArgumentException();
        }
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if(JPG_FILE_EXTENSION.equals(getFileExtension(files[i]).toLowerCase())) {
                renameFile(files[i], String.valueOf(i + 1), JPG_FILE_EXTENSION);
                //createThumbnail(files[i].getParent(), String.valueOf(i + 1), "t");
                System.out.println(files[i].getPath());
            }
        }
    }

    public static void createThumbnail(String dir, String imageName, String thumbnailNameAppendix) throws IOException {

        BufferedImage inImage = ImageIO.read(new File(dir + "\\" + imageName + "." + JPG_FILE_EXTENSION));
        BufferedImage outImage = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, BufferedImage.TYPE_INT_RGB);

        int width = inImage.getWidth();
        int height = inImage.getHeight();
        if(height < THUMBNAIL_HEIGHT || width < THUMBNAIL_WIDTH) {
            throw new IllegalArgumentException();
        }
        float ratio = (float) width / height;

        if(ratio > 1) {
            int scaledWidth = Math.round((float) width * ((float) THUMBNAIL_HEIGHT / height));
            int scaledHeight = THUMBNAIL_HEIGHT;
            int widthDiff = scaledWidth - THUMBNAIL_WIDTH;
            int crop = widthDiff / 2;
            BufferedImage image = scaleImage(inImage, scaledWidth, scaledHeight)
                    .getSubimage(crop, 0, scaledWidth - 2 * (widthDiff % 2 == 0 ? crop : crop + 1), scaledHeight);
            outImage.createGraphics().drawImage(image, 0, 0,null);
        } else {
            int scaledHeight = Math.round((float) height * ((float) THUMBNAIL_WIDTH / width));
            int scaledWidth = THUMBNAIL_WIDTH;
            int heightDiff = scaledHeight - THUMBNAIL_HEIGHT;
            int crop = heightDiff / 2;
            BufferedImage image = scaleImage(inImage, scaledWidth, scaledHeight)
                    .getSubimage(0, crop, scaledWidth, scaledHeight - 2 * (heightDiff % 2 == 0 ? crop : crop + 1));
            outImage.createGraphics().drawImage(image, 0, 0,null);
        }

        ImageIO.write(outImage, JPG_FILE_EXTENSION, new File(dir + "\\" + imageName + thumbnailNameAppendix + "." + JPG_FILE_EXTENSION));

    }

    private static BufferedImage scaleImage(BufferedImage inImage, int scaledWidth, int scaledHeight) {
        Image scaledImage = inImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        BufferedImage outImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = outImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();
        return outImage;
    }


    private static String getFileExtension(File file) {
        return Optional.of(file.getName())
                .filter(n -> n.lastIndexOf(".") != 0)
                .filter(n -> n.lastIndexOf(".") != -1)
                .map(n -> n.substring(n.lastIndexOf(".") + 1))
                .orElse("");
    }

    private static boolean renameFile(File file, String newFileName, String fileExtension) {
        return file.renameTo(new File(file.getParent() + "\\" + newFileName + "." + fileExtension));
    }

    public static void main(String[] args) throws IOException, IllegalAccessException {
        //createThumbnail("C:\\Users\\Comarch\\Desktop\\Brimestone.jpg");
        createThumbnails("C:\\Users\\Comarch\\Desktop\\Nowy folder (5)");
        getFileExtension(new File("C:\\Users\\Comarch\\Desktop\\Brimestone.jpg"));
    }
}
