import cv2 
import numpy as np
import ntcore
import Logger

from networktables import NetworkTables

# Inits Netowktables and connects to robo Rio server

NetworkTables.initialize(server='roboRIO-5740-FRC.local')

Colorcam = 2

cap = cv2.VideoCapture()

cap.open(ColorCam)

centers = []


while (1):

    # Take each frame
    cap.set(3, 12)
    _, frame = cap.read()

    # First convirts BGR 2 Gray then Converts BGR 2 HSV
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

   


    k = cv2.waitKey(10) & 0xFF
    if k == 27:

        break
cap.release()

