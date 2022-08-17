// C343/Summer 2022
// lab 14
// 2022-6-8
// Haochen Sun / haocsun

Task A:

Adjacency Matrix:
   A B C D E F
A      1   1
B      1     1
C  1 1   1   1
D      1     1
E  1         1
F    1 1 1 1

Adjacency List:
Vertex    Adjacency-List
A         [C] -> [E]
B         [C] -> [F]
C         [A] -> [B] -> [D] -> [F]
D         [C] -> [F]
E         [A] -> [F]
F         [B] -> [C] -> [D] -> [E]
