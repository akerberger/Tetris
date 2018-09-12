# Tetris

Object oriented version of the game Tetris. 

I have done this to practice object orientated programming and to learn!




You need to have a text document called "HighScore" in the source code map.

game-package:

GUI -             the window of the game
GameController -  manages the logic
ProgramStarter -  main method
HighScore -       class for HighScore objects


listener-package:

 * Listeners for the buttons New Game and Pause
 * Listener for the Timer that calls movePieceDown() during the game, making the pieceInPlay fall downwards.
 
 The panels-package:
 
 GamePanel -     the panel with black background on which the pieces are falling down during the game.
 
 SidePanel -     the whole long panel to the right of the game panel, containing the NextPanel among other things.
 
 NextPanel -     the smal panel to the right (on the SidePanel) displaying what type the next piece will be.
 
 
 
 
 The pieces-package:
 
 Piece -        The superclass of the game pieces.
 
 Square -       component part of a piece
 
 Orientation -  Enum that describes how the pieces are oriented, since they can be rotated.
 
 (all the sub-classes to Piece = the different types of pieces)




NOT IMPLEMENTED YET:

 - The Paus-button.
 
 - Implementing a short delay once the piece has hit the bottom or another piece in order for the player to be able to move it sideways before the next piece start falling.


