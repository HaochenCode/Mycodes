import java.sql.SQLOutput;

public class Array2DInt implements Array2DIntADT {

    private int[][] array;

    // Constructor
    public Array2DInt(int[][] arr){
        this.array = arr;
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
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++){
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
        for(int i = 0; i < array.length; i++){
            result[i] = array[i][column];
        }
        return result;
    }

    @Override
    public void columnSet(int columnNumber, int[] column){
        for(int i = 0; i < array.length; i++){
            array[i][columnNumber] = column[i];
        }
    }

    public String toString(){
        String str = "";
        for(int i = 0; i < array.length; i++){
            str += "\n";
            for(int j = 0; j < array[0].length; j++){
                str += array[i][j] + ", ";
            }
        }
        return str;
    }

    // Main function for testing
    public static void main(String[] args) {
        int[][] arr1 = {{6, 4, 6, 8}, {3, 1, 5, 8}, {9, 5, 3, 1}};
        int[][] arr2 = {{6, 4, 1, 6, 8}, {3, 1, 2, 5, 8}, {9, 5, 3, 3, 1}};
        int[] row1 = {1, 2, 3, 4, 5};
        int[] col1 = {9, 9, 9};
        Array2DInt array2D_1 = new Array2DInt(arr1);
        System.out.println(array2D_1);
        array2D_1.set2DArray(arr2);
        System.out.println(array2D_1);
        array2D_1.setElementsToZero();
        System.out.println(array2D_1);
        array2D_1.rowSet(0, row1);
        System.out.println(array2D_1);
        array2D_1.columnSet(1, col1);
        System.out.println(array2D_1);
        array2D_1.get2DArray();
        //System.out.println(array2D_1.rowGet(1).toString());

        int[] arry1 = array2D_1.rowGet(1);
        System.out.println();
        int[] arry2 = array2D_1.columnGet(4);


        for(int i[] : arr1){           // for each loop
            for (int j : i){
                System.out.print(j + ", ");
            }
        }
    }

    }


