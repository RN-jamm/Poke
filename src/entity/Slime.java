package entity;

import help.ImageHelper;
import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Slime extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage actualImage;

    public Slime(GamePanel gp, KeyHandler keyH, String imagePath) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        spriteSheet = ImageHelper.getImageSheet(imagePath);
    }

    public void setDefaultValues(){
        x = 0;
        y = 0;
        speed = 2;
    }


    public void update() {
        if (y < gp.screenHeight && x + gp.TileSize < gp.screenWidth) {
            x += speed;
        }
        else if(y < gp.screenHeight) {
            x = 2;
            y += gp.TileSize;
        }
        else {
            x = 2;
            y = 2;
        }

        spriteCounter++;
        if (spriteCounter > 8 && spriteCounter < 16) {
            spriteNum = 1;
        }
        else if (spriteCounter > 16 && spriteCounter < 24) {
            spriteNum = 2;
        }
        else if (spriteCounter > 24 && spriteCounter < 32) {
            spriteNum = 3;
        }
        else if (spriteCounter > 32){
            spriteNum = 0;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        actualImage = spriteSheet.getSubimage(spriteNum*gp.originalTileSize, 0, gp.originalTileSize, gp.originalTileSize);
        g2d.drawImage(actualImage, x, y, gp.TileSize, gp.TileSize, null);
    }
}
