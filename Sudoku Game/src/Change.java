public class Change {
    private int row, col, previousValue;

    public Change(int row, int col, int previousValue) {
        this.row = row;
        this.col = col;
        this.previousValue = previousValue;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getPreviousValue() { return previousValue; }
}



