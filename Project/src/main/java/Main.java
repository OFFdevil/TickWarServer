import com.google.gson.Gson;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) throws DataFormatException, InterruptedException, IOException {
//        MapCreater creater = new MapCreater();
//        int width = 15;
//        int height = 15;
//        int castles = 4;
//
        ArrayList<User> users = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            users.add(new User(i, User.Color.BLACK));
        }
        GameController newGameController = new GameController(6, 6,  2, users);
        newGameController.getGameMapForUser(new User(1, User.Color.BLACK));


        GameMap gameMap = newGameController.getGameMap();
        ArrayList<ArrayList<Block>> array = gameMap.getGameMap();
        int countWalls = 0;
        int countFarm  = 0;
        int countCastle= 0;
        for(int x = 0; x < gameMap.getHeight(); x++) {
            for (int y = 0; y < gameMap.getWidth(); y++) {
                Class<?> type = array.get(x).get(y).getClass();
                if (type.equals(SimpleDrawableBlock.class)) {
                    System.out.print("0 ");
                    continue;
                }
                if(type.equals(MountainBlock.class)){
                    System.out.printf("1 ");
                    countWalls++;
                    continue;
                }
                if(type.equals(FarmBlock.class)){
                    System.out.printf("2 ");
                    countFarm++;
                    continue;
                }
                if(type.equals(CastleBlock.class)){
                    System.out.printf("3 ");
                    countCastle++;
                    continue;
                }
            }
            System.out.print("\n");
        }

        System.out.println("Count Walls: "  + countWalls);
        System.out.println("Count Farm: "   + countFarm);
        System.out.println("Count Castle: " + countCastle + "\n");

        testGson();
    }

    static public void testGson(){
        Gson gson = new Gson();

//        // прикольно, он null элемент вообще не записывает
//        DrawingBlock drawingBlock = new DrawingBlock(1, 1, true);
//        String json = gson.toJson(drawingBlock);
//
//        System.out.println(json);
//
//        DrawingBlock drawingBlock1 = gson.fromJson(json, DrawingBlock.class);
//        System.out.println(drawingBlock1.getX() + " " + drawingBlock1.getY() + " " + drawingBlock1.isDraw() + " " + drawingBlock1.getType());

//        try(FileWriter writer = new FileWriter("map.json")){
//            int width = 5;
//            int height = 5;
//            ArrayList<ArrayList<DrawingBlock>> map = new ArrayList<>();
//            for(int x = 0; x < width; x++){
//                map.add(new ArrayList<DrawingBlock>());
//                for(int y = 0; y < height; y++){
//                    map.get(x).add(new DrawingBlock(x, y, false));
//                }
//            }
//
//            User user = new User(1000, User.Color.BLACK);
//            DrawingMap drawingMap = new DrawingMap(user, height, width, map);
//            writer.write(gson.toJson(drawingMap));
//            writer.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try(FileReader reader = new FileReader("map1.json")){
            DrawingMap map = gson.fromJson(reader, DrawingMap.class);
//            System.out.println(gson.toJson(map));
            ArrayList<ArrayList<DrawingBlock>> drawingMap = map.getMap();

            System.out.println(gson.toJson(map.getUser()));
            System.out.println(gson.toJson(map.getHeight()));
            System.out.println(gson.toJson(map.getWidth()));

            for(int x = 0; x < map.getHeight(); x++){
                for(int y = 0; y < map.getWidth(); y++){
                    System.out.print(gson.toJson(drawingMap.get(x).get(y)) + " ");
                }
                System.out.print("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

