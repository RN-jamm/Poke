package background;

import help.ImageHelper;
import main.GamePanel;

import javax.imageio.ImageIO;
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
        mapTileNum = new int[gp.maxMapRow][gp.maxMapCol];
//        loadMap("res/maps/map02.txt");
        try {
            imgToMap("res/sprites/background/cavesofgallet.png");
        } catch (Exception e) {
            //TODO
        }

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

            while (col < gp.maxMapCol && row < gp.maxMapRow) {

                String line = br.readLine();

                while (col < gp.maxMapCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxMapCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imgToMap(String mapImgPath) {
        try {
            BufferedImage mapImg, imgBlock;
            mapImg = ImageIO.read(new File(mapImgPath));

            int col = 0;
            int row = 0;

            while (col < gp.maxMapCol && row < gp.maxMapRow) {
                while (col < gp.maxMapCol) {
                    imgBlock = mapImg.getSubimage(col*8, row*8, 8, 8);
                    for (int i=0; i< tile.length; i++) {
                        if (ImageHelper.compareTwoImages(tile[i].image, imgBlock)) {
                            mapTileNum[row][col] = i;
                            break;
                        }
                    }
                    col++;
                }
                if (col == gp.maxMapCol) {
                    col = 0;
                    row++;
                }
            }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("res/maps/mapTest"));
                String line = "";
                for (int[] num : mapTileNum) {
                    for (int n : num) {
                        line += Integer.toString(n) + " ";
                    }
                    writer.write(line);
                    line = "\n";
                }
                writer.close();
            } catch (IOException e ) {
                System.out.println(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int mapCol = (gp.player.worldX - gp.screenWidth/2) / gp.TileSize;
        int mapRow = (gp.player.worldY - gp.screenHeight/2) / gp.TileSize;

        int Col = -1;
        int Row = -1;

        while (Col <= gp.maxScreenCol +1 && Row <= gp.maxScreenRow +1) {

            int worldX = mapCol * gp.TileSize;
            int worldY = mapRow * gp.TileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            int currentMapNum = mapTileNum[mapRow][mapCol];
            g2.drawImage(tile[currentMapNum].image, screenX, screenY, gp.TileSize, gp.TileSize, null);
            mapCol++;
            Col++;

            if (Col == gp.maxScreenCol +2){
                mapCol = (gp.player.worldX - gp.screenWidth/2) / gp.TileSize;
                Col = -1;
                mapRow++;
                Row++;
            }
        }
    }

}
