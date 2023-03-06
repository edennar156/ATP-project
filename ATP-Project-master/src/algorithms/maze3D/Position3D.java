package algorithms.maze3D;
import java.util.Objects;

public class Position3D {
    private int row;
    private int col;
    private int depth;

    /**
     *
     */
    public Position3D(int depth, int row, int col) {
        this.depth = depth;
        this.row = row;
        this.col = col;
    }

    // Pay attention to the chronology: depth > row > col:
    // {d,r,c}.

    /**
     * C-tor // string based format constructor
     */
    public Position3D(String str) throws IllegalArgumentException {
        if (str == null || str.length() < 7 || str.charAt(0) != '{' || str.charAt(str.length() - 1) != '}' || !str.contains(","))
            throw new IllegalArgumentException();
        String[] parts = str.split(",");
        this.depth = Integer.parseInt(parts[0].substring(1));
        this.row = Integer.parseInt(parts[1]);
        this.col = Integer.parseInt(parts[2].substring(0, parts[2].length() - 1));
    }

    /**
     * C-tor // Copy constructor
     */
    public Position3D(Position3D position3D) {
        if (position3D != null) {
            this.depth = position3D.depth;
            this.row = position3D.row;
            this.col = position3D.col;
        }
    }

    public int getDepthIndex() {
        return this.depth;
    }

    public int getRowIndex() {
        return this.row;
    }

    public int getColumnIndex() {
        return this.col;
    }

    public Position3D getUpPosition() {
        return new Position3D(this.depth, this.row - 1, this.col);
    }

    public Position3D getRightPosition() {
        return new Position3D(this.depth, this.row, this.col + 1);
    }

    public Position3D getDownPosition() {
        return new Position3D(this.depth, this.row + 1, this.col);
    }

    public Position3D getLeftPosition() {
        return new Position3D(this.depth, this.row, this.col - 1);
    }

    public Position3D getHigherPosition() {
        return new Position3D(this.depth - 1, this.row, this.col);
    }

    public Position3D getLowerPosition() {
        return new Position3D(this.depth + 1, this.row, this.col);
    }

    @Override
    public String toString() {
        return "{" + depth + "," + row + "," + col + '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position3D that = (Position3D) o;
        return row == that.row &&
                col == that.col &&
                depth == that.depth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, depth);
    }
}
