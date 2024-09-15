package view.GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
import java.util.LinkedList;
import javax.swing.JPanel;
import java.util.List;

/**
 * Draw panel is the panel where shapes will be drawn on top of.
 */
class DrawPanel extends JPanel {
  private final List<ShapeColorPair> shapes;

  /**
   * Constructor.
   * @param viewScreenXDim draw panel X size in px.
   * @param viewScreenYDim draw panel Y size in px.
   */
  public DrawPanel(int viewScreenXDim, int viewScreenYDim) {
    setBackground(Color.decode(CustomColor.GRAY));
    setPreferredSize(new Dimension(viewScreenXDim, viewScreenYDim));
    shapes = new LinkedList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;
    //Stroke setup (Outline of shapes)
    final int OUTLINE_THICKNESS = 2;
    g2D.setStroke(new BasicStroke(OUTLINE_THICKNESS));
    //Drawing shapes
    for (ShapeColorPair s : shapes) {
      g2D.setColor(s.color);
      g2D.fill(s.shape);
      g2D.draw(s.shape);
    }
  }

  /**
   * Draw a basic shape.
   * A basic shape is a shape that can be expressed by horizontal and vertical dimensions.
   *
   * @param shape basic shape
   * @param color color
   */
  public void drawBasicShape(RectangularShape shape, Color color) {
    this.shapes.add(new ShapeColorPair(shape, color));
  }

  /**
   * Clear all shapes from the draw panel.
   */
  public void clear() {
    this.shapes.clear();
  }

  /**
   * A helper data class for storing a shape and its color.
   */
  private class ShapeColorPair {
    public RectangularShape shape;
    public Color color;

    private ShapeColorPair(RectangularShape shape, Color color) {
      this.shape = shape;
      this.color = color;
    }
  }
}