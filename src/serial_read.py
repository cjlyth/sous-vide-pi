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


def getConfig():
    resp = requests.get("https://sousvide.lyth.io/api/configuration")
    resp.raise_for_status()
    return resp.json();

while True:
    config = getConfig()
    if config['running'] :
        print('is running: {}').format(config['running'])
        payload = json.dumps({'temperature': ser.readline()})
        r = requests.post(logsUrl, headers = headers, data = payload)
        logentry = r.json()
        print('{} - posted - {} - {}').format(r.status_code, logentry['temperature'], logentry['timestamp'])
    time.sleep(10)