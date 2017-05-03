package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;

/**Label that displays what round it is and out of how many,
 * as well as what turn it is. Implements GameListener.**/
public class RoundAndTurnLabel extends JLabel implements GameListener {

    private final Game game;
    private static final int FONT_SIZE = 25;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 100;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public RoundAndTurnLabel(final Game game) {
	this.game = game;
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
	setHorizontalAlignment(SwingConstants.CENTER);
	game.addGameListener(this);
    }

    private void updateRoundAndTurnLabel() {
	StringBuilder roundAndTurnLabelBuilder = new StringBuilder();
	roundAndTurnLabelBuilder.append("<html><center>Round: ");
	roundAndTurnLabelBuilder.append(game.getRoundString());
	roundAndTurnLabelBuilder.append(" out of ");
	roundAndTurnLabelBuilder.append(game.getTotalRoundsString());
	roundAndTurnLabelBuilder.append("<br>");
	if (game.getTurnString().equals("0")) {
	    roundAndTurnLabelBuilder.append("<b>");
	    roundAndTurnLabelBuilder.append(game.getCurrentPlayer().getName());
	    roundAndTurnLabelBuilder.append("</b>, choose a word!");
	} else {
	    roundAndTurnLabelBuilder.append("Turn: ");
	    roundAndTurnLabelBuilder.append(game.getTurnString());
	}
	roundAndTurnLabelBuilder.append("</center></html>");
	String roundAndTurnLabelText = roundAndTurnLabelBuilder.toString();
	setText(roundAndTurnLabelText);
    }

    @Override public void gameChanged() {
	updateRoundAndTurnLabel();
    }

    @Override public Dimension getPreferredSize() {
    	return preferredSize;
        }
}
