package dndJava;
import java.util.*;
import java.util.Random;

import dndJava.Piece;
import dndJava.Square;

public class Board{

    private static Square[][] gameBoard;
    private static Player[] players;
    private static Dragon dragon;
	private static Square treasureRoom = null;
    private static Square secretOne = null;
    private static Square secretTwo = null;

    public Board(){
        gameBoard = new Square[8][8];
        PopulateBoard();
    }

    public static void PopulateBoard(){
        for(int i = 0; i < gameBoard.length; i++){
            for(int j = 0; j < gameBoard[0].length; j++){
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
                gameBoard[i][j] = new Square(n,e,s,w,i,j);
            }
        }
        selectTreasureRoom();
    }

    public static void selectSecretRoom(int row, int col, int playerNum){
        Square secretRoomSelect = null;

        if(row >= gameBoard.length) System.out.println("Error: setSecretRoom row out of bounds");
        else if(col >= gameBoard[0].length) System.out.println("Error: setSecretRoom column out of bounds");
        else secretRoomSelect = gameBoard[row][col];
        System.out.println("Secret room selected at {" + secretRoomSelect.row + ", " + secretRoomSelect.col + "}");

        if(secretRoomSelect != null){
            //int[] temp = secretRoomSelect.getLocation();
            //System.out.println("Secret room selected at {" + temp[0] + ", " + temp[1] + "}");
            switch(playerNum){
                case 1:
                    if(secretOne != null) System.out.println("Error: setSecretRoom player one already has a secret room");
                    else if(secretRoomSelect == secretTwo) System.out.println("Error: setSecretRoom cannot select this room (0)");
                    else if(secretRoomSelect == treasureRoom) System.out.println("Error: setSecretRoom cannot select this room (1)");
                    else{
                        secretOne = secretRoomSelect;
                        ensurePathToTreasure(secretOne);
                    }
                    break;
                case 2:
                    if(secretTwo != null) System.out.println("Error: setSecretRoom player two already has a secret room");
                    else if(secretRoomSelect == secretOne) System.out.println("Error: setSecretRoom cannot select this room (0)");
                    else if(secretRoomSelect == treasureRoom) System.out.println("Error: setSecretRoom cannot select this room (1)");
                    else{
                        secretTwo = secretRoomSelect;
                        ensurePathToTreasure(secretTwo);
                    }
                    break;
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
            System.out.println("began pathfinding at {" + loc[0] + ", " + loc[1] + "}");
            int stahp = 0;
            while(!Arrays.equals(loc, dest)){
                stahp++;
                if(stahp > 100) break;
                int axis = rng.nextInt(2);
                System.out.println("loc = {" + loc[0] + ", " + loc[1] + "}\ndest = {" + dest[0] + ", " + dest[1] + "}");
                System.out.println("Selected axis " + axis);
                if(axis == 0){
                    int destR = dest[0];
                    int locR = loc[0];
                    if(locR >= destR && lastLoc != 'n'){
                        if(loc[0] > 0){
                            gameBoard[loc[0]][loc[1]].setWall('n', false);
                            loc[0] -= 1;
                            gameBoard[loc[0]][loc[1]].setWall('s', false);
                            lastLoc = 's';
                            System.out.println("moved north to {" + loc[0] + ", " + loc[1] + "}");
                        }
                        else System.out.println("no move made (attempted out of bounds move north)");
                    }
                    else if(locR <= destR && lastLoc != 's'){
                        if(loc[0] < (gameBoard.length - 1)){
                            gameBoard[loc[0]][loc[1]].setWall('s', false);
                            loc[0] += 1;
                            gameBoard[loc[0]][loc[1]].setWall('n', false);
                            lastLoc = 'n';
                            System.out.println("moved south to {" + loc[0] + ", " + loc[1] + "}");
                        }
                        else System.out.println("no move made (attempted out of bounds move south)");
                    }
                    else System.out.println("no move made");
                }
                else if(axis == 1){
                    int destC = dest[1];
                    int locC = loc[1];
                    if(locC >= destC && lastLoc != 'w'){
                        if(loc[1] > 0){
                            gameBoard[loc[0]][loc[1]].setWall('w', false);
                            loc[1] -= 1;
                            gameBoard[loc[0]][loc[1]].setWall('e', false);
                            lastLoc = 'e';
                            System.out.println("moved west to {" + loc[0] + ", " + loc[1] + "}");
                        }
                        else System.out.println("no move made (attempted out of bounds move west)");
                    }
                    else if(locC <= destC && lastLoc != 'e'){
                        if(loc[1] < (gameBoard[0].length - 1)){
                            gameBoard[loc[0]][loc[1]].setWall('e', false);
                            loc[1] += 1;
                            gameBoard[loc[0]][loc[1]].setWall('w', false);
                            lastLoc = 'w';
                            System.out.println("moved east to {" + loc[0] + ", " + loc[1] + "}");
                        }
                        else System.out.println("no move made (attempted out of bounds move east)");
                    }
                    else System.out.println("no move made");
                }
            }
        }
    }
	
	private static void selectTreasureRoom(){
		Random rng = new Random();
		boolean validTreasureRoom = false;
		while(validTreasureRoom == false){
			//Selects Sqaure by randomly generating a row and col value
			int row = rng.nextInt(8);
			int col = rng.nextInt(8);
			Square treasureRoomSelect  =  gameBoard[row][col];
			//check if Square is a Secret Room
			if(treasureRoomSelect != secretOne && treasureRoomSelect != secretTwo){
				treasureRoom = treasureRoomSelect;
                System.out.println("Treasure room selected at {" + row + ", " + col + "}");
				validTreasureRoom = true;
                //sets dragon's location 
                //dragon.row = row;
                //dragon.col = col;
			}
		}		
	}
	
}