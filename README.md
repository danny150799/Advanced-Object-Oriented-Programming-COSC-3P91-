# Advanced-Object-Oriented-Programming-COSC-3P91-
Advanced Object-Oriented Programming (3rd year). This is a project so despite the name "Assignment 2", it is updated with new features according to other assignments. Read the report of each assignment for more details about each version. 

About the project: This a Java program that acts similarly to Clash-On-Clan game on mobile. The player can build/upgrade buildings, resources, army and defence throughout the game. This a real time game so the longer the players play the more resources and stronger they become. There is a level cap for everything except resources so manage your resources carefully. At the 3rd version, players can attack bots and gain more resources, if they win of course. Otherwise, they lose their army and gain little to no resource depend on how hard they lost. At the final version, the program can be run as server and client where the server can hosts multiple clients at the same time using TCP connections. Unfortunately, there isn't any PVP since there PVP is not required for this assignment completion. 

## Table of contents
* [Assignment/Version 2](#assignment-2)
* [Assignment/Version 3](#assignment-3)
* [Assignment/Version 4](#assignment-4)

## Assignment 2 
In this version, the basics of the game such as players able to build and upgrade elements of the town is implemented. Users can build and upgrade but they cannot attack anyone else.

## Assignment 3 
More features added. Players can explore, see stats and choose opponents to attack or not. The opponents are bots that are created based on the army and defence of the player so that bots scale with players' level and power. The players do not participate or have any control over the battles. The battle outcomes are determined but given algorithms and needed to be used strictly without any modifications (requirements for this assignment). Therefore, the player can only see the outcome of the battle and how much they gain/lost after each battle. If they win, they will gain lots of resources and maintain their army (with a few losts), or if they lose, they will gain little to no resources (depend on hard or close they lose) and lose their entire army so they can't continue to attack unless they re-build. 

## Assignment 4
In this final version, the program runs as a server-client where multiple clients can simultaneously connect to the server and play the game. However, there isn't any PVP mode. This version's purpose is to demonstrates the ability of student to write server-client based system through TCP connection.
