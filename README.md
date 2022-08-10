# RPiBotLib
## Introduction
> RPiBotLib is an open-sourced and simple to use LibGDX and Pi4J based library that uses the Raspberry Pi GPIO to be able to control robots or other electronics such as motors or sensors. Similarly to the FRC WPILib, the RPIBotLib functions on a time based runtime, as well as a similar user-side programming process. 

## Getting Started
### Pre-Requirements
> For optimal use, it is suggested to use IntelliJ IDEA to develop with RPIBotLib. Once the environment is set up, you may clone the RPiBotLibTemplate project and open it in your IDE.
> + Download the latest version here: [Download IntelliJ IDEA: The Capable & Ergonomic Java IDE by JetBrains](https://www.jetbrains.com/idea/download/#section=windows)
>
> You will need to install the **latest Java version**. To do this, run the following command on your Raspberry Pi:
> + `$ sudo apt update`
> + `$ sudo apt install default-jdk`
>
> The **pigpio library** will also need to be installed. To do this, run the following command on your Raspberry Pi:
> + `$sudo apt install pigpio python-pigpio python3-pigpio`
>
> Once the project is cloned, you may open the **core/src** folder, then go to **Robot.java**. This is where you will focus to program your robot.
>
> It is recommended to set up **VNC Viewer** onto your raspberry pi in order to be able to remote control it from your PC. 
> + visit: [How To Set Up VNC Connect on Raspberry Pi - RealVNC](https://www.realvnc.com/en/blog/how-to-setup-vnc-connect-raspberry-pi/)
> 
> ***In order to remote control your raspberry pi, you will need both devices under the same internet connection.***

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
> + **Teleop:** where the user is able to take control of the robot via a joystick or controller.
> + **Autonomous:** will allow the user-programmed autonomous code to run. By default, enabling this mode will not do anything unless the user fills in the `autonomousPeriodic()` method.
> + **Enable**
> + **Disable** 
>
> These buttons allow the user to select their **robot mode** and **state** along with a text indicator that displays the current setting of the robot. Above the buttons there is also a section which shows the current connected controller's **stick values** with both their X and Y axis.
> 
>   On its right there is a sensor section which will allow a **maximum of 3 sensors** to be added. The sensor section will only display the sensors when they are manually added via code using `DriverStation.addSensor(Sensor sensor)`. This method is recommended to use in `robotInit()`
>
> When a sensor is added, the driver station will display its name as well as its returned value. 
>
> As stated before there is a third robot state known as kill, to set this mode simply press **k.**
>
> The current robot mode will always be indicated in **green.**
>
### Fundamentals to getting a working robot
> ### LocalXboxController
> `LocalXboxController` is a wrapper class of the `Controller` class found in LibGDX. This class will allow for easy access to the left and right axis values found on a controller as well as a boolean value for when **A, B, X, or Y** is pressed. The axis will return a double, ranging from **-1 to 1** with decimals included. The class must be instantiated in `Robot.java`. Once instantiated, the program will search for a connected controller. Please ensure your raspberry pi is connected to a controller prior to running your program.
>
> ### Context
> The Context class is a PI4J class which is used to be able to control the raspberry pi GPIO. This class is mandatory to instantiate in order to be able to execute your program. The best place to instantiate this class is in `robotInit()`. No parameters are needed in order to instantiate this class. 
> + For more inforation on the PI4J library, visit: [Welcome to Pi4J](https://pi4j.com/)
> 
> ### PwmConfig, DigitalOutputConfig, and DigitalInputConfig
> These classes are wrapper classes of the Pi4J config classes used to declare a new pin for pwm or digital input/output use. These wrappers allow a simple use of the Pi4J classes. The wrapper classes contain a method that return a dedicated object of said configuration. The digital input/output method will require a Pi4J context object, a pin address, and a pin ID in the form of a string and a name. The pwm config method will require a context object, a pin address, and a pwm type whether it be digital or hardware. 
> + Learn more on the Raspberry Pi pins here: [Understanding the GPIO pins - Pi4J](https://pi4j.com/getting-started/understanding-the-pins/)
> 
> ### DualHBridgeController
> The Dual H Bridge class is designed for dual h devices like the I298N motor controller. This class allows for up to **2** motors to be added to the motor controller, and will require the user to manually configure these motors. `The DualHBridgeController` object will take in a Pi4J context object as well as the forward and back channel for both motors. To properly configure any motor in this object, you must call the `configMotor1PWM` or `confiMotor2PWM` methods which will take in a pwm config object.
>
> ### DifferentialDrive
> The `DifferentialDrive` class will handle all the logic needed to drive your robot. The class constructor takes in 2 motor controllers (left and right) and had the `arcadeDrive()` method which takes in an x and y value (x being the turning and y being forward/backward) as parameters. The `arcadeDrive()` method should be called in `teleopPeriodic()`.
> 
> ### TimedAutoBase
> this class is used to develop timed-based autonomous code. It is simple to use as you only need to instantiate it and use the `addCommand()` method to program your autonomous. Once done, simply call the `runAuto()` method in `autonomousPeriodic()`.
> 
> ### TimedCommand
> This class is used to create new commands to add to the `TimedAutoBase` list of commands. The constructor takes a `DifferentialDrive` object as well as an x and y value and the duration in seconds (double) or milliseconds (long) for the command to be executed for. 
> 
### Running your project
> ### Creating a jar
> Once you have finished programming your robot, you will need to create a jar for it. Doing this is simple, all you need to do is select the gradle icon on your IDE -> "Execute Gradle Task" -> "gradle: :desktop:dist". Once done you should find a jar file under "desktop/build/libs" in your project. 
> 
> ### Running your jar
> After you have created your jar all you have to do is run it on your raspberry pi. ***you will need to run this project as sudo in order to be able to initialize pigpio***
> 
> After you open a new terminal you can do: 
>
> + `$ sudo java -jar jarfilename.jar`
> 
> or
> 
> + `$ sudo su`
> 
> + `$ java -jar jarfilename.jar`
> 
>***NOTE: ensure that you have your raspberry pi plugged in to a monitor before running your program. Once the program runs, you may disconnect the raspberry pi from the monitor***
> 
### Updating to newer version
>To update your RPiBotLib version, you can simply go to `gradle/build.gradle`, then go to your dependencies for both the core and desktop project and change the RPiBotLib dependency from:
> 
> `implementation 'com.github.Ghozti:RPiBotLib:CurrentVersion'` 
> 
> to:
> 
> `implementation 'com.github.Ghozti:RPiBotLib:LatestGitHubVersion'`

### Version Logs
>#### ***v1.0.0*** - provides a basic library with everything needed to run a simple robot with autonomous capabilities