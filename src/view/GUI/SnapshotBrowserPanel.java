package view.GUI;

import controller.ICallbacks;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

/**
 * A panel that displays the list of all snapshots by their IDs, and allows the user to select a snapshot
 * to display.
 */
public class SnapshotBrowserPanel extends JPanel {
  private JList<String> snapshotList;
  private DefaultListModel<String> snapshotsListModel;
  private final static int LIST_MAX_WIDTH = 1000;

  /**
   * Constructor.
   */
  public SnapshotBrowserPanel() {
    //Layout
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setBackground(Color.decode(CustomColor.DARK_GRAY));
    this.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK),
        BorderFactory.createEmptyBorder(10,10,10,10)
    ));

    //GUI components
    JLabel label1 = new JLabel("Snapshots");
    label1.setForeground(Color.WHITE);
    label1.setAlignmentX(LEFT_ALIGNMENT);
    this.add(label1);

    this.add(Box.createRigidArea(new Dimension(0,10)));

    snapshotsListModel = new DefaultListModel<>();
    snapshotList = new JList<>(snapshotsListModel);
    snapshotList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    snapshotList.setSelectedIndex(0);
    snapshotList.setBackground(Color.decode(CustomColor.GRAY));
    snapshotList.setSelectionBackground(Color.decode(CustomColor.PINK));
    snapshotList.setLayoutOrientation(JList.VERTICAL);
    snapshotList.setBorder(BorderFactory.createLineBorder(Color.black));
    snapshotList.setAlignmentX(LEFT_ALIGNMENT);
    snapshotList.setMaximumSize(new Dimension(LIST_MAX_WIDTH, Integer.MAX_VALUE));
    this.add(snapshotList);
  }

  /**
   * Get the JList component.
   */
  public JList<String> getList() {
    return snapshotList;
  }
  
  /**
   * Set new values to display on the JList component.
   *
   * @param data values
   */
  public void setListData(List<String> data) {
    snapshotsListModel.clear();
    snapshotsListModel.addAll(data);
    snapshotList.setSelectedIndex(0);
  }

  /**
   * Callback function for selecting different value in JList.
   * @param e event
   * @param cbContainer callback function container
   */
  public void listValueChangedCallback(ListSelectionEvent e, ICallbacks cbContainer) {
    if (!e.getValueIsAdjusting()) {
      String selectedSnapshot = snapshotList.getModel().getElementAt(snapshotList.getSelectedIndex());
      cbContainer.selectSnapshotCallback(selectedSnapshot);
    }
  }

}
