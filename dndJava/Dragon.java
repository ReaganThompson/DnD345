import java.util.*;

public class Dragon extends Piece{


    private boolean awake = false;


    public boolean isAwake()
    {
        return awake;
    }
    public boolean isDragonAwake()
    {
        return this.awake;
    }

    public int proximity(Player p)
    {
        return (Math.abs(row - p.row) + Math.abs(col - p.col));
    }
    public int proximity(int pRow, int pCol)
    {
        return (Math.abs(row - pRow) + Math.abs(col - pCol));
    }
    

    //returns int corresponding to the player that dragon will target
    public int findPlayerToTarget(Player[] players)
    {
        //one player game
        if(players[1] == null)
        {
            return 1;
        }

        //play 1 distance
        int p1dist = proximity(players[0]);
        int p2dist = proximity(players[1]);

        //if player 1 is closer
        if(p1dist < p2dist)
        {
            return 1;
        }
        return 2;
    }

    public int[] dragonMove(int[] playerloc, int[]dragonLoc)
    {
        int[] newDragonLoc = new int[2];

        //Dragon Row and Col 
        int dRow = dragonLoc[0];
        int dCol = dragonLoc[1];
        //Player Row and Col
        int pRow = playerloc[0];
        int pCol = playerloc[1];

        //checks if diagonal move is best option 
        if((dRow != pRow) && (dCol != pCol))
        {
            //If dragon is top left of player
            if((dRow < pRow) && (dCol < pCol))
            {
                //move dragon diagonally down and to the right
                newDragonLoc[0] = dRow + 1;
                newDragonLoc[1] = dCol + 1;
            }
            //If dragon is bottom left of player
            else if((dRow > pRow) && (dCol < pCol))
            {
                //move dragon diagonally up and to the right
                newDragonLoc[0] = dRow - 1;
                newDragonLoc[1] = dCol+ 1;
            }
            //If dragon is top right of player
            else if((dRow < pRow) && (dCol > pCol))
            {
                //move dragon diagonally down and to the left
                newDragonLoc[0] = dRow + 1;
                newDragonLoc[1] = dCol - 1;
            }
            //If dragon is bottom right of player
            else if((dRow > pRow) && (dCol > pCol))
            {   
                //move dragon diagonally up and to the left
                newDragonLoc[0] = dRow - 1;
                newDragonLoc[1] = dCol - 1;
            }
        
        }
        //Dragon on same row as targeted Player
        else if((dRow == pRow))
        { 
            //THIS MAY BE WRONG CHECK WHEN TESTING
            if(dRow < pRow)
            {
                newDragonLoc[0] = dRow + 1;
                newDragonLoc[1] = dCol;
            }
            else
            {
                newDragonLoc[0] = dRow - 1;
                newDragonLoc[1] = dCol;
            }
        }
        //Dragon on same col as targeted Player
        else if((dCol == pCol))
        { 
            //THIS MAY BE WRONG CHECK WHEN TESTING
            if(dCol < pCol)
            {
                newDragonLoc[0] = dRow;
                newDragonLoc[1] = dCol + 1;
            }
            else
            {
                newDragonLoc[0] = dRow;
                newDragonLoc[1] = dCol - 1;
            }        
        }
        return newDragonLoc;
    }

    //boolean return allows for message to be displayed via GameController class 
    public boolean wakeUpDragon(int[] playerLoc)
    {
        if(proximity(playerLoc[0], playerLoc[1]) <= 3)
        {
            this.awake = true;
            return true;
        }
        return false;
    }

}
