public class ArrayStatic {
    private Integer[] array;

    public ArrayStatic(Integer[] arr){
        this.array = arr;
    }

    public Object removeElementAt(int index){
        array[index] = null;
        return array;
    }

    public String toString(){
        String str = "";
        for(Integer i : array){
            str += i + ", ";
        }
        return str;
    }
}
