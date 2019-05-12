# 2d-game-engine

An API for [LWJGL](https://www.lwjgl.org/) (OpenGL in Java) and [JBox2d](http://www.jbox2d.org/) (A java physics engine).
-
In the first version of the game engine, users can:

- Load from texture atlases dynamically or statically
- Import GIFs and WAV files
- Load transparent images
- Define custom click detection for images
- Define custom collision detection based on boundaries
- Handle keyboard events and mouse events
- Handle sound events and display events
- Extend from the GameController class to create a new Game


In the second version of the game engine, users can:

- Define mouse collision events
- Model entities and structures using the JBox2d collision handling
- Save levels to XML files
- Edit levels while testing them (multiple threads)
- Import GIFs or multiple PNGs to use as sprite objects
- Change animation times of different sprites
- Handle keyboard events and mouse events
- Handle sound events and display events
- Use z-filtering for objects that are closer to the screen (creates illusion of depth)

#Some Examples of this Game Engine:
*Note: I also made the textures used in i++ and Logan's Game*
<br>

- i++ (v1)
![](https://github.com/mtresnik/2d-game-engine/blob/master/src/example_games/screenshots/i%2B%2B/level1.PNG)
![](https://github.com/mtresnik/2d-game-engine/blob/master/src/example_games/screenshots/i%2B%2B/level2.PNG)
![](https://github.com/mtresnik/2d-game-engine/blob/master/src/example_games/screenshots/i%2B%2B/level3.PNG)

- Logan's Game (v1) (I made this for a friend's Birthday)
![](https://github.com/mtresnik/2d-game-engine/blob/master/src/example_games/screenshots/logans_game/livingroom.PNG)
![](https://github.com/mtresnik/2d-game-engine/blob/master/src/example_games/screenshots/logans_game/pizza.PNG)
![](https://github.com/mtresnik/2d-game-engine/blob/master/src/example_games/screenshots/logans_game/elevatordown.PNG)

- Live Editor (v2)
![](https://github.com/mtresnik/2d-game-engine/blob/master/src/example_games/screenshots/live_editor/editor.PNG)
![](https://github.com/mtresnik/2d-game-engine/blob/master/src/example_games/screenshots/live_editor/z-filtering.PNG)