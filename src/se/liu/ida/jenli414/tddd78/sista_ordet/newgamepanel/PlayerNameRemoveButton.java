package se.liu.ida.jenli414.tddd78.sista_ordet.newgamepanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**JButton that resets a playerName in Game as well as clears its
 * corresponding PlayerNameInput in NewGamePanel.*/
/*Has its own class because every button needs to know which
  PlayerNameInput it belongs to.*/
public class PlayerNameRemoveButton extends JButton implements ActionListener {

    private final PlayerNameInput playerNameInput;
    private final NewGamePanel newGamePanel;
    private final static int FONT_SIZE = 20;
    private final static int WIDTH = 110;
    private final static int HEIGHT = 40;

    public PlayerNameRemoveButton(final PlayerNameInput playerNameInput,
				  	final NewGamePanel newGamePanel) {
	super("Remove");
	this.newGamePanel = newGamePanel;
	this.playerNameInput = playerNameInput;
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        playerNameInput.clearPlayerName();
	newGamePanel.setErrorMessage(null);
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(WIDTH,HEIGHT);
    }
}
