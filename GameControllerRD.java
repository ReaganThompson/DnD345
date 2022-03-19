import java.io.ObjectInputFilter.Status;

import javax.swing.plaf.TreeUI;

import com.wwu.graphics.*;

public class GameControllerRD implements BoardGraphicsInf {
    private Board board;
    private GameGraphics gg;
    private State state;

    private static Square pendingSecretRoomSelect;

    //game states 
    private static boolean gameStarted;
    private static boolean player1SecretRoomSelect;
    private static boolean NextPress1;
    private static boolean player2SecretRoomSelect;
    private static boolean NextPress2;

    public enum State
    {
        NotStarted,
        Started,
        
    }



    public static void main(String args[])
    {
        GameController gameController = new GameController();
        GameGraphics gg = new GameGraphics(gameController);
        gameController.gg = gg;

        gg.addTextToInfoArea("Select Start to Begin Game");
        gg.addTextToInfoArea("Welcome.");
        
        while(true)
        {
 
            System.out.println("GameState: " + gameStarted);
            if(gameStarted == true)
            {
                gg.addTextToInfoArea("Select Your Secret Room");
                gg.addTextToInfoArea("Player1...");
                while(player1SecretRoomSelect == false)
                {
                    //debug
                    System.out.println();
                }
                Square P1SR = gameController.getSecretRoom(0);
                gg.changeTileImage(P1SR.getCol(), P1SR.getRow(),"HERO1");
                while(player2SecretRoomSelect == false && NextPress2 == false)
                {

                }
                //generate walls
                //secret room is chosen
                //end of game setup
            
            }
            
        }
        
        

    }

    public void stateMethods()
    {
        switch(state)
        {
            case NotStarted:
                caseNotStarted();
            case Started:


        }
    }

    public void caseNotStarted()
    {
        gg.addTextToInfoArea("Press Start to Begin");
        gg.addTextToInfoArea("Welcome");
    }
    public void caseRoomSelect()
    {

    }

    //board needs to be initialized 
    //player(s) need to choose secret room
    //system picks treasure room
    //walls are randomly generated 
    public static void gameSetup()
    {
        while(true)
        {

        }


    }

    public GameController()
    {
        gameStarted = false;
        player1SecretRoomSelect = false;
        player2SecretRoomSelect = false;

        
        this.board = new Board();
        this.state = State.NotStarted;
         
        ButtonReaction br = new ButtonReaction();
        this.gg = new GameGraphics(br);
    }

    public static void gameInit()
    {
        //wait for start button to be pushed
        while(gameStarted == false){}
        //player1 selects starting position. This becomes secret room 1
    }

    public Square getSecretRoom(int playerNum)
    {
        return board.getSecretRoom(playerNum);
    }

    @Override
    public void buttonPressed(GraphicsClickTypes type) {
        if (type == GraphicsClickTypes.START)
        {
            if(state == State.NotStarted)
            {

            }
        }
        if (type == GraphicsClickTypes.RESET)
        {
            this.gameStarted = false;
        }
        if (type == GraphicsClickTypes.NEXT)
        {
            if(player1SecretRoomSelect == false && pendingSecretRoomSelect != null)
            {
                board.setSecretRoom(pendingSecretRoomSelect, 0);
                player1SecretRoomSelect = true;
                NextPress1 = true;
            }

            if(player1SecretRoomSelect == true && player2SecretRoomSelect == false)
            {
                player2SecretRoomSelect = true;
            }

        }
        
    }

    @Override
    public void tilePressed(int tile) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void tilePressed(int col, int row) 
    {
        if(player1SecretRoomSelect == false)
        {
            //set this selected room to be secret room
            //set player1SecretRoomSelect to true
            //graphics: put player1 on this spot
            
            //sets secretRoom for player0
            pendingSecretRoomSelect = board.getRoom(row, col);    
        }


        
    }
}
