package entity;

import help.ImageHelper;
import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Slime extends Entity{

    GamePanel gp;
    BufferedImage actualImage;

    public Slime(GamePanel gp, String imagePath) {
        this.gp = gp;

        setDefaultValues();
        spriteSheet = ImageHelper.getImageSheet(imagePath);
    }

    public void setDefaultValues(){
        worldX = 0;
        worldY = 0;
        speed = 2;
    }


    public void update() {
        if (worldY < gp.screenHeight && worldX + gp.TileSize < gp.screenWidth) {
            worldX += speed;
        }
        else if(worldY < gp.screenHeight) {
            worldX = 2;
            worldY += gp.TileSize;
        }
        else {
            worldX = 2;
            worldY = 2;
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
        g2d.drawImage(actualImage, worldX, worldY, gp.TileSize, gp.TileSize, null);
    }
}
