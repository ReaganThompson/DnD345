import java.util.*;

public class Player extends Piece{

    private int id;
    private int health;
    private boolean treasure;
    private int lives;
    private int moves;
    private static int playerCount = 0;
    private boolean attackedByDragon;

    public Player()
    {
        id = playerCount++; //each creation of the Player object increases id
        playerCount %= 2;//id is either 0 or 1
        lives = 3;
        setMoves();
    }

    public boolean isAttackedByDragon() {
        return attackedByDragon;
    }

    public void setAttackedByDragon(boolean attackedByDragon) {
        this.attackedByDragon = attackedByDragon;
        if (lives > 0) {
            lives -= 2;
        }
    }
    
    public int getIDNum(){
        return id;
    }

    public int getHealth(int change){
        //update with change and then return value
        return health;
    }

    public int getMoveCount(int change){
        //update with change and then return value
        return moves;
    }

    public boolean hasTreasure(){
        //update with change (-1 = false, 0 = no change, 1 = true) and then return value
        return treasure;
    }

    public void move() {}

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
