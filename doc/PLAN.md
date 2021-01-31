# Variants

1. Add a new type of brick that explode when the ball is detected within a 2-tile-radius circle. The explosion contain
   destroy blocks in the same circle (chain explosion is allowed) and reflect the ball, then the block will transform
   into a normal block. Also, a [power up](#power-ups) is triggered.
2. Some blocks (all types of blocks can have this attribute) can move from left to right across the screen, and change
   direction upon hitting the edge.

# Abilities of paddle

- The paddle wraps around the edge of the screen with a penalty, it will move slower for 2 seconds after wrapping.

# Types of blocks

- Normal block: it can be destroyed by a single direct hit.
- Fortified block: it can be destroyed by two direct hits.
- Explosive block: described in [Variants](#variants).
- Indestructible block: cannot be destroyed in any way, and prevent explosive effects being triggered on the blocks
  behind it. Players don't have to clear this kind of blocks to get a victory in a level.

# Power ups

During power ups:

- The ball moves in a higher speed during power ups.
- The ball becomes larger.
- The paddle becomes wider.

# Cheat keys

Player can only choose to use one of the cheats in a level once:

- Trigger a designated explosive block immediately.
- Make the paddle as wide as the screen.
- Remove an indestructible block at given position.
- Make all fortified blocks one hit kill.

# Level designs

- `@`: normal blocks
- `#`: fortified blocks
- `$`: explosive blocks
- `*`: indestructible blocks
- `=`: moving blocks

## 1

Used in game variant 1

```
@@@###@@@###@@@####@@@@@@
@@@@@@@@$@@@@@@@@@@@@@@@@
#########################
@@@@@@@$@@@@@@@@@@@@$@@@@
@@@@@@@@@@@@$@@@@@@@@@@@@
@@@@@$@@@@@@@@@@@@@@@@@@@


     *            *
```

## 2

Used in game variant 2

```
@@@@@@@@@@@@@=@@@@@@@@@@@
@@@@@@@@@@@@@=@@@@@@@@@@@
#############=###########
@@@@@@@@@@@@@=@@@@@@@@@@@
@@@@@@@@@@@@@=@@@@@@@@@@@
                  =

     =
```

## 3

Used in game variant 1

```
#########################
#########$#$#$#$#########
###*#################*###
#########$#$#$#$#########
#########################


       ***********
```

# Classes

Assume `Vec2D` is class used for vector math, since I haven't found a reliable implementation yet.

The pseudocode of classes I plan to implement is the following:

## Ball

`Ball` class stores information about the ball and handles its movement.

```java
class Ball {
    Vec2D v; // velocity

    void step(double time); // called by main game loop, calculate and update position according to velocity of itself
}
```

## ReflectionHandler

Handles reflections, allow easy extension of different types of reflection

```java
class ReflectionHandler {
    Vec2D calcNormal(Vec2D gPos);

    Vec2D reflect(Vec2D normal, boolean powered);
}
```

## Block

Stores the type, remaining health and position of a block

```java
enum BlockType {
    // ...
}

class Block {
    BlockType type;
    int health;
    Vec2D pos;
}
```

## Paddle

```java
class Paddle {
    Vec2D pos;
    double width;
    double maxVelocity;

    void translate(int dir /* -1 left, 1 right */); // take wrapping into account
}
```

## World

Stores information about the level, checks victory conditions, handle collisions and reflections, and accepts cheat
keys.

```java
import java.util.ArrayList;

class World {
    boolean poweredUp;
    int lives;
    int screenWidth;
    int screenHeight;
    ArrayList<Block> blocks;
    Ball ball;
    Paddle paddle;

    void step(double time);

    void checkVictory();

    enum CheatType {
        // ...
    }

    void cheat(CheatType type);

    void checkAndHandleCollision();
}
```

## InputManager

Resolve input signals and dispatch events

```java
class InputManager {
    void step(double time);
}
```

# Code Guidelines

Google Java
style: [https://google.github.io/styleguide/javaguide.html](https://google.github.io/styleguide/javaguide.html)
