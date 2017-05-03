package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**Label that displays which player got the last "Last Word".
 * Implements GameListener.**/
public class LastWordLabel extends JLabel implements GameListener {

    private final List<Player> players;
    private static final int FONT_SIZE = 23;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 70;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public LastWordLabel(final Game game) {
	players = game.getPlayers();
	game.addGameListener(this);
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
	setForeground(Color.BLACK);
	setHorizontalAlignment(SwingConstants.CENTER);
	setVerticalAlignment(SwingConstants.BOTTOM);
	updateLastWordLabel();
    }

    private void updateLastWordLabel() {
	boolean foundPlayer = false;
	StringBuilder lastWordLabelString = new StringBuilder();
	    for (Player player : players) {
		if (player.hasLastWord()) {
		    lastWordLabelString.append("<html><b>");
		    lastWordLabelString.append(player.getName());
		    lastWordLabelString.append(
		    "</b> got last round's Last Word!</html>");
		    setText(lastWordLabelString.toString());
		    foundPlayer = true;
		    break;
		}
	    }
	if (!foundPlayer) {
	    setText("");
	}
     }

     public void gameChanged() {
	 updateLastWordLabel();
     }

    @Override public Dimension getPreferredSize() {
	    return preferredSize;
	 }
}