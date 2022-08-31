package ca.cmpt213.webapp.apiwrapper;

/**
 * ApiGameWrapper class to instantiate a new game
 */

import ca.cmpt213.webapp.model.Game;

/**
 * Wrapper class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int fortressHealth;
    public int numTanksAlive;
    public Game game;

    // Amount of damage that the tanks did on the last time they fired.
    // If tanks have not yet fired, then it should be an empty array (0 size).
    public int[] lastTankDamages;

    public static ApiGameWrapper createNewGame(int gameID, int amountOfTanks) {
        ApiGameWrapper newGame = new ApiGameWrapper();

        Game game = new Game(amountOfTanks);
        newGame.game = game;
        newGame.gameNumber = gameID;
        newGame.isGameWon = game.hasUserWon();
        newGame.isGameLost = game.hasUserLost();
        newGame.fortressHealth = game.getFortressHealth();
        newGame.numTanksAlive = game.getTanksAlive();
        newGame.lastTankDamages = game.getLatestTankDamages();

        return newGame;
    }

}
