import java.util.*;

public class Dragon extends Piece{

    private boolean awake = false;

    public boolean isAwake(){
        return awake;
    }
    
    private int proximity(int ID){
        //Define object 'player' to match the ID
        //private Player player = player.IDnum or smth

        // Calculates the taxicab distance between two pieces
        int dist = Math.abs(dragon.getRow - player.getRow) + Math.abs(dragon.getCol - player.getCol);
        return dist;
    }

    private void dragonMove(){
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

    private void dragonMove(int ID){
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