package modelTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import model.IShape;
import model.Oval;
import model.ShapeFactory;

/**
 * Test Suite for ShapeFactory,
 * which encompasses testing of the concrete classes Rectangle and Oval.
 */
public class ShapeFactoryTest {
  private IShape shape1;
  private IShape shape2;
  private IShape shape3;
  private IShape shape4;

  /**
   * Setup function.
   */
  @Before
  public void setUp() {
    shape1 = ShapeFactory.createBasicShape("c1", "oval", 2, 2, -10, -50, 0, 0, 1);
    shape2 = ShapeFactory.createBasicShape("giowafneoi131wa", "oval", 30.312, 319.23, 31, 231.55, 0.412, 0.421, 0.581);
    shape3 = ShapeFactory.createBasicShape("r1", "rectangle", 141.2, 5, 0, 0, 0, 0, 0);
    shape4 = ShapeFactory.createBasicShape("r130kniovm", "rectangle", 30.312, 319.23, 31, 231.55, 0.412, 0.421, 0.581);
  }

  /**
   * Tests that it fails to instantiate due to empty name.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionName1() {
    IShape shape = ShapeFactory.createBasicShape("", "oval",2, 2, 1, 1,1 ,1 ,1);
  }

  /**
   * Tests that it fails to instantiate due to null name.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionName2() {
    IShape shape = ShapeFactory.createBasicShape(null, "oval",2, 2, 1, 1,1 ,1 ,1);
  }

  /**
   * Tests that it fails to instantiate due to name containing space
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionName3() {
    IShape shape = ShapeFactory.createBasicShape("f 13dc", "oval",2, 2, 1, 1,1 ,1 ,1);
  }

  /**
   * Tests that it fails to instantiate due to unknown type.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionUnknownType1() {
    IShape shape = ShapeFactory.createBasicShape("c10", "polygon",2, 2, 1, 1,1 ,1 ,1);
  }

  /**
   * Tests that it fails to instantiate due to unknown type.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionUnknownType2() {
    IShape shape = ShapeFactory.createBasicShape("c10", "circular",2, 2, 1, 1,1 ,1 ,1);
  }

  /**
   * Tests that it fails to instantiate due to invalid dimension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionDim1() {
    IShape shape = ShapeFactory.createBasicShape("c10", "oval",0, 2, 1, 1,1 ,1 ,1);
  }

  /**
   * Tests that it fails to instantiate due to invalid dimension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionDim2() {
    IShape shape = ShapeFactory.createBasicShape("c10", "oval",2, -4, 1, 1,1 ,1 ,1);
  }

  /**
   * Tests that it fails to instantiate due to invalid color.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionColor1() {
    IShape shape = ShapeFactory.createBasicShape("c10", "oval",2, 2, 1, 1,1.5 ,1.3 ,1.2);
  }

  /**
   * Tests that it fails to instantiate due to invalid color.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailConstructionColor2() {
    IShape shape = ShapeFactory.createBasicShape("c10", "oval",2, 2, 1, 1, 1, -0.001 , 1);
  }

  /**
   * Tests getName.
   */
  @Test
  public void testGetName() {
    assertEquals("c1", shape1.getName());
    assertEquals("giowafneoi131wa", shape2.getName());
    assertEquals("r1", shape3.getName());
    assertEquals("r130kniovm", shape4.getName());
  }

  /**
   * Tests getType.
   */
  @Test
  public void testGetType() {
    assertEquals("oval", shape1.getType());
    assertEquals("oval", shape2.getType());
    assertEquals("rectangle", shape3.getType());
    assertEquals("rectangle", shape4.getType());
  }

  /**
   * Tests getXDim.
   */
  @Test
  public void testGetXDim() {
    assertEquals(2, shape1.getXDim(), 0.0001);
    assertEquals(30.312, shape2.getXDim(), 0.0001);
    assertEquals(141.2, shape3.getXDim(), 0.0001);
    assertEquals(30.312, shape4.getXDim(), 0.0001);
  }

  /**
   * Tests getYDim.
   */
  @Test
  public void testGetYDim() {
    assertEquals(2, shape1.getYDim(), 0.0001);
    assertEquals(319.23, shape2.getYDim(), 0.0001);
    assertEquals(5, shape3.getYDim(), 0.0001);
    assertEquals(319.23, shape4.getYDim(), 0.0001);
  }

  /**
   * Tests getPosition.
   */
  @Test
  public void testGetPosition() {
    assertEquals(-10, shape1.getPosition()[0], 0.0001);
    assertEquals(-50, shape1.getPosition()[1], 0.0001);
    assertEquals(31, shape2.getPosition()[0], 0.0001);
    assertEquals(231.55, shape2.getPosition()[1], 0.0001);
    assertEquals(0, shape3.getPosition()[0], 0.0001);
    assertEquals(0, shape3.getPosition()[1], 0.0001);
    assertEquals(31, shape4.getPosition()[0], 0.0001);
    assertEquals(231.55, shape4.getPosition()[1], 0.0001);
  }

  /**
   * Tests getColor.
   */
  @Test
  public void getColor() {
    assertEquals(0, shape1.getColor()[0], 0.0001);
    assertEquals(0, shape1.getColor()[1], 0.0001);
    assertEquals(1, shape1.getColor()[2], 0.0001);
    assertEquals(0.412, shape2.getColor()[0], 0.0001);
    assertEquals(0.421, shape2.getColor()[1], 0.0001);
    assertEquals(0.581, shape2.getColor()[2], 0.0001);
    assertEquals(0, shape3.getColor()[0], 0.0001);
    assertEquals(0, shape3.getColor()[1], 0.0001);
    assertEquals(0, shape3.getColor()[2], 0.0001);
    assertEquals(0.412, shape4.getColor()[0], 0.0001);
    assertEquals(0.421, shape4.getColor()[1], 0.0001);
    assertEquals(0.581, shape4.getColor()[2], 0.0001);
  }

  /**
   * Tests setXDim.
   */
  @Test
  public void setXDim() {
    shape1.setXDim(1301.3);
    shape3.setXDim(3109);
    assertEquals(1301.3, shape1.getXDim(), 0.0001);
    assertEquals(3109, shape3.getXDim(), 0.0001);
  }

  /**
   * Tests that set fails if dimension is negative.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetXDim1() {
    shape1.setXDim(-1);
  }

  /**
   * Tests that set fails if dimension is 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetXDim2() {
    shape1.setXDim(0);
  }

  /**
   * Tests setYDim.
   */
  @Test
  public void setYDim() {
    shape1.setYDim(1301.3);
    shape3.setYDim(3109);
    assertEquals(1301.3, shape1.getYDim(), 0.0001);
    assertEquals(3109, shape3.getYDim(), 0.0001);
  }

  /**
   * Tests that set fails if dimension is negative.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetYDim1() {
    shape1.setYDim(-141);
  }

  /**
   * Tests that set fails if dimension is 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetYDim2() {
    shape1.setYDim(0);
  }

  /**
   * Tests setPosition.
   */
  @Test
  public void setPosition() {
    shape1.setPosition(31031,301930);
    shape3.setPosition(-3413, 40.31);
    assertEquals(31031, shape1.getPosition()[0], 0.0001);
    assertEquals(301930, shape1.getPosition()[1], 0.0001);
    assertEquals(-3413, shape3.getPosition()[0], 0.0001);
    assertEquals(40.31, shape3.getPosition()[1], 0.0001);
  }

  /**
   * Tests setColor.
   */
  @Test
  public void setColor() {
    shape1.setColor(0.31,0.41,0.63);
    shape3.setColor(1, 1, 1);
    assertEquals(0.31, shape1.getColor()[0], 0.0001);
    assertEquals(0.41, shape1.getColor()[1], 0.0001);
    assertEquals(0.63, shape1.getColor()[2], 0.0001);
    assertEquals(1, shape3.getColor()[0], 0.0001);
    assertEquals(1, shape3.getColor()[1], 0.0001);
    assertEquals(1, shape3.getColor()[2], 0.0001);
  }

  /**
   * Tests that set fails if color value is not between 0.0 and 1.0
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetColor1() {
    shape1.setColor(-1, 0,0);
  }

  /**
   * Tests that set fails if color value is not between 0.0 and 1.0
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetColor2() {
    shape1.setColor(0, 3,1);
  }

  /**
   * Tests that set fails if color value is not between 0.0 and 1.0
   */
  @Test (expected = IllegalArgumentException.class)
  public void testFailSetColor3() {
    shape1.setColor(1, 1,1.5);
  }

  /**
   * Tests toString.
   */
  @Test
  public void testToString() {
    String shape2ExpectedString = "Name: giowafneoi131wa\n"
        + "Type: oval\n"
        + "Center: (31.00, 231.55), X radius: 30.31, Y radius: 319.23, Color: (0.41, 0.42, 0.58)";
    assertEquals(shape2ExpectedString, shape2.toString());
    String shape3ExpectedString = "Name: r1\n"
        + "Type: rectangle\n"
        + "Min corner: (0.00, 0.00), Width: 141.20, Height: 5.00, Color: (0.00, 0.00, 0.00)";
    assertEquals(shape3ExpectedString, shape3.toString());
  }

  /**
   * Tests clone.
   */
  @Test
  public void testClone() {
    IShape clone = shape2.clone();
    assertNotSame(clone, shape2);
    assertTrue(clone instanceof Oval);
    assertEquals(clone.getName(), shape2.getName());
    assertEquals(clone.getXDim(), shape2.getXDim(), 0.0001);
    assertEquals(clone.getYDim(), shape2.getYDim(), 0.0001);
    for (int i = 0; i < clone.getColor().length; i++) {
      assertEquals(clone.getColor()[i], shape2.getColor()[i], 0.0001);
    }
    for (int i = 0; i < clone.getPosition().length; i++) {
      assertEquals(clone.getPosition()[i], shape2.getPosition()[i], 0.0001);
    }
  }

  /**
   * Tests equals.
   */
  @Test
  public void testEquals() {
    IShape clone = shape2.clone();
    assertNotSame(clone, shape2);
    assertEquals(clone, shape2);
  }

}