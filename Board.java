import java.util.*;
import Piece;

public class Board{

    private Square treasureRoom;
    private Player[] players;
    private Dragon dragon;

    public static void main(String[] args){
        boolean game = true;
        Square[][] gameBoard = new Piece[8][8];
        //populate gameBoard
            //can rooms be completely walled in?
            //how are walls decided?
        //choose treasure room and 1-2 secret rooms.
        while(game){
            //main game loop
        }
    }

    private static int[] getTreasureRoomLocation(){
        //return location
        return treasureRoom;
    }
    private static int[] getPlayerLocation(int playerNum){
        //fetch for player specified and return location
        return playerLocation;
    }
    private static int[] getDragonLocation(){
        //fetch and return location
        return 0;
    }
}