public interface Int2DArrayADT {

    /** 1. Create a 2D array of integers with n rows and m columns */
    public void create(int nrow, int ncol);

    /** 2. Return the number of rows */
    public int rowNum();

    /** 3. Return the number of columns */
    public int colNum();

    /** 4. Set the value to the cell specified by a row index and a column index */
    public void set(int rowidx, int colidx, int val);

    /** 5. Get the value in a cell */
    public int get(int rowidx, int colidx);

    /** 6. Given a row index, return the list of elements in that row */
    public int[] rowGet(int rowidx);

    /** 7. Given a column index, return the list of elements in that column */
    public int[] columnGet(int colidx);

    /** 8. Given a row index, delete that row */
    /* public void rowDelete(int rowidx); */

    /** 9. Given a column index, delete that column */
    /* public void columnDelete(int colidx); */

    /** 10. Set to 0 all elements of the array */
    public void setElementsToZero();

    /** 11. Return whether the array is empty */
    /* public boolean isEmpty(); */




}