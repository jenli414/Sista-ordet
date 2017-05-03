package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;

/**Label that displays errors when a user enters an invalid word.
 * Implements GameListener.**/
public class InputErrorLabel extends JLabel implements GameListener {

    private final Game game;
    private static final int FONT_SIZE = 18;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 30;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public InputErrorLabel(final Game game) {
	this.game = game;
	game.addGameListener(this);
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
	setForeground(Color.RED);
	setHorizontalAlignment(SwingConstants.CENTER);
	updateInputErrorLabel();
    }

     private void updateInputErrorLabel() {
	 if (!game.getInputErrorMessage().isEmpty()) {
	     setText(game.getInputErrorMessage());
	 }
	 else {
	     setText("");
	 }
     }

     public void gameChanged() {
	 updateInputErrorLabel();
     }

    @Override public Dimension getPreferredSize() {
	    return preferredSize;
	 }
}
