package controller;

import java.io.InputStream;
import model.IPhotoAlbumApp;

/**
 * An interpreter/parser for a source of data that represents a sequence of commands to populate model.
 */
public interface IInputParser {

  /**
   * Parses the commands coming from the input stream and populate model.
   *
   * @param in input stream
   * @param model model
   */
  void parse(InputStream in, IPhotoAlbumApp model);
}
