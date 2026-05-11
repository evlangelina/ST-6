package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

enum GameState {
    PLAYING,
    XWIN,
    OWIN,
    DRAW
}

class TicTacToeGame {
    static final int BOARD_SIZE = 9;
    static final char EMPTY = ' ';
    static final int INF = 100;

    private final char[] board;

    TicTacToeGame() {
        this.board = new char[BOARD_SIZE];
        reset();
    }

    void reset() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = EMPTY;
        }
    }

    char[] getBoardCopy() {
        return board.clone();
    }

    boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= BOARD_SIZE || board[index] != EMPTY) {
            return false;
        }
        board[index] = symbol;
        return true;
    }

    GameState checkState(char[] currentBoard, char symbol) {
        int[][] lines = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] line : lines) {
            if (currentBoard[line[0]] == symbol
                && currentBoard[line[1]] == symbol
                && currentBoard[line[2]] == symbol) {
                return symbol == 'X' ? GameState.XWIN : GameState.OWIN;
            }
        }

        for (char cell : currentBoard) {
            if (cell == EMPTY) {
                return GameState.PLAYING;
            }
        }
        return GameState.DRAW;
    }

    int findBestMove(char aiSymbol) {
        char opponent = aiSymbol == 'X' ? 'O' : 'X';
        int bestValue = -INF;
        int bestMove = -1;

        for (int move : generateMoves(board)) {
            board[move] = aiSymbol;
            int score = minimax(board, false, aiSymbol, opponent);
            board[move] = EMPTY;

            if (score > bestValue) {
                bestValue = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimax(char[] currentBoard, boolean isMaximizing, char aiSymbol, char opponentSymbol) {
        int score = evaluatePosition(currentBoard, aiSymbol);
        if (score != Integer.MIN_VALUE) {
            return score;
        }

        if (isMaximizing) {
            int best = -INF;
            for (int move : generateMoves(currentBoard)) {
                currentBoard[move] = aiSymbol;
                best = Math.max(best, minimax(currentBoard, false, aiSymbol, opponentSymbol));
                currentBoard[move] = EMPTY;
            }
            return best;
        }

        int best = INF;
        for (int move : generateMoves(currentBoard)) {
            currentBoard[move] = opponentSymbol;
            best = Math.min(best, minimax(currentBoard, true, aiSymbol, opponentSymbol));
            currentBoard[move] = EMPTY;
        }
        return best;
    }

    private int evaluatePosition(char[] currentBoard, char aiSymbol) {
        GameState aiState = checkState(currentBoard, aiSymbol);
        if (aiState == GameState.XWIN || aiState == GameState.OWIN) {
            return INF;
        }

        char opponent = aiSymbol == 'X' ? 'O' : 'X';
        GameState opponentState = checkState(currentBoard, opponent);
        if (opponentState == GameState.XWIN || opponentState == GameState.OWIN) {
            return -INF;
        }

        for (char cell : currentBoard) {
            if (cell == EMPTY) {
                return Integer.MIN_VALUE;
            }
        }
        return 0;
    }

    private List<Integer> generateMoves(char[] currentBoard) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (currentBoard[i] == EMPTY) {
                moves.add(i);
            }
        }
        return moves;
    }
}

public class Program {
    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();
        game.makeMove(0, 'X');
        game.makeMove(4, 'O');
        int bestMove = game.findBestMove('X');
        System.out.println("Best move for X: " + bestMove);
    }
}
