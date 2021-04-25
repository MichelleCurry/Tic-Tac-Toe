import java.util.*;

public class Lab8{

   // Scanner for user input
   static Scanner CONSOLE = new Scanner(System.in);

   // A random number generator for makeing the computer move.
   static Random RND = new Random();

   // Some constants to make changing the symbols very easy.
   static char HUMAN_PLAYER = 'X', COMPUTER_PLAYER = 'O';

   public static void main(String[] args) {

      // Display the instructions.
      displayInstructions();

      String play = "y";
      String winner = "";
      while (play.toLowerCase().equals("y")){
         // initial board.
         char[][] board
            = {{'1', '2', '3'},
               {'4', '5', '6'},
               {'7', '8', '9'}};
               
            while (!haveWinner(board) && !isBoardFull(board)) {
               // show the board
               displayBoard(board);
   
               // Handle the human's choice/move
               humanMove(board);
               
               //check if human won
               if (haveWinner(board) || isBoardFull(board)){
                  break;
               }
               
               // handle the computers choice/move
               computerMove(board);
   
           }
           // Display game results.
           displayResults(board); // TODO: more parameters may be necessary.
           System.out.println("\tEnter y to play again");
           System.out.println("\tEnter n to stop playing");
           play = CONSOLE.next();
           
         while (!(play.equals("y") || play.equals("n"))){
            System.out.println("Not a valid answer. Enter y or n");
            play = CONSOLE.next();
         }
      }
      System.out.println("Goodbye");
   }


    // returns true of the board is full, false if not.
    static boolean isBoardFull(char[][] board) {
         int filledSpaces = 0;
         
         for (char[] row : board) {
            for (char element : row) {
               char status = element;
               if (status == HUMAN_PLAYER || status == COMPUTER_PLAYER){
                  filledSpaces++;                
               }
            }
         }
         
        return filledSpaces == 9;
    }

   static char whoWon(char[][] board){
      char element = ' ';
      char winner = ' ';
      
      for (int length = 0; length < 3; length++){
         element = board[0][length];
         //horizontal win 
         if(element == board[1][length] && element == board[2][length]){
            winner = element;
         } 
         
         element = board[length][0];
         //vertical win 
         if(element == board[length][1] && element == board[2][length]){
            winner = element;
         }   
      }
         
      if(winner==' '){
         element = board[0][0];
         //L-R diagonal win 
         if(element == board[1][1] && element == board[2][2]){
               winner = element;
         } else {   
            element = board[2][0];
            //R-L diagonal win 
            if(element == board[1][1] && element == board[0][2]){
                  winner = element;
            } 
         }
      }                              
      return winner;
   }
    
   //returns true if either player won, false if no winner yet.
   static boolean haveWinner(char[][] board) {
      boolean winner = false;
      char element = ' ';
         
      for (int length = 0; length < 3; length++){       
         element = board[length][0];
         //horizontal win 
         if(element == board[length][1] && element == board[length][2]){
            winner=true;
         } 
            
         element = board[0][length];
         //horizontal win 
         if(element == board[1][length] && element == board[2][length]){
            winner=true;
         }   
      }
         
      element = board[0][0];
      //starting from top L-R diagonal win 
      if(element == board[1][1] && element == board[2][2]){
         winner=true;
      }   
      
      element = board[2][0];
      //starting from top R-L diagonal win 
      if(element == board[1][1] && element == board[0][2]){
         winner=true;
      } 
      return winner;
   }


   //updates a vaild move by the players
   static void humanMove(char[][] board) {
      System.out.print("\nChoose Cell: ");
      int cell = CONSOLE.nextInt();
     
      boolean isUpdateValid = updateBoard(board, cell, HUMAN_PLAYER);
        
      while (!isUpdateValid){
         System.out.print("That spot is already taken chose another: ");
         cell = CONSOLE.nextInt();
         isUpdateValid = updateBoard(board, cell, HUMAN_PLAYER);
      }    
   }


   //Computer makes a winning blocking or random move 
   static void computerMove(char[][] board) {
      //Chooses blocking or winning or random move if the first 2 action do not exist
      int compuMove = blockOrWin(board);
      if(compuMove == -1){
         compuMove = RND.nextInt(9)+1;
      }
        
      boolean isUpdateValid = updateBoard(board, compuMove, COMPUTER_PLAYER);
        
      while (!isUpdateValid){
         compuMove = RND.nextInt(9)+1;
         isUpdateValid = updateBoard(board, compuMove, COMPUTER_PLAYER);
      }     
   }
   
   //Determines which cell can block the human player or win for the computer 
   static int blockOrWin(char[][] board){
      int open = 0;
      int human = 0;
      int comp = 0;
      boolean win = false;
      int cell = -1;
      int openPos = 0;
   
      //check for horizontal block/win
      for (int row = 0; row < 3; row++){
         for (int col = 0; col < 3; col++) {
            if(board[row][col] == HUMAN_PLAYER){
               human++;
            } else if(board[row][col] == COMPUTER_PLAYER){
               comp++;
            } else {
               open++;
               openPos = col;
            }
            
            //prioritize win over block
            if(open == 1 && comp == 2){
               cell = (row*3)+openPos+1;
               win = true;
            } else if(open == 1 && human == 2 && !win){
               cell = (row*3)+openPos+1;
            }
         }
      
         human=0;
         comp=0;
         open=0;
         openPos=0;
      }
      
      //check for vertical block/win
      for (int col = 0; col < 3; col++){
         for (int row = 0; row < 3; row++) {
            if(board[row][col] == HUMAN_PLAYER){
               human++;
            } else if(board[row][col] == COMPUTER_PLAYER){
               comp++;
            } else {
               open++;
               openPos = row;
            }
             
            if(open == 1 && comp == 2){
               cell = (openPos*3)+col+1;
               win = true;
            } else if(open == 1 && human == 2 && !win){
               cell = (openPos*3)+col+1;
            }
         }
         
         human=0;
         comp=0;
         open=0;
         openPos=0;
      }
      
      //check for top L-r diagonal win/block
      for (int rocol = 0; rocol < 3; rocol++){
         if(board[rocol][rocol] == HUMAN_PLAYER){
            human++; 
         } else if(board[rocol][rocol] == COMPUTER_PLAYER){
            comp++; 
         } else {
            open++;
            openPos = rocol;
         }
         if(open == 1 && comp == 2){
            cell = (openPos*3+openPos+1);
            win = true;
         } else if(open == 1 && human == 2 && !win){
            cell = (openPos*3+openPos+1);
         }
         
      }
      human=0;
      comp=0;
      open=0;
      openPos=0;
      
      //check for top r-L diagonal win/block
      if(board[1][1] == HUMAN_PLAYER){
         human++; 
      } else if(board[1][1] == COMPUTER_PLAYER){
         comp++; 
      } else {
         open++;
         openPos = 1;
      }
      
      if(board[0][2] == HUMAN_PLAYER){
         human++; 
      } else if(board[0][2] == COMPUTER_PLAYER){
         comp++; 
      } else {
         open++;
         openPos = 0;
      }
      
      if(board[2][0] == HUMAN_PLAYER){
         human++; 
      } else if(board[2][0] == COMPUTER_PLAYER){
         comp++; 
      } else {
         open++;
         openPos = 2;
      }
      
      if(open == 1 && comp == 2){
         cell = (openPos*3)+(3-openPos);
         win = true;
      } else if(open == 1 && human == 2 && !win){
         cell = (openPos*3)+(3-openPos);
      }     
      human=0;
      comp=0;
      open=0;
      openPos=0;
      
      return cell;
   } 

   //Update the board with either players move.
   static boolean updateBoard(char[][] board, int cellNumber, char player) {
      boolean available = false;
      if(!(cellNumber < 1 && cellNumber > 9)){   
         int row = (cellNumber - 1) / board.length;
         int col = (cellNumber - 1) % board.length;
         char status = board[row][col];

         if (status != HUMAN_PLAYER && status != COMPUTER_PLAYER){
            board[row][col] = player;
            available = true;
         }
      }
      return available;
   }


    // Display the contents of the board.
    static void displayBoard(char[][] board) {
      System.out.printf("%10s","");
      for (int row = 0; row < 3; row++){
         for (int col = 0; col < 3; col++){
            System.out.print(board[row][col]);
            if(col!= 2){
               System.out.print(" | ");
            }
         }
         System.out.println();            
         if(row!= 2){            
            System.out.printf("%21s\n","-------------");
            System.out.printf("%10s","");
         }
      }         
   }
          
   // Instructions
   static void displayInstructions() {
       System.out.println("Instructions:");
       System.out.println("\tPlay a game of tic-tac-toe against the computer");
       System.out.println("\tGet three Xs in a row to win\n");
   }   
   
   // Game Over (Results)
   static void displayResults(char[][] board) {
       displayBoard(board);     
       System.out.println();
       if(isBoardFull(board) && !haveWinner(board)){
         System.out.println("So close! It was a tie.");
       } else {
       
         if(whoWon(board)== HUMAN_PLAYER){
            System.out.println("You won. Congrtulations!");
         } else {
            System.out.println("The computer won. Better luck next time");
         }
      }      
   }
}