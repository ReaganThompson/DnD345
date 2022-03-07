import java.util.*;

public class Player{
    private int health, moveCount, attackCount;
    private boolean hasTreasure;

    public int getHealth(){
        return health;
    }
    public int getMoveCount(){
        return moveCount;
    }
    public boolean isDead(){
        return 0;
    }
    public void pickTreasure(Board board){}
    public void attack(Piece piece){}
}