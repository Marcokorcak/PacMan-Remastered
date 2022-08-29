# PacMan

## Table of contents
* [General info](#general-info)
* [How it works](#how-it-works)
* [Files](#files)
* [Objects](#objects)
* [JavaFX Aspects](#javafx-aspects)

## General info
This project was created using JavaFX and Java in order to recreate the iconic PacMan 2D grid game. This was created using the principles of Object Oriented Programming such as abstract files, interface files, coordinate files, object files and implemented object serialization. 

## How it works
This project is very complex and has a lot of files. Each of the moving characters (i.e pacman and ghosts) are all gifs that are displayed as animations. Depending on which direction pacman moves (based on key strokes), the program determines which gif to display. The pellets are images that appear aand dissapear as pacman moves across the borard and all of this is kept track of by a large text file. The text file is mnade up of numerous characters that represent different aspects of the game. There is a characters that outlines the maze, a character that is the current position of pacman, a character that displays the path pacman has taken, unique characters for each ghost to keep track of them, a character to show where the pellets are as well as a character to show where the power pellets are. Each ghost has a randomized path that is uniquely generated each time the program is ran to simulate the game. Power pellets work as in the original game where the users points can increase or give pacman the ability to eat the ghosts.     

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
* PacMan
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
* Animations of pacman and ghosts
* Toggle audio ex. background music, noises when eating pellets/powerups
