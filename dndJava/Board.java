import java.util.*;
import java.util.Random;

public class Board{

    private Square[][] gameBoard;
    private Player[] players = new Player[2];
    private Dragon dragon;
	private Square treasureRoom = null;
    private Square[] secretRooms = new Square[2];

    //player location
    public int[] playerOneLoc;
    public int[] playerTwoLoc;

    public int[] dragonLoc;

    public Board(){
        gameBoard = new Square[8][8];
        //places walls at random
        PopulateBoard();
        this.dragon = new Dragon();
    }

    public void PopulateBoard(){
        Random rng = new Random();
        //generates a random number between 20 and 30. Max # of walls needs to be 30
        int numberOfWalls = rng.nextInt(10) + 21;
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
                numberOfWalls--;
                //if(numberOfWalls)
            }
        }
    }

    public void selectSecretRoom(int row, int col, int playerNum){
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
                    if(secretRooms[0] != null) System.out.println("Error: setSecretRoom player one already has a secret room");
                    else if(secretRoomSelect == secretRooms[1]) System.out.println("Error: setSecretRoom cannot select this room (0)");
                    else if(secretRoomSelect == treasureRoom) System.out.println("Error: setSecretRoom cannot select this room (1)");
                    else{
                        secretRooms[0] = secretRoomSelect;
                    }
                    break;
                case 2:
                    if(secretRooms[1] != null) System.out.println("Error: setSecretRoom player two already has a secret room");
                    else if(secretRoomSelect == secretRooms[0]) System.out.println("Error: setSecretRoom cannot select this room (0)");
                    else if(secretRoomSelect == treasureRoom) System.out.println("Error: setSecretRoom cannot select this room (1)");
                    else{
                        secretRooms[1] = secretRoomSelect;
                    }
                    break;
                default: System.out.println("Error: setSecretRoom invalid player number");
            }
        } else System.out.println("Error: setSecretRoom attempted to select invalid square");
    }

    public void ensurePathToTreasure(){
        if(treasureRoom == null) System.out.println("Error: ensurePathToTreasure no treasure room selected");
        else if(secretRooms[0] == null && secretRooms[1] == null) System.out.println("Error: ensurePathToTreasure no origin selected");
        else{
            for(Square room : secretRooms){
                if(room == null)
                {
                    break;
                }
                Random rng = new Random();
                char lastLoc = ' ';
                int[] loc = treasureRoom.getLocation();
                int[] dest = room.getLocation();
                System.out.println("began pathfinding at {" + loc[0] + ", " + loc[1] + "}");
                while(!Arrays.equals(loc, dest)){
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
    }
	
	public void selectTreasureRoom(){
		Random rng = new Random();
		boolean validTreasureRoom = false;
		while(validTreasureRoom == false){
			//Selects Sqaure by randomly generating a row and col value
			int row = rng.nextInt(8);
			int col = rng.nextInt(8);
			Square treasureRoomSelect  =  gameBoard[row][col];
			//check if Square is a Secret Room
			if(treasureRoomSelect != secretRooms[0] && treasureRoomSelect != secretRooms[1]){
				treasureRoom = treasureRoomSelect;
                System.out.println("Treasure room selected at {" + row + ", " + col + "}");
				validTreasureRoom = true;
                //sets dragon's location 
                //dragon.row = row;
                //dragon.col = col;
                this.dragonLoc = new int[] {row,col};
                
			}
		}		
	}

    public boolean validMoveCheck(int[] pmSelect, int playerNum)
    {
        int[] playerLoc = new int[2];
        if(playerNum == 1) playerLoc = playerOneLoc;
        else if(playerNum == 2) playerLoc = playerTwoLoc;
        else System.out.println("Error: validMoveCheck invalid player selected");

        int rowDifference = (playerLoc[0]-pmSelect[0]);
        int colDifference = (playerLoc[1]-pmSelect[1]);

        if(Math.abs(rowDifference + colDifference) != 1)
        {
            return false;
        } 
        else 
        {
            char[] moveDir = new char[2];
            //moving up
            if(rowDifference > 0) moveDir = new char[] {'n', 's'};
            //moving down
            else if(rowDifference < 0) moveDir = new char[] {'s', 'n'};
            //moving left
            else if(colDifference > 0) moveDir = new char[] {'w', 'e'};
            //moving right
            else if(colDifference < 0) moveDir = new char[] {'e', 'w'};
            else System.out.println("Error: validMoveCheck no move selected");

            Square currentSquare = gameBoard[playerLoc[0]][playerLoc[1]];
            Square destSquare = gameBoard[pmSelect[0]][pmSelect[1]];

            if(currentSquare.hasWall(moveDir[0]) || destSquare.hasWall(moveDir[1])){
                //indicate that wall was encountered
                return false;
            }
            return true;
        } 
    }

    public void addPlayer(Player p, int activePlayer)
    {
        players[activePlayer - 1] = p;
        //
        p.resetMoves();
    }

    public Player getPlayer(int p){
        return players[p - 1];
    }

    public Square[][] getBoard(){
        return gameBoard;
    }

    public Dragon getDragon()
    {
        return this.dragon;
    }

    //player num is either 1 or 2
    //used by Dragon Move Method in GameControler
    public int[] getPlayerLoc(int playerNum)
    {
        if(playerNum == 1)
        {
            return this.playerOneLoc;
        }
        return this.playerTwoLoc;
    }

    public int[] getDragonLoc()
    {
        return this.dragonLoc;
    }


	
}