package se.liu.ida.jenli414.tddd78.sista_ordet;

/**Enum class for the different states the program can be in.
 * Used by MainFrame to determine what panel to display.
 * RETURN and CURRNEWGAME are the only ones that might not be
 * entirely self-explainatory. If you think of the program as
 * built up by levels, RETURN means that there has been a
 * request to go down a level. The levels (from lowest to highest)
 * are: MENU -> NEWGAME -> SETTINGS, only if you went directly
 * to SETTINGS from MENU, RETURN will take you back to MENU.
 * CURRNEWGAME simply means that the user will be taken to
 * newGamePanel but the information stored there will not be
 * reset as opposed to the case with NEWGAME.*/
public enum State {

    /**Enum for the Menu State**/
    MENU(),
    /**Enum for the NewGame State**/
    NEWGAME(),
    /**Enum for the CurrNewGame State**/
    CURRNEWGAME(),
    /**Enum for the Settings State**/
    SETTINGS(),
    /**Enum for the HowToPlay State**/
    HOWTOPLAY(),
    /**Enum for the Game State**/
    GAME(),
    /**Enum for the Return State**/
    RETURN();

    State() {
    }
}
