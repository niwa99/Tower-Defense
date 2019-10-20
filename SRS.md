# Tower Defense - Software Requirements Specification

## Table of Contents

-   [1. Introduction](#1-introduction)

    -   [1.1 Purpose](#11-purpose)
    -   [1.2 Scope](#12-scope)
    -   [1.3 Definitions, Acronyms and Abbreviations](#13-definitions-acronyms-and-abbreviations)
    -   [1.4 References](#14-references)
    -   [1.5 Overview](#15-overview)

-   [2. Overall Description](#2-overall-description)

    -   [2.1 Project Vision](#21-project-vision)

-   [2.2 Product perspective](#22-product-perspective)

    -   [2.3 User characteristics](#23-user-characteristics)
    -   [2.4 Dependencies](#24-dependencies)

-   [3. Specific Requirements](#3-specific-requirements)

    -   [3.1 Functionality](#31-functionality)

        -   [3.1.1 Startscreen](#311-startscreen)
        -   [3.1.2 Gamescreen - Structure](#312-gamescreen---structure)
        -   [3.1.3 Gamescreen - Actions](#313-gamescreen---actions)
        -   [3.1.4 Settings Menu](#314-settings-menu)
        -   [3.1.5 Customize Enemies, Towers and the Map](#315-customize-enemies-towers-and-the-map)

    -   [3.2 Usability](#32-usability)

    -   [3.3 Reliability](#33-reliability)

        -   [3.3.1 Publishing of Updates](#331-publishing-of-updates)
        -   [3.3.2 Availability](#332-availability)

    -   [3.4 Performance](#34-performance)

    -   [3.5 Supportability](#35-supportability)

    -   [3.6 Design Constraints](#36-design-constraints)

    -   [3.7 Online User Documentation and Help System Requirements](#37-online-user-documentation-and-help-system-requirements)

    -   [3.8 Purchased Components](#38-purchased-components)

    -   [3.9 Interfaces](#39-interfaces)

        -   [3.9.1 User Interfaces](#391-user-interfaces)
        -   [3.9.2 Hardware Interfaces](#392-hardware-interfaces)
        -   [3.9.3 Software Interfaces](#393-software-interfaces)
        -   [3.9.4 Communications Interfaces](#394-communications-interfaces)

    -   [3.10 Licensing Requirements](#310-licensing-requirements)

    -   [3.11 Legal, Copyright and other Notices](#311-legal-copyright-and-other-notices)

    -   [3.12 Applicable Standards](#312-applicable-standards)

-   [4. Supporting Information](#4-supporting-information)

## 1. Introduction

### 1.1 Purpose

This document provides a general overview and a detailed description of the Tower-Defense project. In order of achieving this, it explains the vision or rather the purpose and all the features of the product. This document offers insights into the single steps of the project-development.

### 1.2 Scope

The document should be used for internal purposes only.

### 1.3 Definitions, Acronyms and Abbreviations

| Term     |                                     |
| -------- | ----------------------------------- |
| **SRS**  | Software Requirements Specification |
| **TD**   | Tower Defense                       |
| **UCD**  | Use Case Diagram                    |
| **FPS**  | Frames Per Second                   |
| **tbd**  | to be determined                    |


### 1.4 References

| Title                                                                                                 | Date       |
| ----------------------------------------------------------------------------------------------------- | ---------- |
| [Blog](https://dh-towerdefense.de/)                                                                   | 20/10/2019 |
| [GitHub](https://github.com/niwa99/Tower-Defense)                                                     | 20/10/2019 |
| [Jira](http://jira.dh-towerdefense.de)                                                                | 20/10/2019 |
| [Use Case Diagram](https://github.com/niwa99/Tower-Defense/blob/master/UCD-2019-10-20.png)            | 20/10/2019 |

### 1.5 Overview

Beside a conceptional overview of the project, the perspective on single requirements are demonstrated within the following chapters.

## 2. Overall Description

### 2.1 Project Vision

After two semesters of development, an Android Tower-Defense-game-app with the following functionalities should be designed.
Beginning on a startscreen, one can start the Tower-Defense-game. Being on the game-screen, one will be able to see a grid consisting of many tiles. Some tiles are protected and represent the way which enemies will walk on and some tiles will be editable so that a player can build towers on it.
The whole game consists of “waves”. In a wave, a specific amount of enemies approach and try to reach the goal. The player needs to build towers so that enemies become killed before they reach the goal.
Probably, there will be different enemies with certain speed and health points. Also, it is planned to have different towers which distinguish in their fire rate, range and strength.
On top of those basic features, custom editing functionalities for enemies, towers and the map-structure are planned.


## 2.2 Product perspective

Our Use-Case-Diagram

![UseCaseDiagram](https://github.com/niwa99/Tower-Defense/blob/master/UCD-2019-10-20.png)

### 2.3 User characteristics

Our target group is pretty wide-ranging from teenagers up to older adults. In order to have fun playing this game, tactical thinking is required. Different levels provide a great challenge for people at any age.

### 2.4 Dependencies

Tower Defense does not require any additional software. One will be able to download the app from a website or from the Google Play Store which only requires a current smartphone running Android 7.0 or higher.

## 3. Specific Requirements

### 3.1 Functionality

#### 3.1.1 Startscreen

The start screen functions as a landing page when opening the app. It should contain a gameplay image as background, a “start” button which forwards to the game page and a menu button which will pop up a settings-menu.

#### 3.1.2 Gamescreen - Structure

The gamescreen shows the map, a button for the settings menu and a top bar with some game information (lives, money, …).

#### 3.1.3 Gamescreen - Actions

A player should be able to build, remove and upgrade towers. Those actions should be doable by clicking on a map-tile. Moreover, a button for skipping to the next wave could be useful.

#### 3.1.4 Settings Menu

tbd

#### 3.1.5 Customize Enemies, Towers and the Map

tbd

### 3.2 Usability

The Android application will not require any practice or tutorial because of its user-friendly design.

### 3.3 Reliability

#### 3.3.1 Publishing of Updates

Updates may only become published if all new features have been tested in order to guarantee full functionality. Updates will never be mandatory.

#### 3.3.2 Availability

Due to the fact that Tower Defense becomes published in the Google Play Store, the availability belongs to Google. For playing the game, network connection is not required.

### 3.4 Performance

The app must be runnable fluently (FPS: 30 or more) on current Android devices.
tbd

### 3.5 Supportability

Common conventions (such as Java conventions) are used.
While having code reviews, the quality of code is kept high.

### 3.6 Design Constraints

Tower Defense is developed with Java, XML and the Android framework wherefore the app will only be runnable on Android devices.

### 3.7 Online User Documentation and Help System Requirements

N/A

### 3.8 Purchased Components

Since we create our app including the designs completely by ourselves, there is no need for any commercial licenses or components except the Google Developers Account in order to publish the app in the Google Play Store.

### 3.9 Interfaces

#### 3.9.1 User Interfaces

tbd

#### 3.9.2 Hardware Interfaces

tbd

#### 3.9.3 Software Interfaces

tbd

#### 3.9.4 Communications Interfaces

tbd

### 3.10 Licensing Requirements

tbd

### 3.11 Legal, Copyright and other Notices

tbd

### 3.12 Applicable Standards

tbd

## 4. Supporting Information

tbd
