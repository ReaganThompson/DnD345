import java.util.*;

//Player and Dragon is a Piece 
public abstract class Piece{

    private int id;
    protected int row;
    protected int col;

    public static int getIDnum(){
        //return idnum
        return 0;
    }

    public int[] getLocation(){
        return location;
    }

    public abstract void move();

    public void setLocation(int row, int col)
    {
        this.row = row;
        this.col = col;

    }

    public static int[] moveTo(int[] newLocation){
        //move piece to new location and return new location.
        return newLocation;
    }


}