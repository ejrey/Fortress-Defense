package ca.cmpt213.webapp.apiwrapper;

/**
 * ApiLocationWrapper to set a move on the board given the game and move by the user.
 * This class is used as an object when receiving a move from the user
 */

/**
 * Wrapper class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiLocationWrapper {
    public int row;
    public int col;
}
