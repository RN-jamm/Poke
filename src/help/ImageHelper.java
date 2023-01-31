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
        boolean outcomeFlag = true;
        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();

        // Checking whether the images are of same size or not
        if ((width1 != width2) || (height1 != height2)) {
            return false;
        }

        BufferedImage[] sides = {img2, imageReflectionHorizontal(img2),
                imageReflectionPerpendicular(img2), imageReflectionPerpendicular(imageReflectionHorizontal(img2))};

        // Outer loop for rows(height)
        for (BufferedImage tmpImg: sides) {
            for (int y = 0; y < height1; y++) {
                // Inner loop for columns(width)
                for (int x = 0; x < width1; x++) {
                    int rgbA = img1.getRGB(x, y);
                    int rgbB = tmpImg.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;

                    if (redA != redB || greenA != greenB || blueA != blueB) {
                        outcomeFlag = false;
                    }
                }
            }
            if (outcomeFlag) {
                return true;
            }
            outcomeFlag = true;
        }
        return false;
    }
}
