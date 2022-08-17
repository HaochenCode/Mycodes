import java.util.Random;

public class PS04Model implements Subject{
    private Int2DArray array;
    private Observer ob;
    private int width;
    private int height;

    public PS04Model(int x, int y){
        array = new Int2DArray(x, y);
        width = x;
        height = y;
    }

    public Int2DArray getArray(){
        return array;
    }

    public void randomize(){
        Random rdm = new Random();
        for(int i = 0; i < array.rowNum();i++) {
            for(int j = 0 ; j <array.colNum();j++){
                array.set(i,j,rdm.nextInt(510)-255);
            }
        }
    }

    public void sortRows(){
        long lTimeBefore = System.nanoTime();
        for(int i = 0; i < array.rowNum();i++) {
            int[] row = array.rowGet(i);
            insertionSort(row);
        }
        long lTimeAfter = System.nanoTime();
        long lElapsedNanoSeconds = (lTimeAfter - lTimeBefore);
        System.out.println(lElapsedNanoSeconds);
    }

    public void sortColumns(){
        long lTimeBefore = System.nanoTime();
        for(int i = 0; i < array.colNum();i++) {
            int[] column = array.columnGet(i);
            insertionSort(column);
            for (int j = 0; j < column.length; j++) {
                array.set(j, i, column[j]);
            }
        }
        long lTimeAfter = System.nanoTime();
        long lElapsedNanoSeconds = (lTimeAfter - lTimeBefore);
        System.out.println(lElapsedNanoSeconds);

    }


    // Sort function used for sorting rows and columns
    public void insertionSort(int arr[])
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }


    @Override
    public void registerObserver(Observer o) {
        this.ob = o;
    }

    @Override
    public void removeObserver(Observer o) {
        ob = null;
    }

    @Override
    public void notifyObservers() {
        ob.update();
    }
}
