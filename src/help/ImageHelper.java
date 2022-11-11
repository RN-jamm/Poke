package help;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHelper {

    public static BufferedImage imageReflection(BufferedImage image) {
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
}
