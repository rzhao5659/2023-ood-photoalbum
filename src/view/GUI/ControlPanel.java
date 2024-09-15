package view.GUI;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The control panel contain all the buttons in GUI.
 */
public class ControlPanel extends JPanel {
  private JButton prevButton;
  private JButton browseButton;
  private JButton nextButton;

  /**
   * Constructor.
   */
  public ControlPanel() {
    //Layout
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    setBackground(Color.decode(CustomColor.DARK_GRAY));
    setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.DARK_GRAY));
    //GUI components
    prevButton = new JButton("<<");
    browseButton = new JButton("Browse...");
    nextButton = new JButton(">>");
    prevButton.setBackground(Color.decode(CustomColor.PINK));
    prevButton.setFocusPainted(false);
    browseButton.setBackground(Color.decode(CustomColor.PINK));
    browseButton.setFocusPainted(false);
    nextButton.setBackground(Color.decode(CustomColor.PINK));
    nextButton.setFocusPainted(false);
    add(Box.createHorizontalGlue());
    add(prevButton);
    add(browseButton);
    add(nextButton);
    add(Box.createHorizontalGlue());
  }

  /**
   * Returns the browse button.
   *
   * @return .
   */
  public JButton getBrowseButton() {
    return browseButton;
  }

  /**
   * Returns the next button.
   * @return .
   */
  public JButton getNextButton() {
    return nextButton;
  }

  /**
   * Returns the previous button.
   * @return .
   */
  public JButton getPrevButton() {
    return prevButton;
  }
}