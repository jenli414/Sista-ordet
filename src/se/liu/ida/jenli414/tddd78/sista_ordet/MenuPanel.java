package se.liu.ida.jenli414.tddd78.sista_ordet;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.logging.Level;

/**Panel that contains the main menu of the program.
 * Navigation can be requested to States:
 * CURRNEWGAME, SETTINGS, HOWTOPLAY and QUIT.*/
public class MenuPanel extends JPanel {

    private static final ErrorLogger ERROR_LOGGER = ErrorLogger.getInstance();
    private final ClassLoader cl = getClass().getClassLoader();
    private final ActionListener navigationListener;
    private static final Dimension BUTTON_DIMENSION = new Dimension(250, 85);
    private static final int IMAGE_LABEL_SIZE = 430;
    private static final int BUTTON_FONT_SIZE = 25;

    public MenuPanel(final ActionListener navigationListener) {
	super(new MigLayout("", "[1280, center]",
			    "[430][100][100][100][100][30]"));
	this.navigationListener = navigationListener;
	JLabel menuLogo = new JLabel();
	String imgPath = "images/lastword.png";
	if (cl.getResource(imgPath) == null) {
	    FileNotFoundException e = new FileNotFoundException(
		    			"Couldn't find file: " + imgPath);
	    e.printStackTrace();
	    ERROR_LOGGER.log(Level.SEVERE, e.toString(), e);
	    JOptionPane.showMessageDialog(null, new JLabel(
		    "<html><center>Error!<br>Couldn't find file: "
		    + imgPath + "</center></html>"));
	    System.exit(1);
	} else {
	    /*2016-08-28 v2
	    Because URL location for ImageIcon is @NotNull while
	    getResource() could potentially return null, IntelliJ
	    is warning us about this. However we have made sure
	    that if the URL is null, that error is handled appropriately.*/
	    Icon scaledImg = new ImageIcon(new ImageIcon(
		    cl.getResource(imgPath)).getImage().getScaledInstance(
		    IMAGE_LABEL_SIZE, IMAGE_LABEL_SIZE, Image.SCALE_SMOOTH));
	    menuLogo.setIcon(scaledImg);
	}
	add(menuLogo, "cell 0 0, gap bottom 25");
	addButton("New Game", 'N', "cell 0 1, gap bottom 25");
	addButton("Settings", 'S', "cell 0 2, gap bottom 25");
	addButton("How To Play", 'H', "cell 0 3, gap bottom 25");
	addButton("Quit", 'Q', "cell 0 4");
    }

    private void addButton(String title, char mnemonic, String position ) {
	JButton button = new JButton(title);
	button.setFont(new Font("Calibri", Font.PLAIN, BUTTON_FONT_SIZE));
	button.setPreferredSize(BUTTON_DIMENSION);
	button.setMnemonic(mnemonic);
	button.addActionListener(navigationListener);
	add(button, position);
    }
}
