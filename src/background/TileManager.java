package background;

import help.ImageHelper;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TileManager {

    GamePanel gp;
    ArrayList<Tile> tile;
    ArrayList<ArrayList<HashMap<String, String>>> mapTileNum;

    BufferedImage tileSheet;

    public TileManager(GamePanel gp, String imagePath) {
        this.gp = gp;
        tileSheet = ImageHelper.getImageSheet(imagePath);
        tile = new ArrayList<>();
        setTileImages();
        mapTileNum = new ArrayList<>();

//        mapTileNum = new HashMap[gp.maxMapRow][gp.maxMapCol];
//        loadMap("res/maps/map02.txt");
        try {
            imgToMap("res/sprites/background/cavesofgallet.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setTileImages() {
        int col = 0;
        int row = 0;

        int i = 0;
        while (col < tileSheet.getWidth()/8 && row < tileSheet.getHeight()/8) {
            while (col < tileSheet.getWidth()/8) {
                tile.add(new Tile());
                tile.get(i).image = tileSheet.getSubimage(col*8, row*8, 8, 8);
                col++;
                i++;
            }
            if (col == tileSheet.getWidth()/8) {
                col = 0;
                row++;
            }
        }
    }

    public void setTileCollisions() {

        int col = 0;
        int row = 0;

        int i = 0;
        while (col < tileSheet.getWidth()/8 && row < tileSheet.getHeight()/8) {
            while (col < tileSheet.getWidth()/8) {
                tile.get(i).image = tileSheet.getSubimage(col*8, row*8, 8, 8);
                col++;
                i++;
            }
            if (col == tileSheet.getWidth()/8) {
                col = 0;
                row++;
            }
        }
    }

    public void loadMapFromTxt(String mapPath) {
        try {
            File file = new File(mapPath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<HashMap<String, String>> tmpList;

            int col = 0;
            int row = 0;

            while (col < gp.maxMapCol && row < gp.maxMapRow) {

                String line = br.readLine();

                tmpList = new ArrayList<>();
                while (col < gp.maxMapCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    HashMap<String, String> setOfInfo= new HashMap<>();
                    setOfInfo.put("tileNum", String.format("%d", num));
//                    setOfInfo.put("reflectionType", String.format("%d", reflectionType));
//                    setOfInfo.put("collision", String.format("%d", reflectionType));
                    tmpList.add(setOfInfo);
                    col++;
                }
                mapTileNum.add(tmpList);
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
            ArrayList<HashMap<String, String>> tmpList;
            BufferedImage mapImg, imgBlock;
            mapImg = ImageIO.read(new File(mapImgPath));
            int reflectionType;

            int col = 0;
            int row = 0;

            while (col < gp.maxMapCol && row < gp.maxMapRow) {
                tmpList = new ArrayList<>();
                while (col < gp.maxMapCol) {
                    imgBlock = mapImg.getSubimage(col*8, row*8, 8, 8);
                    for (int i=0; i< tile.size(); i++) {

                        // -1 means False in this function
                        // other numbers == type of reflection (check ImageHelper)
                        reflectionType = ImageHelper.compareImgToReflections(tile.get(i).image, imgBlock);

                        if (reflectionType != -1) {
                            HashMap<String, String> setOfInfo= new HashMap<>();
                            setOfInfo.put("tileNum", String.format("%d", i));
                            setOfInfo.put("reflectionType", String.format("%d", reflectionType));
//                            setOfInfo.put("collision", String.format("%d", reflectionType));
                            tmpList.add(setOfInfo);
                            break;
                        }
                    }
                    col++;
                }
                mapTileNum.add(tmpList);
                if (col == gp.maxMapCol) {
                    col = 0;
                    row++;
                }
            }

            // code which makes map in txt format... for now not needed
//            try {
//                BufferedWriter writer = new BufferedWriter(new FileWriter("res/maps/mapTest"));
//                String line = "";
//                for (int[] num : mapTileNum) {
//                    for (int n : num) {
//                        line += Integer.toString(n) + " ";
//                    }
//                    writer.write(line);
//                    line = "\n";
//                }
//                writer.close();
//            } catch (IOException e ) {
//                System.out.println(e);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldX, worldY, screenX, screenY;

        // -1 at the end because we want to create map of size 1 bigger from left and up
        // so it renders better without white squares
        int mapCol = ((gp.player.worldX - gp.screenWidth / 2) / gp.TileSize ) -1;
        int mapRow = ((gp.player.worldY - gp.screenHeight / 2) / gp.TileSize ) -1;


        // here aswell we need bigger map of 1
        int Col = -1;
        int Row = -1;
        int currentMapNum, reflectionType;


        // render map (outer map is basicTile = tile[0]) |
        //                                               V
        // here we need biiger map of 1 for Col and Row and bigger of 1 for mapCol and mapRow
        while (Col < gp.maxScreenCol + 2 && Row < gp.maxScreenRow + 2) {

            worldX = mapCol * gp.TileSize;
            worldY = mapRow * gp.TileSize;
            screenX = worldX - gp.player.worldX + gp.player.screenX;
            screenY = worldY - gp.player.worldY + gp.player.screenY;

            if ((mapCol < 0 || mapCol >= gp.maxMapCol) || (mapRow < 0 || mapRow >= gp.maxMapRow)) {
                currentMapNum = 0;
                reflectionType = 0;
            } else {
                currentMapNum = Integer.parseInt(mapTileNum.get(mapRow).get(mapCol).get("tileNum"));
                reflectionType = Integer.parseInt(mapTileNum.get(mapRow).get(mapCol).get("reflectionType"));
            }
            // reflectByType reflects image to the form that it appears on ImgMap
            // some tiles on ImgMap are reflected
            g2.drawImage(ImageHelper.reflectByType(tile.get(currentMapNum).image, reflectionType), screenX, screenY, gp.TileSize, gp.TileSize, null);
            mapCol++;
            Col++;

            // size same as earlier
            if (Col == gp.maxScreenCol + 2) {
                mapCol = ((gp.player.worldX - gp.screenWidth / 2) / gp.TileSize ) -1;
                Col = -1;
                mapRow++;
                Row++;
            }
        }
    }
}
