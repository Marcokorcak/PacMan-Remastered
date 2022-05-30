package application;

public class GhostCoordinates {

private int row;
private int col;

/**
 * Non-default constructor for GhostCoordinates object
 * @param r
 * @param c
 */

public GhostCoordinates(int r, int c) {
    row = r;
    col = c;
}

/**
 * @return row value
 */

public int getRow() {
    return row;
}

/**
 * @return column value
 */

public int getCol() {
    return col;
}

/**
 * @return set row value
 * @param a
 */

public void setRow(int a)
{
    row = a;
}


/**
 * @return set column value
 * @param b
 */
public void setCol(int b)
{
    col = b;
}
}


