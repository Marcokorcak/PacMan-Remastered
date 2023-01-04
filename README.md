# Pac-Man
 
## Table of contents
* [General info](#general-info)
* [How it works](#how-it-works)
* [How to run it](#run-it)
* [Files](#files)
* [Objects](#objects)
* [JavaFX Aspects](#javafx-aspects)

## General info
This project was created using JavaFX and Java in order to recreate the iconic Pac-Man 2D grid game. This was created using the principles of Object Oriented Programming such as abstract files, interface files, coordinate files, object files and implemented object serialization. 

## Pac-Man Board
<img src= "https://user-images.githubusercontent.com/77298953/210633982-817022d5-37b6-4e4a-abe7-056cfbd21679.png" width=30% height=30%>

## Start Screen
<img src= "https://user-images.githubusercontent.com/77298953/210633232-6e0d9d08-2e4e-4559-8992-33d0053b7175.png" width=30% height=30%>

## Pause Screen
<img src= "https://user-images.githubusercontent.com/77298953/210633709-a2d806a4-f117-4d0d-8070-9c115abea5fb.png" width=30% height=30%>

## End Screen
<img src= "https://user-images.githubusercontent.com/77298953/210633538-afe6d41b-217a-4d49-8ed6-89de871a784c.png" width=30% height=30%>



## How it works
This project is very complex and has a lot of files. Each of the moving characters (i.e Pac-Man and ghosts) are all gifs that are displayed as animations. Depending on which direction Pac-Man moves (based on key strokes), the program determines which gif to display. The pellets are images that appear aand dissapear as Pac-Man moves across the board and all of this is kept track of by a large text file. The text file is mnade up of numerous characters that represent different aspects of the game. There is a characters that outlines the maze, a character that is the current position of Pac-Man, a character that displays the path Pac-Man has taken, unique characters for each ghost to keep track of them, a character to show where the pellets are as well as a character to show where the power pellets are. Each ghost has a randomized path that is uniquely generated each time the program is ran to simulate the game. Power pellets work as in the original game where the users points can increase or give Pac-Man the ability to eat the ghosts. A user can save the game and then reload to where they left off using object serialization where a file is created and then stored temporarily until it is either loaded again to resume the game or deleted if the user decides to start a new game.  

## Files

Abstract files
* Ghost 1
* Ghost 2
* Ghost 3
* Ghost 4

Interface files
* RegularPellet - increases score by 10 points
* PowerPellet - increases score by 50 points
* Cherry - increases score by 100 points
* Strawberry - increases score by 300 points
* Orange - increases score by 500 points
* Apple - increases score by 700 points

## Objects
 
* RegularPellet
* PowerPellet
* Pac-Man
* Ghost1
* Ghost2
* Ghost3
* Ghost4
* Cherry
* Strawberry
* Orange
* Apple

## JavaFX Aspects
* Start Screen (start new game or resume old game)
* Menu to restart game
* Number of lives left
* Score of user
* Finishing/victory screen
* Animations of Pac-Man and ghosts
* Toggle audio ex. background music, noises when eating pellets/powerups

## Credits
* https://github.com/justinZ45
* https://github.com/madisondegrezia
* https://github.com/SebastianS02
