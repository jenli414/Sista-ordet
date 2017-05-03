package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;

/**Label that displays how much time is left in the turn.
 * (As an integer.) Implements GameListener.**/
public class TimeLeftLabel extends JLabel implements GameListener {

    private final Game game;
    private static final int FONT_SIZE = 40;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 80;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public TimeLeftLabel(final Game game) {
	this.game = game;
	game.addGameListener(this);
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
	setHorizontalAlignment(SwingConstants.CENTER);
	updateTimeLeft();
    }

     private void updateTimeLeft() {
	 int timeLeft = Math.round(game.getTimeLeft());
	 String timeLeftString = Integer.toString(timeLeft);
	 if (timeLeft < 6) {
	     setForeground(Color.RED);
	 } else {
	     setForeground(Color.BLACK);
	 }
	 setText(timeLeftString);
     }

     public void gameChanged() {
    updateTimeLeft();
     }

    @Override public Dimension getPreferredSize() {
	    return preferredSize;
	 }
}
