# -*- coding: UTF-8 -*-

import random

def startGame():
    car = random.randint(1,3)
    print("=================================")
    firstChoice = int(input("Chose a door to open (1, 2, 3): "))
    allDoors = [1, 2, 3]
    allDoors.remove(firstChoice)
    hostChoice = random.choice(allDoors)
    allDoors.remove(hostChoice)
    hostAnotherChoice = allDoors[0]
    if hostChoice == car:
        hostChoice = hostAnotherChoice
    lastChoice = [1, 2, 3]
    lastChoice.remove(firstChoice)
    lastChoice.remove(hostChoice)
    print("Goat behind the door " + str(hostChoice))
    switchOrNot = input("Switch to door " + str(lastChoice[0]) + "? <Y/N>: ").upper()
    if switchOrNot == "Y":
        firstChoice = lastChoice[0]
    if firstChoice == car:
        print("You Win!")
    else:
        print("You lose!")
    anotherGame = input("Do you wanna try once more? <Y/N>: ").upper()
    if anotherGame == "Y":
        startGame()
    else:
        print("Goodbye~")

def autoGame(choice, switch):
    car = random.randint(1,3)
    firstChoice = int(choice)
    allDoors = [1, 2, 3]
    allDoors.remove(firstChoice)
    hostChoice = random.choice(allDoors)
    allDoors.remove(hostChoice)
    hostAnotherChoice = allDoors[0]
    if hostChoice == car:
        hostChoice = hostAnotherChoice
    lastChoice = [1, 2, 3]
    lastChoice.remove(firstChoice)
    lastChoice.remove(hostChoice)
    switchOrNot = str(switch).upper()
    if switchOrNot == "Y":
        firstChoice = lastChoice[0]
    if firstChoice == car:
        return 1
    else:
        return 0

def getProbality(times):
    resultOfNotSwitch = []
    resultOfDoSwitch = []
    for i in range(int(times)):
        resultOfNotSwitch.append(autoGame(random.randint(1,3),"N"))
        resultOfDoSwitch.append(autoGame(random.randint(1,3),"Y"))
    print("[Not switch] Win: " + str(resultOfNotSwitch.count(1)) + ", Lose: " + str(resultOfNotSwitch.count(0)))
    print("[Do  switch] Win: " + str(resultOfDoSwitch.count(1)) + ", Lose: " + str(resultOfDoSwitch.count(0)))

if __name__ == "__main__":
    startGame()
    # getProbality(100000)