public class Task1 {
    public static void main(String[] args) {
        int iCounter = 0;
        int n = 10;

        // Example 2
        // n = 10
        int sum2 = 0;
        iCounter ++;
        for(i = 1; i <= n; i ++)
            for(j = 1; j <= n; j ++) {
                sum2++;
                iCounter++;
            }

        System.out.println("Example 1: ");
        System.out.println("n = 10 /t iCounter = " + iCounter + "/t (iCounter / n) = " + iCounter/n);
        // n = 100
        iCounter = 0;
        n = 100;
        sum2 = 0;
        iCounter ++;
        for(i = 1; i <= n; i ++)
            for(j = 1; j <= n; j ++) {
                sum2++;
                iCounter++;
            }

        // n = 1000
        iCounter = 0;
        n = 1000;
        sum2 = 0;
        iCounter ++;
        for(i = 1; i <= n; i ++)
            for(j = 1; j <= n; j ++) {
                sum2++;
                iCounter++;
            }



    }
}
