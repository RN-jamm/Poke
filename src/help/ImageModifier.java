package help;

import java.awt.image.BufferedImage;

public class ImageModifier {

    public static BufferedImage imageReflection(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double hfact = 0.5;

        BufferedImage result = image;
        return new BufferedImage(width, new Double(height
                * (1 + hfact)).intValue(), BufferedImage.TYPE_INT_ARGB);
    }
}
