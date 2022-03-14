import Player;
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

    public Square(int row, int col) {
        row = this.row;
        col = this.col;
    }

    private Square(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, boolean isSecretRoom, boolean isTreasureRoom){
        this.northWall = northWall;
        this.eastWall = eastWall;
        this.southWall = southWall;
        this.westWall = westWall;
        this.isSecretRoom = isSecretRoom;
        this.isTreasureRoom = isTreasureRoom;
    }

    private static boolean hasNorthWall(){
        return northWall;
    }
    private static boolean hasEastWall(){
        return eastWall;
    }
    private static boolean hasSouthWall(){
        return southWall;
    }
    private static boolean hasWestWall(){
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
}