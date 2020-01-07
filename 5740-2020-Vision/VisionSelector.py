import ntcore 

from networktables import NetworkTables


# Inits Netowktables and connects to robo Rio server

NetworkTables.initialize(server='roboRIO-5740-FRC.local')

# Checks what mode the robot is in 
while True:
    # gets the Networktable RobotMode
    robotmode = NetworkTables.getTable('RobotMode') 

    # gets the Network entry auto
    automode = robotmode.getBool('auto')
    
    # gets the Network entry telep
    teleopmode = robotmode.getBool('teleop')

    if(automode == True):

    else:

    
    if(teleopmode == True):

    else:

            
        
    
