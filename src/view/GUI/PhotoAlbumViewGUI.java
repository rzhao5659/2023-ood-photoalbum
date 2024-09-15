package view.GUI;

import controller.ICallbacks;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The main window that represents the GUI View.
 * The layout is as follows:
 * 1. The content pane contains two panels: the snapshotBrowserPanel and the mainPanel.
 * - snapshotBrowserPanel is the panel that allows user to select a snapshot from the list of all snapshots.
 *   It stays hidden until the user presses a button to open it.
 * - mainPanel is the panel that contains the infoPanel, drawPanel, and the controlPanel.
 *    - infoPanel: displays snapshot ID and description.
 *    - controlPanel: allows user to select snapshot to display through buttons.
 *    - drawPanel: displays the state of shapes of the selected snapshot.
 */
public class PhotoAlbumViewGUI extends JFrame implements IPhotoAlbumViewGUI {
  private InfoPanel infoPanel;
  private DrawPanel drawPanel;
  private ControlPanel controlPanel;
  private SnapshotBrowserPanel snapshotBrowserPanel;

  /**
   * Constructor.
   *
   * @param viewScreenXDim draw panel X dimension
   * @param viewScreenYDim draw panel Y dimension
   */
  public PhotoAlbumViewGUI(int viewScreenXDim, int viewScreenYDim) {
    //Set the window frame.
    setTitle("Photo Album");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    //Setting the content pane, which has two panels: snapshotBrowserPanel and mainPanel.
    //Initially snapshotBrowserPanel will be hidden.
    setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
    snapshotBrowserPanel =  new SnapshotBrowserPanel();
    JPanel mainPanel = new JPanel();
    add(mainPanel);

    //Setting the main panel.
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBackground(Color.decode(CustomColor.DARK_GRAY));
    infoPanel = new InfoPanel();
    drawPanel = new DrawPanel(viewScreenXDim, viewScreenYDim);
    controlPanel = new ControlPanel();
    mainPanel.add(infoPanel);
    mainPanel.add(new JScrollPane(drawPanel));
    mainPanel.add(controlPanel);

    //Display the window.
    pack();
    setVisible(true);
  }

  @Override
  public void setSnapshotInfo(String id, String description) {
    this.infoPanel.setSnapshotID(id);
    this.infoPanel.setSnapshotDescription(description);
  }

  @Override
  public String getSnapshotInfo() {
    return this.infoPanel.getSnapshotID();
  }

  @Override
  public void displayErrorDialog(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage);
  }

  @Override
  public void setSnapshotList(List<String> snapshotIDs) {
    this.snapshotBrowserPanel.setListData(snapshotIDs);
  }

  @Override
  public void setSnapshotListSelectedValue(int index) {
    this.snapshotBrowserPanel.getList().setSelectedIndex(index);
  }

  @Override
  public void drawRectangle(double x, double y, double xDim, double yDim, double r, double g, double b) {
    RectangularShape shape = new Rectangle2D.Double(x, y, xDim, yDim);
    Color color = new Color((float) r, (float) g, (float) b);
    drawPanel.drawBasicShape(shape, color);
  }

  @Override
  public void drawOval(double x, double y, double xDim, double yDim, double r, double g, double b) {
    RectangularShape shape = new Ellipse2D.Double(x, y, xDim, yDim);
    Color color = new Color((float) r, (float) g, (float) b);
    drawPanel.drawBasicShape(shape, color);
  }

  @Override
  public void repaintDrawPanel() {
    drawPanel.repaint();
  }

  @Override
  public void clearDrawPanel() {
    drawPanel.clear();
  }

  @Override
  public void showSnapshotBrowser() {
    this.add(this.snapshotBrowserPanel, 0);
    this.controlPanel.getBrowseButton().setBackground(Color.decode(CustomColor.DARKER_PINK));
    this.refreshWindow();
  }

  @Override
  public void closeSnapshotBrowser() {
    this.remove(this.snapshotBrowserPanel);
    this.controlPanel.getBrowseButton().setBackground(Color.decode(CustomColor.PINK));
    this.refreshWindow();
  }

  @Override
  public void setCallbacks(ICallbacks cbContainer) {
    this.controlPanel.getBrowseButton().addActionListener(event -> cbContainer.browseSnapshotCallback());
    this.controlPanel.getNextButton().addActionListener(event -> cbContainer.nextSnapshotCallback());
    this.controlPanel.getPrevButton().addActionListener(event -> cbContainer.prevSnapshotCallback());
    this.snapshotBrowserPanel.getList().addListSelectionListener(
        event -> this.snapshotBrowserPanel.listValueChangedCallback(event, cbContainer));
  }

  /**
   * Repaints the window frame.
   */
  private void refreshWindow() {
    this.revalidate();
    this.repaint();
  }
}



