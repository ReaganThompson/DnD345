import java.util.*;
import java.util.Random;


//Board class responsibilities:
// -keeps track of the Piece's location
// -keeps track of special rooms
//
//Questions: Will this class facilitate movement of pieces or just used to store information about pieces?

public class Board{

    private Square[][] gameBoard;
	  private Square treasureRoom;
    private Square[] secretRooms;
    private Player[] players;
    private Dragon dragon;

    public final int Up = 0;
    public final int Down = 1;
    public final int Left = 2;
    public final int Right = 3;


    //private Wall[] walls;

    /*
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
    */

    public Board()
    {
        this.gameBoard = new Square[8][8];
        for(int row = 0; row < 8; row++)
        {
            for(int col = 0; col < 8; col++)
            {
                Square s = new Square(row, col);
                this.gameBoard[row][col] = s;
                /*
                Square s = new Square(row,col);
                gameBoard[row][col] = s;
                */
            }
        }
        this.secretRooms = new Square[2];
        this.players = new Player[2];
        players[0] = new Player();
        dragon = new Dragon();

    }

	public void selectTreasureRoom(){
		Random rng = new Random();
		boolean validTreasureRoom = false;
		while(validTreasureRoom == false){
			//Selects Sqaure by randomly generating a row and col value
			int rowVal = rng.nextInt(8);
			int colVal = rng.nextInt(8);
			Square room  =  gameBoard[rowVal][colVal];
			//check if Square is a Secret Room or an occupied room
			if(room.getIsSecretRoom() == false && room.isOccupied() == false){
				this.treasureRoom = room;
        room.setTreasureRoom();
				validTreasureRoom = true;
        //sets dragon's location
        dragon.row = rowVal;
        dragon.col = colVal;
			}
		}
	}

    public Square getTreasureRoom() {
        return treasureRoom;
    }

    public void generateWalls()
    {
        Random rng = new Random();
        //will generate random number between 20 and 30;
        int howManyWalls = rng.nextInt(11)+20;
        for(int i = 0; i < howManyWalls; i++)
        {
            boolean validWall = false;
            while(!validWall)
            {
                int rowVal = rng.nextInt(8);
                int colVal = rng.nextInt(8);
                //0 north, 1 south, 2 east, 3 west
                int direction = rng.nextInt(4);

                boolean invalidNorthWallCheck = (rowVal == 0 && direction == 0);
                boolean invalidSouthWallCheck = (rowVal == 7 && direction == 1);
                boolean invalidEastWallCheck  = (colVal == 7 && direction == 2);
                boolean invalidWestWallCheck  = (colVal == 0 && direction == 3);

                boolean wallAlreadyExists = gameBoard[rowVal][colVal].getWall(direction);

                if(!invalidNorthWallCheck && !invalidSouthWallCheck && !invalidEastWallCheck && !invalidWestWallCheck && !wallAlreadyExists)
                {
                    System.out.println("row: " + rowVal + " col: " + colVal);
                    validWall = true;
                    //set opposing room wall
                    //if north wall set
                    if(direction == 0)
                    {
                        Square opposingRoom = gameBoard[rowVal-1][colVal];
                        //set south wall
                        opposingRoom.setWall(1);
                    }
                    //if south wall is set
                    else if(direction == 1)
                    {
                        Square opposingRoom = gameBoard[rowVal+1][colVal];
                        //set north wall
                        opposingRoom.setWall(0);
                    }
                    //if east wall is set
                    else if(direction == 2)
                    {
                        Square opposingRoom = gameBoard[rowVal][colVal+1];
                        //set west wall
                        opposingRoom.setWall(3);
                    }
                    else if(direction == 3)
                    {
                        Square opposingRoom = gameBoard[rowVal][colVal-1];
                        //set east wall
                        opposingRoom.setWall(2);
                    }
                }
            }

        }

        System.out.print("gothere");
    }

    public boolean openPathExists(Square from, Square to)
    {
        if(from != to)
        {
            //find all movement options.
            //order array based on distances
            Square[] possibleMoves = movementChoices(from,to);
            for(int i = 0; i < 4; i++)
            {
                Square move = possibleMoves[i];
                if(move != null)
                {
                    //when do I backtrack?
                    openPathExists(move, to);
                }
            }
            return false;
        }
        return true;
    }

    public double calculateDistance(Square from, Square to)
    {
        double colDifference = to.getCol() - from.getCol();
        double rowDifference = to.getRow() - from.getRow();

        return colDifference/rowDifference;
    }

    public void getLeftSquareCoordinates(Square current)
    {

    }

    public Square getLeftSquare(Square current)
    {
        if(current.getRow() != 0 && !current.getWall(Left))
        {
            //minus 1 row means moving left
            return gameBoard[current.getRow()-1][current.getCol()];
        }
        return null;
    }


    public Square[] movementChoices(Square from, Square to)
    {
        //is moving up an option?
        Square[] possibleMoves = new Square[4];
        double[] distanceFromGoal = new double[4];

        if(from.getRow() != 0 && !from.getWall(0))
        {
            Square s = gameBoard[from.getRow()-1][from.getCol()];
            possibleMoves[0] = s;
            distanceFromGoal[0] = calculateDistance(s, to);
        }
        if(from.getRow() != 7 && !from.getWall((1)))
        {
            Square s = gameBoard[from.getRow()+1][from.getCol()];
            possibleMoves[1] = s;
            distanceFromGoal[1] = calculateDistance(s, to);
        }
        if(from.getCol() != 7 && !from.getWall((2)))
        {
            Square s = gameBoard[from.getRow()][from.getCol()+1];
            possibleMoves[2] = s;
            distanceFromGoal[2] = calculateDistance(s, to);
        }
        if(from.getCol() != 0 && !from.getWall((3)))
        {
            Square s = gameBoard[from.getRow()][from.getCol()-1];
            possibleMoves[3] = s;
            distanceFromGoal[2] = calculateDistance(s, to);
        }
        //order the moves based on distance
        for(int i = 1; i < 4; i++)
        {
            for(int j = i; j > 0; j--)
            {
                if(distanceFromGoal[j] < distanceFromGoal[j-1] || (distanceFromGoal[j] != 0 && distanceFromGoal[j-1] == 0))
                {
                    //swap
                    double tempD = distanceFromGoal[j];
                    distanceFromGoal[j] = distanceFromGoal[j-1];
                    distanceFromGoal[j-1] = tempD;
                    Square tempS= possibleMoves[j];
                    possibleMoves[j] = possibleMoves[j-1];
                    possibleMoves[j-1] = tempS;

                }
            }

        }
        return possibleMoves;
    }

    public void gameBoardSetup()
    {
        boolean playableBoard = false;
        while(!playableBoard)
        {
            selectTreasureRoom();
            generateWalls();
            while(!openPathExists(secretRooms[0], treasureRoom))
            {
                //clear walls
                for(int row = 0; row < 8; row++)
                {
                    for(int col = 0; col < 8; col++)
                    {
                        gameBoard[row][col].clearWalls();
                    }
                }
                //generate walls
                generateWalls();
            }
        }
    }

    //player secretRoom facilitates and playerNum facilitates multi player modes
    public void setSecretRoom(Square s, int playerNum)
    {
        s.setSecretRoom();
        secretRooms[playerNum] = s;
    }

    public Square getSecretRoom(int playerNum)
    {
        return secretRooms[playerNum];
    }

    public Square getRoom(int row, int col)
    {
        Square s = gameBoard[row][col];
        System.out.println(s.getCol() + " " + s.getRow());
        return gameBoard[row][col];
    }

    public void setPlayerLocation(int row, int col, int playerNum)
    {
        int currentRow = players[playerNum].row;
        int currentCol = players[playerNum].col;
        gameBoard[currentRow][currentCol].decreaseOccupant();
        players[playerNum].setLocation(row, col);
        gameBoard[row][col].increaseOccupant();
    }

    public Player getPlayer(int playerNum)
    {
        return players[playerNum];
    }

    public boolean validMoveCheck(Square pendingRoomSelect, int playerNum)
    {
        int playerRow = players[playerNum].row;
        int playerCol = players[playerNum].col;
        int roomRow = pendingRoomSelect.getRow();
        int roomCol = pendingRoomSelect.getCol();
        int rowDifference = Math.abs(playerRow-roomRow);
        int colDifference = Math.abs(playerCol-roomCol);
        if(rowDifference + colDifference != 1)
        {
            return false;
        }
        return true;
    }

    public void setPlayer(Player p, int playerNum)
    {
        players[playerNum] = p;
    }
