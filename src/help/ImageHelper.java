package help;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHelper {

    public static BufferedImage imageReflectionHorizontal(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage reflectedImage = new BufferedImage(width, height, image.getType());

        int tmp;
        for (int i=0; i < width; i++) {
            for (int j=0; j<height; j++) {
                tmp = image.getRGB(i, j);
                reflectedImage.setRGB(i, j, image.getRGB(width-i-1, j));
                reflectedImage.setRGB(width-i-1, j, tmp);
            }
        }

        return reflectedImage;
    }

    public static BufferedImage imageReflectionPerpendicular(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage reflectedImage = new BufferedImage(width, height, image.getType());

        int tmp;
        for (int i=0; i < width; i++) {
            for (int j=0; j<height; j++) {
                tmp = image.getRGB(i, j);
                reflectedImage.setRGB(i, j, image.getRGB(i, height-j-1));
                reflectedImage.setRGB(i, height-j-1, tmp);
            }
        }

        return reflectedImage;
    }


    public static BufferedImage getImageSheet(String imagePath) {
        try {
            BufferedImage imageSheet;
            imageSheet = ImageIO.read(new File(imagePath));
            return imageSheet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean compareTwoImages(BufferedImage img1, BufferedImage img2)
    {
        if (!checkImgSizes(img1, img2)) {
            return false;
        }

        // Outer loop for rows(height)
        for (int y = 0; y < img1.getHeight(); y++) {
            // Inner loop for columns(width)
            for (int x = 0; x < img1.getWidth(); x++) {
                int rgbA = img1.getRGB(x, y);
                int rgbB = img2.getRGB(x, y);
                int redA = (rgbA >> 16) & 0xff;
                int greenA = (rgbA >> 8) & 0xff;
                int blueA = (rgbA) & 0xff;
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) & 0xff;

                if (redA != redB || greenA != greenB || blueA != blueB) {
                    return false;
                }
            }
        }
        return true;
    }

    // Function which loops through all reflection of an img2 and checks if img1 is one of them
    // if img1 is not one of them fun return -1 which will mean False in this case
    // if img1 is one of them fun returns idx of reflection
    // originalImg = 0; reflectedPerpendicularImg = 1; reflectedHorizontalImg = 2; bothReflectionsImg = 3;
    public static int compareImgToReflections(BufferedImage img1, BufferedImage img2) {

        if (!checkImgSizes(img1, img2)) {
            return -1;
        }

        BufferedImage[] sides = {img2, imageReflectionPerpendicular(img2),
                imageReflectionHorizontal(img2), imageReflectionPerpendicular(imageReflectionHorizontal(img2))};
        BufferedImage tmpImg;
        for (int idx=0; idx < sides.length; idx++) {
            tmpImg = sides[idx];
            if (compareTwoImages(img1, tmpImg)) {return idx;}
        }

        return -1;
    }

    // Function checks if two images are of the same size
    public static boolean checkImgSizes(BufferedImage img1, BufferedImage img2) {
        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();

        // Checking whether the images are of same size or not
        return (width1 == width2) && (height1 == height2);
    }

    public static BufferedImage reflectByType(BufferedImage img, int type) {
        BufferedImage outImg = null;
        switch (type) {
            case 0 -> outImg = img;
            case 1 -> outImg = imageReflectionPerpendicular(img);
            case 2 -> outImg = imageReflectionHorizontal(img);
            case 3 -> outImg = imageReflectionHorizontal(imageReflectionPerpendicular(img));
        }
        return outImg;
    }
}
