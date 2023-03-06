package algorithms.mazeGenerators;
import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    // row property
    private int row;
    // col property
    private int col;

    /**
     * C-tor // given row-col.
     * @param row: position's row. (represents by "i" in the grid).
     * @param col: position's col. (represents by "j" in the grid).
     */
    public Position(int row, int col){
        if (row < 0 || col < 0){
            // Prevent Illegal Argument Exceptions.
            this.row = 0;
            this.col = 0;
        }
        this.row = row;
        this.col = col;
    }

    /**
     * C-tor // copy constructor.
     * @param position: the "other" to copy.
     */
    public Position(Position position){
        if (position != null){
            this.row=position.row;
            this.col=position.col;
        }
    }

    /**
     * C-tor // string based format constructor
     * @param str: the string represents the 2D position.
     */
    public Position(String str) throws IllegalArgumentException {
        if (str.charAt(0) != '{' || str.charAt(str.length() - 1) != '}' || !str.contains(",") || str.length() < 5) // null check is unnecessary.
            throw new IllegalArgumentException();
        String[] parts = str.split(",");
        this.row = Integer.parseInt(parts[0].substring(1));
        this.col = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
    }

    public int getRowIndex() {
        return row;
    }

    public void setRow(int row) throws IllegalArgumentException{
        if (row < 0){
            throw new IllegalArgumentException("Row index must be greater or equal to 0");
        }
        this.row = row;
    }

    public int getColumnIndex() {
        return col;
    }

    public void setCol(int col) {
        if (col<0){
            throw new IllegalArgumentException("Col index must be greater to equal to 0.");
        }
        this.col = col;
    }

    // Get the relevant position neighbors: Up/Down/Right/Left.
    public Position getUpPosition(){
        // i-1
        return new Position(this.row-1, this.col);
    }
    public Position getDownPosition(){
        // i+1
        return new Position(this.row+1, this.col);
    }
    public Position getRightPosition(){
        // j+1
        return new Position(this.row, this.col+1);
    }
    public Position getLeftPosition(){
        // j-1
        return new Position(this.row, this.col-1);
    }

    public Position getLeftUpPosition() {
        return new Position(this.row-1, this.col - 1);
    }

    public Position getLeftDownPosition() {
        return new Position(this.row+1, this.col - 1);
    }

    public Position getRightUpPosition() {
        return new Position(this.row-1, this.col + 1);
    }

    public Position getRightDownPosition() {
        return new Position(this.row+1, this.col + 1);
    }

    @Override
    public String toString() {
        return "{" + this.row + "," + this.col + '}';
    }

    // Equals & HashCode templates (overrides the Object class functions).
    // Provides position equality options.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
