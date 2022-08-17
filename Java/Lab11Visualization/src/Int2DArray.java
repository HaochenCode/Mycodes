import java.sql.SQLOutput;
import java.util.Random;

public class Int2DArray implements Int2DArrayADT {

    private int[][] array;

    // Constructor
    public Int2DArray(int[][] arr) {
        this.array = arr;
    }

    public Int2DArray(int x, int y) {
        this.array = new int[x][y];

    }


    @Override
    public void set2DArray(int[][] array) {
        this.array = array;
    }

    @Override
    public int[][] get2DArray() {
        return array;
    }

    @Override
    public void setElementsToZero() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = 0;
            }
        }
    }

    @Override
    public int[] rowGet(int row) {
        return array[row];
    }

    @Override
    public void rowSet(int rowNumber, int[] row) {
        array[rowNumber] = row;
    }

    @Override
    public int[] columnGet(int column) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i][column];
        }
        return result;
    }

    @Override
    public void columnSet(int columnNumber, int[] column) {
        for (int i = 0; i < array.length; i++) {
            array[i][columnNumber] = column[i];
        }
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str += "\n";
            for (int j = 0; j < array[0].length; j++) {
                str += array[i][j] + ", ";
            }
        }
        return str;
    }
}