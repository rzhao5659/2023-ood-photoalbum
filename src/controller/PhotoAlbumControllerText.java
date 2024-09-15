package controller;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import model.IPhotoAlbumApp;
import model.IShape;
import model.ISnapshot;
import view.HTML.IPhotoAlbumViewText;

/**
 * A synchronous controller for text-based view that prints all snapshots of the album on an output stream.
 */
public class PhotoAlbumControllerText implements IPhotoAlbumController {
  private IPhotoAlbumApp model;
  private IPhotoAlbumViewText view;
  private InputStream in;
  private PrintWriter out;
  private IInputParser inputParser;

  /**
   * Constructor.
   *
   * @param model photo album model
   * @param view photo album text-based view
   */
  public PhotoAlbumControllerText(IPhotoAlbumApp model, IPhotoAlbumViewText view, PrintWriter out) {
    this.model = model;
    this.view = view;
    this.out = out;
    this.in = null;
    this.inputParser = null;
  }

  /**
   * Overloaded constructor that allows user pass in a sequence of commands to perform on the model.
   *
   * @param model photo album model
   * @param view photo album text-based view
   * @param in input stream with the commands sequence
   * @param inputParser a parser that can parse the commands sequence from the input stream.
   */
  public PhotoAlbumControllerText(IPhotoAlbumApp model, IPhotoAlbumViewText view, PrintWriter out, InputStream in, IInputParser inputParser) {
    this(model, view, out);
    this.in = in;
    this.inputParser = inputParser;
  }

  @Override
  public void go() {
    this.modelSetup();

    //Draw all snapshots.
    List<ISnapshot> snapshotList = model.getSnapshots();
    for (ISnapshot snapshot : snapshotList) {
      view.startDrawingSnapshot(snapshot.getID(), snapshot.getDescription());
      for (IShape shape : snapshot.getPhoto().getShapes()) {
        double x = shape.getPosition()[0];
        double y = shape.getPosition()[1];
        double xDim = shape.getXDim();
        double yDim = shape.getYDim();
        double r = shape.getColor()[0];
        double g = shape.getColor()[1];
        double b = shape.getColor()[2];
        if (shape.getType().equals("rectangle")) {
          view.drawRectangle(x, y, xDim, yDim, r, g, b);
        } else if (shape.getType().equals("oval")) {
          view.drawOval(x, y, xDim, yDim, r, g, b);
        }
      }
      view.endDrawingSnapshot();
    }

    //Print to an output stream.
    try {
      view.printOutput(out);
    } catch (Exception e) {
      throw new IllegalArgumentException("The output stream produced an exception.");
    }

  }

  /**
   * Setup function for Model. Invokes the input parser on the input stream if they are available.
   */
  private void modelSetup() {
    if (in != null && inputParser != null) {
      inputParser.parse(in, model);
    }
  }

}
