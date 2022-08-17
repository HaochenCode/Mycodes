// C343/Summer 2022
// Problem Set 02
// 2022-5-18
// Haochen Sun / haocsun

This project basically creates two types of arrays, static and dynamic. For the removeElementAt() function,
the static array just replace the given index of element with a null value, but the dynamic array version just
delete it, and decrease the array size by 1.

In the main file, I just wrote tests for the functions of both classes.

Part A:
  I used the ADT from Professor Ye.
  1.
  list.mvToPos(3);
  list.delete();

  2. The equation is n > D*E/P+E
  A. n > 20 * 8/ 4 + 8 =13.3333
     since n is integer, so when n <= 13, linked-list needs less space than array.
  B. n > 30 * 2/ 2+4 = 10
     So when n <= 10, linked-list needs less space than array.
  C. n > 30 * 1 / 1 + 4 = 31/5 = 6.2
     So when n <= 6, linked-list needs less space than array.
  D. n > 32 * 40 / 32 + 4 = 35.56
     So when n <= 35, linked-list needs less space than the array.


Part B:
  1. The best, average and worst case for static array is the same, which is a constant. Since only takes a few steps,
  first locate the element by the index, second change it to null, and just return it. Complexity is O(1),Ω(1) and
  Θ(1).
     The best case for static array is to remove the last element, which takes a constant time. Average case is when
  the target element is in the middle, so program needs to move the right half of the array to the left by 1. The worst
  case is when the target is the first element, and requires to move every element other than the first to the left.
  Complexity is O(n).

  3. First, static arrays doesn't change size, so it won't save space after the elements are removed. Second, when
  the element is removed, the value null is in that position, so when manipulating or iterating through the array,
  it will be more complicated to deal with.

  Static array works better when the user want to store data on the stack, since it will stay there at a fixed postion,
  not like dynamic allocation of memory, it is more stable.