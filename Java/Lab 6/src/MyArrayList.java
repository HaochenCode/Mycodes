import java.util.Arrays;
public class MyArrayList {
    private int[] data;
    private int size;


    public int getSize(){return size;}

    public int getDataCapacity(){ return data.length;}

    public MyArrayList(){
        data = new int[4];
        size = 0;
    }


    public MyArrayList(int[] arr){
        data = arr;
        size = arr.length;
    }

    public int[] getData(){
        return data;
    }

    public void add(int n){
        if (size >= data.length) {
            data = Arrays.copyOf(data, size * 2);
        }
        data[size] = n;
        size++;

    }

    public boolean remove(int n){
        int[] temp = new int[data.length -1];
        if(isEmpty()){
            return false;
        }

        else{
            for (int i = 0; i < data.length; i++) {
                if(i < n) {
                    temp[i] = data[i];
                }
                else if(i > n){
                    temp[i-1] = data[i];
                }
            }
            size--;
            data = temp;
            return true;
        }

    }

    @Override
    public String toString(){
        String temp ="";
        for (int i = 0; i < size; i++) {
             temp = temp + "," + data[i];
        }
        return temp;
    }



    public boolean isEmpty(){
        if(size == 0)
            return true;
        else
            return false;
    }

    public int indexOf(int n){
        boolean found = false;
        int result = 0;
        for (int i = 0; i < data.length; i++) {
            if(data[i] == n){
                result = i;
                found = true;
                return i;
            }
        }
        if(!found)
            return -1;
        else
        return result;
    }
}
