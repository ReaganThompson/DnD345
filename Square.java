public class Square {

    private boolean northWall;
    private boolean eastWall;
    private boolean southWall;
    private boolean westWall;
    private boolean isSecretRoom;
    private boolean isTreasureRoom;
    private boolean isOccupied;
    private Piece occupant;

    public Square() {};

    public Square(boolean northWall, boolean eastWall,
                   boolean southWall, boolean westWall,
                   boolean isSecretRoom, boolean isTreasureRoom) {
        this.northWall = northWall;
        this.eastWall = eastWall;
        this.southWall = southWall;
        this.westWall = westWall;
        this.isSecretRoom = isSecretRoom;
        this.isTreasureRoom = isTreasureRoom;
    }

    public boolean hasNorthWall(){
        return northWall;
    }

    public boolean hasEastWall(){
        return eastWall;
    }

    public boolean hasSouthWall(){
        return southWall;
    }

    public boolean hasWestWall(){
        return westWall;
    }

    public Piece getOccupant() {
        return occupant;
    }

    /*
    Set the occupancy of this tile with the given Piece object;
    If the object is null, the tile becomes empty.
     */
    public void setOccupant(Piece p) {
        if (p != null) {
            occupant = p;
            isOccupied = true;
        } else {
            occupant = null;
            isOccupied = false;
        }
    }

    public boolean isSecretRoom(){
        return isSecretRoom;
    }

    public boolean isTreasureRoom(){
        return isTreasureRoom;
    }

    public boolean isOccupied(){
        return isOccupied;
    }
}
