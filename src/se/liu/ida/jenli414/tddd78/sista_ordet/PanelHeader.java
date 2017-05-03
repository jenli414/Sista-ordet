package se.liu.ida.jenli414.tddd78.sista_ordet;

import javax.swing.*;
import java.awt.*;

/**Header that displays which menu the user is currently in.
 * extends JLabel.**/
public class PanelHeader extends JLabel {

    private final static int FONT_SIZE = 35;
    private final static int WIDTH = 1280;
    private final static int HEIGHT = 200;
    private final Dimension preferredSize = new Dimension(WIDTH, HEIGHT);

    public PanelHeader(String panelName) {
	super(panelName);
	setFont(new Font("Calibri", Font.PLAIN, FONT_SIZE));
	setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override public Dimension getPreferredSize() {
    	return preferredSize;
        }

}