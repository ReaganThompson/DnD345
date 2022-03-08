import java.util.*;
import java.util.Random;
import Piece;

public class Board{

    private Square[][] gameBoard;
	private Square treasureRoom;
    private Player[] players;
    private Dragon dragon;

    public static void main(String[] args){
        boolean game = true;
        Square[][] gameBoard = new Square[8][8];
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
	
	public void selectTreasureRoom(){
		Random rng = new Random();
		boolean validTreasureRoom = false;
		while(validTreasureRoom == false){
			//Selects Sqaure by randomly generating a row and col value
			int rowVal = rng.nextInt(8);
			int colVal = rng.nextInt(8);
			Square TreasureRoomSelect  =  gameBoard[rowVal][colVal];
			//check if Square is a Secret Room
			if(TreasureRoomSelect.getIsSecretRoom() == false){
				this.treasureRoom = TreasureRoomSelect;
				validTreasureRoom = true;
                //sets dragon's location 
                dragon.row = rowVal;
                dragon.col = colVal;
			}
		}		
	}
	
}