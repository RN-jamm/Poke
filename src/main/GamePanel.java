package main;

import background.TileManager;
import entity.Player;
import entity.Slime;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    public final int originalTileSize = 16;
    public final int scale = 3;

    public final int TileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = TileSize * maxScreenCol;
    public final int screenHeight = TileSize * maxScreenRow;

    public int FPS = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this, keyH, "res/sprites/characters/Dwarf_Miner_Sprite_Sheet_16x16.png");
    Slime slime = new Slime(this, keyH, "res/sprites/characters/slime.png");
    TileManager map01 = new TileManager(this, "res/sprites/background/cavesofgallet_tiles.png");

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        //this.setBackground(Color.PINK);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        slime.update();
        player.update();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        map01.draw(g2d);
        slime.draw(g2d);
        player.draw(g2d);
        g2d.dispose();
    }
}
