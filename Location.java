public class Location {
    private int row, col;
    private char[][] gameMatrix;
    public Location(int row,int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void set(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean equals(Location other){
        if(row == other.row && col == other.col)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Row: "+row+" Col: "+col;
    }
}