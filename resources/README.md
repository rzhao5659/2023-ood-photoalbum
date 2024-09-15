## Features / Requirements for Model

An excerpt from application specification:

"One way the application may **show the progression of creating the photo album** is to produce a text description of the commands
and the model state changes.
You may think of the **first part of this output as a “read-back” of the model state changes**, perhaps for devices that cannot show the photo album visually, or for users who are visually impaired who have screen readers.
The **snapshots are historical "save points" that can be retrieved irrespective of the model state**."

From this specification, I abstracted and defined two important objects for my model:

- A **Photo** is a picture that shows the state of shapes, and it's generated everytime
  the user requests a transformation (e.g., create a shape, set certain shape position) that will cause a model state change.
  Keeping track of photos will allow the model be able to produce the first part of the output.
- A **Snapshot** is a photo and metadata coupled together. This is generated only when the user wants to take a snapshot.
  Keeping track of snapshots will allow the model be able to produce the second part of the output.

The requirements for the model can be summarized as:

- Get photos from the album (Be able to see all model state changes).
- Take and get snapshots.
- Create new photo through various transformation.

## Overview of implementation:

### Interfaces:

**IPhotoAlbumApp** is the interface for the application model.
It acts as a service provider with all the required functionalities, such that the controller only needs to interact with it.

**ISnapshot** represents a snapshot. It wraps around a photo and contains metadata related to the snapshot (e.g., description, time stamp, etc).

**IPhoto** represents a photo on the album, which composes a list of shapes.
It manages the shapes on the photo and facilitates query of existing shapes on the photo. For example, searching a shape by name.

**IShape** represents a basic shape whose dimension can be parametrized by two values (horizontal and vertical dimensions).
The concrete classes from this interface are **Rectangle** and **Oval**.

### PhotoAlbumApp:

**PhotoAlbumApp** is the main program of the model that will interact with controller.

- Storing Snapshots: It keep tracks of snapshots with a internal map of snapshots, with key being the snapshot ID and value being the snapshot instance.
- Getting Snapshots: Gets the desired snapshot from the map.

- Storing Photos: It keep tracks of photos with an internal list of command objects called **PhotoCommand** and
  an instance of **IPhoto** that represents the current photo of the album.

- Photo creation when user requests transformations: The interface **IPhotoAlbumApp** provide methods that represent any photo transformation,
  such as creating shape, moving shape, resizing shape, etc.

      Anytime the client requests a transformation through one of these methods, the PhotoAlbumApp will first verify that it's valid by
      checking the current IPhoto instance that it stores. For example, setShapeColor(name = "r1", ...) is invalid if there isn't any shape with the name "r1".

      Once verified, instead of creating a new instance of IPhoto, it will store this transformation command as a **PhotoCommand**.
      A **PhotoCommand** is a private nested class inside **PhotoAlbumApp** that stores the command as a string, and has an invoke method that can modify a given photo in place.

- Getting Photos: When the client requests photos from the album, through the methods **getPhotoAlbum**,
  **getPhotoAlbumRange**, and **toString**, the PhotoAlbumApp will internally parse the sequence of transformation commands from the start to generate the photos on demand.

Because of my implementation, there will be two sets of transformation methods:

1. The public transformation method such as setShapeColor(),
   which mainly just verifies the command and stores it as a string inside **PhotoCommand**.
2. The private transformation method such as setShapeColorOnPhoto() is the actual implementation of the transformation.
   These are called whenever photos are requested.

## Controller

There are two controllers:

- An asynchronous controller for any kind of GUI called **PhotoAlbumControllerGUI**. It mainly contains callback logic for the view.
- A synchronous controller for any text-based view called **PhotoAlbumControllerText**. It contains logic that uses the view methods in a predefined sequence in order to print out all snapshots to some output stream.

Both controllers implements **IPhotoAlbumController** which declares the shared go() method.

The **PhotoAlbumControllerGUI** additionally implements **ICallbacks**, which is an interface that contains all the callback functions signatures.
By providing this interface, the controller can tell the view to set up callback functions for all GUI components inside it by calling the method **IPhotoAlbumViewGUI.setCallbacks(ICallbacks)**, without having any view-specific codes.

## View

### Interfaces

There are three interfaces:

- **IPhotoAlbumView**: a common interface that contains the shared draw shapes behavior.
- **IPhotoAlbumViewText**: an interface for a text-based view. Implemented by **PhotoAlbumViewHTML**.
- **IPhotoAlbumViewGUI**: an interface for a graphic-based view.Implemented by **PhotoAlbumViewGUI**.
