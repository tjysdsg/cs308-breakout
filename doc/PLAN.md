# Variants

- Add a new type of brick that explode when the ball is detected within a certain range. The explosion contain destroy
  surrounding bricks (chain explosion is allowed) and reflect the ball, then the block will transform into a normal
  block. Also, a [power up](#power-ups) is triggered.
- Some blocks (all types of blocks can have this attribute) can move from left to right across the screen, and change
  direction upon hitting the edge.

# Abilities of paddle

- When in power ups, the paddle should reflect the ball in a 20% larger angle of emergence than normal.
- The paddle wraps around the edge of the screen with a penalty, it will move slower for 2 seconds after wrapping.

# Types of blocks

- Normal block: it can be destroyed by a single direct hit.
- Fotified block: it can be destroyed by two direct hits.
- Explosive block: described in [Variants](#variants).
- Indestructible block: cannot be destroyed in any way, and prevent explosive effects being triggered on the blocks
  behind it. Players don't have to clear this kind of blocks to get a victory in a level.

# Power ups

During power ups:

- The ball moves in a higher speed during power ups.
- The paddle and walls reflect the ball in a larger angle of emergence than normal.
- The paddle can wrap around the edge of the screen without movement speed penalty.

# Cheat keys

Player can only choose to use one of the cheats in a level once:
- Trigger a designated explosive block immediately.
- Make the paddle as wide as the screen.
- Give the player one extra life.
- TODO