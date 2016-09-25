#!/usr/bin/env python


import time
import serial
import requests
import json

ser = serial.Serial(
    port='/dev/ttyACM0',
    baudrate = 1200,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
    timeout=1
)
counter=0
logsUrl='https://sousvide.lyth.io/api/logs'
headers = {'Content-Type': 'application/json'}

ser.write('0')

def getConfig():
    resp = requests.get("https://sousvide.lyth.io/api/configuration")
    if resp.status_code == 200 :
        return resp.json();
    else :
        return {'running': False}

def sendLog():
    payload = json.dumps({'temperature': ser.readline()})
    resp = requests.post(logsUrl, headers = headers, data = payload)
    if resp.status_code == 200 :
        return resp.json();
    else :
        return {'temperature':0, 'timestamp':0}

while True:
    config = getConfig()
    if config['running'] :
        minTemp = config['temperature'] - 5
        maxTemp = config['temperature'] + 5
        logEntry = sendLog()
        print('current temp: {}, desired range: {} - {}').format(logEntry['temperature'],minTemp,maxTemp)
        if logEntry['temperature'] > 0:
            if logEntry['temperature'] < maxTemp:
                ser.write('1')
            else:
                ser.write('0')
            # print('is running: {}').format(config['running'])
    else:
        ser.write('0')
    time.sleep(10)