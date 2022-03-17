import java.util.*;

public class Player extends Piece{

    private static int health;
    private static int moves;
    private static boolean treasure;
    private static int attacks;

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