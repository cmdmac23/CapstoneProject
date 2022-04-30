# CapstoneProject

# CapstoneProjectPublic

## Introduction
The inFocus Scheduling Application allows users to create an account in 
which they can create planner entries, to-do list entries, and earn rewards.
Users can easily track their schedules by creating and complete planner 
entries. Users can also create and completing to-do lists for a set list
they would like to complete. By completing planner entries and to-do list items,
users will be rewarded with points they can use to unlock a reward all in the
application. Users can also share planner entries and to-do lists with other
users for collaborative access to schedules and lists. 

## Important Notes
1. As this repository is public, the code seen here is a fresh version of our code with sensitive information removed (API password). If you download and run this code in the current state it will not work. The password for the API must be put in the ApiManagement class on line 15.
2. The application will only have access to the necessary database and API until Sunday, May 22, 2022. After this date, our hosting service will expire and the application will no longer function.

## Requirements
This application requires the following:<br />
- Android Studios: <br />
	- Users will also be required to create an Android Virtual Device (AVD) in order to compile the application if they do not have an Android device. <br />
		- Recommended AVD: Google Pixel 3a XL <br />
		- Recommended System Image: R x86 Android 11.0. <br />
- The API password (not included in this Repository) in order for the application to launch.
                                 
## Installation
1. Download Android Studios .exe file (Windows) or .dmg file (Mac) from this <a href="https://developer.android.com/studio/?gclid=Cj0KCQjwma6TBhDIARIsAOKuANw2pBTf4ETZJLEYHf40gcHuxSlgHmJunJ8d4taxOUJyaeC3xMJ_2bcaAoItEALw_wcB&gclsrc=aw.ds/">link</a>
if you do not have Android Studios installed on your computer.
2. Once you download the file, click on the .exe or .dmg file and follow the setup wizard (Windows) or setup assistant (Mac) to install Android Studios. <br />
	- Install any recommended SDK packages.
4. Once Android Studios is installed, open a blank project. (This is to set up your Android Device or Android Virtual Device). <br />
	- If you have an android device, connect it to your computer via a USB cable and make sure you can select it from the device menu (usually located near the top right corner). <br />
	- If you do not have an Android device, find the "AVD Manager" button to open the menu. Select the "Create Virtual Device". <br />
	- Choose the type of AVD you want to trun on the emulator (we recommend Pixel 3a XL), then select the system image of choice (we recommend R x86 Android 11.0), and select "Finish". <br />
		- You will need to wait for your system to create and install the AVD. <br />
	- On the AVD manager menu, select the triangular "Play" button under "Actions" to make sure your emulator runs. <br />
		- If the emulator will not run, check SDK and storage settings on your computer. <br />

## Configuration
1. Now that you have created a virtual device, you can close the blank project in Android Studios.<br />
2. Navigate to the Github repository, select the green "Code" button to open a drop-down menu.<br />
 You may download the project as a ZIP file or clone the project via the HTTPS in Android Studios.<br />
	- ZIP File Instructions <br />
		- After selecting the "Download ZIP" button in github, navigate to the Downloads file on your computer. <br />
		- Extract all of the files and make sure there is no white space in the extraction folder name. <br />
		- Open Android Studios, select "Import Project", and navigate to the extracted project file. <br />
		- The project will open in Android Studios. <br />
	- Clone via HTTPS <br />
		- Open Android Studios, select "Import Project from Version Control".<br />
		- Copy the "HTTPS" link under the green "Code" button drop-down menu on GitHub.<br />
		- Paste the "HTTPS" link into the "URL" section in the pop-up in Android Studios, then choose the clone button.<br />
		- The project will open in Android Studios. <br />
			- *Note you may have to log into your GitHub Account in Android Studios to clone the project.* 
3. If you have Gradle installed on Android Studios (probably during the installation process), the application files will load automatically to Android Studios. <br />
	- Otherwise, make sure you select the "Gradle" option to run the files. <br />
	- Any errors with Gradle are shown in the event log with recommended downloads to fix the problems. Troubleshoot with Android Studios recommendations. <br />
4. When you are ready to start the application, look for a tab on the top of Android Studios with the word "app", next to that button your Android Device or Android Virtual Device should be showing. <br />
	- The AD or AVD is a drop-down menu that allows you to choose a device if you have multiple ADs or AVDs. <br />
5. To run the application, click on the green "Play" button next to your Device. The emulator should launch and the application will automatically open on the emulator's screen. <br />
6. When the application is launched, follow these steps to navigate the application: <br />
	- Once the login page is showing on your emulator screen, click on the "Create Account" button and input an email address, a username, and a password. Upon completing these steps, you will be prompted back to the login screen where you will use the username and password you just created to login to the application. <br />
		- If you forget your password, click the "Forgot Password" button and input your account email. You will be sent an email that will allow you to change your password in the application. <br />
			- *Note: You will have to login to your email on your Android Device or Android Virtual Device in order to reset your password in the application.* <br />
	- Homepage: <br /> When you login to the application, you will be brought to the welcome screen that will automatically pre-load your planner entries for that day when you begin using the planner. You will also see your total points that you will earn by completing planner entries and to-do list items. <br />
	- Planner: <br /> To navigate through the application, click the three lines in the top left corner for a navigation menu. You will see  four options: Home, Planner, To-Do List, Rewards, and Settings. Choose "Planner" first. <br />
		- On the Planner main page, you will see a calendar, an "Add New" button, and an area where planner entries will be displayed as they are created.<br />
			- *Note: You can select any date on the calendar to see future, past, or current entries.*<br />
		- Choose a date on the calendar to create an entry and click the "Add New" button.<br />
		- You will be brought to the "Create Entry" page. Enter an entry title, a description, select a group for this entry (Work, Personal, Other), select a date and time for the entry, select a difficulty (correlates to amount of points earned for completing entry), select a location if needed, enable a reminder if needed, and share the event with another (existing) user if needed. Choose the "Create" button upon entering appropriate fields for your entry.<br />
		- You will be navigated back to the Planner main page. Select the day you input during the creation of the entry and you will see the entry on your screen.<br />
		- Click on the entry title you created for all details (description, group, difficulty, reminder, etc.) to be displayed. You will also be able to select the check box to indicate you have completed the planner entry and will be rewarded points. <br />
	- To-Do Lists: <br /> Click the three lines in the top left corner for the navigation menu. Choose the "To-Do Lists" this time. <br />
		- On the To-Do List main page, you will see an "Add New List" button and an area that will be populated with lists as they are created. <br />
		- Select the "Add New List" button and you will be brought to the "Create List" page. <br />
		- Enter a list title, select a group (work, personal, other), select the share switch and share list with another (exising) user if need, click the "New Item" button, select the "Create" Button. <br />
			- Upon clicking the "New Item" button, a pop-up window will prompt you for the item name, enter the item name, select the "Add Item" button on the left. A second pop-up window will prompt you for the item difficulty on a slide bar. After selecting the difficulty (1-5), select the "Add Difficulty"  button on the right. Repeat as many times as needed. <br />
		- You will return to the To-Do List main page. Click on the title of the list you created and the list items, difficulty, and a checkbox next to each item will populate the screen. Check the boxes as you complete the items on the list and you will be given points. <br />
	- Rewards: <br />Click on the three lines in the top left corner for the navigation menu. Choose the "Rewards" this time. <br />
		- On the Rewards main page, you will see an empty screen besides a "Your Rewards" title and an "Unlock Plant" button.<br />
		- You may click the "Unlock Plant" button and will be navigated to a screen with an animated sprout called plant. <br />
			- *Note: You will see that you have zero points. It is recommended you create a to-do list with items with a  selected difficulty of 5 and check off those items on the list to quickly earn points for the sake of this  tutorial. However, if you are using a pre-existing account for the tutorial, you will have a determined amount of points.* <br />
		- The pre-selected plant is a sprout, but you will have five choices of plants. <br />
			- 1. Sprout - 10 points <br />
			- 2. Aloe - 15 points <br />
			- 3. Flowers - 20 points <br />
			- 4. Cactus - 25 points <br />
			 - 5. Lilies - 30 points <br />
		- Select your desired plant and enter a name for your plant. Choose the "Unlock" button to redeem your reward.<br />
		- Now, you can see that you have redeemed a plant on the Rewards main page. <br />
	- Click on the three lines for the navigation menu. Choose "Settings" this time. <br />
		- You have two choices on this screen: update your email or change your password. <br />
		- Select the "Update Email" button where you will be prompted for a new email address. <br />
		- Select the "Change Password" button where you will be prompted to change your account password.<br />
7. Congratulations! You have successfully run the inFocus Scheduling Application.
