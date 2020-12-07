# OSM_MDP

## Project B - Analysis of optimal strategies in Markov games.

This repository contains implemention of generic Markov Decision Process (MDP) solver which provides an optimal solution for MDP problems.
In addition it contains a number of MDP games when the main game of our project is the Optimal Selfish Mining (OSM) MDP game.

Selfish Mining is an attack from the world of blockchain which the attacker does not immediately publish the blocks he has created and therefore he can increase the profit ratio versus his computing power.
The article Optimal Selfish Mining Strategies in Bitcoin presented an improvement to the SM attack and presented a modeling of the attack as an MDP problem.
We took the modeling presented in the article and used the tools we created to solve the problem - to find an optimal strategy.


## Packages
***
* MDP :            classes which belong to genric MDP problem like State and Action. also included the MDP calculator. 
* MDPinterfaces :  interfaces which belong to genric MDP problem like State set, probability table and reward table.
* MyMDPimp :       implemention of MDP game called "Treasures and crocodiles". The world is NxN board and every cell is a state. The actions are the regular                     directions and there is probability for every action and reward for every cell. The game provides an optimal solution of the way to the                       treasure. 
* MyMDPmaze :      implemention of MDP game called "A Maze of Treasures and Crocodiles". In this game we took the previous treasure and crocodile game and                      added complexity to it. There are walls between a cells and therefore not all the transitions that were legal in the original game are                       legal in this game. The game provides an optimal solution of the way to the treasure.
* MyMDPmonopoly : implemention of MDP game called "Monopoly". The world is N + 1 states and state N is the last state. for two players, every player has Y                     fair dice with X wigs in each and the player must choose the number of dice he wants to roll in each turn. For any normal state there can                     be a  hotel of the player or of the opponent or it is a public space which those gave the reward - positive if the hotel belogns to the                       current player or negative if its belongs to the opponent. The game provides an optimal solution of the way to last state with maximum                       profit.
* OSM :           implemention of the main MDP game in our project - Optimal Selfish Mining. We use the MDP calculator to implement the algorithm specified                     in the article where we find the limits on the attacker's profits and the policies of Under paying and Over paying.
                  The state represnts the number of blocks the attacker and the honest network have and the action represnt the action like publish the                         block,wait or retire, due to the article. The game provides an Upper bound and Lower bound the attacker's profits.



