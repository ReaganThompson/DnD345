import java.util.*;

public class Player extends Piece{

    private int health;
    private boolean treasure;
    private int lives;

    private int moves;

    public Player()
    {
        lives = 3;
        setMoves();
    }

    
    public static int getHealth(int change){
        //update with change and then return value
        return health;
    }
    public static int getMoveCount(int change){
        //update with change and then return value
        return moves;
    }
    public static int getAttackCount(int change){
        //update with change and then return value
        return attacks;
    }
    public static boolean hasTreasure(int change){
        //update with change (-1 = false, 0 = no change, 1 = true) and then return value
        return treasure;
    }

    public void move()
    {
        
    }

    public void setMoves()
    {
        if(lives == 3)
        {
            moves = 8;
        }
        else if(lives == 2)
        {
            moves = 6;
        }
        else if(lives == 1)
        {
            moves = 4;
        }
    }
    public int getMoveCount()
    {
        return moves;
    }


    public void useMove()
    {
        moves--;
    }

         
}