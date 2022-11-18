using System;
namespace Chessgame
{
    public  class Token
    {
        public int remainingTokensP1{get;set;} = 16;
        public int remainingTokensP2{get;set;} = 16;
        public enum TokenTypes
        {
            pawn = 1,
            bishop = 4,
            knight = 3,
            rook = 5,
            queen = 9,
            king = 99
        }
    }

    public class Board
    {
        public const int boardLen = 8;
        public const int boardWid = 8;
        public int[,] board = new int[boardLen,boardWid];
        //P1 is on the bottom of the chessboard
        public int turn {get;set;} = 0;
        //P2 is on the top of the chessboard

        public int p1Steps{get;set;} = 0;
        public int p2Steps{get;set;} = 0;

        //Constructor with no parameter
        //Initialize the board
        public Board()
        {
            //Pawns
            for(int i = 0; i < boardWid;i++)
            {
                board[1,i] = 1;
                board[6,i] = -1;
            }

            //Rooks
            board[0,0] = 5;
            board[0,7] = 5;
            board[7,0] = -5;
            board[7,7] = -5;

            board[0,1] = 3;
            board[0,6] = 3;
            board[7,1] = -3;
            board[7,6] = -3;

            board[0,2] = 3;
            board[0,5] = 3;
            board[7,2] = -3;
            board[7,5] = -3;

            board[0,3] = 99;
            board[7,3] = -99;

            board[0,4] = 9;
            board[7,4] = -9;

            //Fill the empty spaces with 0
            for(int i = 0; i < boardLen; i++)
            {
                for(int j = 0; j < boardWid; j++)
                {
                    if(Math.Abs(board[i,j]) != 1 && Math.Abs(board[i,j]) != 5 && Math.Abs(board[i,j]) != 3 && Math.Abs(board[i,j]) != 9 && Math.Abs(board[i,j]) != 99)
                    {
                        board[i,j] = 0;
                    }
                }
            }
        }
        // Initialize with given board
        public Board(int[,] b){
            for(int i = 0; i < boardLen; i++)
            {
                for(int j = 0; j < boardWid; j++)
                {
                    board[i,j] = b[i,j];
                }
            }
        }

        //Check if the given space on board is white
        public bool isWhite(int x,int y){
            if (x % 2 == 0 && y % 2 == 0)
            {
                return false;
            }
            if(x % 2 == 0 && y % 2 != 0)
            {
                return true;
            }
            if(x % 2 != 0 && y % 2 == 0)
            {
                return true;
            } 
            if(x % 2 != 0 && y % 2 != 0)
            {
                return false;
            }
            Console.WriteLine("Invalid input.\n");
            return false; //Todo
        }
        //Check if the given place on board is black
        public bool isBlack(int x, int y)
        {
            if(isWhite(x,y))
                return false;
            return true;
        }

        public bool isEmpty(int x , int y)
        {
            if (board[x,y] == 0)
                return true;
            else
                return false;
        }

        public bool isValidMove(int x, int y, int currentX, int currentY, Token.TokenTypes t, int turn)
        {
            //If the destination is out of the board
            if(y + currentX > 7 || y + currentX < 0 || x + currentY > 7 || x + currentY < 0)
                return false;

            if(Token.TokenTypes.pawn == t)
            {
                if(turn == 0)
                {
                    if(p1Steps == 0 && x == 0 && (y == 1 || y == 2))
                        return true;
                    else if(x == 0 && y == 1 && board[currentX + 1,currentY] == 0)
                        return true;
                    else if(x == 1  && y == 1 && board[currentX + 1, currentY + 1] < 0)
                        return true;
                    else if(x == -1 && y == 1 && board[currentX +1, currentY - 1] < 0)
                        return true;
                    else   
                        return false;
                }
                if(turn == 1)
                {
                    if(p2Steps == 0 && x == 0 && (y == -1 || y == -2))
                        return true;
                    else if(x == 0 && y == -1 && board[currentX - 1,currentY] == 0)
                        return true;
                    else if(x == 1 && y == -1 && board[currentX - 1, currentY + 1] > 0)
                        return true;
                    else if(x == -1 && y == -1 && board[currentX - 1, currentY - 1] > 0)
                        return true;
                    else   
                        return false;
                }
                
            }

            if(Token.TokenTypes.bishop == t)
            {
                if(turn == 0)
                {
                             
                    if(x == y && x > 0 && board[currentX + x, currentY + y] <= 0)
                    {
                        for(int i = 1; i < x; i++)
                        {
                            if(board[currentX + i, currentY + i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == y && x < 0 && board[currentX + x, currentY + y] <= 0)
                    {
                        for(int i = 1; i < (-x); i++)
                        {
                            if(board[currentX - i, currentY - i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == (-y) && x > 0 && board[currentX + y, currentY + x] <= 0)
                    {
                        for(int i = 1; i < x; i++)
                        {
                            if(board[currentX - i, currentY + i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == (-y) && x < 0 && board[currentX + y, currentY + x] <= 0)
                    {
                        for(int i = 1; i < y; i++)
                        {
                            if(board[currentX + i, currentY - i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else
                        return false;
                }

                if(turn == 1)
                {

                    if(x == y && x > 0 && board[currentX + x, currentY + y] >= 0)
                    {
                        for(int i = 1; i < x; i++)
                        {
                            if(board[currentX + i, currentY + i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == y && x < 0 && board[currentX + x, currentY + y] >= 0)
                    {
                        for(int i = 1; i < (-x); i++)
                        {
                            if(board[currentX - i, currentY - i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == (-y) && x > 0 && board[currentX + y, currentY + x] >= 0)
                    {
                        for(int i = 1; i < x; i++)
                        {
                            if(board[currentX - i, currentY + i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == (-y) && x < 0 && board[currentX + y, currentY + x] >= 0)
                    {
                        for(int i = 1; i < y; i++)
                        {
                            if(board[currentX + i, currentY - i] != 0)
                                return false;
                        }
                        return true;
                    }
                    
                    else
                        return false;
                }
            }

            if(Token.TokenTypes.knight == t)
            {
                if(turn == 0)
                {
                    if(Math.Abs(x) == 1 && Math.Abs(y) == 2 && board[currentX + y, currentY + x] <= 0)
                        return true;
                    else if(Math.Abs(x) == 2 && (Math.Abs(y) == 1) && board[currentX + y, currentY + x] <= 0)
                        return true;
                    else
                        return false;
                }

                if(turn == 1)
                {
                    if(Math.Abs(x) == 1 && Math.Abs(y) == 2 && board[currentX + y, currentY + x] >= 0)
                        return true;
                    else if(Math.Abs(x) == 2 && (Math.Abs(y) == 1) && board[currentX + y, currentY + x] >= 0)
                        return true;
                    else
                        return false;
                }

            }

            if(Token.TokenTypes.queen == t)
            {
                if(turn == 0)
                {
                    if(x == 0 && board[currentX + y,currentY] <= 0){
                        int oneStep = y / Math.Abs(y);
                        for(int i = 1; i < Math.Abs(y); i++)
                        {
                            if(board[currentX + oneStep,currentY] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(y == 0 && board[currentX,currentY + x] <= 0){
                        int oneStep = x / Math.Abs(x);
                        for(int i = 1; i < Math.Abs(y); i++)
                        {
                            if(board[currentX ,currentY+ oneStep] != 0)
                                return false;
                        }
                        return true;
                    }
                        
                    else if(x == y && x > 0 && board[currentX + x, currentY + y] <= 0)
                    {
                        for(int i = 1; i < x; i++)
                        {
                            if(board[currentX + i, currentY + i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == y && x < 0 && board[currentX + x, currentY + y] <= 0)
                    {
                        for(int i = 1; i < (-x); i++)
                        {
                            if(board[currentX - i, currentY - i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == (-y) && x > 0 && board[currentX + y, currentY + x] <= 0)
                    {
                        for(int i = 1; i < x; i++)
                        {
                            if(board[currentX - i, currentY + i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == (-y) && x < 0 && board[currentX + y, currentY + x] <= 0)
                    {
                        for(int i = 1; i < y; i++)
                        {
                            if(board[currentX + i, currentY - i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else
                        return false;
                }

                if(turn == 1)
                {
                    if(x == 0 && board[currentX + y,currentY] >= 0){
                        int oneStep = y / Math.Abs(y);
                        for(int i = 1; i < Math.Abs(y); i++)
                        {
                            if(board[currentX + oneStep,currentY] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(y == 0 && board[currentX,currentY + x] >= 0){
                        int oneStep = x / Math.Abs(x);
                        for(int i = 1; i < Math.Abs(y); i++)
                        {
                            if(board[currentX ,currentY + oneStep] != 0)
                                return false;
                        }
                        return true;
                    }
                    else if(x == y && x > 0 && board[currentX + x, currentY + y] >= 0)
                    {
                        for(int i = 1; i < x; i++)
                        {
                            if(board[currentX + i, currentY + i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == y && x < 0 && board[currentX + x, currentY + y] >= 0)
                    {
                        for(int i = 1; i < (-x); i++)
                        {
                            if(board[currentX - i, currentY - i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == (-y) && x > 0 && board[currentX + y, currentY + x] >= 0)
                    {
                        for(int i = 1; i < x; i++)
                        {
                            if(board[currentX - i, currentY + i] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(x == (-y) && x < 0 && board[currentX + y, currentY + x] >= 0)
                    {
                        for(int i = 1; i < y; i++)
                        {
                            if(board[currentX + i, currentY - i] != 0)
                                return false;
                        }
                        return true;
                    }
                    
                    else
                        return false;
                }

            }

            if(Token.TokenTypes.rook == t)
            {
                if(turn == 0)
                {
                    if(x == 0 && board[currentX + y,currentY] <= 0){
                        int oneStep = y / Math.Abs(y);
                        for(int i = 1; i < Math.Abs(y); i++)
                        {
                            if(board[currentX + oneStep,currentY] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(y == 0 && board[currentX,currentY + x] <= 0){
                        int oneStep = x / Math.Abs(x);
                        for(int i = 1; i < Math.Abs(y); i++)
                        {
                            if(board[currentX ,currentY + oneStep] != 0)
                                return false;
                        }
                        return true;
                    }
                        
                    else
                        return false;
                }

                if(turn == 1)
                {
                    if(x == 0 && board[currentX + y,currentY] >= 0){
                        int oneStep = y / Math.Abs(y);
                        for(int i = 1; i < Math.Abs(y); i++)
                        {
                            if(board[currentX + oneStep,currentY] != 0)
                                return false;
                        }
                        return true;
                    }

                    else if(y == 0 && board[currentX, currentY + x] >= 0){
                        int oneStep = x / Math.Abs(x);
                        for(int i = 1; i < Math.Abs(y); i++)
                        {
                            if(board[currentX ,currentY + oneStep] != 0)
                                return false;
                        }
                        return true;
                    }
                    
                    else
                        return false;
                }

            }


            if(Token.TokenTypes.king == t)
            {
                if(Math.Abs(x) >= 0 && Math.Abs(x) <= 1 && Math.Abs(y) <= 1 && Math.Abs(y) >= 0 && board[currentX + y, currentY] == 0)
                    return true;
                else 
                    return false;
            }

            return true;   
        }
        public void makeMove(int x, int y, int currentX, int currentY, Token.TokenTypes t,int turn)
        {
            if(isValidMove(x,y,currentX,currentY,t,turn))
            {
                board[currentX + y, currentY + x] = board[currentX,currentY];
                board[currentX,currentY] = 0;
                if(turn == 0){
                    this.turn = 1;
                    p1Steps++;
                }
                    
                else{
                    this.turn = 0;
                    p2Steps++;
                }
                    
            }
            else 
                Console.WriteLine("Invalid Move.\n");
        }

        public void printBoard()
        {
            for(int i = 0; i < boardLen; i++)
            {
                for(int j = 0; j < boardWid; j++)
                {
                    Console.Write(String.Format("{0,4}", board[i,j]));
                }
                Console.WriteLine();
            }
            Console.WriteLine();
        }

        
        static void Main(string[] args)
        {
            Board b = new Board();
            b.printBoard();
            b.makeMove(0,2,1,0,Token.TokenTypes.pawn,b.turn);
            b.printBoard();
            b.makeMove(0,-1,6,0,Token.TokenTypes.pawn,b.turn);
            b.printBoard();

            b.makeMove(0,1,1,1,Token.TokenTypes.pawn,b.turn);
            b.printBoard();
            b.makeMove(0,-1,6,3,Token.TokenTypes.pawn,b.turn);
            b.printBoard();

            b.makeMove(0,1,1,3,Token.TokenTypes.pawn,b.turn);
            b.printBoard();
            b.makeMove(0,-1,7,3,Token.TokenTypes.king,b.turn);
            b.printBoard();

            b.makeMove(-3,3,0,4,Token.TokenTypes.queen,b.turn);
            b.printBoard();
            b.makeMove(0,-1,7,0,Token.TokenTypes.rook,b.turn);
            b.printBoard();

            b.makeMove(4,4,0,2,Token.TokenTypes.bishop,b.turn);
            b.printBoard();
            b.makeMove(0,1,6,0,Token.TokenTypes.rook,b.turn);
            b.printBoard();

            b.makeMove(-2,2,4,6,Token.TokenTypes.bishop,b.turn);
            b.printBoard();
            b.makeMove(-1,-1,7,5,Token.TokenTypes.bishop,b.turn);
            b.printBoard();

            b.makeMove(2,1,0,1,Token.TokenTypes.knight,b.turn);
            b.printBoard();
            

            //b.makeMove(-2,2,0,2,Token.TokenTypes.bishop,b.turn);
            //b.printBoard();
            //b.makeMove(1,2,0,1,Token.TokenTypes.knight,b.turn);
            //b.printBoard();
            //b.makeMove(-4,-4,7,4,Token.TokenTypes.queen,b.turn);
            //b.printBoard();
        }
    }


    
}


        


