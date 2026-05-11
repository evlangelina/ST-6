package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void resetInitializesBoardWithEmptyCells() {
        TicTacToeGame game = new TicTacToeGame();

        char[] expected = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        assertArrayEquals(expected, game.getBoardCopy());
    }

    @Test
    void makeMoveRejectsInvalidCellAndOccupiedCell() {
        TicTacToeGame game = new TicTacToeGame();

        assertFalse(game.makeMove(-1, 'X'));
        assertTrue(game.makeMove(0, 'X'));
        assertFalse(game.makeMove(0, 'O'));
        assertFalse(game.makeMove(10, 'O'));
    }

    @Test
    void checkStateDetectsXWin() {
        TicTacToeGame game = new TicTacToeGame();
        char[] board = {'X', 'X', 'X', ' ', 'O', ' ', 'O', ' ', ' '};

        assertEquals(GameState.XWIN, game.checkState(board, 'X'));
    }

    @Test
    void checkStateDetectsOWin() {
        TicTacToeGame game = new TicTacToeGame();
        char[] board = {'O', 'X', 'X', ' ', 'O', ' ', 'X', ' ', 'O'};

        assertEquals(GameState.OWIN, game.checkState(board, 'O'));
    }

    @Test
    void checkStateDetectsDraw() {
        TicTacToeGame game = new TicTacToeGame();
        char[] board = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};

        assertEquals(GameState.DRAW, game.checkState(board, 'X'));
        assertEquals(GameState.DRAW, game.checkState(board, 'O'));
    }

    @Test
    void checkStateDetectsPlaying() {
        TicTacToeGame game = new TicTacToeGame();
        char[] board = {'X', 'O', 'X', ' ', 'O', ' ', ' ', 'X', ' '};

        assertEquals(GameState.PLAYING, game.checkState(board, 'X'));
    }

    @Test
    void findBestMoveChoosesImmediateWinningMove() {
        TicTacToeGame game = new TicTacToeGame();
        game.makeMove(0, 'X');
        game.makeMove(1, 'X');
        game.makeMove(4, 'O');
        game.makeMove(7, 'O');

        int bestMove = game.findBestMove('X');
        assertEquals(2, bestMove);
    }

    @Test
    void findBestMoveReturnsValidCellOnFreshBoard() {
        TicTacToeGame game = new TicTacToeGame();

        int bestMove = game.findBestMove('X');
        assertTrue(bestMove >= 0 && bestMove < 9);
    }

    @Test
    void getBoardCopyReturnsDefensiveCopy() {
        TicTacToeGame game = new TicTacToeGame();
        game.makeMove(0, 'X');

        char[] copy = game.getBoardCopy();
        copy[0] = 'O';

        char[] secondCopy = game.getBoardCopy();
        assertNotEquals(copy[0], secondCopy[0]);
        assertEquals('X', secondCopy[0]);
    }
}
