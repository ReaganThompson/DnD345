import com.wwu.graphics.*;

import java.util.Arrays;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

public class GameController implements BoardGraphicsInf
{
    private Board gb;
    private GameGraphics gg;

    private int playersTurn = 1;
    
    //Game can be in Setup Mode or Game Play Mode
    public gameSetupState setupState;
    public gamePlayState playState;

    //player secret room select
    private int[] psrSelect;
    //player move select
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
        this.setupState = gameSetupState.start;
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
                activePlayer = 1;
                break;
            case firstPlayerSetup:
                //puts player one into selected secret room

                gb.selectSecretRoom(psrSelect[0], psrSelect[1], 1);
                //first parameter col, second parameter row
                gg.changeTileImage(psrSelect[1], psrSelect[0], "HERO1");
                gb.playerOneLoc = new int[] {psrSelect[0], psrSelect[1]};
                gb.addPlayer(new Player(),activePlayer);
                //reset secret room
                this.psrSelect = null;
                setupState = gameSetupState.secondPlayerSetup;
                gg.addTextToInfoArea("For single player select enter again");
                gg.addTextToInfoArea("For 2 Player Select Another Secret Room and press enter");
                break;
            case secondPlayerSetup:
                //puts player two into selected secret room
                //finishes setup if no player two room is selected

                if(psrSelect != null)
                {
                    activePlayer = 2;
                    gb.addPlayer(new Player(),activePlayer);
                    //first parameter col, second parameter row
                    gb.selectSecretRoom(psrSelect[0], psrSelect[1], 2);
                    gb.playerTwoLoc = new int[] {psrSelect[0], psrSelect[1]};
                    gg.changeTileImage(psrSelect[1], psrSelect[0], "HERO2");
                    // numPlayers = 2;
                }
                gb.selectTreasureRoom();
                gb.ensurePathToTreasure();
                //test
                showWalls();

                setupState = gameSetupState.complete;
                playState = gamePlayState.playerMove;
                //set active player back to 1
                activePlayer = 1;
                
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
                playerMove(activePlayer);
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

    //GamePlayMethods

    public void playerMove(int activePlayer)
    {
        int[] currentLoc = new int[2];
        if(activePlayer == 1) currentLoc = gb.playerOneLoc;
        else if(activePlayer == 2) currentLoc = gb.playerTwoLoc;
        int[] selectedMove = pmSelect;

        if(!validMoveCheck(currentLoc, selectedMove)) return;
        if(!wallCheck(currentLoc, selectedMove))
        {
            //turn is over
            if(activePlayer == 1)
            {
                this.activePlayer = 2;
                gb.getPlayer(2).resetMoves();

            }
            else if(activePlayer == 2)
            {
                this.activePlayer = 1;
                gb.getPlayer(1).resetMoves();
                //playState = gamePlayState.dragonMove;
            }
            return;
        }

        //moving of player if move is valid
        gg.changeTileImage(currentLoc[1], currentLoc[0], "TILE");
        if(activePlayer == 1) 
        {
            gb.playerOneLoc[0] = pmSelect[0];
            gb.playerOneLoc[1] = pmSelect[1];
            gg.changeTileImage(pmSelect[1], pmSelect[0], "HERO1");  
        }
        else if(activePlayer == 2)
        {
            gb.playerTwoLoc[0] = pmSelect[0];
            gb.playerTwoLoc[1] = pmSelect[1];
            gg.changeTileImage(pmSelect[1], pmSelect[0], "HERO2");
        }
        //If Dragon is not awake checks to see if dragon needs to be woken up
        if(!gb.getDragon().isAwake())
        {
            //This coniditon is real messy. Change if time allows.
            //WakeupDragon takes player location and dragon location, check to se if player is within 3 moves, if so it wakes up the dragon and returns boolean true through which we print message.
            if(gb.getDragon().wakeUpDragon(gb.getPlayerLoc(activePlayer),gb.dragonLoc ))
            {
                gg.addTextToInfoArea("DRAGON IS AWAKE!");
            }

        }




        //subtract one from moves
        gb.getPlayer(activePlayer).moveMade();
        int movesLeft = gb.getPlayer(activePlayer).getMoves();
        if(movesLeft == 0)
        {
            if(activePlayer == 1)
            {
                //if player 2 is null, switch to dragon state 
                this.activePlayer = 2;
                gb.getPlayer(activePlayer).resetMoves();
            }
            else if(activePlayer == 2)
            {
                //switch to dragon state 
                this.activePlayer = 1;
                gb.getPlayer(activePlayer).resetMoves();
            }
        }
    }

    public boolean validMoveCheck(int[] currentLoc, int[] selectedMove)
    {
        int rowDifference = Math.abs(currentLoc[0]-selectedMove[0]);
        int colDifference = Math.abs(currentLoc[1]-selectedMove[1]);
    
        if(rowDifference + colDifference != 1)
        {
            gg.addTextToInfoArea("Invalid Move");
            return false;
        } 
        return true;
    }

    public boolean wallCheck(int[] currentLoc, int[] selectedMove)
    {
        int rowDifference = (currentLoc[0]-selectedMove[0]);
        int colDifference = (currentLoc[1]-selectedMove[1]);

        char[] moveDir = new char[2];
            //moving up
            if(rowDifference > 0) moveDir = new char[] {'n', 's'};
            //moving down
            else if(rowDifference < 0) moveDir = new char[] {'s', 'n'};
            //moving left
            else if(colDifference > 0) moveDir = new char[] {'w', 'e'};
            //moving right
            else if(colDifference < 0) moveDir = new char[] {'e', 'w'};
            
            Square[][] board = gb.getBoard();
            Square currentSquare = board[currentLoc[0]][currentLoc[1]];
            Square destSquare = board[pmSelect[0]][pmSelect[1]];

                if(currentSquare.hasWall(moveDir[0]) || destSquare.hasWall(moveDir[1]))
            {
                if(moveDir[0] == 'n') gg.wallGraphicSetVisible(currentLoc[1], currentLoc[0], GraphicsWallDirections.NORTH,true);
                else if(moveDir[0] == 's') gg.wallGraphicSetVisible(currentLoc[1], currentLoc[0], GraphicsWallDirections.SOUTH,true);
                else if(moveDir[0] == 'e') gg.wallGraphicSetVisible(currentLoc[1], currentLoc[0], GraphicsWallDirections.EAST,true);
                else if(moveDir[0] == 'w') gg.wallGraphicSetVisible(currentLoc[1], currentLoc[0], GraphicsWallDirections.WEST,true);
                
                return false;
            }
            return true;
    }

    public void dragonTurnToMove()
    {
        //check if dragon is awake
        //should dragon be woken up

        //find target player
        //move dragon
       
        //if dragon is not awake 
        if(!gb.getDragon().isAwake())
        {
            this.playState = gamePlayState.playerMove;
            return;
        }
        else
        {
            Player[] players = {gb.getPlayer(1), gb.getPlayer(2)};
            Dragon dragon = gb.getDragon();
            //returns player num of targer either 1 or 2
            int TargetPlayerNum = dragon.findPlayerToTarget(players);
            
            int[] targetPlayerLoc = gb.getPlayerLoc(TargetPlayerNum);
            int[] dragonLoc = gb.getDragonLoc();
    
            //finds dragons move
            int[] newDragonMove = dragon.dragonMove(targetPlayerLoc, dragonLoc);
            //updates dragon location
            gb.dragonLoc = newDragonMove;

        }
        //if dragon found player dragon attacks 












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
            if((setupState == gameSetupState.firstPlayerSetup || setupState == gameSetupState.secondPlayerSetup))
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
        //test
        public void showWalls()
        {
            Square[][] board = gb.getBoard();
            for (int row = 0; row < 8; row++)
            {
                for(int col = 0; col < 8; col++)
                {
                    Square s = board[row][col];
                    if(s.hasWall('n'))
                    {
                        gg.wallGraphicSetVisible(col,row, GraphicsWallDirections.NORTH, true);

                    }
                    if(s.hasWall('s'))
                    {
                        gg.wallGraphicSetVisible(col,row, GraphicsWallDirections.SOUTH, true);

                    }
                    if(s.hasWall('e'))
                    {
                        gg.wallGraphicSetVisible(col,row, GraphicsWallDirections.EAST, true);

                    }
                    if(s.hasWall('w'))
                    {
                        gg.wallGraphicSetVisible(col,row, GraphicsWallDirections.WEST, true);

                    }
                }
            }
        }
    
}
