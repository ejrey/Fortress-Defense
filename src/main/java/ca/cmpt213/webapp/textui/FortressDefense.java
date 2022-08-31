package ca.cmpt213.webapp.textui;

import ca.cmpt213.webapp.model.Game;
import ca.cmpt213.webapp.model.Tank;

/**
 * Launch the Fortress Defense game (text-mode).
 */
public class FortressDefense {
    private static final int DEFAULT_NUMBER_TANKS = 5;

    public static void main(String[] args) {
        // Determine # Tanks in Game
        int numTanks;
        if (args.length == 0) {
            numTanks = DEFAULT_NUMBER_TANKS;
        } else {
            numTanks = Integer.parseInt(args[0]);
        }

        // Check if cheating:
        boolean cheating = false;
        if (args.length >= 2) {
            if (args[1].trim().compareToIgnoreCase("--cheat") == 0) {
                cheating = true;
            }
        }

        // Play game
        try {
            Game game = new Game(numTanks);
            TextUI ui = new TextUI(game);

            if (cheating) {
                ui.displayBoard(true);
                System.out.println();
                System.out.println();
            }

            System.out.println("Starting game with " + numTanks + " tanks.");
            ui.playGame();
        } catch (Tank.UnableToCreateTankException e) {
            System.out.println("Error: Unable to place " + numTanks + " on the board.");
            System.out.println("       Try running game again with fewer tanks.");
        }
    }
}
