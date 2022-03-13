public class GameManager {

    private Board board;
    private Player[] players = new Player[2];
    private boolean playerAhead;
    private boolean wallAhead;

    public GameManager() {
        board = new Board();
        players[0] = new Player();
        players[1] = new Player();
        playerAhead = false;
    }

    /*
     After the player has selected a tile, the program will search if there
     is an existing player in the direction of the chosen tile (either in the column
     or row).
     Note: tileNumber = columnNumber + rowNumber * 8
     */
    public boolean checkPlayerAhead(int startTileNumber, int targetTileNumber) {
        boolean sameCol = ((startTileNumber % 8) - (targetTileNumber % 8) == 0);
        boolean sameRow = ((startTileNumber / 8) - (targetTileNumber / 8) == 0);
        int tile;
        if (sameRow) {
            int row = (startTileNumber / 8);
            if (startTileNumber < targetTileNumber) {
                tile = startTileNumber + 1;
                while (tile <= targetTileNumber) {
                    if (board.getSquare(tile).isOccupied()) {
                        return true;
                    }
                    tile++;
                }
            } else {
                tile = startTileNumber - 1;
                while (tile >= targetTileNumber) {
                    if (board.getSquare(tile).isOccupied()) {
                        return true;
                    }
                    tile--;
                }
            }
        } else if (sameCol) {
            if (startTileNumber < targetTileNumber) {
                tile = startTileNumber + 8;
                while (tile <= targetTileNumber) {
                    if (board.getSquare(tile).isOccupied()) {
                        return true;
                    }
                    tile += 8;
                }
            } else {
                tile = startTileNumber - 8;
                while (tile >= targetTileNumber) {
                    if (board.getSquare(tile).isOccupied()) {
                        return true;
                    }
                    tile -= 8;
                }
            }
        }
        return false;
    }

    /*
     After the player has selected a tile, the program will search if there
     is an existing player in the direction of the chosen tile (either in the column
     or row).
     */
    public boolean checkPlayerAhead(int startCol, int startRow, int targetCol, int targetRow) {
        if (startCol < targetCol) {
            for (int i = startCol + 1; i <= targetCol; i++) {
                if (board.getSquare(i, startRow).isOccupied()) {
                    return true;
                }
            }
        } else if (targetCol < startCol) {
            for (int i = startCol - 1; i >= targetCol; i--) {
                if (board.getSquare(i, startRow).isOccupied()) {
                    return true;
                }
            }
        } else if (startRow < targetRow) {
            for (int i = startRow + 1; i <= targetRow; i++) {
                if (board.getSquare(startCol, i).isOccupied()) {
                    return true;
                }
            }
        } else {
            for (int i = startRow - 1; i >= targetRow; i--) {
                if (board.getSquare(startCol, i).isOccupied()) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    Move player to the tile at the given column and row. Note,
    the player can move only up, down, left or right. No
    diagonal moves are allowed.
    */
    public boolean movePlayer(int col, int row, int playerNum) {
        Player p = players[playerNum];
        int[] current = board.getPlayerLocation(p);
        //If the player hasn't entered the game yet, or if the player
        //has chosen an invalid tile.
        if (current != null && (current[0] != col && current[1] != row)) {
           return false;
        } else {
            //If the move is valid, check for player ahead
            if (current != null) {
                playerAhead = checkPlayerAhead(current[0], current[1], col, row);
                if (playerAhead) {
                    return false;
                }
            }
            board.setPlayerLocation(p, col, row);
            return true;
        }
    }

    /*
    Move the player to the given tile number.
    */
    public boolean movePlayer(int tileNumber, int playerNum) {
        Player p = players[playerNum];
        int currentTileNum = getCurrentTileNumber(playerNum);
        //If the player hasn't entered the game yet, or if the player
        //has chosen an invalid tile.
        if (currentTileNum != -1 && (((currentTileNum % 8) != (tileNumber % 8)) &&
                ((currentTileNum / 8) != (tileNumber / 8)))) {
            return false;
        } else {
            if (currentTileNum != -1) {
                playerAhead = checkPlayerAhead(currentTileNum, tileNumber);
                if (playerAhead) {
                    return false;
                }
            }
            board.setPlayerLocation(p, tileNumber % 8, tileNumber / 8);
            return true;
        }
    }

    /*
    Return the number of the current tile the player is on.
    tileNumber = columnNumber + rowNumber * 8
    */
    public int getCurrentTileNumber(int playerNum) {
        Player p = players[playerNum];
        int[] current = board.getPlayerLocation(p);
        if (current == null) {
            return -1;
        } else {
            return current[0] + current[1] * 8;
        }
    }

    /*
    Return true if there's a player in the path of the chosen tile; false, otherwise.
     */
    public boolean isPlayerAhead() {
        return playerAhead;
    }

}
