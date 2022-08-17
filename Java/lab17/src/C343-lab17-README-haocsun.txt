// C343/Summer 2022
// lab 17
// 2022-6-15
// Haochen Sun / haocsun

Task A:
1. AC = 2, DF = 10, CF = 4, CB = 3, BF = 7, AE = 11, EF = 6;
2. C -> D -> A -> B -> F -> E
3. Minimum cost spanning tree, which means that to traverse all the nodes with the least total distance. And the
starting vertex does not matter, and the result will be unique.

Task B:
1. To C: C -> C (0)
   To A: C -> A (3)
   To B: C -> A -> B (8)
   To E: C -> E (13)
   To D: C -> A -> B -> D (23)

2.(IF = infinity)
           A  B  C  D  E
Initial    IF IF 0  IF IF
Process C  3  20 0  IF 13
Process B  3  8  0  23 13
Process A  3  8  0  23 13
Process D  3  8  0  23 13
Process E  3  8  0  23 13

Task C:
1.Iteration1: [3->2](3)
  Iteration2: [2->4](5)
  Iteration3: [4->6](10)
  Iteration4: [6->1](2)
  Iteration5: [6->5](3)


  Edges on tree: [3-2],[2-4],[4,6],[6-1],[6,5]

2. Yes, it forms a spanning tree but not a MST, since Dijkstra's algorithm also visit every node with locally
closest distance, and mark every visited node, which guarantees there is no cycle. Therefore it is a spanning tree.
Unless the graph is not fully connected, then the unconnected nodes won't be visited.

3. No, since Dijkstra's algorithm is greedy and only focuses on the locally best option, but a MST is a global best
solution, so it can't be guaranteed to form a MST.


Task D:

The moment after inserting 9, right before rearranging

                7
              /   \
             4     42
            / \    / \
           3  6   8   55
                   \  / \
                   32 54 64
                   /
                  9

Since the nodes 8,32,9 are unbalanced, and it's the right-left case, then we need a double rotation. First  right rotate
on the vertex 32, which gives us 8-9-32, and then a left rotation around vertex 8, now becomes 8-9-32, as shown below.
 8          8               9
  \          \             / \
  32  -->     9     ->    8  32
  /            \
 9             32

Final AVL:
                7
              /   \
             4     42
            / \    / \
           3  6   9   55
                 / \  / \
                8  32 54 64
