package se.liu.ida.jenli414.tddd78.sista_ordet.gamepanel;

import javax.swing.*;
import java.awt.*;

/**Component that displays how much time is left in the turn.
 * (graphically as a bar.) Implements GameListener.**/
public class TimeLeftComponent extends JComponent implements GameListener {

    private final Game game;
    private final static int FROM_X = 0;
    private final static int FROM_Y = 0;
    private final static int BACKGROUND_RECTANGLE_WIDTH = 500;
    private float timeLeftRectangleWidth;
    private final static int HEIGHT = 80;
    private final static int ARC_WIDTH = 5;
    private final static int ARC_HEIGHT = 5;
    private final static int DIM_WIDTH = 500;
    private final static int DIM_HEIGHT = 80;
    private final Dimension preferredSize = new Dimension(DIM_WIDTH,
							  DIM_HEIGHT);

    public TimeLeftComponent(final Game game) {
	this.game = game;
	game.addGameListener(this);
	timeLeftRectangleWidth = BACKGROUND_RECTANGLE_WIDTH;
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setColor(Color.RED);
	g2d.fillRoundRect(FROM_X, FROM_Y, BACKGROUND_RECTANGLE_WIDTH, HEIGHT,
			  ARC_WIDTH, ARC_HEIGHT);
	g2d.drawRoundRect(FROM_X, FROM_Y, BACKGROUND_RECTANGLE_WIDTH, HEIGHT,
			  ARC_WIDTH, ARC_HEIGHT);
	g2d.setColor(Color.GREEN);
	g2d.fillRoundRect(FROM_X, FROM_Y, Math.round(timeLeftRectangleWidth),
			  HEIGHT, ARC_WIDTH, ARC_HEIGHT);
	g2d.drawRoundRect(FROM_X, FROM_Y, Math.round(timeLeftRectangleWidth),
			  HEIGHT, ARC_WIDTH, ARC_HEIGHT);
    }

    private void updateTimeLeftComponent() {
	float timeLeft = game.getTimeLeft();
	int turnTime = game.getTurnTime();
	timeLeftRectangleWidth =
		(BACKGROUND_RECTANGLE_WIDTH * timeLeft) / turnTime;
	if (timeLeftRectangleWidth >= 0) {
	    repaint();
	} else {
	    timeLeftRectangleWidth = 0;
	    repaint();
	}
    }

    @Override public void gameChanged() {
	updateTimeLeftComponent();
    }

    @Override public Dimension getPreferredSize() {
	return preferredSize;
    }
}
