import java.util.*;

public class Player extends Piece{

    private int health;
    private int moves;
    private int strength = 100;
    private boolean treasure;
    private static int attacks;

    public Player()
    {
        this.health = 3;
        resetMoves();
    }

    //CONSTRUCTORS
    public int getHealth()
    {
        return this.health;
    }
    public int getAttackCount()
    {
        return this.attacks;
    }
    public int getMoves()
    {
        return this.moves;
    }  
    public int getStrength()
    {
        return this.strength;
    }
    public boolean hasTreasure()
    {
        return this.treasure;
    }

    
    public void resetMoves()
    {
        if(!treasure){
            this.moves = (2 + (2*health));
        }
        else
        {
            this.moves = 4;
        }
    }

    public void moveMade()
    {
        this.moves--;
        this.strength--;
    }

    public void attacked()
    {
        this.health--;
    }
    
    public void wonFight()
    {
        this.treasure = true;
    }

    public void lostFight()
    {
        this.treasure = false;
    }
}