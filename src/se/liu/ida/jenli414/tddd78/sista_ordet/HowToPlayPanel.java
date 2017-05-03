package se.liu.ida.jenli414.tddd78.sista_ordet;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**Panel that contains the "How To Play" menu.
 * Navigation can be requested to States: RETURN.*/
public class HowToPlayPanel extends JPanel {

    private static final int SMALL_BUTTON_FONT_SIZE = 22;
    private static final int LABEL_FONT_SIZE = 23;

    public HowToPlayPanel(ActionListener navigationListener) {
	super(new MigLayout("", "[1280, center]", "[130][560][170]"));
	//PanelHeader
	add(new PanelHeader("How To Play"), "cell 0 0");
	//HowToPlayLabel
	JLabel howToPlayLabel = new JLabel();
	howToPlayLabel.setFont(new Font("Calibri", Font.PLAIN,
							LABEL_FONT_SIZE));
	StringBuilder howToPlayTextBuilder = new StringBuilder();
	howToPlayTextBuilder.append("<html><center><h1>The rules of ");
	howToPlayTextBuilder.append("<i>The Last Word</i> are simple and");
	howToPlayTextBuilder.append(" as follows:</h1><br></center><ul><li>");
	howToPlayTextBuilder.append("Choose a category in the <i>Settings");
	howToPlayTextBuilder.append("</i> meny.<br>(You can also set number ");
	howToPlayTextBuilder.append("of rounds and difficulty.)</li><br><li>");
	howToPlayTextBuilder.append("The first player enters a word from ");
	howToPlayTextBuilder.append("that category.</li><br><li>The next ");
	howToPlayTextBuilder.append("player must enter a word that ends with");
	howToPlayTextBuilder.append(" the same letter<br>that the current ");
	howToPlayTextBuilder.append("word ends with, within a given time.");
	howToPlayTextBuilder.append("</li><br><li>The round ends when no ");
	howToPlayTextBuilder.append("player was able to come up with a word");
	howToPlayTextBuilder.append("<br>to match the current category and ");
	howToPlayTextBuilder.append("word.</li><br><li>Score is earned for ");
	howToPlayTextBuilder.append("each valid word that is entered, as ");
	howToPlayTextBuilder.append("well as when<br>a player gets <i>The ");
	howToPlayTextBuilder.append("Last Word</i> at the end of each round.");
	howToPlayTextBuilder.append("</li><br><li>When all rounds have been ");
	howToPlayTextBuilder.append("played,<br>the player(s) with the ");
	howToPlayTextBuilder.append("highest score wins!</li></ul></html>");
	String howToPlayText = howToPlayTextBuilder.toString();
	howToPlayLabel.setText(howToPlayText);
	add(howToPlayLabel, "cell 0 1");
	//ReturnButton
	JButton returnButton = new JButton("Return");
	returnButton.setMnemonic('R');
	returnButton.setFont(new Font("Calibri", Font.PLAIN,
						    SMALL_BUTTON_FONT_SIZE));
	returnButton.addActionListener(navigationListener);
	returnButton.setPreferredSize(new Dimension(122, 50));
	add(returnButton, "cell 0 2");
    }
}