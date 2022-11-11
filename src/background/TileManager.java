package background;

import help.ImageHelper;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    BufferedImage tileSheet;

    public TileManager(GamePanel gp, String imagePath) {
        this.gp = gp;
        tileSheet = ImageHelper.getImageSheet(imagePath);
        tile = new Tile[getTileSize()];
        setTileImages();
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        loadMap("res/maps/map01.txt");

    }

    private int getTileSize() {
        int size = 0;
        for (int x = 0; x < tileSheet.getWidth()/8; x++) {
            for (int y = 0; y < tileSheet.getHeight()/8; y++) {
                size++;
            }
        }
        return size;
    }

    public void setTileImages() {
        int col = 0;
        int row = 0;

        int i = 0;
        while (col < tileSheet.getWidth()/8 && row < tileSheet.getHeight()/8) {
            while (col < tileSheet.getWidth()/8) {
                tile[i] = new Tile();
                tile[i].image = tileSheet.getSubimage(col*8, row*8, 8, 8);
                col++;
                i++;
            }
            if (col == tileSheet.getWidth()/8) {
                col = 0;
                row++;
            }
        }
    }

    public void loadMap(String mapPath) {
        try {
            File file = new File(mapPath);
            BufferedReader br = new BufferedReader(new FileReader(file));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int currentMapNum = mapTileNum[col][row];
            g2.drawImage(tile[currentMapNum].image, x, y, gp.TileSize, gp.TileSize, null);
            col++;
            x += gp.TileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.TileSize;
            }
        }
    }

}
