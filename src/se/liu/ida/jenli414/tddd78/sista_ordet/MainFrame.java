package se.liu.ida.jenli414.tddd78.sista_ordet;

import net.miginfocom.swing.MigLayout;
import se.liu.ida.jenli414.tddd78.sista_ordet.newgamepanel.NewGamePanel;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Game;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import javax.swing.UIManager.*;

/**JFrame with a JMenu that will create one of each panel and then show one
 * of them at a time. The "Nimbus" Look-And-Feel is also set here.
 * This is the second and final step in the navigation process.
 * setState() will take a State newState from NavigationListener and analyze
 * it. If newState is RETURN, currentState is set depending on previousState.
 * Otherwise it will set currentState to previousState and currentState
 * to newState and call updatePanel(). In updatePanel(), the old panel is
 * removed and the new panel is added.*/
public final class MainFrame extends JFrame {

    private static final ErrorLogger ERROR_LOGGER = ErrorLogger.getInstance();
    private final Game game = new Game();
    private final GamePanel gamePanel;
    private final MenuPanel menuPanel;
    private final JMenu optionsMenu;
    private final HowToPlayPanel howToPlayPanel;
    private final SettingsPanel settingsPanel;
    private final NewGamePanel newGamePanel;
    private JPanel currentPanel;
    private final NavigationListener navigationListener;
    private final static Color BACKGROUND_COLOR = new Color(214, 217, 223);
    private State currentState;
    private State previousState = State.MENU;

    public MainFrame() {
	super("The Last Word");
	this.currentState = State.MENU;
	try {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException | InstantiationException |
		IllegalAccessException | UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	    ERROR_LOGGER.log(Level.WARNING, e.toString(), e);
	    JOptionPane.showMessageDialog(null, new JLabel(
	    	"<html><center>Error!<br>IOException occurred in " +
	       	"MainFrame while setting \"Nimbus\" Look-And-Feel." +
						"</center></html>"));
	}
	getContentPane().setBackground(BACKGROUND_COLOR);
	//NavigationListener
	navigationListener = new NavigationListener(this, game);
	//Panels
	menuPanel = new MenuPanel(navigationListener);
	newGamePanel = new NewGamePanel(game, navigationListener);
	navigationListener.setNewGamePanel();
	howToPlayPanel = new HowToPlayPanel(navigationListener);
      	settingsPanel = new SettingsPanel(game, navigationListener);
	gamePanel = new GamePanel(this, game);
	addWindowListener(navigationListener);
	//Menu bar and items
	final JMenuBar menuBar = new JMenuBar();
	optionsMenu = new JMenu("Options");
	optionsMenu.setMnemonic('O');
	addOptionsMenuItem("New Game", 'N', "ctrl N", "New Game");
	addOptionsMenuItem("Main Menu", 'M', "ctrl M", "Main Menu");
	addOptionsMenuItem("Quit", 'Q', "ctrl Q", "Quit");
	menuBar.add(Box.createHorizontalGlue());
	menuBar.add(optionsMenu);
	setJMenuBar(menuBar);
	//Layout
	setLayout(new MigLayout("","[1280, center]", "[860]"));
	//
	add(menuPanel, "cell 0 0");
	currentPanel = menuPanel;
	pack();
	setVisible(true);
    }

    private void addOptionsMenuItem(String text, char mnemonic,
				    String keyStroke, String actionCommand) {
	final JMenuItem jMenuItem = new JMenuItem(text, mnemonic);
	jMenuItem.setAccelerator(KeyStroke.getKeyStroke(keyStroke));
	jMenuItem.addActionListener(navigationListener);
	jMenuItem.setActionCommand(actionCommand);
	optionsMenu.add(jMenuItem);
    }

    /**Updates currentPanel to match currentState**/
    private void updateCurrentPanel() {
	assert(currentState != State.RETURN);
	remove(currentPanel);
	switch (currentState) {
	    case MENU: currentPanel = menuPanel; break;
	    case NEWGAME:
	    case CURRNEWGAME:
		currentPanel = newGamePanel;
		break;
	    case HOWTOPLAY: currentPanel = howToPlayPanel; break;
	    case SETTINGS: currentPanel = settingsPanel; break;
	    case GAME: currentPanel = gamePanel; break;
	    case RETURN:
		IllegalStateException e =
			new IllegalStateException("Illegal state occurred");
		e.printStackTrace();
		ERROR_LOGGER.log(Level.WARNING, e.toString(), e);
		JOptionPane.showMessageDialog(
			    null, new JLabel("<html><center>Error!<br>"
			   + "IllegalStateException occured in SoundPlayer." +
			    		"</center></html>"));
		System.exit(1);
		break;
	}
	add(currentPanel, "cell 0 0");
	currentPanel.repaint();
	pack();
    }

    /**Takes a new State request from NavigationListener and evaluates what
     * do according to what currentState and previousState is.
     * Sets everything up for updatePanel() to make the actual panel change.
     * (Effectively takes care of necessary set up of panels/game before
     * the actual panel change can happen.)**/
    public void setState(State newState) {
	assert (previousState != State.RETURN);
	if (currentState == State.NEWGAME ||
	    currentState == State.CURRNEWGAME) {
	    newGamePanel.setErrorMessage(null);
	}
	if (currentState == State.GAME) {
	    game.stopGame();
	}
	if (newState == State.RETURN) {
	    switch (previousState) {
		case NEWGAME:
		case CURRNEWGAME:
		    currentState = State.CURRNEWGAME;
		    break;
		case SETTINGS:
		case MENU:
		case GAME:
		case HOWTOPLAY:
		    currentState = State.MENU;
		    break;
		case RETURN:
		    IllegalStateException e =
		    new IllegalStateException("Illegal state occurred");
		    e.printStackTrace();
		    ERROR_LOGGER.log(Level.WARNING, e.toString(), e);
		    JOptionPane.showMessageDialog(
			null, new JLabel("<html><center>Error!<br>"
			   + "IllegalStateException occured in SoundPlayer." +
					"</center></html>"));
		    System.exit(1);
		    break;
	    }
	    previousState = State.MENU;
	} else {
	    previousState = currentState;
	    currentState = newState;
	}
	if (currentState == State.GAME) {
	    game.startGame();
	    gamePanel.adjustToCurrentGame();
	}
	updateCurrentPanel();
    }

    public NewGamePanel getNewGamePanel() {
	return newGamePanel;
    }

    public State getCurrentState() {
	return currentState;
    }
}
