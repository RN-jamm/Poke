package entity;

import help.ImageModifier;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage actualImageHead, actualImageTorso;
    String direction = "idle";
    int xSubImgHead, ySubImgHead, xSubImgTorso, ySubImgTorso;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDeafultValues();
        getPlayerImage();
    }

    public void setDeafultValues() {
        x = gp.screenWidth/2 - gp.TileSize/2;
        y = gp.screenHeight/2 - gp.TileSize/2;
        speed = 4;
    }

    public void getPlayerImage() {
        try {
            spriteSheet = ImageIO.read(new File("src/sprites/characters/Dwarf_Miner_Sprite_Sheet_16x16.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed ||
                keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
                y -= speed;
            }
            if (keyH.downPressed) {
                direction = "down";
                y += speed;
            }
            if (keyH.leftPressed) {
                direction = "left";
                x -= speed;
            }
            if (keyH.rightPressed) {
                direction = "right";
                x += speed;
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
                g2d.drawImage(actualImageTorso, x, y, gp.TileSize, gp.TileSize, null);
                g2d.drawImage(actualImageHead, x, y, gp.TileSize, gp.TileSize, null);
            }
            case "right" -> {
                xSubImgHead = 96;
                ySubImgHead = 16;
                xSubImgTorso = spriteNum * 16;
                ySubImgTorso = 48;
                actualImageHead = spriteSheet.getSubimage(xSubImgHead,ySubImgHead, gp.originalTileSize, gp.originalTileSize);
                actualImageTorso = spriteSheet.getSubimage(xSubImgTorso,ySubImgTorso, gp.originalTileSize, gp.originalTileSize);
                g2d.drawImage(actualImageTorso, x, y, gp.TileSize, gp.TileSize, null);
                g2d.drawImage(actualImageHead, x, y, gp.TileSize, gp.TileSize, null);
            }
            case "left" -> {
                xSubImgHead = 96;
                ySubImgHead = 16;
                xSubImgTorso = spriteNum * 16;
                ySubImgTorso = 48;
                actualImageHead = spriteSheet.getSubimage(xSubImgHead,ySubImgHead, gp.originalTileSize, gp.originalTileSize);
                actualImageTorso = spriteSheet.getSubimage(xSubImgTorso,ySubImgTorso, gp.originalTileSize, gp.originalTileSize);
                g2d.drawImage(ImageModifier.imageReflection(actualImageTorso), x, y, gp.TileSize, gp.TileSize, null);
                g2d.drawImage(ImageModifier.imageReflection(actualImageHead), x, y, gp.TileSize, gp.TileSize, null);
            }
            case "idle" -> {
                xSubImgHead = 96;
                ySubImgHead = 16;
                xSubImgTorso = (spriteNum % 2) * 16;
                ySubImgTorso = 32;
                actualImageHead = spriteSheet.getSubimage(xSubImgHead,ySubImgHead, gp.originalTileSize, gp.originalTileSize);
                actualImageTorso = spriteSheet.getSubimage(xSubImgTorso,ySubImgTorso, gp.originalTileSize, gp.originalTileSize);
                g2d.drawImage(actualImageTorso, x, y, gp.TileSize, gp.TileSize, null);
                g2d.drawImage(actualImageHead, x, y, gp.TileSize, gp.TileSize, null);
            }
        }
    }
}

