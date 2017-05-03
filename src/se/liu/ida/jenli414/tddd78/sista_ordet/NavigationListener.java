package se.liu.ida.jenli414.tddd78.sista_ordet;

import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Game;
import se.liu.ida.jenli414.tddd78.sista_ordet.newgamepanel.NewGamePanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

/**Window- and ActionListener for all navigation in the program
 * and windowClosing WindowEvent. All navigation buttons in the
 * program add this as their actionListener. This is the first step
 * when a navigation request is made and this is where a user will
 * be asked to confirm if they really want to quit, leave a game,
 * or if they want to save player information. this is also where
 * we will check if a game is ready to start. If everything is ok,
 * setState() in the mainFrame will be called with the new State
 * of the program. (Effectively this step checks that everything
 * user-related about a navigation is ok).**/
public class NavigationListener extends WindowAdapter implements
						ActionListener {

    private final Game game;
    private final MainFrame mainFrame;
    private NewGamePanel newGamePanel = null;
    private Collection<String> playerNamesSave = new ArrayList<>();

    public NavigationListener(final MainFrame mainFrame, final Game game) {
	this.mainFrame = mainFrame;
	this.game = game;
    }

    public void setNewGamePanel() {
	this.newGamePanel = mainFrame.getNewGamePanel();
    }

    @Override public void actionPerformed(final ActionEvent e) {
	/*newGamePanel should be set right after being created in MainFrame
	this cannot be done before that because both object are dependant on
	each other. One of them simply has to update.*/
	assert(newGamePanel != null);
	String command = e.getActionCommand();
	switch (command) {
	    case "New Game":
		newGameRequest();
		break;
	    case "Settings":
		settingsRequest();
		break;
	    case "Difficulty Info":
		JOptionPane.showMessageDialog(null,
		  "Increasing the game's difficulty decreases the time \n " +
		    " you have to come up with a valid word.", "Difficulty",
					  JOptionPane.INFORMATION_MESSAGE);
		break;
	    case "How To Play":
		howToPlayRequest();
		break;
	    case "Start":
		startRequest();
		break;
	    case "Return":
		returnRequest();
		break;
	    case "Main Menu":
		mainMenuRequest();
		break;
	    case "Quit":
		if (confirmQuit()) {
		    System.exit(0);
		}
		break;
	}

    }

    @Override public void windowClosing(final WindowEvent e) {
	if (confirmQuit()) {
	    System.exit(0);
	}
    }

    private void newGameRequest() {
	State currentState = mainFrame.getCurrentState();
	if (currentState == State.GAME) {
	    if (confirmLeave()) {
		keepOrResetPlayerInfo();
		mainFrame.setState(State.NEWGAME);
	    }
	} else {
	    mainFrame.setState(State.CURRNEWGAME);
	}
    }

    private void settingsRequest() {
	mainFrame.setState(State.SETTINGS);
    }

    private void howToPlayRequest() {
	mainFrame.setState(State.HOWTOPLAY);
    }

    private void startRequest() {
	if (game.hasEnoughPlayers()) {
	    mainFrame.setState(State.GAME);
	} else {
	    newGamePanel.setErrorMessage(
		    "Not enough players! Please enter at least 2.");
	}
    }

    private void mainMenuRequest() {
	if (mainFrame.getCurrentState() == State.GAME) {
	    if (confirmLeave()) {
		keepOrResetPlayerInfo();
		mainFrame.setState(State.MENU);
	    }
	} else {
	    mainFrame.setState(State.MENU);
	}
    }

    private void returnRequest() {
	mainFrame.setState(State.RETURN);
    }

    private boolean confirmLeave() {
	return JOptionPane.showConfirmDialog(null,
		     "You can't go back to this game if you leave it. \n" +
		    	"Do you really want to leave this game?", "Confirm",
			 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private boolean confirmQuit() {
	return JOptionPane.showConfirmDialog(null,
		    	"Do you really want to quit playing?", "",
			JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void keepOrResetPlayerInfo() {
	if (JOptionPane.showConfirmDialog(null, "Keep player information?",
					  "", JOptionPane.YES_NO_OPTION) ==
    						JOptionPane.YES_OPTION) {
	    playerNamesSave.clear();
	    for (String playerName : game.getPlayerNames()) {
		playerNamesSave.add(playerName);
	    }
	    game.resetGame();
	    game.setPlayerNames(playerNamesSave);
	} else {
	    game.resetGame();
	    newGamePanel.resetNameInputs();
	}
    }
}
