# CUATROS

<p align="center">
<img src="logo.png"
width="400">
</p>

  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white) ![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white) <img src="https://img.shields.io/badge/-HuggingFace-FDEE21?style=for-the-badge&logo=HuggingFace&logoColor=black" /> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" /> <img src="https://img.shields.io/badge/Visual_Studio_Code-0078D4?style=for-the-badge&logo=visual%20studio%20code&logoColor=white" /> <img src="https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white" />  


## ❔Overview

Cuatros is a block puzzle game where players shift and rotate falling four-square shaped blocks to fill lines horizontally. As the lines of blocks are filled, the lines will be cleared, awarding the player points for each line cleared. As the game progresses, the blocks will fall at a higher rate and will require quicker decision making to survive. The game ends when the player's screen is filled to the top with no more space for another block to spawn. The score is calculated by how many blocks have been placed and the number of lines that have been cleared.

## 🎮 Controls

The controls of this game are quite simple. You can navigate the current block by either using the arrow keys or WASD.
- LEFT ARROW / A - shift block left
- RIGHT ARROW / D - shift block right
- DOWN ARROW / W - move block down
- UP ARROW / S - rotate block
- SPACEBAR - drop block instantly
-  C - hold / change block

## ✨ Story Points: Second Sprint Completion (13 Points)
- Title Screen: 1 point  
- About Screen: 1 point  
- Help Screen: 1 point  
- Game Music: 2 points  
- Game Window: 1 point  

* Gameplay - Spawn Blocks: 1 point
* Gameplay - Controls: 2 points
* Gameplay - Rotate Block: 1 point
* Gameplay - Collisions: 2 points
* Gameplay - Pause Menu: 1 point

## 🎯 Final Sprint Targets (13 Points)
- Scoring: 1 point COMPLETE
- Difficulty Levels (Speed): 1 point COMPLETE
- Leaderboard: 2 points COMPLETE
- Sound Effects: 2 points COMPLETE

* Gameplay - Randomize Block: 2 points COMPLETE
* Gameplay - Show Next Blocks: 1 point COMPLETE
* Gameplay - Line Clearing: 2 points COMPLETE
* Gameplay - Hold Block: 2 points COMPLETE
  
### Additional features (11 Points)
* Optional Cheat Mode: 3 points PARTIALLY(NEXT BLOCK)
* Optional Game Levels: 3 points COMPLETE
* Optional Power-ups: 2 points
* Optional Save/Load: 3 points PARTIALLY COMPLETE

## 👀 Game Preview (SECOND SPRINT)
Click [this link](https://www.loom.com/share/7bd0ea2c24794fff97b0d08d0bfc6aed?sid=852407d6-2380-4e66-8276-b75fb81ea026) to see the a preview of the CUATROS run with the progress made so far.


### ✨ CUATROS Title Screen

<p align="center">
<img src="titlescreen.png"
width="400">
</p>

### ✨ CUATROS Game Screen

<p align="center">
<img src="gamescreen.png"
width="400">
</p>

#### System Requirements

> Both MacOS and Windows supported.
> Better optimized for MacOS.


## Code

### 🛠️ Environment Setup

```bash
git clone https://github.com/bjucps209/group-project-team-jd.git
```

#### Gradle Setup

```bash
gradle init --type java-application --dsl groovy --package app --test-framework junit-jupiter --use-defaults --overwrite 
```
#### Gradle commands

```bash
gradle clean build
```

```bash
gradle run
```

#### JavaFX Setup
##### Add a line to app/build.gradle
```bash
plugins {
    id 'application'
    id 'jacoco'
    id 'org.openjfx.javafxplugin' version '0.1.0'
}
```
#### Add a new block to the bottom of app/build.gradle:
```bash
javafx {
    version = "23.0.1"
    modules = [ 'javafx.controls', 'javafx.fxml', 'javafx.media' ]
}
```

## 🎯 Expenses and Contributions


|      Name          | Total Hours | Journal |
|--------------------|-------------|---------------              
|    John Tam        |      24     | [John](https://github.com/bjucps209/group-project-team-jd/wiki/Project-Journal#john-tam)|
|   David Geddam     |      40     | [David](https://github.com/bjucps209/group-project-team-jd/wiki/Project-Journal#david-geddam)|
|  Daniel Cornelius  |      16     | [Daniel](https://github.com/bjucps209/group-project-team-jd/wiki/Project-Journal#daniel-cornelius)|

## ✨ Credits

> + John Tam [github.com](https://github.com/jtam496)
> + David Geddam [github.com](https://github.com/dave21-py)
> + Daniel Cornelius [github.com](https://github.com/dcorn713)

## 🚀 Citation

If you find our work useful, please consider citing:

```
title={CUATROS},
  author={John Tam, Daniel Cornelius and David Geddam},
  journal={[Journal](https://github.com/bjucps209/group-project-team-jd/wiki/Project-Journal#david-geddam)},
  year={2025}
}
```

## 📭 Contact

For any questions, please raise a concern or contact us at
+ David Geddam [linkedin.com](https://www.linkedin.com/in/david-geddam/)
