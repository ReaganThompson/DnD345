import java.util.*;

//Player and Dragon is a Piece
public abstract class Piece{

    protected int row;
    protected int col;

    public int[] getLocation(){
        int[] location = new int[2];
        location[0] = row;
        location[1] = col;
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
