///hbimport Player;

import javax.swing.text.html.HTMLDocument.RunElement;

//import Dragon;

public class Square {

    private boolean northWall;
    private boolean eastWall;
    private boolean southWall;
    private boolean westWall;
    private boolean isSecretRoom;
    private boolean isTreasureRoom;
    private int row;
    private int col;
    private int occupant = 0; //0, 1 for player and 2 for dragon

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
        isSecretRoom = true;
    }

    public boolean hasNorthWall(){
        return northWall;
    }
    public boolean hasEastWall(){
        return eastWall;
    }
    public boolean hasSouthWall(){
        return southWall;
    }
    public boolean hasWestWall(){
        return westWall;
    }

    public boolean isOccupied() {
        if (occupant > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void increaseOccupant() {
        if (occupant < 4) {
            occupant++;
        }
    }

    public void decreaseOccupant() {
        if (occupant > 0) {
            occupant--;
        }
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
