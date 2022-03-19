import com.wwu.graphics.GraphicsClickTypes;
import com.wwu.graphics.*;

public class MovementDriver implements BoardGraphicsInf
{
    
    private Player p;
    public MovementDriver()
    {
        Player p = new Player();
        p.row = 3;
        p.col = 3;
        this.p = p;

        GameGraphics gg = new GameGraphics();

        gg.changeTileImage(23,"HERO1");
        gg.changeTileImage(20,"TILE");
    }

    @Override
    public void buttonPressed(GraphicsClickTypes arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void tilePressed(int arg0) {
        // TODO Auto-generated method stub

        
    }

    @Override
    public void tilePressed(int row, int col) 
    {
        int playerPosRow = p.row;
        int playerPosCol = p.col;

        //value can be either 1,-1, or 0 
        int rowMov = Math.abs( playerPosRow - row);
        int colMov = Math.abs(playerPosCol - col);
        
        if(rowMov+colMov == 1)
        {
            p.row = row;
            p.col = col;



        }
    }
    
}
