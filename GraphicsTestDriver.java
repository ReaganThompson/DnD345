import com.wwu.graphics.*;


public class GraphicsTestDriver implements BoardGraphicsInf
{
    static int playerTurn;
    static int tileNumber;
    static int[] tileLocation = new int[2];
    static GameManager gm = new GameManager();
    static GameGraphics gg;

    public GraphicsTestDriver() {
        tileLocation[0] = -1;
        tileLocation[1] = -1;
        tileNumber = -1;
        gg = new GameGraphics(this);
    }

    private boolean movePlayer (int row, int col, int playerNum) {
        return gm.movePlayer(row, col, playerNum);
    }

    private void changeTileImage(int tileNumber, String image) {
        gg.changeTileImage(tileNumber, image);
    }

    public static void main(String[] args)
    {
        GraphicsTestDriver driver = new GraphicsTestDriver();
        while(true) {
        }

    }

    @Override
    public void buttonPressed(GraphicsClickTypes type) {
        if (type.equals(GraphicsClickTypes.NEXT)) {
            playerTurn = (playerTurn + 1) % 2; //player turn switches between 0 and 1
            //0 --> player 1
            //1 --> player 2
        }
        System.out.println("buttonPressed: " + type);
    }

    /*
    Move the player if successful; otherwise, print an error message onto game board.
     */
    @Override
    public void tilePressed(int tileNumber) {
        String image = (playerTurn == 0) ? "HERO1" : "HERO2";
        int currentTileNum = gm.getCurrentTileNumber(playerTurn);
        boolean success = gm.movePlayer(tileNumber, playerTurn);
        if (success) {
            gg.changeTileImage(tileNumber, image);
            if (currentTileNum != -1 ) {
                gg.changeTileImage(currentTileNum, "TILE");
            }
            this.tileNumber = tileNumber;
        } else if (gm.isPlayerAhead()) {
            gg.addTextToInfoArea("Player ahead!");
            gg.addTextToInfoArea("Choose another tile!");
        }
        System.out.println("tilePressed: " + tileNumber);
    }

    @Override
    public void tilePressed(int column, int row) {
        /*String image = (playerTurn == 0) ? "HERO1" : "HERO2";
        int currentTileNum = gm.getCurrentTileNumber(playerTurn);
        boolean success = gm.movePlayer(column, row, playerTurn);
        if (success) {
           gg.changeTileImage(column, row, image);
            if (currentTileNum != -1 ) {
                gg.changeTileImage(currentTileNum, "TILE");
            }
           tileLocation[0] = column;
           tileLocation[1] = row;
        } else if (gm.isPlayerAhead()) {
            gg.addTextToInfoArea("Player ahead!");
            gg.addTextToInfoArea("Choose another tile!");
        }
        System.out.println("tilePressed: " + column + ":" + row);*/
    }
}
