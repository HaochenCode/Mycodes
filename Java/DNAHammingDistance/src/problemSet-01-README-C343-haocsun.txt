/*C343 / Summer 2022
2022-5-16
Haochen Sun
haocsun*/


The Hamming Distance Class briefly generates three pairs of random DNA sequences, and calculates the Hamming Distance between, and
print the output. Also, the lengths of the sequence are 100, 200, and 300.


// The ProblemSet01TaskA class first prints the versions of java, as follows
java.vm.version is 17.0.1+12-39
java.vm.vendor is Oracle Corporation
java.vm.name is OpenJDK 64-Bit Server VM
java.vm.specification.version is 17
java.vm.specification.vendor is Oracle Corporation
java.vm.specification.name is Java Virtual Machine Specification
java.version is 17.0.1
java.vendor is Oracle Corporation

// Then it creates three objects of Student Class and put them in an array, and prints students' info.
Sam is in CSCI
John is in Music
Susan is in Info


******************************************************************************************************************
// Answers for Part C
1. a = b + c;
   d = a + e;
Answer: Θ(1), since both of the best case and worst case are in O(1) and Ω(1), which are bounded by a constant,
so the average case is Θ(1).


2.sum = 0;
  for (i=0; i<3; i++)
      for (j=0; j<n; j++)
          sum++;

Answer: Θ(n), since the first line is O(1).Since the outer loop runs 3 times, and the inner loop runs n times, so the
worst case for the nested for-loop is 3n, which is O(n).The best case is also Ω(n), so the average case is Θ(n).


3.sum=0;
  for (i=0; i<n*n; i++)
      sum++;

Answer: Θ(n^2), since the first line is O(1). The worst case for the  for-loop is n^2, which is O(n^2). According to
the rule 3, it's in O(n^2). The best case is also Ω(n^2), so the average case is Θ(n^2).


4.for (i=0; i < n-1; i++)
      for (j=i+1; j < n; j++) {
          tmp = AA[i][j];
          AA[i][j] = AA[j][i];
          AA[j][i] = tmp;
      }
Answer: Θ(n^2), since the nested for-loop will loop (n-1)^2 times, which is n^2 - 2n + 1. For each loop, there are
three instructions, so totally about 3 * (n^2 - 2n + 1). After using the rules, we only consider the n^2 and neglect
the rest. The best case is also Ω(n^2). Therefore the average case is Θ(n^2).


5.sum = 0;
  for (i=1; i<=n; i++)
      for (j=1; j<=n; j*=2)
          sum++;

Answer:Θ(nlogN). Since the outer loop runs n times, and the inner loop runs logn times, to totally runs nlogn times.
Since the best and worst cases are both nlogn, so  is the average case.


6.sum = 0;
  for (i=1; i<=n; i*=2)
      for (j=1; j<=n; j++)
          sum++;

Answer:Θ(nlogN).Since the inner loop runs n times, and the outer loop runs logn times, to totally runs nlogn times.
Since the best and worst cases are both nlogn, so  is the average case.


7.for (i=0; i<n; i++) {
      for (j=0; j<n; j++)
          A[j] = DSutil.random(n);
      sort(A);
  }

Answer: Θ(n^2*logn). Since the outer loop runs n times, and inner loop runs n times, and the random function runs n^2 times,
and the sort function runs n times, so the total steps are n^2 + n* nlogn, which is n^2 + n^2logn, and we only keep the
larger one n^2logn. Also, the best and worst case are the same, hence the average case Θ(n^2logn).

8.sum = 0;
  for (i=0; i<n; i++)
      for (j=0; A[j]!=i; j++)
          sum++;

Answer: Θ(n^2). Since the outer loop executes n times, and inner loop does n-1 times, and the sum++ takes constant time,
so the total steps are n*(n-1), which can be written as O(n^2).
Also, the best and worst case are the same, hence the average case Θ(n^2).



*********************************************************************************************************************
// Answers for Part D:
1. (Growing fastest) n! > 3^n > 4n^2 > 20n > n^2/3 > log2n  > log3n > 2(Growing slowest)
Explanation: First, factorial grows most fast compared to other functions. Second, exponential function grows faster
than n square. 20n is a linear function with degree 1, which is greater than 2/3, so 20n is faster than n^2/3. Natural
log function grows slower than linear function, no matter what the base is. Finally, constant never grows.


2.(a)big-Omega is Ω(n), for values c = c1, and n0 = 1 (which is the same as to say "for any n > 0")
     big-O is O(n), for values c = c1, and n0 = 1.

  (b)big-Omega is Ω(n^3), for values c = c2, and n0 = 1.
     big-O is O(n^3), for values c = c2+c3, and n0 = 1.

  (c)big-Omega is Ω(nlog2n),for values c = c4, and n0 = 1.
     big-O is O(nlog2n), for values c = c4+c5, and n0 = 1.

  (d)big-Omega is Ω(2^n),for values c = c6, and n0 = 1.
     big-O is O(2^n), for values c = c6+c7, and n0 = 1.



3.Θ(n). Since this is an if-else statement, and the for-loop only executes when n is even, and sum++ is constant, and
it loops n times, which takes n steps; otherwise if n is odd, only constant step in the else block will execute. So the
best case is that all numbers are odd so that O(1), and worst case is all numbers are even so that Ω(n). So the average
case is (n-1)/2, which is 1/2n - 1/2, neglecting constants we get Θ(n).