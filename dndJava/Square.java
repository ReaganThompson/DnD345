package dndJava;
import dndJava.Player;
import dndJava.Dragon;

public class Square {

    private static boolean northWall;
    private static boolean eastWall;
    private static boolean southWall;
    private static boolean westWall;

    private static int row;
    private static int col;

    private static Piece occupant;

    public Square(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, int row, int col){
        this.northWall = northWall;
        this.eastWall = eastWall;
        this.southWall = southWall;
        this.westWall = westWall;
        this.row = row;
        this.col = col;
    }

    public static boolean hasNorthWall(){
        return northWall;
    }
    public static boolean hasEastWall(){
        return eastWall;
    }
    public static boolean hasSouthWall(){
        return southWall;
    }
    public static boolean hasWestWall(){
        return westWall;
    }

    public static int[] getLocation(){
        return new int[] {row,col};
    }

    public static boolean[] setWall(char dir, boolean type){
        switch (dir){
            case 'n':
                northWall = type;
            case 'e':
                eastWall = type;
            case 's':
                southWall = type;
            case 'w':
                westWall = type;
            default:
                System.out.println("attempted to set nonexistent wall");
        }
        return new boolean[] {northWall, eastWall, southWall, westWall};
    }

    public static Piece occupant(){
        //return occupant
        return occupant;
    }

    public static Piece occupant(Player p){
        //update occupant and return
        return occupant;
    }
}