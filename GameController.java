import com.wwu.graphics.*;
import java.util.Scanner;

public class GameController implements BoardGraphicsInf
{
    private Board gameBoard;
    private GameGraphics gg;
    private boolean twoPlayer;
    private boolean square2player;

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
        gg.addTextToInfoArea("---");
        gg.addTextToInfoArea("Press Start to play!");
        gg.addTextToInfoArea("Welcome!");
        //stops the main method from running
        Scanner scan = new Scanner(System.in);
        while(true)
        {
            String input = scan.next();
            System.out.println(input);
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
        complete;
    }

    //Defines States During GamePlay
    public enum gamePlayState
    {
        notSetup,
        player1Move,
        player2Move,
        dragonMove,
        dragonAttack,
        playerAttack,
        gameWon;
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
            case playerAttack:
                playerCombat();
                break;
        }
    }

    public void stateNotStarted()
    {
        setupState = setupState.playerSetup;
        gg.addTextToInfoArea("---");
        gg.addTextToInfoArea("Confirm w/t Next");
        gg.addTextToInfoArea("Choose secret room!");
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
        this.gg.addTextToInfoArea("---");
        this.gg.addTextToInfoArea("Secret room!");
        this.gg.addTextToInfoArea("Choose 1 more,");
        this.gg.addTextToInfoArea("For 2 Player,");
        this.gg.addTextToInfoArea("Enter for 1 player!");
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
        //set up treasure room
        gameBoard.selectTreasureRoom();
        Square room = gameBoard.getTreasureRoom();
        this.gg.changeTileImage(room.getCol(), room.getRow(), "ANTAGONIST");
        //generate walls
        //setupWalls();

        setupState = gameSetupState.complete;
        gpState = gamePlayState.player1Move;
        this.gg.addTextToInfoArea("---");
        this.gg.addTextToInfoArea("1 tile at a time!");
        this.gg.addTextToInfoArea("Player 1, move!");
    }

    private void setupWalls() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square room = gameBoard.getRoom(i, j);
                if (room.hasEastWall()) {
                    gg.toggleWallGraphic(room.getCol(), room.getRow(), GraphicsWallDirections.EAST);
                }
                if (room.hasSouthWall()) {
                    gg.toggleWallGraphic(room.getCol(), room.getRow(), GraphicsWallDirections.SOUTH);
                }
                if (room.hasNorthWall()) {
                    gg.toggleWallGraphic(room.getCol(), room.getRow(), GraphicsWallDirections.NORTH);
                }
                if (room.hasWestWall()) {
                    gg.toggleWallGraphic(room.getCol(), room.getRow(), GraphicsWallDirections.WEST);
                }

            }
        }
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
        int moveCount = gameBoard.getPlayer(playerNum).getMoveCount();
        if (moveCount > 0) {
            if(isMoveValid)
            {
                //check for wall
                if (!square2player) {
                    gg.changeTileImage(currentCol, currentRow, "TILE");
                } else {
                    if (gpState == gamePlayState.player1Move) {
                        gg.changeTileImage(currentCol, currentRow, "HERO2");
                    } else {
                        gg.changeTileImage(currentCol, currentRow, "HERO1");
                    }
                    square2player = false;
                }
                if(playerNum == 0)
                {
                    gg.changeTileImage(pendingCol, pendingRow, "HERO1");
                }
                else if(playerNum == 1)
                {
                    gg.changeTileImage(pendingCol, pendingRow, "HERO2");
                }
                boolean occupied = gameBoard.getRoom(pendingRow, pendingCol).isOccupied();
                gameBoard.setPlayerLocation(pendingRow, pendingCol, playerNum);
                //subtracts move from moveCount
                gameBoard.getPlayer(playerNum).useMove();
                if (twoPlayer && occupied) {
                    square2player = true;
                    int currentPlayer = -1;
                    if (gpState == gamePlayState.player1Move) {
                        currentPlayer = 0;
                    } else if (gpState == gamePlayState.player2Move) {
                        currentPlayer = 1;
                    }
                    if (gameBoard.getPlayer(1 - currentPlayer).hasTreasure()) {
                        gpState = gamePlayState.playerAttack;
                    } else {
                        if (gpState == gamePlayState.player1Move) {
                            gpState = gamePlayState.player2Move;
                            gameBoard.getPlayer(1).setMoves();
                            gg.addTextToInfoArea("---");
                            gg.addTextToInfoArea("1 tile at a time!");
                            gg.addTextToInfoArea("Player 2, move!");
                        } else if (gpState == gamePlayState.player2Move) {
                            gpState = gamePlayState.player1Move;
                            gameBoard.getPlayer(0).setMoves();
                            gg.addTextToInfoArea("---");
                            gg.addTextToInfoArea("1 tile at a time!");
                            gg.addTextToInfoArea("Player 1, move!");
                        }
                    }
                }
            }
            else
            {
                gg.addTextToInfoArea("---");
                gg.addTextToInfoArea("1 tile at a time!");
            }
        } else if (moveCount == 0)
        {
            gg.addTextToInfoArea("---");
            gg.addTextToInfoArea("No moves left!");
        }
    }

    public void playerCombat() {
        System.out.println("attack!");
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
        if (type == GraphicsClickTypes.START)
        {
            //Start button will initiated game setup process
            if(this.setupState == gameSetupState.notStarted)
            {
                updateState();
            }
        }
        else if (type == GraphicsClickTypes.RESET)
        {
            resetRoutine();
        }
        else if (type == GraphicsClickTypes.NEXT)
        {
            //Player 1 Confirms Secret Room Choice
            if(setupState == gameSetupState.playerSetup
                    && pendingSecretRoomSelect != null)
            {
                updateState();
            }
            else if(setupState == gameSetupState.finalizeSetup)
            {
                updateState();
            }
            else if (setupState == gameSetupState.complete) {
                if (gpState == gamePlayState.player1Move) {
                    if (twoPlayer) {
                        gpState = gamePlayState.player2Move;
                        this.gameBoard.getPlayer(1).setMoves();
                        this.gg.addTextToInfoArea("---");
                        this.gg.addTextToInfoArea("1 tile at a time!");
                        this.gg.addTextToInfoArea("Player 2, move!");
                    }
                } else if (gpState == gamePlayState.player2Move){
                    gpState = gamePlayState.player1Move;
                    this.gameBoard.getPlayer(0).setMoves();
                    this.gg.addTextToInfoArea("---");
                    this.gg.addTextToInfoArea("1 tile at a time!");
                    this.gg.addTextToInfoArea("Player 1, move!");
                }
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
        if (setupState == gameSetupState.playerSetup
                || setupState == gameSetupState.finalizeSetup)
        {
            pendingSecretRoomSelect = gameBoard.getRoom(row, col);
        }
        else if (gpState == gamePlayState.player1Move
                || gpState == gamePlayState.player2Move)
        {
            pendingMoveSelect = gameBoard.getRoom(row, col);
            gamePlay();
        }
    }

}
