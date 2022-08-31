package ca.cmpt213.webapp.apiwrapper;

/**
 * ApiBoardWrapper to return back a game board for the request game
 */

import ca.cmpt213.webapp.model.CellLocation;
import ca.cmpt213.webapp.model.Game;
import ca.cmpt213.webapp.model.GameBoard;

/**
 * Wrapper class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public String[][] cellStates;

    public static ApiBoardWrapper getGameBoard(Game game, boolean cheatState) {
        ApiBoardWrapper boardWrapper = new ApiBoardWrapper();

        boardWrapper.boardWidth = GameBoard.NUMBER_ROWS;
        boardWrapper.boardHeight = GameBoard.NUMBER_COLS;
        boardWrapper.cellStates = new String[boardWrapper.boardWidth][boardWrapper.boardHeight];

        if (cheatState) {
            for (int i = 0; i < boardWrapper.boardWidth; i++) {
                for (int j = 0; j < boardWrapper.boardHeight; j++) {
                    CellLocation coordinate = new CellLocation(i, j);
                    boardWrapper.cellStates[i][j] = "field";

                    if (game.getCellState(coordinate).hasBeenShot()) {
                        if (game.getCellState(coordinate).hasTank()) {
                            boardWrapper.cellStates[i][j] = "hit";
                        }else {
                            boardWrapper.cellStates[i][j] = "miss";
                        }
                    }

                    if (!game.getCellState(coordinate).hasBeenShot()) {
                        if (game.getCellState(coordinate).hasTank()) {
                            boardWrapper.cellStates[i][j] = "tank";
                        }
                    }
                }
            }
        }else {
            for (int i = 0; i < boardWrapper.boardWidth; i++) {
                for (int j = 0; j < boardWrapper.boardHeight; j++) {
                    CellLocation coordinate = new CellLocation(i, j);
                    boardWrapper.cellStates[i][j] = "fog";

                    if (game.getCellState(coordinate).hasBeenShot()) {
                        if (game.getCellState(coordinate).hasTank()) {
                            boardWrapper.cellStates[i][j] = "hit";
                        }else {
                            boardWrapper.cellStates[i][j] = "miss";
                        }
                    }
                }
            }
        }
        return boardWrapper;
    }
}
