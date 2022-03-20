import java.util.*;

public class Dragon extends Piece{

    private boolean awake = false;

    public boolean isAwake()
    {
        return awake;
    }
    
    public int proximity(Player p)
    {
        return Math.abs(row - p.row) + Math.abs((col - p.col));
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
    public boolean wakeUpDragon(int[] playerLoc, int[] dragonLoc)
    {
        int rowDifference = dragonLoc[0] - playerLoc[0];
        int colDifference = dragonLoc[1] - playerLoc[1];

        if(Math.abs(rowDifference) <= 3 || Math.abs(colDifference) <= 3)
        {
            this.awake = true;
            return true;
        }
        return false;
    }

    public boolean isDragonAwake()
    {
        return this.awake;
    }

    

/* OLD CODE!
__________________________________________________________________________________________________

    

    /*
    private int proximity(int ID){
        //Define object 'player' to match the ID
        //private Player player = player.IDnum or smth

        // Calculates the taxicab distance between two pieces
        int dist = Math.abs(dragon.getRow - player.getRow) + Math.abs(dragon.getCol - player.getCol);
        return dist;
    }
    */

    private void dragonMove_rm(){
        /* To - Do:
        * Clone two player objects; player 1 & player 2 (if exists)
        * Calculate the proximity between both the dragon and each player
        * Whichever is closer (has a lower proximity #) is called to the
        * other overloaded dragonMove method... */
        
        /*
        int p1prox = proximity(player1ID);
        int p2prox = proximity(player2ID);

        if(p1prox > p2prox){
            dragonMove(player2ID);
        }else if(p2prox > p1prox){
            dragonMove(player1ID);
        }        
        */
    }

    private void dragonMove_rm(int ID){
        //Define object 'player' to match the ID
        //private Player player = player.IDnum

        //Dragon moves diagonally to player
        if((dragon.getRow != player.getRow) && (dragon.getCol != player.getCol)){
            if((dragon.getRow < player.getRow) && (dragon.getCol < player.getCol)){
                //If dragon is top left of player
                dragon.row = dragon.row + 1;
                dragon.col = dragon.col + 1;
            }else if((dragon.getRow > player.getRow) && (dragon.getCol < player.getCol)){
                //If dragon is bottom left of player
                dragon.row = dragon.row - 1;
                dragon.col = dragon.col + 1;
            }else if((dragon.getRow < player.getRow) && (dragon.getCol > player.getCol)){
                //If dragon is top right of player
                dragon.row = dragon.row + 1;
                dragon.col = dragon.col - 1;
            }else if((dragon.getRow > player.getRow) && (dragon.getCol > player.getCol)){
                //If dragon is bottom right of player
                dragon.row = dragon.row - 1;
                dragon.col = dragon.col - 1;
            }

        //Dragon moves along the row to player
        }else if((dragon.getRow = player.getRow)){ 
            if(dragon.getRow < player.getRow){
                dragon.row = dragon.row + 1;
            }else{
                dragon.row = dragon.row - 1;
            }

        //Dragon moves along the col to player
        }else if((dragon.getCol = player.getCol)){ 
            if(dragon.getCol < player.getCol){
                dragon.col = dragon.col + 1;
            }else{
                dragon.col = dragon.col - 1;
            }        
        }
    }


    public void dragonOp(){
        /*Define [both] Player object(s) & attributes including:
        *                                        - Player location
        *                                        - Player secret room 
        */

        if(/*[Player Location] != [Secret Room]*/){ //If warrior is outside secret room
            if(dragon.isAwake()){ //If dragon is awake
                if(player.hasTreasure()){ //If treasure is occupied by player
                    if(proximity() <= 1){ //If player is next to dragon
                        //Player is in range of dragon:
                        // attack(player.hasTreasure);
                    }else{ 
                        // Player has treasure but isn't next to dragon
                        dragonMove(player.getIDnum.hasTreasure);
                    }
                }else{
                    //No player has treasure, move to closest player
                    dragonMove();
                }
            }else if(proximity() <= 3){//Dragon isn't awake; is warrior close enough?
                awake = true;
                dragonOp();
            }
        }
    }
    

}