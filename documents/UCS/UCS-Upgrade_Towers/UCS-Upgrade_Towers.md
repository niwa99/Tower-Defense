# 1 Use-Case Name: Upgrade Towers

## 1.1 Brief Description
Every built tower can be upgraded in order to increase some of the stats of this tower.
For upgrading a tower, ingame money is needed.
Visually, such upgrades should be recognizable by stars which are located on top of a tower.

# 2 Flow of Events
## 2.1 Basic Flow
- User clicks on an already focused tower
- a popup menu opens which contains the tower stats, the upgraded tower stats and the option to upgrade the tower
- User clicks on the upgrade button
- a star appears on top of the clicked tower and on the tower image in the popup
- the stats of the tower change
- the value of the tower increases
- the new values are shown in the popup
- the popup can be closed by clicking on the "close" button

### 2.1.1 Activity Diagram
![Activity Diagram for UpgradeTowers](./Activity_Diagram-Upgrade_Towers.jpg)

### 2.1.2 Mock-up
![Mockup for UpgradeTowers](./Mockup-Upgrade_Towers.jpg)

### 2.1.3 Narrative
(n/a)

## 2.2 Alternative Flows
- if the tower is already once upgraded, there will be another golden star added when upgrading the tower
- if the tower is already upgraded up to its maximum level, the upgrade button will be disabled so it is not possible to upgrade the tower once more

# 3 Special Requirements
(n/a)

# 4 Preconditions
## 4.1 A tower has to be built
Before it is possible to upgrade a tower it is necessary to build at least one tower.

## 4.2 Necessary money is needed
Before it is possible to upgrade a tower it is necessary to have enough money to buy the upgrade.

## 4.3 A tower must be selected
To open the necessary popup for upgrading a tower, the tower has to be focused by clicking on it.
The focus is recognizable by the displayed range of the tower.
Click once more on the selected tower to open the tower information popup.

# 5 Postconditions
(n/a)
 
# 6 Extension Points
(n/a)
