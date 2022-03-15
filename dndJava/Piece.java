package dndJava;
import java.util.*;

public class Piece{

    private int IDnum;
    protected int row;
    protected int col;

    public static int getIDnum(){
        //return idnum
        return 0;
    }

    public int[] getLocation(){
        int[] location = {0,0};
        return location;
    }
    

    public static int[] moveTo(int[] newLocation){
        //move piece to new location and return new location.
        return newLocation;
    }
}