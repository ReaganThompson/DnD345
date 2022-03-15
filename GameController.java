import com.wwu.graphics.*;
import java.util.Scanner;

public class GameController implements BoardGraphicsInf
{
    private Board gameBoard;
    private GameGraphics gg;
    private boolean twoPlayer;
    
    //Game can be in Setup Mode or Game Play Mode
    private gameSetupState setupState;
    private gamePlayState gpState;

    private Square pendingSecretRoomSelect;
    private Square pendingMoveSelect;



    public static void main(String args[])
    {
        GameController gameController = new GameController();
        GameGraphics gg = new GameGraphics(gameController);
        gameController.gg = gg;

        gg.addTextToInfoArea("Press Start Button to Play");
        gg.addTextToInfoArea("Welcome!");
        //stops the main method from running
        Scanner scan = new Scanner(System.in);
        while(true)
        {
            String input = scan.next();
            scan.close();
        }
    }

    //constructor 
    public GameController()
    {
        this.gameBoard = new Board();
        setupState = gameSetupState.notStarted;
        gpState = gamePlayState.notSetup;
        twoPlayer = false;
    }



    //Defines States During GameSetup 
    public enum gameSetupState
    {
        notStarted,
        playerSetup,
        finalizeSetup,
        complete,
    }

    //Defines States During GamePlay
    public enum gamePlayState
    {
        notSetup,
        player1Move,
        player2Move,
        dragonMove,
        dragonAttach,
        playerAttach,
        gameWon,
    }


    //Facilitates methods that execute during State Change 
    //
    public void updateState()
    {
        switch(this.setupState)
        {
            case notStarted:
                //handle this state and move to the next one
                stateNotStarted();
                break;
            case playerSetup:
                stateP1Setup();
                break;
            case finalizeSetup:
                stateFinalizeSetup();
                break;
            case complete:
                break;
        }
    }

    //Facilitates methods that execute during State Change 
    public void gamePlay()
    {
        switch(gpState)
        {
            case player1Move:
                playerMove(0);
                break;
            case player2Move:
                playerMove(1);
                break;
        }

    }


    public void stateNotStarted()
    {
        setupState = setupState.playerSetup;
        gg.addTextToInfoArea("Press Next To Confirm Choice");
        gg.addTextToInfoArea("Select Your Secret Room");
    }
    
    public void stateP1Setup()
    {
        Square secretRoom = this.pendingSecretRoomSelect;
        this.gameBoard.setSecretRoom(secretRoom, 0);
        this.gg.changeTileImage(secretRoom.getCol(), secretRoom.getRow(), "HERO1");
        this.gameBoard.setPlayerLocation(secretRoom.getRow(),secretRoom.getCol(),0);
        this.pendingSecretRoomSelect = null;
        
        // move to next state
        this.setupState = gameSetupState.finalizeSetup;
        this.gg.addTextToInfoArea("For single player select enter again");
        this.gg.addTextToInfoArea("For 2 Player Select Another Secret Room and press enter");

    }
    // optional second player, sets up tresure room and walls
    public void stateFinalizeSetup()
    {
        // this puts us in a 2 player game
        if(pendingSecretRoomSelect != null)
        {
            Player p2 = new Player();
            gameBoard.setPlayer(p2, 1);
            twoPlayer = true;

            Square secretRoom = pendingSecretRoomSelect;
            gameBoard.setSecretRoom(secretRoom, 1);
            gameBoard.setPlayerLocation(secretRoom.getRow(),secretRoom.getCol(),1);

            gg.changeTileImage(secretRoom.getCol(), secretRoom.getRow(), "HERO2");
        }
        //chooses treasure room and generates walls
        gameBoard.gameBoardSetup();
        
        //randomly chooses treasure room.
        //gameBoard.selectTreasureRoom();
        //gameBoard.generateWalls();

        setupState = gameSetupState.complete;
        gpState = gamePlayState.player1Move;
        gg.addTextToInfoArea("Player1 Make Your Move");

    }

    //GamePlayMethods
    public void playerMove(int playerNum)
    {
        //how to initialize 8 moves?
        //check if chosen move is valid
        //check if wall is in the way 
        //is player on same square
            //if so end turn switch state
        //subtract move and 
        int pendingRow = pendingMoveSelect.getRow();
        int pendingCol = pendingMoveSelect.getCol();

        int currentRow = gameBoard.getPlayer(playerNum).row;
        int currentCol = gameBoard.getPlayer(playerNum).col;

        boolean isMoveValid = gameBoard.validMoveCheck(pendingMoveSelect, playerNum);
        if(isMoveValid)
        {
            //check for wall
            gg.changeTileImage(currentCol, currentRow, "TILE");
            if(playerNum == 0)
            {
                gg.changeTileImage(pendingCol, pendingRow, "HERO1");  
            }
            else if(playerNum == 1)
            {
                gg.changeTileImage(pendingCol, pendingRow, "HERO2");
            }
            
            gameBoard.setPlayerLocation(pendingRow, pendingCol, playerNum); 
            //subtracts move from moveCount
            gameBoard.getPlayer(playerNum).useMove();
        }
        else
        {
            gg.addTextToInfoArea("Invalid Move");
        }

        //change state if player has no more rooms
        if(gameBoard.getPlayer(playerNum).getMoveCount() == 0)
        {
            if(twoPlayer == true)
            {
                if(gpState == gamePlayState.player1Move)
                {
                    gameBoard.getPlayer(1).setMoves();
                    gpState = gamePlayState.player2Move; 
                }
                else if(gpState == gamePlayState.player2Move)
                {
                    gameBoard.getPlayer(0).setMoves();
                    gpState = gamePlayState.player1Move;
                    //gpState = gamePlayState.dragonMove;
                }  
            }
            else
            {
                gpState = gamePlayState.dragonMove;
            }
        }
    }

    public void resetRoutine()
    {
        setupState = gameSetupState.notStarted;
        gpState = gamePlayState.notSetup;
        //clear graphics on screen
    }






    @Override
    public void buttonPressed(GraphicsClickTypes type) 
    {
        if(type == GraphicsClickTypes.START)
        {
            //Start putton will initiated game setup process 
            if(this.setupState == gameSetupState.notStarted)
            {
                updateState();
            }
        }
        if(type == GraphicsClickTypes.RESET)
        {
            resetRoutine();
        }

        if(type == GraphicsClickTypes.NEXT)
        {
            //Player 1 Confirms Secret Room Choice 
            if(setupState == gameSetupState.playerSetup && pendingSecretRoomSelect != null)
            {
                updateState();
            }
            else if(setupState == gameSetupState.finalizeSetup)
            {
                updateState();
            }
        }
        
    }

    @Override
    public void tilePressed(int arg0) 
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void tilePressed(int col, int row) 
    {
        //Player 1 is choosing Secret Room
        if(setupState == gameSetupState.playerSetup || setupState == gameSetupState.finalizeSetup) 
        {
            pendingSecretRoomSelect = gameBoard.getRoom(row, col);
        }

        else if(gpState == gamePlayState.player1Move || gpState == gamePlayState.player2Move)
        {
            pendingMoveSelect = gameBoard.getRoom(row, col);
            gamePlay();
        }
    }
    
}
