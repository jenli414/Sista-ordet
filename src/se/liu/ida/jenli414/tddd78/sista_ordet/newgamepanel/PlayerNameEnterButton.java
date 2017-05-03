package se.liu.ida.jenli414.tddd78.sista_ordet.newgamepanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**JButton that sets the player name entered by the user inside its
 * corresponding text field to playerNames (ArrayList<String>) in Game.*/
/*Has its own class because every button needs to know which
  PlayerNameInput it belongs to.*/
public class PlayerNameEnterButton extends JButton implements ActionListener {

    private final PlayerNameInput playerNameInput;
    private final static int FONT_SIZE = 20;
    private final static int WIDTH = 110;
    private final static int HEIGHT = 40;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public PlayerNameEnterButton(final PlayerNameInput playerNameInput) {
	super("Enter");
	this.playerNameInput = playerNameInput;
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        playerNameInput.setPlayerName();
    }

    @Override public Dimension getPreferredSize() {
	return preferredSize;
    }
}
