# DESIGN Document for Breakout

## NAME(s)

Jiyang Tang (jt304)

## Design Goals

The goal is to make a variant of Breakout with new types of blocks and power ups.

There are many bricks on the screen, and the player's goal is to destroy them all. A ball moves
straight around the screen, bouncing off the top and two sides of the screen. When a brick is hit,
the ball bounces back, and the brick is destroyed. Some bricks require two hits to be destroyed, and
some cannot be destroyed at all. The player loses one life when the ball touches the bottom of the
screen, if there is 0 life left, the player loses the game. There is also a horizontally movable
paddle that the player can use to bounce the ball upward, keeping it in play.

### Abilities of paddle

Player can use left and right direction keys to move the paddle horizontally. In addition to that,
the paddle can wrap around the edge of the screen.

### Types of blocks

- Normal block: it can be destroyed by a single direct hit.
- Fortified block: it can be destroyed by two direct hits.
- Indestructible block: cannot be destroyed in any way, and prevent explosive effects being
  triggered on the blocks behind it. Players don't have to clear this kind of blocks to get a
  victory in a level.

### Power ups

When a block is destroyed, there is a random chance to trigger a power up, there are three types of
power ups during which:

- the ball moves in a higher speed during power ups,
- the ball becomes larger, or
- the paddle becomes wider.

The power up will last for 5 seconds and stop

### Levels

There are three levels in this game. The player starts the game in Level 1 and moves on to the next
level after winning the current one.

### Cheat keys

Developers may use cheat keys to help test the game, effects of different cheat keys can be applied
at the same time.

- "L": add one extra live
- "R": reset the level
- "W": instantly win this level
- "P": make the paddle full width
- "F": make all fortified block 1 health
- Number 1 to 3: load level 1, 2 or 3

## High-Level Design

- `Main`: application entry point, contains the main game loop.
- `Level`: updates level in the game loop, checks for winning/loss, handles level loading, and
  manages UI menus.
- `Ball`: handles movement and collision of the ball.
- `Block`: handles collision, destruction, and conversion of a block.
- `Paddle`: handles collision, and movement of the paddle.
- `KeyboardInputManager`: watch input events and dispatch events to input handlers.
- `Collider` and its subclasses: handles collision detection and normal vector calculation.
- `GameObject`: parent class of all visible, moving, and collidable objects.
- `UIComponent`: parent class of all UI components.
- `SplashScreen`: full-screen pause menu used to display game rules or winning text.
- `StatusDisplay`: display game status on top the of screen (remaining live, scores, level name,
  etc.)

## Assumptions or Simplifications

- Assuming running on PC, no touchscreen support
- The direction of the ball can only be +-45 degrees from horizontal direction, because the
  reflection model is too simple

Known Bugs:

- Sometimes the ball pass through the block and collide with the inner side of it
- The game stop working when the 3rd level is won

## Changes from the Plan

- The explosive and moving block types are not implemented due to limited time.
- The penalty of the paddle wrapping around the screen is not added, as it reduces the playability.

## How to Add New Features

### Add a new block type

In [Block.java](src/breakout/Block.java),

1. add the name of the block type to `Block.BlockType`
2. add the score that players can get when the block is destroyed to `Block.BLOCK_SCORE`
3. add the image file path to `Block.IMAGE`
4. add the health of the block to `Block.HEALTH`
5. if the block handles collision differently, add relevant code
   to `Block.handleCollision(Collision collision)`
6. if the block has a different update logic, override `step(double time)` method.

### Change the way the ball reflects

Change `Ball.handleCollision(Collision collision)` method in [Ball.java](src/breakout/Ball.java).
The `collision` parameter contains the point where the collision happened, the normal vector, and
the two colliders that collided.

### Add a new cheat key

In [Level.java](src/breakout/Level.java), add code like the following
to `Level.registerInputHandlers()` method:

```
inputManager.registerInputHandler("<Key name here>", val -> {
    // <actions performed by the key>
});
```

### Add a new power up

1. Add the name of the power up to `PowerUpType`
   in [PowerUpType.java](src/breakout/PowerUpType.java)
2. Add code that applies power up effect in `Level.triggerPowerUp(PowerUpType type)`
   in [Level.java](src/breakout/Level.java)
