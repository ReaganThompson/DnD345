package dndJava;
import java.util.*;
import java.util.Random;

import dndJava.Piece;
import dndJava.Square;

public class Board{

    private static Square[][] gameBoard;
    private static Player[] players;
    private static Dragon dragon;
	private static Square treasureRoom;
    private static Square secretOne;
    private static Square secretTwo;

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

    public void PopulateBoard(){
        for(int i = 0; i <= gameBoard.length; i++){
            for(int j = 0; j <= gameBoard[0].length; j++){
                boolean n = false;
                boolean e = false;
                boolean s = false;
                boolean w = false;
                if(Math.random() > 0.5){
                    n = true;
                }
                if(Math.random() > 0.5){
                    e = true;
                }
                if(Math.random() > 0.5){
                    s = true;
                }
                if(Math.random() > 0.5){
                    w = true;
                }
                Square sq = new Square(n,e,s,w,i,j);
                gameBoard[i][j] = sq;
            }
        }
        selectTreasureRoom();
    }

    public void selectSecretRoom(int row, int col, int playerNum){
        Square secretRoomSelect = null;

        if(row >= gameBoard.length) System.out.println("Error: setSecretRoom row out of bounds");
        else if(col >= gameBoard[0].length) System.out.println("Error: setSecretRoom column out of bounds");
        else secretRoomSelect = gameBoard[row][col];

        if(secretRoomSelect != null){
            switch(playerNum){
                case 1:
                    if(secretOne != null) System.out.println("Error: setSecretRoom player one already has a secret room");
                    else if(secretRoomSelect == secretTwo) System.out.println("Error: setSecretRoom cannot select this room (0)");
                    else if(secretRoomSelect == treasureRoom) System.out.println("Error: setSecretRoom cannot select this room (1)");
                    else{
                        this.secretOne = secretRoomSelect;
                        ensurePathToTreasure(secretOne);
                    }
                case 2:
                    if(secretTwo != null) System.out.println("Error: setSecretRoom player two already has a secret room");
                    else if(secretRoomSelect == secretOne) System.out.println("Error: setSecretRoom cannot select this room (0)");
                    else if(secretRoomSelect == treasureRoom) System.out.println("Error: setSecretRoom cannot select this room (1)");
                    else{
                        this.secretTwo = secretRoomSelect;
                        ensurePathToTreasure(secretTwo);
                    }
                default: System.out.println("Error: setSecretRoom invalid player number");
            }
        } else System.out.println("Error: setSecretRoom attempted to select invalid square");
    }

    private static void ensurePathToTreasure(Square origin){
        if(treasureRoom == null) System.out.println("Error: ensurePathToTreasure no treasure room selected");
        else if(origin == null) System.out.println("Error: ensurePathToTreasure no origin selected");
        else{
            Random rng = new Random();
            char lastLoc = ' ';
            int[] loc = origin.getLocation();
            int[] dest = treasureRoom.getLocation();
            while(!loc.equals(dest)){
                int axis = rng.nextInt(1);
                if(axis == 0){
                    int destR = dest[0];
                    int locR = loc[0];
                    if(locR >= destR && lastLoc != 'n'){
                        gameBoard[loc[0]][loc[1]].setWall('n', false);
                        loc[0] -= 1;
                        gameBoard[loc[0]][loc[1]].setWall('s', false);
                        lastLoc = 's';
                    }
                    else if(locR <= destR && lastLoc != 's'){
                        gameBoard[loc[0]][loc[1]].setWall('s', false);
                        loc[0] += 1;
                        gameBoard[loc[0]][loc[1]].setWall('n', false);
                        lastLoc = 'n';
                    }
                }
                else if(axis == 1){
                    int destC = dest[1];
                    int locC = loc[1];
                    if(locC >= destC && lastLoc != 'w'){
                        gameBoard[loc[0]][loc[1]].setWall('w', false);
                        loc[1] -= 1;
                        gameBoard[loc[0]][loc[1]].setWall('e', false);
                        lastLoc = 'e';
                    }
                    if(locC <= destC && lastLoc != 'e'){
                        gameBoard[loc[0]][loc[1]].setWall('e', false);
                        loc[1] += 1;
                        gameBoard[loc[0]][loc[1]].setWall('w', false);
                        lastLoc = 'w';
                    }
                }
            }
        }
    }
	
	private void selectTreasureRoom(){
		Random rng = new Random();
		boolean validTreasureRoom = false;
		while(validTreasureRoom == false){
			//Selects Sqaure by randomly generating a row and col value
			int row = rng.nextInt(8);
			int col = rng.nextInt(8);
			Square treasureRoomSelect  =  gameBoard[row][col];
			//check if Square is a Secret Room
			if(treasureRoomSelect != secretOne && treasureRoomSelect != secretTwo){
				this.treasureRoom = treasureRoomSelect;
				validTreasureRoom = true;
                //sets dragon's location 
                dragon.row = row;
                dragon.col = col;
			}
		}		
	}
	
}