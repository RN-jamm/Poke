package entity;

import help.ImageHelper;
import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage actualImageHead, actualImageTorso;
    String direction = "idle";
    int xSubImgHead, ySubImgHead, xSubImgTorso, ySubImgTorso;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH, String imagePath) {
        this.gp = gp;
        this.keyH = keyH;
        setDeafultValues();
        spriteSheet = ImageHelper.getImageSheet(imagePath);

        screenX = gp.screenWidth/2 - gp.TileSize/2;
        screenY = gp.screenHeight/2 - gp.TileSize/2;
    }

    public void setDeafultValues() {
        worldX = gp.mapWidth/2 - gp.TileSize/2;
        worldY = gp.mapHeight/2 - gp.TileSize/2;
        speed = 6;
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed ||
                keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
                if (worldY > 0) {
                    worldY -= speed;
                } else { worldY = 0; }
            }
            if (keyH.downPressed) {
                direction = "down";
                if (worldY < gp.mapHeight) {
                    worldY += speed;
                } else { worldY = gp.mapHeight; }
            }
            if (keyH.leftPressed) {
                direction = "left";
                if (worldX > 0) {
                    worldX -= speed;
                } else { worldX = 0;}
            }
            if (keyH.rightPressed) {
                direction = "right";
                if (worldX < gp.mapWidth) {
                    worldX += speed;
                } else { worldX = gp.mapWidth; }
            }
        }
        else {
            direction = "idle";
        }

        spriteCounter++;
        if (direction.equals("idle")) {
            if (spriteCounter > 25 && spriteCounter < 50) {
                spriteNum = 1;
            }
            else if (spriteCounter > 50) {
                spriteNum = 0;
                spriteCounter = 0;
            }
        }
        else {
            if (spriteCounter > 10 && spriteCounter < 20) {
                spriteNum = 1;
            } else if (spriteCounter > 20 && spriteCounter < 30) {
                spriteNum = 2;
            } else if (spriteCounter > 30 && spriteCounter < 40) {
                spriteNum = 3;
            } else if (spriteCounter > 40) {
                spriteNum = 0;
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2d) {
        switch (direction) {
            case "up", "down" -> {
                xSubImgHead = 128;
                ySubImgHead = 16;
                xSubImgTorso = (spriteNum % 2) * 16 + 128;
                ySubImgTorso = 64;
                actualImageHead = spriteSheet.getSubimage(xSubImgHead,ySubImgHead, gp.originalTileSize, gp.originalTileSize);
                actualImageTorso = spriteSheet.getSubimage(xSubImgTorso,ySubImgTorso, gp.originalTileSize, gp.originalTileSize);
                g2d.drawImage(actualImageTorso, screenX, screenY, gp.TileSize, gp.TileSize, null);
                g2d.drawImage(actualImageHead, screenX, screenY, gp.TileSize, gp.TileSize, null);
            }
            case "right" -> {
                xSubImgHead = 96;
                ySubImgHead = 16;
                xSubImgTorso = spriteNum * 16;
                ySubImgTorso = 48;
                actualImageHead = spriteSheet.getSubimage(xSubImgHead,ySubImgHead, gp.originalTileSize, gp.originalTileSize);
                actualImageTorso = spriteSheet.getSubimage(xSubImgTorso,ySubImgTorso, gp.originalTileSize, gp.originalTileSize);
                g2d.drawImage(actualImageTorso, screenX, screenY, gp.TileSize, gp.TileSize, null);
                g2d.drawImage(actualImageHead, screenX, screenY, gp.TileSize, gp.TileSize, null);
            }
            case "left" -> {
                xSubImgHead = 96;
                ySubImgHead = 16;
                xSubImgTorso = spriteNum * 16;
                ySubImgTorso = 48;
                actualImageHead = spriteSheet.getSubimage(xSubImgHead,ySubImgHead, gp.originalTileSize, gp.originalTileSize);
                actualImageTorso = spriteSheet.getSubimage(xSubImgTorso,ySubImgTorso, gp.originalTileSize, gp.originalTileSize);
                g2d.drawImage(ImageHelper.imageReflection(actualImageTorso), screenX, screenY, gp.TileSize, gp.TileSize, null);
                g2d.drawImage(ImageHelper.imageReflection(actualImageHead), screenX, screenY, gp.TileSize, gp.TileSize, null);
            }
            case "idle" -> {
                xSubImgHead = 96;
                ySubImgHead = 16;
                xSubImgTorso = (spriteNum % 2) * 16;
                ySubImgTorso = 32;
                actualImageHead = spriteSheet.getSubimage(xSubImgHead,ySubImgHead, gp.originalTileSize, gp.originalTileSize);
                actualImageTorso = spriteSheet.getSubimage(xSubImgTorso,ySubImgTorso, gp.originalTileSize, gp.originalTileSize);
                g2d.drawImage(actualImageTorso, screenX, screenY, gp.TileSize, gp.TileSize, null);
                if (spriteNum == 1) {
                    g2d.drawImage(actualImageHead, screenX, screenY+2, gp.TileSize, gp.TileSize, null);
                }
                else {
                    g2d.drawImage(actualImageHead, screenX, screenY, gp.TileSize, gp.TileSize, null);
                }
            }
        }
    }
}

