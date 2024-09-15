package view.GUI;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Info Panel contain all the labels about the snapshot.
 */
public class InfoPanel extends JPanel {
  private JLabel snapshotIDLabel;
  private JLabel snapshotDescriptionLabel;

  /**
   * Constructor.
   */
  public InfoPanel() {
    //Layout
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    setBackground(Color.decode(CustomColor.PINK));
    setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY));
    //GUI components
    snapshotIDLabel = new JLabel("(Snapshot ID)");
    snapshotDescriptionLabel = new JLabel("(Snapshot Description)");
    add(snapshotIDLabel);
    add(Box.createRigidArea(new Dimension(10, 0)));
    add(snapshotDescriptionLabel);
    add(Box.createHorizontalGlue());
  }

  /**
   * Returns the currently displayed snapshot ID.
   *
   * @return .
   */
  public String getSnapshotID() {
    return this.snapshotIDLabel.getText();
  }

  /**
   * Returns the currently displayed snapshot description.
   *
   * @return .
   */
  public String getSnapshotDescription() {
    return this.snapshotDescriptionLabel.getText();
  }

  /**
   * Sets the currently displayed snapshot ID to another string
   *
   * @param id a string value that should represent a snapshot ID
   */
  public void setSnapshotID(String id) {
    this.snapshotIDLabel.setText(id);
  }

  /**
   * Sets the currently displayed snapshot description to another string
   *
   * @param description a string value that should represent a snapshot description
   */
  public void setSnapshotDescription(String description) {
    this.snapshotDescriptionLabel.setText(description);
  }

}
