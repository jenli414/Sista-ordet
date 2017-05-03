package se.liu.ida.jenli414.tddd78.sista_ordet.newgamepanel;

import net.miginfocom.swing.MigLayout;
import se.liu.ida.jenli414.tddd78.sista_ordet.NavigationListener;
import se.liu.ida.jenli414.tddd78.sista_ordet.PanelHeader;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Game;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**Panel that contains the "New Game" part of the program where
 * player names are entered and the game can be started from.
 * The Settings Menu can also be accessed from here.
 * Navigation can be requested to States:
 * GAME, SETTINGS and RETURN.*/
public final class NewGamePanel extends JPanel {

    private final Game game;
    private final JLabel enterPlayerNamesLabel =
					    new JLabel("Enter Player Names!");
    private final NavigationListener navigationListener;
    private static final int ENTER_NAMES_FONT_SIZE = 25;
    private final JLabel errorTextLabel = new JLabel("");
    private static final int ERROR_TEXT_FONT_SIZE = 20;
    private final List<PlayerNameInput> playerNameInputs = new LinkedList<>();
    private static final int INPUT_LABEL_FONT_SIZE = 28;
    private static final Dimension BIG_BUTTON_DIMENSION =
						    new Dimension(255, 80);
    private static final Dimension SMALL_BUTTON_DIMENSION =
	    						new Dimension(122, 50);
    private static final int BIG_BUTTON_FONT_SIZE = 25;
    private static final int SMALL_BUTTON_FONT_SIZE = 22;

    public NewGamePanel(final Game game,
			final NavigationListener navigationListener) {
	super(new MigLayout("", "[475,right][330,center][75,left]",
		"[80][100][100][100][100][100][70][80][80][50]"));
	this.game = game;
	this.navigationListener = navigationListener;
	//PanelHeader
	add(new PanelHeader("New Game"), "cell 0 0, span 3");
	//EnterPlayerNamesLabel "cell 1 1"
	addEnterPlayerNamesLabel();
	//PlayerNameInputLabels "cell 0 2-5"
	addPlayerNameInputLabels();
	//PlayerNameInputs "cell 1 2-5"
	addPlayerNameInputs();
	//PlayerNameEnterButtons "cell 2 2-5"
	addPlayerNameEnterButtons();
	//PlayerNameEnterButtons "cell 2 2-5"
	addPlayerNameRemoveButtons();
	//ErrorTextLabel "cell 0 6, center, span 3"
        addErrorTextLabel();
	//SettingsButton "cell 1 7"
	addButton("Settings", BIG_BUTTON_FONT_SIZE, BIG_BUTTON_DIMENSION,
					    'S', "Settings", "cell 1 7");
	//StartButton "cell 1 8, gap right 10"
	addButton("Start", SMALL_BUTTON_FONT_SIZE, SMALL_BUTTON_DIMENSION,
				    'T', "Start", "cell 1 8, gap right 10");
	//ReturnButton "cell 1 8"
	addButton("Return", SMALL_BUTTON_FONT_SIZE, SMALL_BUTTON_DIMENSION,
		  				'R', "Return", "cell 1 8");

    }

    private void addEnterPlayerNamesLabel() {
	enterPlayerNamesLabel.setFont(new Font("Calibri", Font.PLAIN,
						    ENTER_NAMES_FONT_SIZE));
	enterPlayerNamesLabel.setHorizontalAlignment(SwingConstants.CENTER);
	add(enterPlayerNamesLabel, "cell 1 1");
    }

    /**Adds 4 labels with format: "Player: x" for 1 <= x <= 5**/
    private void addPlayerNameInputLabels() {
	int row = 2;
	for (int playerNumber = 1; playerNumber < 5; playerNumber++) {
	    StringBuilder playerNameInputLabelString = new StringBuilder();
	    playerNameInputLabelString.append("Player ");
	    playerNameInputLabelString.append(Integer.toString(playerNumber));
	    playerNameInputLabelString.append(": ");
	    final JLabel playerNameInputLabel =
	    new JLabel(playerNameInputLabelString.toString());
	    playerNameInputLabel.setFont(new Font("Calibri", Font.PLAIN,
					      INPUT_LABEL_FONT_SIZE));
	    playerNameInputLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    playerNameInputLabel.setVerticalAlignment(SwingConstants.CENTER);
	    StringBuilder objectConstraints = new StringBuilder();
	    objectConstraints.append("cell 0 ");
	    objectConstraints.append(Integer.toString(row));
	    add(playerNameInputLabel, objectConstraints.toString());
	    row++;
	}
    }

    /**Adds 4 playerNameInput fields**/
    private void addPlayerNameInputs() {
	int row = 2;
	for (int playerNumber = 1; playerNumber < 5; playerNumber++) {
	    StringBuilder objectConstraints = new StringBuilder();
	    objectConstraints.append("cell 1 ");
	    objectConstraints.append(Integer.toString(row));
	    PlayerNameInput playerNameInput = new PlayerNameInput(
						    playerNumber, this, game);
	    playerNameInputs.add(playerNameInput);
	    add(playerNameInput, objectConstraints.toString());
	    row++;
	}
    }

    /**Adds 4 player name enter buttons**/
    private void addPlayerNameEnterButtons() {
	int row = 2;
	for (int i = 0; i < 4; i++) {
	    StringBuilder objectConstraints = new StringBuilder();
	    objectConstraints.append("cell 2 ");
	    objectConstraints.append(Integer.toString(row));
	    objectConstraints.append(", gap right 30, gap left 30");
	    PlayerNameEnterButton playerNameEnterButton =
		    new PlayerNameEnterButton(playerNameInputs.get(i));
	    playerNameEnterButton.addActionListener(playerNameEnterButton);
	    add(playerNameEnterButton, objectConstraints.toString());
	    row++;
	}
    }

    /**Adds 4 player name remove buttons**/
    private void addPlayerNameRemoveButtons() {
	int row = 2;
	for (int i = 0; i < 4; i++) {
	    StringBuilder objectConstraints = new StringBuilder();
	    objectConstraints.append("cell 2 ");
	    objectConstraints.append(Integer.toString(row));
	    PlayerNameRemoveButton playerNameRemoveButton =
		    new PlayerNameRemoveButton(playerNameInputs.get(i), this);
	    playerNameRemoveButton.addActionListener(playerNameRemoveButton);
	    add(playerNameRemoveButton, objectConstraints.toString());
	    row++;
	}
    }

    private void addErrorTextLabel(){
        errorTextLabel.setFont(new Font("Calibri", Font.PLAIN,
						ERROR_TEXT_FONT_SIZE));
        errorTextLabel.setForeground(Color.RED);
        errorTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(errorTextLabel, "cell 0 6, center, span 3");
    }

    private void addButton(String text, int fontSize, Dimension dimension,
		       char mnemonic, String actionCommand, String position) {
	JButton jButton = new JButton(text);
        jButton.setFont(new Font("Calibri", Font.PLAIN, fontSize));
        jButton.setPreferredSize(dimension);
	jButton.setMnemonic(mnemonic);
        jButton.setActionCommand(actionCommand);
        jButton.addActionListener(navigationListener);
        add(jButton, position);
    }


    public void setErrorMessage(String message) {
	errorTextLabel.setText(message);
    }

    public void resetNameInputs() {
	for (PlayerNameInput playerNameInput : playerNameInputs) {
	    playerNameInput.setText(null);
	}
    }
}
