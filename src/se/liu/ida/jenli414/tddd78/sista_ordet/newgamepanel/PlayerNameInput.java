package se.liu.ida.jenli414.tddd78.sista_ordet.newgamepanel;

import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Game;
import se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**Extends JFormattedTextField and is where the user can enter a player name
 * that will be set in playerNames (ArrayList<String>) in Game.
 * Implements both ActionListener and FocusListener because the style
 * of the text depends on whether the object has focus and/or if the text
 * currently entered corresponds to the name set in game.**/
public class PlayerNameInput extends JFormattedTextField implements
					ActionListener, FocusListener {

    private final Game game;
    private final int playerNumber;
    private final NewGamePanel newGamePanel;
    private static final int NAME_LENGTH_LIMIT = Player.getNameLengthLimit();
    private final static int FONT_SIZE = 25;
    private final static int WIDTH = 315;
    private final static int HEIGHT = 60;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public PlayerNameInput(final int playerNumber,
			    final NewGamePanel newGamePanel, final Game game) {
	this.playerNumber = playerNumber;
	this.newGamePanel = newGamePanel;
	this.game = game;
	addActionListener(this);
 	addFocusListener(this);
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
	setHorizontalAlignment(SwingConstants.CENTER);

	addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
	        if (getText().length() >= NAME_LENGTH_LIMIT)
	            e.consume();
	    }
	});
    }

    public void setPlayerName() {
	if (!getText().isEmpty()) {
	    StringBuilder playerNameCapitalizedBuilder = new StringBuilder();
	    playerNameCapitalizedBuilder.append(
				    getText().substring(0, 1).toUpperCase());
	    playerNameCapitalizedBuilder.append(
					getText().substring(1).toLowerCase());
	    String playerNameCapitalized =
				    playerNameCapitalizedBuilder.toString();
	    game.setPlayerName(playerNumber, playerNameCapitalized);
	    setText(playerNameCapitalized);
	    setForeground(Color.GRAY);
	    newGamePanel.setErrorMessage(null);
	} else {
	    clearPlayerName();
	}
    }

    public void clearPlayerName() {
	game.setPlayerName(playerNumber, "");
	setText(null);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        setPlayerName();
    }

    @Override public void focusGained(final FocusEvent e) {
	setForeground(Color.BLACK);
    }

    @Override public void focusLost(final FocusEvent e) {
	String nameSetInGame = game.getPlayerNames().get(playerNumber - 1);
	if (!nameSetInGame.equals(getText())) {
	    setForeground(Color.RED);
	} else {
	    setForeground(Color.GRAY);
	}
    }

    @Override
    public Dimension getPreferredSize() {
	return preferredSize;
    }
}
