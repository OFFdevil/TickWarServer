import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    GameMap gameMap;
    ArrayList<User> users;
    public GameController(int height, int width, int countCastles, ArrayList<User> users){
        gameMap = new GameMap(height, width, countCastles, users);
        this.users = users;
    }

    public GameMap getGameMap(){
        return gameMap;
    }

    public void attack(Pair start, Pair end, boolean is50){
        // TODO: должна быть проверка, что ход делает именно игрок, которй захватил еду клетку
        gameMap.attack(start, end, is50);
    }

    //
    public void getGameMapForUser(User user){
        boolean haveUser = false;
        // TODO: тут может быть проблемаа с users -> users = null
//        for(int i = 0; i < users.size(); i++){
//            if(user == users.get(i)){
//                haveUser = true;
//                break;
//            }
//        }
//        if(!haveUser){
//            throw new RuntimeException("Don't find user: " + user.getId());
//        }

        Gson gson = new Gson();

        try(FileWriter writer = new FileWriter("map1.json")){
            ArrayList<ArrayList<DrawingBlock>> arrayDrawingMap = new ArrayList<ArrayList<DrawingBlock>>();
            ArrayList<ArrayList<Block>> map = gameMap.getGameMap();

            int height = gameMap.getHeight();
            int width = gameMap.getWidth();

            for(int x = 0; x < height; x++){
                arrayDrawingMap.add(new ArrayList<DrawingBlock>());
                for(int y = 0; y < width; y++){
                    boolean haveNearbyUserBlock = false;
                    if(0 <= x - 1 && 0 <= y - 1
                            && (map.get(x - 1).get(y - 1) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x - 1).get(y - 1)).getUser() != null
                            && ((CapturedBlock)map.get(x - 1).get(y - 1)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }
                    if(0 <= x - 1
                            && (map.get(x - 1).get(y) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x - 1).get(y)).getUser() != null
                            && ((CapturedBlock)map.get(x - 1).get(y)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }
                    if(0 <= x - 1 && y + 1 < width
                            && (map.get(x - 1).get(y + 1) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x - 1).get(y + 1)).getUser() != null
                            && ((CapturedBlock)map.get(x - 1).get(y + 1)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }
                    if(0 <= y - 1
                            && (map.get(x).get(y - 1) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x).get(y - 1)).getUser() != null
                            && ((CapturedBlock)map.get(x).get(y - 1)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }

                    if((map.get(x).get(y) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x).get(y)).getUser() != null
                            && ((CapturedBlock)map.get(x).get(y)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }
                    if(y + 1 < width
                            && (map.get(x).get(y + 1) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x).get(y + 1)).getUser() != null
                            && ((CapturedBlock)map.get(x).get(y + 1)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }
                    if(x + 1 < height && 0 <= y - 1
                            && (map.get(x + 1).get(y - 1) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x + 1).get(y - 1)).getUser() != null
                            && ((CapturedBlock)map.get(x + 1).get(y - 1)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }
                    if(x + 1 < height
                            && (map.get(x + 1).get(y) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x + 1).get(y)).getUser() != null
                            && ((CapturedBlock)map.get(x + 1).get(y)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }

                    if(x + 1 < height && y + 1 < width
                            && (map.get(x + 1).get(y + 1) instanceof CapturedBlock)
                            && ((CapturedBlock)map.get(x + 1).get(y + 1)).getUser() != null
                            && ((CapturedBlock)map.get(x + 1).get(y + 1)).getUser().equals(user)){
                        haveNearbyUserBlock = true;
                    }
                    if(haveNearbyUserBlock){
                        if(map.get(x).get(y).getClass().equals(SimpleDrawableBlock.class)){
                            arrayDrawingMap.get(x).add(new DrawingBlock(x, y, true, DrawingBlock.Type.Neutral));
                        }
                        if(map.get(x).get(y).getClass().equals(MountainBlock.class)){
                            arrayDrawingMap.get(x).add(new DrawingBlock(x, y, true, DrawingBlock.Type.Wall));
                        }
                        if(map.get(x).get(y).getClass().equals(FarmBlock.class)){
                            arrayDrawingMap.get(x).add(new DrawingBlock(x, y, true, DrawingBlock.Type.Farm));
                        }
                        if(map.get(x).get(y).getClass().equals(CastleBlock.class)){
                            arrayDrawingMap.get(x).add(new DrawingBlock(x, y, true, DrawingBlock.Type.Castle));
                        }
                    } else {
                        arrayDrawingMap.get(x).add(new DrawingBlock(x, y, false));
                    }
                }
            }

            DrawingMap drawingMap = new DrawingMap(user, height, width, arrayDrawingMap);
            writer.write(gson.toJson(drawingMap));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
