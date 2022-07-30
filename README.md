# RPiBotLib
## Introduction
> A open-sourced and simple to use LibGDX and Pi4J based library that uses a Raspberry Pi GPIO to be able to control robots or other electronics such as motors or sensors. Similarly to the FRC WPILib, the RPIBotLib functions on a time based runtime, as well as a similar user-side programming process. 

## How To Use It
### Pre-Requirements
> For optimal use, it is suggested to use IntelliJ IDEA to develop with RPIBotLib. Once the environment is set up you may clone this project into your IDE.
> + Download the latest version here: [Download IntelliJ IDEA: The Capable & Ergonomic Java IDE by JetBrains](https://www.jetbrains.com/idea/download/#section=windows)
>
> You will need to install the **latest Java version**. To do this, run the following command on your Raspberry Pi:
> + `$ sudo apt update`
> + `$ sudo apt install default-jdk`
>
> The **pigpio library** will also need to be installed. To do this, run the following command on your Raspberry Pi:
> + `$sudo apt install pigpio python-pigpio python3-pigpio`
>
> Once the project is cloned, you may open the **RPIBotLib folder** then go to **core/src/Robot**. This is where you will find `Robot.java` and where you fill focus on to program your robot. While the entire project is available, it is not recommended for users to alter or change the source code of the library unless they are familar with the LibGDX and Pi4J libaries. Altering any other class except `Robot.java` could cause critical errors.

### Understanding The Library
> `robotInit()` is a method which is called once at the start of the program. This is where it is suggested to declare all motor controllers, sensors, and etc. Essentially this method works as a constructor.
>
> `robotPeriodic()` is a method which is called periodically at all times, this method will still run regardless of the driver station being enabled or disabled.
>
> `autonomousPeriodic()` is a method which is called periodically much like `robotPeriodic()` except it only runs while the robot mode is set to autonomous and the robot state is enabled.
>
> `teleopPeriodic()` is similar to `autonomousPeriodic()` but it will only run when the robot mode is set to tele-op and the robot state is enabled.
>
> `robotShutDown()` is similar `robotInit()` except it will only run when the driver station state is set to **"kill"**. By default, `robotShutDown()` will cancel any ongoing method and will suspend the program. 
> 
### Understanding The Driver Station
>![DriverStation](https://cdn.discordapp.com/attachments/412278280180203552/1002756776724271195/Capture.PNG)
>
> The driver station has several features to it, including 4 functional buttons:
> + **Teleop, Auto, Enabled,** and **Disabled**
>
> These buttons allow the user to select their **robot mode** and **state**. On the right, there is a text indicator that displays the current setting of the robot. Above the buttons there is also a section which shows the current connected controller's **stick values** with both their X and Y axis. On its right there is a sensor section which will allow a **maximum of 3 sensors** to be added. The sensor section will only display the sensors when they are manually added via code using `DriverStation.addSensor(Sensor sensor)`. This method is recommended to use in `robotInit()`
>
>
>
>

