package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;

/**Label that displays the current word in gamePanel.
 * Implements GameListener**/
public class CurrentWordLabel extends JLabel implements GameListener
{
    private final Game game;
    private static final int FONT_SIZE = 33;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 100;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public CurrentWordLabel(final Game game) {
	this.game = game;
	game.addGameListener(this);
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
	setHorizontalAlignment(SwingConstants.CENTER);
	updateCurrentWord();
    }

    private void updateCurrentWord() {
	StringBuilder currentWordLabelBuilder = new StringBuilder();
	currentWordLabelBuilder.append("Current Word: ");
	currentWordLabelBuilder.append(game.getCurrentWord());
	String currentWordLabelText = currentWordLabelBuilder.toString();
	setText(currentWordLabelText);
    }

    public void gameChanged() {
	updateCurrentWord();
    }

    @Override public Dimension getPreferredSize() {
    	return preferredSize;
        }
}
