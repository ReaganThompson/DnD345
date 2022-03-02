import java.util.*;

public class Player extends Piece{

    private int health;
    private int moves;
    private boolean treasure;
    private int attacks;

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
}