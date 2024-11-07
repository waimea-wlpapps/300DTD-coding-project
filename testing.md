# Test Plan and Evidence / Results of Testing

## Game Description

The project involves the programming of a game.

The project is a small map based game with the theme of New Zealand and Australia
The objective of the game was for the player to move from location to location with the objective of completeing all the tasks 
on the task list displayed on the side of the ui and to fly to australia once they've
completed all the tasks.

### Game Features and Rules

The game has the following features and/or rules:

- The game features a leaderboard displaying your lowest move count you've completed the game in
- The game consists of a help menu to teach you how to play the game without help
- The game features locations that the player has to move throughout to reach the end location
- The game has a rule where all the tasks must be finished before you can travel to Australia
- The game has a move counter so you can see how many moves you've done on this attempt

---

## Test Plan

The following game features / functionality and player actions will need to be tested:

- Moving the character North, East, South West
- Help menu displays a help menu
- Leaderboard displays scores and sorts them in lowest move order
- Tasks get checked off when you complete them
- Finish message doesn't pop up unless you've completed all the tasks
- Players can only move 1 location at a time
- Player wins once visited australia

The following tests will be run against the completed game. The tests should result in the expected outcomes shown.


### Moving the character

We will test if the player is able to move 

#### Test Data / Actions to Use

We will need to see if the player can move using the arrow keys on your keyboard
With each arrow representing a direction to move

We will also need to make sure that the player is unable to move multiple locations at a time
#### Expected Outcome
When pressing each arrow key the player should be able to move in that direction if theirs an available location
I expect that no matter how many buttons the player tries to press at once each button press should count as a move 



### Leaderboard Display

We will test if the leaderboard displays the scores and sorts them

#### Test Data / Actions to Use

We will enter a few different attempts with various amounts of moves and then see if the leaderboard displays them in a sorted order

#### Expected Outcome

We expect that as the player tries the game multiple times and get's different scores the leaderboard should display the best scores at the top in a descending order with the best being the least amount of moves made to finish


### Tasks List

We will test that the task list is working

#### Test Data / Actions to Use

We will perform all the tasks in the task list

#### Expected Outcome

We expect that as we complete the tasks list they will get completed off the task list and have a little tick next to them indicating that they're completed


### Completeing the game

We will need to make sure that the player is able to finish the game at appropriate time

#### Test Data / Actions to Use

We will try to finish the game before we complete all our tasks and we will then try to complete the game once we have completed all the tasks

#### Expected Outcome

We expect that as the player tries to go to australia before completing all the tasks on their list it will prompt them with a message saying they cannot go their and teleport them back to their last location whilst still increasing a move as punishment we then expect that if they visit australia once they've completed all their tasks it will prompt them with a message saying their score and that they've won


---


## Evidence / Results of Testing

Here is a video link where I displayed all of my games features and testing into one video

https://youtu.be/7B8XZJbzohc
