public class Board {

    private Square[][] board; //an array of tile numbers --> [col][row]
    private int[][] playerLocations; // id colum row

    public Board() {
        board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square();
            }
        }
        playerLocations = new int[2][3]; //[id][col][row]
        playerLocations[0][1] = -1;
        playerLocations[0][2] = -1;
        playerLocations[1][1] = -1;
        playerLocations[1][2] = -1;
    }

    /*
    Return the tile with the given tile number.
     */
    public Square getSquare(int tileNumber) {
        int col = tileNumber % 8;
        int row = tileNumber / 8;
        return board[col][row];
    }

    /*
    Return the tile at the given column and row
     */
    public Square getSquare(int col, int row) {
        return board[col][row];
    }

    /*
    Return the current location of the given player.
     */
    public int[] getPlayerLocation(Player p) {
        int id = p.getId();
        int[] location = new int[2];
        if (playerLocations[id][1] == -1) { //the player hasn't entered the game yet
            return null;
        } else {
            location[0] = playerLocations[id][1];//column
            location[1] = playerLocations[id][2];//row
            return location;
        }
    }

    /*
    Set the location of the given player at the given column and row
     */
    public void setPlayerLocation(Player p, int col, int row) {
        int id = p.getId();
        int prevCol = playerLocations[id][1];
        int prevRow = playerLocations[id][2];
        if(prevCol != -1 && prevRow != -1) {//empty the previous tile the player was on
            setSquare(null, prevCol, prevRow);
        }
        playerLocations[id][1] = col;
        playerLocations[id][2] = row;
        setSquare(p, col, row);
    }

    /*
    Set the occupancy of the tile at the given column and row with the given
    Piece object
     */
    private void setSquare(Piece player, int col, int row) {
        Square s = board[col][row];
        s.setOccupant(player);
    }

}
