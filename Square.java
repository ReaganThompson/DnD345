hbimport Player;

import javax.swing.text.html.HTMLDocument.RunElement;

import Dragon;

public class Square {

    private boolean northWall;
    private boolean eastWall;
    private boolean southWall;
    private boolean westWall;
    private boolean isSecretRoom;
    private boolean isTreasureRoom;

    private int row;
    private int col;

    private Piece occupant;

    public Square(int row, int col) 
    {
        this.row = row;
        this.col = col;
    }

    public void setTreasureRoom()
    {
        isTreasureRoom = true;
    }

    public void setSecretRoom()
    {
        isTreasureRoom = true;
    }

    private boolean hasNorthWall(){
        return northWall;
    }
    private boolean hasEastWall(){
        return eastWall;
    }
    private boolean hasSouthWall(){
        return southWall;
    }
    private boolean hasWestWall(){
        return westWall;
    }

    private static int occupant(){
        //return occupant
        return occupant;
    }

    private static int occupant(Player p){
        //update occupant and return
        return occupant;
    }

    public boolean getIsSecretRoom(){
        return isSecretRoom;
    }
    private boolean getIsTreasureRoom(){
        return isTreasureRoom;
    }

    //0 north, 1 south, 2 east, 3 west
    public void setWall(int direction)
    {
        if(direction == 0)
        {
            this.northWall = true;
        }
        else if(direction == 1)
        {
            this.southWall = true;
        }
        else if(direction == 2)
        {
            this.eastWall = true;
        }
        else if(direction == 3)
        {
            this.westWall = true;
        }
    }

    public boolean getWall(int direction)
    {
        //north
        if(direction == 0)
        {
            return northWall;
        }
        //south
        else if(direction == 1)
        {
            return southWall;
        }
        //east
        else if(direction == 2)
        {
            return eastWall;
        }
        //west
        else if(direction == 3)
        {
            return westWall;
        }
        return false;
    }

    public int getCol()
    {
        return this.col;
    }

    public void clearWalls()
    {
        northWall = false;
        southWall = false;
        eastWall = false;
        westWall = false;
    }

    public int getRow()
    {
        return this.row;
    }
}
