# Tetris

Tetris game that I have created to practice object-oriented programming principles.




Game package:
 * ProgramStarter -  main method
 * GUI -             the window of the game
 * GameController -  manages the game logic
 * HighScore -       class for HighScore objects


Listeners package:
 * Listeners for the buttons "New Game" and "Pause"
 * Listener for the timer that calls movePieceDown() during the game, making the pieceInPlay fall downwards
 
 
Panels package:
 * GamePanel -     the panel with black background on which the pieces are falling down during the game
 * SidePanel -     the panel to the right of the game panel, containing the view of the highscore list, pause btn etc.
 * NextPanel -     the small panel to the right (on the SidePanel) displaying what type the next piece will be
 
 
Pieces package:
 * Piece -        the superclass of the different types of game pieces.
 * Square -       component part of a piece.
 * Orientation -  enum that describes how the pieces are oriented, since they can be rotated.
 





NOT IMPLEMENTED YET:
 
 - Implementing a short delay once the piece has hit the bottom or another piece in order for the player to be able to move it sideways before the next piece start falling.


