public class PyramidPrinting {
    public static void main(String[] args) {
        PrintPyramid(7);
    }
    public static void PrintPyramid(int base){
        for(int i = 1; i <= (base+ 1)/2 ; i++){
            for(int j = 1; j <(base+ 1)/2 - (i -1)  ; j++){
                System.out.print(" ");
            }
            for(int j = 0; j < (2*i - 1) ; j++){
                System.out.print("*");
            }
            System.out.println("");
        }
    }
}
