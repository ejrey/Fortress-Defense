package ca.cmpt213.webapp.controller;

/**
 * The controller of the REST API to be able to launch the tank game on
 * localhost:8080
 */

import ca.cmpt213.webapp.apiwrapper.ApiBoardWrapper;
import ca.cmpt213.webapp.apiwrapper.ApiGameWrapper;
import ca.cmpt213.webapp.apiwrapper.ApiLocationWrapper;
import ca.cmpt213.webapp.model.CellLocation;
import ca.cmpt213.webapp.model.Game;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FortressController {
    private static final String NAME = "Eric Reyes";
    private static final String acceptedCheatMsg = "SHOW_ALL";
    private static final int NUMBER_OF_TANKS = 5;
    private List<ApiGameWrapper> gamesApiList = new ArrayList<>();
    private boolean cheatState = false;

    //1.General
    @GetMapping("/api/about")
    public String getName() {
        return NAME;
    }

    //2.Games
    @GetMapping("/api/games")
    public List<ApiGameWrapper> getAllGames() {
        return gamesApiList;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createGame() {
        cheatState = false;
        ApiGameWrapper gameWrapper = ApiGameWrapper.createNewGame(gamesApiList.size(), NUMBER_OF_TANKS);
        gamesApiList.add(gameWrapper);
        return gameWrapper;
    }

    @GetMapping("/api/games/{id}")
    public ApiGameWrapper getSelectedGame(@PathVariable("id") int gameId) {
        for (ApiGameWrapper game : gamesApiList) {
            if (game.gameNumber == gameId) {
                return game;
            }
        }
        throw new IllegalArgumentException();
    }

    //3. Board
    @GetMapping("/api/games/{id}/board")
    public ApiBoardWrapper getCurrentGameBoard(@PathVariable("id") int gameId) {
        ApiBoardWrapper boardWrapper;
        try {
            if (cheatState) {
                boardWrapper = ApiBoardWrapper.getGameBoard(gamesApiList.get(gameId).game, true);
            }else {
                boardWrapper = ApiBoardWrapper.getGameBoard(gamesApiList.get(gameId).game, false);
            }
        }catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
        return boardWrapper;
    }

    //4. Moves
    @PostMapping("/api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void userMoveOnBoard(@PathVariable("id") int gameId, @RequestBody ApiLocationWrapper userMove) {

        Game selectedGame;
        try {
            selectedGame = gamesApiList.get(gameId).game;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }

        if (userMove.row < 0 || userMove.row > 9 || userMove.col < 0 || userMove.col > 9) {
            throw new InvalidParameterException();
        }

        int row = userMove.row;
        int col = userMove.col;

        CellLocation chosenSpot = new CellLocation(row, col);
        selectedGame.recordPlayerShot(chosenSpot);

        selectedGame.fireTanks();
        gamesApiList.get(gameId).fortressHealth = selectedGame.getFortressHealth();
        gamesApiList.get(gameId).numTanksAlive = selectedGame.getLatestTankDamages().length;
        gamesApiList.get(gameId).lastTankDamages = selectedGame.getLatestTankDamages();
        gamesApiList.get(gameId).isGameWon = selectedGame.hasUserWon();
        gamesApiList.get(gameId).isGameLost = selectedGame.hasUserLost();
    }

    //5. Cheats
    @PostMapping("/api/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void activateCheatState(@PathVariable("id") int gameId, @RequestBody String cheatMsg) {
        Game selectedGame;

        try {
            selectedGame = gamesApiList.get(gameId).game;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
        if (!cheatMsg.equals(acceptedCheatMsg)) {
            throw new InvalidParameterException();
        }
        cheatState = true;
    }

    // Exception handling
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File Not Found")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
        // Nothing
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Body Input")
    @ExceptionHandler(InvalidParameterException.class)
    public void badRequestLocationExceptionHandler() {
        // Nothing
    }

}
