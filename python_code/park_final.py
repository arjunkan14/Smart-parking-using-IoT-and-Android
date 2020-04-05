import requests
import json
import urllib2
import datetime as dt
import RPi.GPIO as GPIO
import time

entry_1= 38
entry_2= 40

exit_1= 35
exit_2= 37

GPIO_TRIGGER1 = 7
GPIO_ECHO1    = 11
GPIO_TRIGGER2 = 13
GPIO_ECHO2    = 15
GPIO_TRIGGER3 = 16
GPIO_ECHO3    = 18
GPIO_TRIGGER4 = 19
GPIO_ECHO4    = 21
GPIO_TRIGGER5 = 29
GPIO_ECHO5    = 31
GPIO_TRIGGER6 = 24
GPIO_ECHO6    = 26

slot_res=None
isopen="close"

slot_url= "http://gamerscreedapp.com/Parking/ParkingCheck/check.php"

gate_url= "http://gamerscreedapp.com/Parking/ParkingCheck/opengate.php"

post_url= "http://gamerscreedapp.com/Parking/ParkingCheck/opengate.php"
GPIO.setmode(GPIO.BOARD)
GPIO.setup(entry_1, GPIO.OUT)
GPIO.setup(entry_2, GPIO.OUT)

GPIO.setup(exit_1 , GPIO.OUT)
GPIO.setup(exit_2 , GPIO.OUT)

GPIO.setup(GPIO_TRIGGER1,GPIO.OUT)  # Trigger
GPIO.setup(GPIO_ECHO1,GPIO.IN)      # Echo
GPIO.setup(GPIO_TRIGGER2,GPIO.OUT)  # Trigger
GPIO.setup(GPIO_ECHO2,GPIO.IN)      # Echo
GPIO.setup(GPIO_TRIGGER3,GPIO.OUT)  # Trigger
GPIO.setup(GPIO_ECHO3,GPIO.IN)      # Echo
GPIO.setup(GPIO_TRIGGER4,GPIO.OUT)  # Trigger
GPIO.setup(GPIO_ECHO4,GPIO.IN)      # Echo
GPIO.setup(GPIO_TRIGGER5,GPIO.OUT)  # Trigger
GPIO.setup(GPIO_ECHO5,GPIO.IN)      # Echo
GPIO.setup(GPIO_TRIGGER6,GPIO.OUT)  # Trigger
GPIO.setup(GPIO_ECHO6,GPIO.IN)      # Echo

# Set trigger to False (Low)
GPIO.output(GPIO_TRIGGER1, False)
GPIO.output(GPIO_TRIGGER2, False)
GPIO.output(GPIO_TRIGGER3, False)
GPIO.output(GPIO_TRIGGER4, False)
GPIO.output(GPIO_TRIGGER5, False)
GPIO.output(GPIO_TRIGGER6, False)

def entry_open():
	global isopen
	GPIO.output(entry_1, True)
        GPIO.output(entry_2, False)
        time.sleep(2)
        GPIO.output(entry_2, False)
        GPIO.output(entry_1, False)
	isopen= "open"
	print "gate opened"

def entry_close():
	GPIO.output(entry_1, False)
        GPIO.output(entry_2, True)
        time.sleep(2)
        GPIO.output(entry_2, False)
        GPIO.output(entry_1, False)
	isopen="close"
	print "gate closed"
	
def measure1():
  # This function measures a distance
  GPIO.output(GPIO_TRIGGER1, True)
  time.sleep(0.00001)
  GPIO.output(GPIO_TRIGGER1, False)
  start = time.time()

  while GPIO.input(GPIO_ECHO1)==0:
    start = time.time()

  while GPIO.input(GPIO_ECHO1)==1:
    stop = time.time()

  elapsed = stop-start
  distance1 = (elapsed * 34300)/2

  return distance1
def measure2():
  # This function measures a distance
  GPIO.output(GPIO_TRIGGER2, True)
  time.sleep(0.00001)
  GPIO.output(GPIO_TRIGGER2, False)
  start = time.time()

  while GPIO.input(GPIO_ECHO2)==0:
    start = time.time()

  while GPIO.input(GPIO_ECHO2)==1:
    stop = time.time()

  elapsed = stop-start
  distance2 = (elapsed * 34300)/2

  return distance2
def measure3():
  # This function measures a distance
  GPIO.output(GPIO_TRIGGER3, True)
  time.sleep(0.00001)
  GPIO.output(GPIO_TRIGGER3, False)
  start = time.time()

  while GPIO.input(GPIO_ECHO3)==0:
    start = time.time()

  while GPIO.input(GPIO_ECHO3)==1:
    stop = time.time()

  elapsed = stop-start
  distance3 = (elapsed * 34300)/2

  return distance3
def measure4():
  # This function measures a distance
  GPIO.output(GPIO_TRIGGER4, True)
  time.sleep(0.00001)
  GPIO.output(GPIO_TRIGGER4, False)
  start = time.time()

  while GPIO.input(GPIO_ECHO4)==0:
    start = time.time()

  while GPIO.input(GPIO_ECHO4)==1:
    stop = time.time()

  elapsed = stop-start
  distance4 = (elapsed * 34300)/2

  return distance4
def measure5():
  # This function measures a distance
  GPIO.output(GPIO_TRIGGER5, True)
  time.sleep(0.00001)
  GPIO.output(GPIO_TRIGGER5, False)
  start = time.time()

  while GPIO.input(GPIO_ECHO5)==0:
    start = time.time()

  while GPIO.input(GPIO_ECHO5)==1:
    stop = time.time()

  elapsed = stop-start
  distance5 = (elapsed * 34300)/2

  return distance5
def measure6():
  # This function measures a distance
  GPIO.output(GPIO_TRIGGER6, True)
  time.sleep(0.00001)
  GPIO.output(GPIO_TRIGGER6, False)
  start = time.time()

  while GPIO.input(GPIO_ECHO6)==0:
    start = time.time()

  while GPIO.input(GPIO_ECHO6)==1:
    stop = time.time()

  elapsed = stop-start
  distance6 = (elapsed * 34300)/2

  return distance6


def measure_average1():
  # This function takes 3 measurements and
  # returns the average.
  distancea=measure1()
  time.sleep(0.1)
  distanceb=measure1()
  time.sleep(0.1)
  distancec=measure1()
  distance1 = distancea + distanceb + distancec
  distance1 = distance1 / 3
  return distance1

def measure_average2():
  # This function takes 3 measurements and
  # returns the average.
  distancea=measure2()
  time.sleep(0.1)
  distanceb=measure2()
  time.sleep(0.1)
  distancec=measure2()
  distance2 = distancea + distanceb + distancec
  distance2 = distance2 / 3
  return distance2
def measure_average3():
  # This function takes 3 measurements and
  # returns the average.
  distancea=measure3()
  time.sleep(0.1)
  distanceb=measure3()
  time.sleep(0.1)
  distancec=measure3()
  distance3 = distancea + distanceb + distancec
  distance3 = distance3 / 3
  return distance3
def measure_average4():
  # This function takes 3 measurements and
  # returns the average.
  distancea=measure4()
  time.sleep(0.1)
  distanceb=measure4()
  time.sleep(0.1)
  distancec=measure4()
  distance4 = distancea + distanceb + distancec
  distance4 = distance4 / 3
  return distance4
def measure_average5():
  # This function takes 3 measurements and
  # returns the average.
  distancea=measure5()
  time.sleep(0.1)
  distanceb=measure5()
  time.sleep(0.1)
  distancec=measure5()
  distance5 = distancea + distanceb + distancec
  distance5 = distance5 / 3
  return distance5
def measure_average6():
  # This function takes 3 measurements and
  # returns the average.
  distancea=measure6()
  time.sleep(0.1)
  distanceb=measure6()
  time.sleep(0.1)
  distancec=measure6()
  distance6 = distancea + distanceb + distancec
  distance6 = distance6 / 3
  return distance6



def gate_status():

	global isopen
	gate_res= requests.request("GET", gate_url).text
	gate_res= json.loads(gate_res)
	print gate_res
	
	if gate_res['status'] == "open" and isopen == "close":
		gate_open()

	elif gate_res['status'] == "close" and isopen == "open":
		gate_close()
		

def check_slot1():
	time=dt.datetime.strptime(slot_res[0]['time'], '%Y-%m-%d %H:%M:%S')
	if time < dt.datetime.now():
		if measure_average1() > 10 and post1 != "true":
			payload= {'SlotNo':'one'}
			response= requests.post( url, data=payload )
			print response.text
			post1= "true"
		
		

while True:

	global slot_res
	slot_res= requests.request("GET", slot_url).text
        slot_res= json.loads(slot_res)
	
	#print slot_res[0]
	
	check_slot1()
	time.sleep(5)
