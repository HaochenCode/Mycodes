public class CalculateRuntime {
    public void countInstructions(int n) {
        int iCounter = 0;
        // Example 2

        int sum2 = 0;
        iCounter ++;
        for(int i = 1; i <= n; i ++)
            for(int j = 1; j <= n; j ++) {
                sum2++;
                iCounter++;
            }

        System.out.println("Example 2: ");
        System.out.println("n = " + n + " \t iCounter = " + iCounter + "\t (iCounter / n) = " + iCounter/n);





        // Example 7

        int m = n;
        int c = 0;
        int iCounter7 = 0;
        int[] a = new int[m+1];
        int[] b = new int[n+1];
        int[][] d = new int[n+1][n+1] ;

        for(int i = 1; i <= m; i ++)  {
            iCounter7++;
            for(int j = 1; j <= n; j ++) {
                if (a[i] == b[j]) {
                    c = 0;
                    iCounter7++;
                } else {
                    c = 1;
                    iCounter7++;
                }
                d[i][j] = Math.min(d[i-1][j] + 1, d[i][j-1] + 1);
                iCounter7++;
                if( d[i-1][j-1]+c >= d[i][j]){
                    d[i][j] = d[i][j];
                    iCounter7++;
                }
                else
                    d[i][j] = d[i-1][j-1]+c;
                    iCounter7 ++;
            }
        }

        System.out.println("Example 7 : " );
        System.out.println("n = " + n + " \t iCounter = " + iCounter7 + "\t (iCounter / n) = " + iCounter7/n);






    }
}

