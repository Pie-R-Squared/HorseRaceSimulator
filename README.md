# HorseRaceSimulator

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
<<<<<<< HEAD
    <li><a href="#set-up">Set Up</a>
      <ul>
        <li><a href="#dependencies">Dependencies</a></li>
        <li><a href="#gui-installation">GUI Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage-guidelines">Usage Guidelines</a></li>
=======
    <li><a href="#setup">Set Up</a>
      <ul>
        <li><a href="#dependencies">Dependencies</a></li>
        <li><a href="#installation">GUI Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage Guidelines</a></li>
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

![image](https://github.com/Pie-R-Squared/HorseRaceSimulator/assets/152538125/cf982694-9a60-4a10-ae9e-a0c3fd918698)

![image](https://github.com/Pie-R-Squared/HorseRaceSimulator/assets/152538125/7c104700-c48b-4473-a31e-8c1159c8f716)

![image](https://github.com/Pie-R-Squared/HorseRaceSimulator/assets/152538125/4e534be4-f7a3-4fbd-afac-edd622bb3178)

![image](https://github.com/Pie-R-Squared/HorseRaceSimulator/assets/152538125/ff42cbd1-cf5b-4ab4-b574-5390feea8c84)


Horse Race Simulator project, consisting of part 1 (textual race simulator) and part 2 (GUI development). Part 1 includes the Horse class and the modified Race class, along with documentation. Part 2 includes all GUI-related classes providing a user-friendly interface that expands on part 1.

<!-- SETTING UP -->
## Set Up

1. **Operating System**:
   - Windows 10 or later
   - MacOS 10.14 or later
2. **Applications**:
   - Java Development Kit (JDK)
   - Java Runtime Environment (JRE)
  
## Dependencies
This application requires no additional dependencies, relying solely on those provided in the JDK.

<<<<<<< HEAD
## GUI Installation
=======
### GUI Installation
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c

1. Clone the repository
   ```sh
   git clone https://github.com/Pie-R-Squared/HorseRaceSimulator.git
   ```
2. Navigate to project directory
   ```sh
   cd HorseRaceSimulator
   ```
3. Compile GUI class
   ```sh
   javac HorseRaceSimulatorGUI.java
   ```
4. Run the application
   ```sh
   java HorseRaceSimulatorGUI
   ```


<!-- USAGE EXAMPLES -->
## Usage Guidelines
![image](https://github.com/Pie-R-Squared/HorseRaceSimulator/assets/152538125/0c14e1fe-64fe-4ed5-9750-477c750a434d)

After running the application, initiate the race by clicking on the 'Start Race' button. This image portrays the main screen of the application, and closing this will close all other open windows and terminate the app. A series of buttons open extra windows, but attempting to click a button twice just closes and reopens the corresponding window.

![image](https://github.com/Pie-R-Squared/HorseRaceSimulator/assets/152538125/d1b03894-70de-4caa-979d-4856ded7b96e)

The 'Customise' button opens a new window allowing you to specify custom racelength and number of tracks. Track colour can be selected by clicking the button with the paintbrush icon, which opens a colour select panel. Below this, users can select from a number of different horse breeds, coat colours and associated accessories of each horse. Additionally, users can choose to set a name and confidence level for a horse then add it to the race.

Users must press 'Save' to apply the customisations. To ensure this is not forgotten, the button will be highlighted upon making a change. Racelength is limited to between 2 and 30 metres, while tracks are limited to between 2 and 14, inclusive. This is to prevent performance issues from having a large number of tracks on screen at once.

![image](https://github.com/Pie-R-Squared/HorseRaceSimulator/assets/152538125/547e7fdf-f3d8-4a00-a949-7443fc3cec45)

The 'Statistics' button opens a window displaying a textual view of the race along with confidence levels of each horse. The past performance of horses are also displayed. Note, adding a new horse means they won't be displayed with a history until they have partaken in at least one race. The history shows metrics such as wins, falls, average speed and average finish times across races. It also displays the previous betting odds, which is independent of track conditions but accounts for all performance metrics. When a race is started, progress bars indicate distance travelled by the horse as a percentage of the total distance, accompanied by average speed during the race and finish times (when the race ends).

![image](https://github.com/Pie-R-Squared/HorseRaceSimulator/assets/152538125/15b6ac1c-f98e-4e86-be88-abeee849952d)

For a more accurate and dynamic display of the betting odds, which includes track conditions and metrics, the betting window is available. Track conditions are listed at the top, below the current balance. The user has a default balance of 1000 horseshoes (a virtual currency), which they can use to bet on races. Betting has limitations so that it may not exceed the current balance. Error checking is implemented in all input fields so users cannot enter non-numerical data into them where required.


<!-- CONTACT -->
## Contact

Aneeka Ahmad - ahmadaneeka@outlook.com

Project Link: [https://github.com/Pie-R-Squared/HorseRaceSimulator](https://github.com/Pie-R-Squared/HorseRaceSimulator)
