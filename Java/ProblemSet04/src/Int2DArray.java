public class Int2DArray implements Int2DArrayADT {

    private int[][] mat;
    private int rows;
    private int cols;

    /** 0. we could have a constructor.... */
    public Int2DArray(int nrow, int ncol) {
        rows = nrow;
        cols = ncol;
        mat = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = 0;
            }
        }
    }

    /** 1. .... or a "create" method. */
    public void create(int nrow, int ncol) {
        rows = nrow;
        cols = ncol;
        mat = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = 0;
            }
        }
    }

    /** 2. Return the number of rows */
    public int rowNum() {
        return rows;
    }
    /** 3. Return the number of columns */
    public int colNum() {
        return cols;
    }

    /** 4. Set the value to the cell specified by a row index and a column index */
    public void set(int rowidx, int colidx, int val) {
        mat[rowidx][colidx] = val;
    }

    /** 5. Get the value in a cell */
    public int get(int rowidx, int colidx) {
        return mat[rowidx][colidx];
    }

    /** etc....  */

    /** 10. setElementsToZero (set to 0 all elements of the array) */
    public void setElementsToZero() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = 0;
            }
        }
    }

    public int[] rowGet(int row) {
        return mat[row];
    }

    public int[] columnGet(int column) {
        int[] result = new int[mat.length];
        for(int i = 0; i < mat.length; i++){
            result[i] = mat[i][column];
        }
        return result;
    }
}
