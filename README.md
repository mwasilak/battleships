# Battleships

Simple implementation of Battleship game. Somewhere on the board (10x10 fields) there are several ships hidden.
Each turn you need to pick one of the fields by specifying its coordinates e.g. A8 or H7. The shot will result in a miss, hit or sink.
When all ships are eliminated, the game ends.

Ships:
* Battleship (5 squares)
* 2x Destroyers (4 squares)

## How to run

1. Install Java 17 and Maven 3.8.x
2. Clone this repository to your local drive

    ```
    git clone https://github.com/mwasilak/battleships.git
    ```
3. Build the project

    ```
    cd battleships
    mvn clean package 
    ```
4. Run the application
    ```
    cd target
    java -jar battleships-1.0-SNAPSHOT.jar 
    ```
