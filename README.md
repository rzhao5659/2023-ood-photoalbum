This is a Object-Oriented Design project. 

It parses commands from an input text file (which consists of creation of geometric shapes and snapshots) and display this as a snapshot album either in GUI or HTML view.

The final result is quickly shown in this video: https://youtu.be/MV2dJCyaP9c

## How to Run

An artifact has been built using IntelliJ as `resources/FinalProject.jar`, which can be used for execution

Examples: 

Parse `building.txt` input file and display the photo album in a GUI 
```
java -jar resources/FinalProject.jar -in resource/buildings.txt -view graphical
```

Parse `building.txt` input file and create as an output a `building.html` file with SVG drawings, which can be viewed in a browser.
```
java -jar resources/FinalProject.jar -in resource/buildings.txt -out buildings.html -view web
```

## Other details
### Input text file command set
**shape**: Creates a new shape. Followed by these attributes:
   - ID - textual name for the shape 
   - Type - type of shape (only rectangles and ovals for this assignment) 
   - x position - coordinate system for both Swing and SVG starts in upper left corner 
   - y position - coordinate system for both Swing and SVG starts in upper left corner 
   - width - or "first dimension" like radius_x. for ovals 
   - height - or "second dimension" like radius_y for ovals 
   - red - RGB red value 
   - green - RGB green value 
   - blue - RGB blue value 

**move**: Moves a shape to a new x, y position
- ID - text name for the shape
- x position - coordinate system for both Swing and SVG starts in upper left corner
- y position - coordinate system for both Swing and SVG starts in upper left corner

**color**: Changes the color of a shape
- red - RGB red value
- green - RGB green value
- blue - RGB blue value

**resize**: Resizes the shape
- width - or "first dimension" like radius_x. for ovals
- height - or "second dimension" like radius_y for ovals

**remove**: Removes the shape
- ID - text name for shape to remove

**snapshot**: Tells the model to take a snapshot of the current state of the album
- description (optional) - optional text that the command file can use to tag the snapshot with extra information


