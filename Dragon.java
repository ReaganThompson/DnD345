import java.util.*;

public class Dragon extends Piece{

    private boolean awake = false;

    public boolean isAwake(){
        return awake;
    }

    private int proximity(Player p) {
        // Calculates the taxicab distance between two pieces
        return  Math.abs(this.row - p.row) + Math.abs(this.col - p.col);
    }

    private void dragonMove(Player p1, Player p2){
        /* To - Do:
         * Clone two player objects; player 1 & player 2 (if exists)
         * Calculate the proximity between both the dragon and each player
         * Whichever is closer (has a lower proximity #) is called to the
         * other overloaded dragonMove method... */
        if (p2 != null) {
            int p1prox = proximity(p1);
            int p2prox = proximity(p2);
            if (p1prox > p2prox){
                dragonMove(p2);
            } else if(p2prox > p1prox){
                dragonMove(p1);
            }
        } else {
            dragonMove(p1);
        }
    }

    private void dragonMove(Player p){
        //Dragon moves diagonally to player
        if((this.row != p.row) && (this.col != p.col)){
            if((this.row < p.row) && (this.col < p.col)){
                //If dragon is top left of player
                this.row = this.row + 1;
                this.col = this.col + 1;
            }else if((this.row > p.row) && (this.col < p.col)){
                //If dragon is bottom left of player
                this.row = this.row - 1;
                this.col = this.col + 1;
            }else if((this.row < p.row) && (this.col > p.col)){
                //If dragon is top right of player
                this.row = this.row + 1;
                this.col = this.col - 1;
            }else if((this.row > p.row) && (this.col > p.col)){
                //If dragon is bottom right of player
                this.row = this.row - 1;
                this.col = this.col - 1;
            }
            //Dragon moves along the row to player
        } else if((this.row != p.row)){
            if(this.row < p.row){
                this.row = this.row + 1;
            }else{
                this.row = this.row - 1;
            }
            //Dragon moves along the col to player
        } else if((this.col != p.col)){
            if(this.col < p.col){
                this.col = this.col + 1;
            }else{
                this.col = this.col - 1;
            }
        }
    }
    public void dragonOp1Player(Player p, Square secretRoom) {
        boolean outSecretRoom = !Arrays.equals(p.getLocation(), secretRoom.getLocation());
        if (outSecretRoom) {
            if (isAwake()) {
                if (p.hasTreasure()) {
                    if (proximity(p) <= 1) {
                        attack(p);
                    } else {
                        dragonMove(p);
                    }
                }
            } else if (proximity(p) <= 3) {
                awake = true;
                dragonOp1Player(p, secretRoom);
            }
        }
    }

    public void dragonOp2Player(Player p1, Player p2, Square secretRoom1, Square secretRoom2){
        /*Define [both] Player object(s) & attributes including:
         *                                        - Player location
         *                                        - Player secret room
         */
        boolean outSecretRoom1 = !Arrays.equals(p1.getLocation(), secretRoom1.getLocation());
        boolean outSecretRoom2 = !Arrays.equals(p2.getLocation(), secretRoom2.getLocation());
        if(outSecretRoom1 && outSecretRoom2){ //If warrior is outside secret room
            if (isAwake()){ //If dragon is awake
                Player p = (p1.hasTreasure()) ? p1 : (p2.hasTreasure() ? p2 : null);
                if (p != null) { //If treasure is occupied by player
                    if (proximity(p) <= 1){ //If player is next to dragon
                        //Player is in range of dragon:
                        attack(p);
                    } else {
                        // Player has treasure but isn't next to dragon
                        dragonMove(p);
                    }
                } else {
                    //No player has treasure, move to closest player
                    dragonMove(p1, p2);
                }
            } else if(proximity(p1) <= 3 || proximity(p2) <= 3){//Dragon isn't awake; is warrior close enough?
                awake = true;
                dragonOp2Player(p1, p2, secretRoom1, secretRoom2);
            }
        }
    }

    public void attack(Player p) {
        p.setAttackedByDragon(true);
    }
}
