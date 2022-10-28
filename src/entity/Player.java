package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage actualImage;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDeafultValues();
        //getPlayerImage();
    }

    public void setDeafultValues() {
        x = gp.screenWidth/2 - gp.TileSize/2;
        y = gp.screenHeight/2 - gp.TileSize/2;
        speed = 4;
    }

    public void getPlayerImage() {
        try {
            spriteSheet = ImageIO.read(new File("src/sprites/characters/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed) {
            y -= speed;
        }
        if (keyH.downPressed) {
            y += speed;
        }
        if (keyH.leftPressed) {
            x -= speed;
        }
        if (keyH.rightPressed) {
            x += speed;
        }
    }

    public void draw(Graphics2D g2d) {
        //actualImage = spriteSheet.getSubimage(0,0, gp.originalTileSize, gp.originalTileSize);
        //g2d.drawImage(actualImage, x, y, gp.TileSize, gp.TileSize, null);
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(x, y, gp.TileSize, gp.TileSize);
    }
}

