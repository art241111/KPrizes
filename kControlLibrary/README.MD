# CONTROL SYSTEM FOR KAWASAKI ROBOTS
This library is designed to simplify the connection and control of the Kawasaki robot.

## Usage
Quick Usage  
Step 1 - Download or Clone the library (using Git, or a zip archive to unzip)  
Step 2 - Open your project in Android Studio  
Step 3 - Go to File > New > Import Module  
Step 4 - Find and select kControlLibrary in your project tree  
Step 5 - Right-click your app in project view and select "Open Module Settings"  
Step 6 - Click the "Dependencies" tab and then the '+' button (Module Dependency)  
Step 7 - Select "kControlLibrary"

## At first
To use this library, you need to download the program to the robot,
which is located in the root folder of the programs.

## Connect to the robot
In order to connect to the robot, you need to create the KRobot() class and then
call the connect method and pass the ip and port
```kotlin
    val robot = KRobot()
    robot.connect(ip, port)
```

## Tracking the connection status
To track the connection status, you need to subscribe to updates in a new thread. 
It is recommended to create a new thread using the ViewModel Scope, 
so that at the end of the lifecycle, tracking is stopped. 
It is also important to understand that this way you can hang as many
listeners as you want and you need to make sure that the same thing is not created

Example of creating a connection function with connection status tracking:
```kotlin
    val robot: KRobot =  KRobot()
    private var _connectStatus = mutableStateOf(Status.DISCONNECTED)
    var connectStatus: State<Status> = _connectStatus

    private var isFirstConnect = true
    fun connect(ip: String, port: Int = 49152) {
        viewModelScope.launch(Dispatchers.IO) {
            connectRobot.connect(ip, port)
        }

        if (isFirstConnect) {
            isFirstConnect = false

            viewModelScope.launch(Dispatchers.IO) {
                robot.statusState.collect { status ->
                    if (status != null) {
                        _connectStatus.value = status
                    }
                }
            }
        }
    }
```

## Tracking the robot's position
In this case, everything is similar to tracking the connection status, 
only you need to look at the positionState variable.

There is actually a second way to track coordinates if you use Jetpack Compose. 
In this case, the coordinate field will always contain the last version of 
the position and the UI will be updated when the data changes.
```kotlin
    val coordinate = robotVM.coordinate.collectAsState()
```

## First program
The library uses a DSL approach. To create your first program, you need to write::
```kotlin
    kProgram {
        // You program
     }
```

## Move commands
In this library, you can move the robot in 4 ways:
* Moving to the point
  To create this command separately:
```kotlin
    // Full-fledged challenge
    MoveToPoint(point, typeOfMovement)    

    // Infix call
    Move to point
```
  To use the command in the program
```kotlin
    kProgram {
        moveToPoint(point, typeOfMovement)

        // Infix call
        Move to point
    }
```
* Moving by a single coordinate
  To create this command separately:
```kotlin
    // Full-fledged challenge
    MoveByAxes(axes, distance)    

    // Infix call
    Move by Axes.X on 100.0
```
To use the command in the program
```kotlin
    kProgram {
        moveByAxes(axes, distance)
    
        // Infix call
        Move by Axes.X on 100.0
    }
```
* Moving across multiple coordinates
  To create this command separately:
```kotlin
    // Full-fledged challenge
    MoveOnDistance(x = 10.0, z = -10.0)
```
To use the command in the program
```kotlin
    kProgram {
        moveOnDistance(x = 10.0, z = -10.0)
    }
```
* Move the distance from the point
  To create this command separately:
```kotlin
    // Full-fledged challenge
    DepartPoint(point = point, dx = 10.0, dz = -10.0)
```
To use the command in the program
```kotlin
    kProgram {
        departPoint(point = point, dx = 10.0, dz = -10.0)
    }
```

## Gripper commands
*To open the gripper
To create this command separately:
```kotlin
    // Full-fledged challenge
    OpenGripper()
```
To use the command in the program
```kotlin
    kProgram {
        openGripper()
    }
```

*To close the gripper
To create this command separately:
```kotlin
    // Full-fledged challenge
    CloseGripper()
```
To use the command in the program
```kotlin
    kProgram {
        closeGripper()
    }
```

## Delay
To create this command separately:
```kotlin
    // Full-fledged challenge
    Delay(100L)
```
To use the command in the program
```kotlin
    kProgram {
        delsy(100L)
    }
```

## Waiting the signal
To create this command separately:
```kotlin
    // Full-fledged challenge
    WaitingSignal(10)
```
To use the command in the program
```kotlin
    kProgram {
        waitingSignal(10)
    
        // Infix 
        waitingSignal 10
    }
```

## To send a signal
To create this command separately:
```kotlin
    // Full-fledged challenge
    Signal(10)
    Signal(-10)
```
To use the command in the program
```kotlin
    kProgram {
        signalOn(10)
        signalOff(10)
    
        // Infix 
        signalOn 10
        signalOff 10
    }
```



## License
Copyright © 2021 <copyright holders>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
