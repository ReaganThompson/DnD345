import com.wwu.graphics.*;
import com.wwu.graphics.BoardGraphicsInf;
import com.wwu.graphics.GameGraphics;
import com.wwu.graphics.GraphicsClickTypes;

import java.util.HashMap;

public class MovementDriver implements BoardGraphicsInf
{
    private class tileStat
    {
        public boolean north = false;
        public boolean south = false;
        public boolean east = false;
        public boolean west = false;
    }



    private HashMap<Integer, tileStat> tileMap = null;
    private  GameGraphics gameGraphics = null;
    private  int currentTile = -1;

    public static void main (String[] arfs)
    {
        MovementDriver movementDriver = new MovementDriver();
    }

    public MovementDriver()
    {
        tileMap = new HashMap<Integer, tileStat>();

        tileStat ts = new tileStat();

        ts.north = true;

        tileMap.put(0, ts );

        gameGraphics = new GameGraphics(this);
    }


    @Override
    public void buttonPressed(GraphicsClickTypes type) {

    }

    @Override
    public void tilePressed(int tileNumber)
    {
        System.out.println(tileNumber);

        tileStat ts = tileMap.get(0);

        if (ts.north == true) {
            gameGraphics.addTextToInfoArea("Cant go that way");
            return;
        }


        if (currentTile != -1)
        {
            gameGraphics.changeTileImage(currentTile, "TILE");
        }

        gameGraphics.changeTileImage(tileNumber, "HERO1");
        currentTile = tileNumber;
    }

    @Override
    public void tilePressed(int column, int row) {

        System.out.println("column: " + column + " row: " + row);

    }
}
