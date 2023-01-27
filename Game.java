public class Game {
    private int[][] Grid;
    public int turn, numberMoves;
    public Boolean isEditable;
    public int[] bestMove = new int[2];

    Game() {
        reset();
    }
    public void reset() {
        turn = 1;
        numberMoves = 0;
        isEditable = true;
        Grid = new int[3][3];
    }

    public boolean isEmpty(int i, int j) {
        return (Grid[i][j] == 0) && isEditable;
    }
    public String getPlayer() {
        return turn==1 ? "X" : "O";
    }
    public void playMove(int i, int j) {
        Grid[i][j] = turn;
        numberMoves++;
        if (turn == 1) turn = -1;
        else turn = 1;
    }
    public int winDet() {
        // horizontal and vertical winner
        for (int i=0; i<3; i++) {
            int tempRow = winDetHelper(Grid[i][0] + Grid[i][1] + Grid[i][2]);
            int tempCol = winDetHelper(Grid[0][i] + Grid[1][i] + Grid[2][i]);

            if ( tempRow!=0 ) return tempRow;
            if ( tempCol!=0 ) return tempCol;
        }
        // diagonal winner
        int tempPDiagonal = winDetHelper(Grid[0][0] + Grid[1][1] + Grid[2][2]);
        int tempSDiagonal = winDetHelper(Grid[2][0] + Grid[1][1] + Grid[0][2]);

        if ( tempPDiagonal!=0 ) return tempPDiagonal;
        if ( tempSDiagonal!=0 ) return tempSDiagonal;

        return numberMoves<9 ? 0 : 2;
    }
    private int winDetHelper(int sum) {
        if (Math.abs(sum)==3) return sum/3;
        return 0;
    }

    public String getMessage(int winner) {
        if (winner==0) {
            return (turn==1 ? "X" : "O") + "'s turn";
        }
        else if (winner==2) {
            isEditable = false;
            return "Game has tied";
        }
        else {
            isEditable = false;
            return (winner==1 ? "X" : "O") + " has won!";
        }
    }
    public boolean playAsAI() {
        if (turn != -1) return false; // not allowed to play
        if (!isEditable) return false; // game has drew or someone has won

        int bestScore = -2;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (isEmpty(i,j)) {
                    Grid[i][j] = -1;
                    numberMoves++;
                    int tempScr = minimax(false);  // play as human
                    if (tempScr > bestScore) {
                        bestScore = tempScr;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                    numberMoves--;
                    Grid[i][j] = 0;
                }
            }
        }
        return true;
    }

    private int minimax(boolean maximizer) {
        int winner = winDet();
        if (winner==2) return 0; // draw_case
        if (winner!=0) {    // winner found
            return -winner; // player2 should have highest score
        }

        int maxScore = -2;
        int minScore =  2;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (isEmpty(i,j)) {
                    // remaining possible moves
                    // performing move
                    Grid[i][j] = maximizer ? -1 : 1;
                    numberMoves++;
                    int tempScr = minimax(!maximizer);
                    if (tempScr > maxScore) maxScore = tempScr;
                    if (tempScr < minScore) minScore = tempScr;
                    // undoing move
                    numberMoves--;
                    Grid[i][j] = 0;
                }
            }
        }
        if (maximizer) return maxScore;
        return minScore;
    }
}
