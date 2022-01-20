public final class Viewport
{
    private int row;
    private int col;
    private int numRows;
    private int numCols;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    public boolean contains(Point p) {
        return p.getY() >= this.row && p.getY() < this.row + this.numRows
                && p.getX() >= this.col && p.getX() < this.col + this.numCols;
    }

    public Point worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }
}
