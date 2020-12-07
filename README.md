# OSM_MDP
## Project B - Analysis of optimal strategies in Markov games.

This repository contains implemention of generic Markov Decision Process (MDP) solver which provides an optimal solution for MDP problems.
In addition it contains a number of MDP games when the main game of our project is the Optimal Selfish Mining (OSM) MDP game.

Selfish Mining is an attack from the world of blockchain which the attacker does not immediately publish the blocks he has created and therefore he can increase the profit ratio versus his computing power.
The article Optimal Selfish Mining Strategies in Bitcoin presented an improvement to the SM attack and presented a modeling of the attack as an MDP problem.
We took the modeling presented in the article and used the tools we created to solve the problem - to find an optimal strategy.


## packages
***
* [MDP] : classes which belong to genric MDP problem like State and Action. also included the MDP calculator. 
* [MDPinterfaces] :  interfaces which belong to genric MDP problem like State set, probability table and reward table.
* [MyMDPimp] : implemention of the first MDP called Treasures and crocodiles. The world is NxN board and every cell is a state. The actions are regular directions 
* [MyMDPmaze] : 
* [MyMDPmonopoly] : 
* [OSM] : 



