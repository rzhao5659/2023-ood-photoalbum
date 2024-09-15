package view.HTML;

import java.io.IOException;
import java.io.PrintWriter;
import view.IPhotoAlbumView;

/**
 * Interface for a text-based view.
 */
public interface IPhotoAlbumViewText extends IPhotoAlbumView {

  /**
   * Set up the text that indicates the start of a snapshot, along with its id and description.
   */
  void startDrawingSnapshot(String id, String description);

  /**
   * Set up the text that indicates the end of a snapshot.
   */
  void endDrawingSnapshot();

  /**
   * Prints the text that is generated cumulatively from all the operations to an output stream.
   */
  void printOutput(PrintWriter out) throws IOException;
}
