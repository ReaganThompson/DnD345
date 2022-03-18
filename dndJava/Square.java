public class Square {

    private boolean northWall;
    private boolean eastWall;
    private boolean southWall;
    private boolean westWall;

    public int row;
    public int col;

    private Piece occupant;

    public Square(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, int row, int col){
        this.northWall = northWall;
        this.eastWall = eastWall;
        this.southWall = southWall;
        this.westWall = westWall;
        this.row = row;
        this.col = col;
    }

    public boolean hasWall(char dir){
        switch(dir){
            case 'n':
                return northWall;
            case 'e':
                return eastWall;
            case 's':
                return southWall;
            case 'w':
                return westWall;
            default:
                System.out.println("Error: hasWall invalid dir selected");
                return false;
        }
    }

    public int[] getLocation(){
        return new int[] {row,col};
    }

    public boolean[] setWall(char dir, boolean type){
        switch (dir){
            case 'n':
                northWall = type;
                break;
            case 'e':
                eastWall = type;
                break;
            case 's':
                southWall = type;
                break;
            case 'w':
                westWall = type;
                break;
            default:
                System.out.println("attempted to set nonexistent wall");
        }
        return new boolean[] {northWall, eastWall, southWall, westWall};
    }

    public Piece occupant(){
        //return occupant
        return occupant;
    }

    public Piece occupant(Player p){
        //update occupant and return
        return occupant;
    }
}