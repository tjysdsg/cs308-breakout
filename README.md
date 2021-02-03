# breakout

This project implements the game of Breakout.

Name: Jiyang Tang

### Timeline

Start Date: 01/30/2021

Finish Date: 02/01/2021

Hours Spent: 20

### Resources Used

### Running the Program

Main class: [src/breakout/Main.java](src/breakout/Main.java)

Data files needed:

- [ball.gif](data/ball.gif)
- [paddle.gif](data/paddle.gif)

Key/Mouse inputs:

- Direction keys to control the paddle

Cheat keys:

- "L": add one extra live
- "R": reset the level
- "W": instantly win this level
- "P": make the paddle full width
- "F": make all fortified block 1 health
- Number 1 to 3: load level 1, 2 or 3

### Notes/Assumptions

Assumptions or Simplifications:

- Assuming running on PC, no touchscreen support
- The direction of the ball can only be +-45 degrees from horizontal direction, because the
  reflection model is too simple

Known Bugs:

- Sometimes the ball pass through the block and collide with the inner side of it
- The game stop working when the 3rd level is won

Extra credit:

- [stackexchange.com](https://gamedev.stackexchange.com/questions/96337/collision-between-aabb-and-circle)
  about collision detection (though not using the methods mentioned there, my implementation was
  inspired by them)
- [data/heart.png](https://en.wikipedia.org/wiki/Heart_symbol)
- [rules.txt](data/rules.txt) is modified based
  on [this](https://en.wikipedia.org/wiki/Breakout_(video_game))

### Impressions
