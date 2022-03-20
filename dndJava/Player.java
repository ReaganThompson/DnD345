import java.util.*;

public class Player extends Piece{

    private int health;
    private int moves;
    private static boolean treasure;
    private static int attacks;

    public Player()
    {
        this.health = 3;
        resetMoves();
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

    public void resetMoves()
    {
        this.moves = (2 + (2*health));
    }

    public void moveMade()
    {
        this.moves--;
    }

    public int getMoves()
    {
        return this.moves;
    }


    
}