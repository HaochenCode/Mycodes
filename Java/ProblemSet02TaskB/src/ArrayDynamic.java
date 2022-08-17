public class ArrayDynamic {
    private Integer[] array;

    public ArrayDynamic(Integer[] arr){
        this.array = arr;
    }

    public Integer[] removeElementAt(int index){
        Integer[] temp = new Integer[array.length-1];
        for(int i = 0; i < index; i++){
            temp[i] = array[i];
        }
        for(int i = index; i < temp.length;i++){
            temp[i]= array[i+1];
        }
        array = temp;
        return temp;
    }

    public String toString(){
        String str = "";
        for(Integer i : array){
            str += i + ", ";
        }
        return str;
    }
}
