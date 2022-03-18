import com.wwu.graphics.*;

import java.util.Arrays;
import java.util.Scanner;

public class GameController implements BoardGraphicsInf
{
    private Board gb;
    private GameGraphics gg;

    private int numPlayers = 1;
    
    //Game can be in Setup Mode or Game Play Mode
    public gameSetupState setupState;
    public gamePlayState playState;

    private int[] psrSelect;
    private int[] pmSelect;
    private int activePlayer;

    public static void main(String args[])
    {
        GameController gc = new GameController();
        gc.gg.addTextToInfoArea("Press Start Button to Play");
        gc.gg.addTextToInfoArea("Welcome!");
        
        //setup loop (this wont work properly, we need to make sure to add triggers to call exec methods at the correct times)
        while(gc.setupState != gameSetupState.complete){
            gc.execSetup();
        }
        gc.playState = gamePlayState.playerMove;

        //gameplay loop (same here)
        while(gc.playState != gamePlayState.end){
            gc.execPlay();
        }
    }

    //CONSTRUCTOR
    public GameController()
    {
        gg = new GameGraphics(this);
        gb = new Board();
        setupState = gameSetupState.start;
    }

    //SETUP STATES
    public enum gameSetupState
    {
        start,
        firstPlayerSetup,
        secondPlayerSetup,
        complete
    }

    //GAMEPLAY STATES
    public enum gamePlayState
    {
        playerMove,
        dragonMove,
        playerAttack,
        dragonAttack,
        end
    }

    //STATE MACHINE FOR SETUP
    public void execSetup(){
        switch(setupState){
            case start:
                gg.addTextToInfoArea("Press Next To Confirm Choice");
                gg.addTextToInfoArea("Select Your Secret Room");
                setupState = gameSetupState.firstPlayerSetup;
                break;
            case firstPlayerSetup:
                //puts player one into selected secret room

                gb.selectSecretRoom(psrSelect[0], psrSelect[1], 1);
                gg.changeTileImage(psrSelect[0], psrSelect[1], "HERO1");
                gb.playerOneLoc = new int[] {psrSelect[0], psrSelect[1]};
                gb.addPlayer(new Player());
                psrSelect = new int[] {};
                setupState = gameSetupState.secondPlayerSetup;
                gg.addTextToInfoArea("For single player select enter again");
                gg.addTextToInfoArea("For 2 Player Select Another Secret Room and press enter");
                break;
            case secondPlayerSetup:
                //puts player two into selected secret room
                //finishes setup if no player two room is selected

                if(psrSelect != null)
                {
                    gb.addPlayer(new Player());
                    gb.selectSecretRoom(psrSelect[0], psrSelect[1], 2);
                    gb.playerOneLoc = new int[] {psrSelect[0], psrSelect[1]};
                    gg.changeTileImage(psrSelect[0], psrSelect[1], "HERO2");
                }
                gb.selectTreasureRoom();
                gb.ensurePathToTreasure();

                setupState = gameSetupState.complete;
                playState = gamePlayState.playerMove;
                gg.addTextToInfoArea("Player 1 turn");
                break;
            case complete:
                break;
            default:
        }
    }

    public void execPlay(){
        switch(playState){
            case playerMove:
                break;
            case dragonMove:
                break;
            case playerAttack:
                break;
            case dragonAttack:
                break;
            default:
        }
    }

//DANIEL DEPRECTIATED
//=============================================================================================================

    // //Facilitates methods that execute during State Change 
    // //
    // public void updateState()
    // {
    //     switch(this.setupState)
    //     {
    //         case notStarted:
    //             //handle this state and move to the next one
    //             stateNotStarted();
    //             break;
    //         case playerSetup:
    //             stateP1Setup();
    //             break;
    //         case finalizeSetup:
    //             stateFinalizeSetup();
    //             break;
    //         case complete:
    //             break;
    //     }
    // }

    // //Facilitates methods that execute during State Change 
    // public void gamePlay()
    // {
    //     switch(playState)
    //     {
    //         case player1Move:
    //             playerMove(0);
    //             break;
    //         case player2Move:
    //             playerMove(1);
    //             break;
    //     }

    // }


    // public void stateNotStarted()
    // {
    //     setupState = setupState.playerSetup;
    //     gg.addTextToInfoArea("Press Next To Confirm Choice");
    //     gg.addTextToInfoArea("Select Your Secret Room");
    // }
    
    // public void stateP1Setup()
    // {
    //     Square secretRoom = this.pendingSecretRoomSelect;
    //     this.gb.setSecretRoom(secretRoom, 0);
    //     this.gg.changeTileImage(secretRoom.getCol(), secretRoom.getRow(), "HERO1");
    //     this.gb.setPlayerLocation(secretRoom.getRow(),secretRoom.getCol(),0);
    //     this.pendingSecretRoomSelect = null;
        
    //     // move to next state
    //     this.setupState = gameSetupState.finalizeSetup;
    //     this.gg.addTextToInfoArea("For single player select enter again");
    //     this.gg.addTextToInfoArea("For 2 Player Select Another Secret Room and press enter");

    // }
    // // optional second player, sets up tresure room and walls
    // public void stateFinalizeSetup()
    // {
    //     // this puts us in a 2 player game
    //     if(pendingSecretRoomSelect != null)
    //     {
    //         Player p2 = new Player();
    //         gb.setPlayer(p2, 1);
    //         twoPlayer = true;

    //         Square secretRoom = pendingSecretRoomSelect;
    //         gb.setSecretRoom(secretRoom, 1);
    //         gb.setPlayerLocation(secretRoom.getRow(),secretRoom.getCol(),1);

    //         gg.changeTileImage(secretRoom.getCol(), secretRoom.getRow(), "HERO2");
    //     }
    //     //chooses treasure room and generates walls
    //     gb.gbSetup();
        
    //     //randomly chooses treasure room.
    //     //gb.selectTreasureRoom();
    //     //gb.generateWalls();

    //     setupState = gameSetupState.complete;
    //     playState = gamePlayState.player1Move;
    //     gg.addTextToInfoArea("Player1 Make Your Move");

    // }

//=============================================================================================================

    //GamePlayMethods
    public void playerMove(int playerNum)
    {
        //how to initialize 8 moves?
        //check if chosen move is valid
        //check if wall is in the way 
        //is player on same square
        //if so end turn switch state
        //subtract move and

        int[] currentLoc = new int[2];
        if(playerNum == 1) currentLoc = gb.playerOneLoc;
        else if(playerNum == 2) currentLoc = gb.playerTwoLoc;
        else System.out.println("Error: playerMove invalid player selected");
        if(gb.validMoveCheck(pmSelect, playerNum))
        {
            //check for wall
            gg.changeTileImage(currentLoc[0], currentLoc[1], "TILE");
            if(playerNum == 1)
            {
                gg.changeTileImage(pmSelect[0], pmSelect[1], "HERO1");  
                gb.playerOneLoc[0] = pmSelect[0];
                gb.playerOneLoc[1] = pmSelect[1];
            }
            else if(playerNum == 2)
            {
                gg.changeTileImage(pmSelect[0], pmSelect[1], "HERO2");
                gb.playerTwoLoc[0] = pmSelect[0];
                gb.playerTwoLoc[1] = pmSelect[1];
            }
            //subtracts move from moveCount
            gb.getPlayer(playerNum).getMoveCount(-1);
        }
        else
        {
            gg.addTextToInfoArea("Invalid Move");
        }

        //change state if player has no more moves
        if(gb.getPlayer(playerNum).getMoveCount(0) == 0)
        {
            if(gb.getPlayer(2) != null)
            {
                if(activePlayer == 1 && playState == gamePlayState.playerMove)
                {
                    gb.getPlayer(2).resetMoves();
                    activePlayer = 2;
                }
                else if(activePlayer == 2 && playState == gamePlayState.playerMove)
                {
                    gb.getPlayer(0).resetMoves();
                    playState = gamePlayState.dragonMove;
                }
            }
            else
            {
                playState = gamePlayState.dragonMove;
            }
        }
    }

    public void resetRoutine()
    {
        setupState = gameSetupState.start;
        playState = null;
        //clear graphics on screen
    }

    @Override
    public void buttonPressed(GraphicsClickTypes type) 
    {
        if(type == GraphicsClickTypes.START)
        {
            //Start putton will initiated game setup process 
            if(this.setupState == gameSetupState.start)
            {
                execSetup();
            }
        }

        if(type == GraphicsClickTypes.RESET)
        {
            resetRoutine();
        }

        if(type == GraphicsClickTypes.NEXT)
        {
            //Player 1 Confirms Secret Room Choice 
            if((setupState == gameSetupState.firstPlayerSetup || setupState == gameSetupState.secondPlayerSetup) && !Arrays.equals(psrSelect, new int[] {}))
            {
                execSetup();
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
        if(setupState == gameSetupState.firstPlayerSetup || setupState == gameSetupState.secondPlayerSetup) 
        {
            psrSelect = new int[] {row, col};
        }

        else if(playState == gamePlayState.playerMove)
        {
            pmSelect = new int[] {row, col};
            execPlay();
        }
    }
    
}
