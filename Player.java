import java.util.*;

public class Player extends Piece {

    private int health = 100;
    private int moves = 8;
    private boolean hasTreasure;
    private int attackCount;
    private static int playerCount;

    public Player() {
        super(playerCount++);
    }

    public int getHealth(){
        return health;
    }

    public int getMoveCount(){
        return moves;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void setMoveCount(int moves){
        this.moves = moves;
    }

    public int getAttackCount(){
        return attackCount;
    }

    public void setAttackCount(int attackCount) {
        this.attackCount = attackCount;
    }

    public boolean hasTreasure() {
        return hasTreasure;
    }

    public void pickUpTreasure() {
        hasTreasure = true;
    }

    public void loseTreasure() {
        hasTreasure = false;
    }

}
