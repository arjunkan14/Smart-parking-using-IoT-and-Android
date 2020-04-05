import requests
import json
import urllib2
import datetime as dt
import RPi.GPIO as GPIO
import time

post1="false"
post2="false"
post3="false"
post4="false"
post5="false"
post6="false"

entry= 40
exit= 38

ir= 36

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

exopen="close"

slot_url= "http://192.168.1.8/Parking/ParkingCheck/check.php"

gate_url= "http://192.168.1.8/Parking/ParkingCheck/opengate.php"

post_url= "http://192.168.1.8/Parking/ParkingCheck/changeSlotStatus.php"

GPIO.setmode(GPIO.BOARD)
GPIO.setwarnings(False)
GPIO.setup(entry, GPIO.OUT)
GPIO.setup(exit , GPIO.OUT)

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

GPIO.setup(ir,GPIO.IN)

en= GPIO.PWM(entry,50)              # GPIO19 as PWM output, with 50Hz frequency
en.start(0)                             # generate PWM signal with 7.5% duty cycle

ex = GPIO.PWM(exit,50)              # GPIO19 as PWM output, with 50Hz frequency
ex.start(0)                             # generate PWM signal with 7.5% duty cycle

# Set trigger to False (Low)
GPIO.output(GPIO_TRIGGER1, False)
GPIO.output(GPIO_TRIGGER2, False)
GPIO.output(GPIO_TRIGGER3, False)
GPIO.output(GPIO_TRIGGER4, False)
GPIO.output(GPIO_TRIGGER5, False)
GPIO.output(GPIO_TRIGGER6, False)


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

def entry_open():
	global isopen
	en.ChangeDutyCycle(4.75)
	isopen= "open"
	print "gate opened"

def entry_close():

	en.ChangeDutyCycle(9.25)
	
	isopen="close"
	print "gate closed"


def gate_status():

	global isopen, gate_res
	gate_res= requests.request("GET", gate_url).text
	gate_res= json.loads(gate_res)
	print gate_res

	if gate_res['status'] == "open": #and isopen == "close":
		entry_open()

	if gate_res['status'] == "close":# and isopen == "open":
		entry_close()

def exit_open():
	global exopen
        ex.ChangeDutyCycle(4.75)

	exopen= "open"
	print "exit gate opened"

def exit_close():
	global exopen
	ex.ChangeDutyCycle(9.25)
	exopen="close"
	print "exit gate closed"


def exit_gate_status():

	global exopen
	if GPIO.input(ir) == 0: #and exopen == "close":
		exit_open()

	if  GPIO.input(ir) == 1:# and exopen == "open":
		exit_close()

def check_slot1():
	global post1
	time=dt.datetime.strptime(slot_res[0]['time'], '%Y-%m-%d %H:%M:%S')
	if time < dt.datetime.now():
		if measure_average1() > 10 and post1 != "true":
			payload= {'SlotNo':'one','exit_time':dt.datetime.now().strftime("%Y-%m-%d %X") }
			response= requests.post( post_url, data=payload )
			print "slot1 status updated\n", response.text
			post1= "true"
	else:
		post1="false"


def check_slot2():
	global post2
	time=dt.datetime.strptime(slot_res[1]['time'], '%Y-%m-%d %H:%M:%S')
	if time < dt.datetime.now():
		if measure_average2() > 10 and post2 != "true":
			payload= {'SlotNo':'two', 'exit_time':dt.datetime.now().strftime("%Y-%m-%d %X") }
			response= requests.post( post_url, data=payload )
			print "slot2 status updated", response.text
			post2= "true"
	else:
		post2="false"


def check_slot3():
	global post3
	time=dt.datetime.strptime(slot_res[2]['time'], '%Y-%m-%d %H:%M:%S')
	if time < dt.datetime.now():
		if measure_average3() > 10 and post3 != "true":
			payload= {'SlotNo':'three', 'exit_time':dt.datetime.now().strftime("%Y-%m-%d %X") }
			response= requests.post( post_url, data=payload )
			print "slot3 status updated",response.text
			post3= "true"
	else:
		post3="false"

def check_slot4():
	global post4
	time=dt.datetime.strptime(slot_res[3]['time'], '%Y-%m-%d %H:%M:%S')
	if time < dt.datetime.now():
		if measure_average4() > 10 and post4 != "true":
			payload= {'SlotNo':'four','exit_time':dt.datetime.now().strftime("%Y-%m-%d %X") }
			response= requests.post( post_url, data=payload )
			print "slot4 status updated",response.text
			post4= "true"
	else:
		post4="false"

def check_slot5():
	global post5
	time=dt.datetime.strptime(slot_res[4]['time'], '%Y-%m-%d %H:%M:%S')
	if time < dt.datetime.now():
		if measure_average1() > 10 and post5 != "true":
			payload= {'SlotNo':'five', 'exit_time':dt.datetime.now().strftime("%Y-%m-%d %X") }
			response= requests.post( post_url, data=payload )
			print "slot5 status updated",response.text
			post5= "true"
	else:
		post5="false"

def check_slot6():
	global post6
	time=dt.datetime.strptime(slot_res[5]['time'], '%Y-%m-%d %H:%M:%S')
	if time < dt.datetime.now():
		if measure_average6() > 10 and post6 != "true":
			payload= {'SlotNo':'six', 'exit_time':dt.datetime.now().strftime("%Y-%m-%d %X")}
			response= requests.post( post_url, data=payload )
			print "slot6 status updated",response.text
			post6= "true"
	else:
		post6="false"
try:

	while True:

#		global slot_res
		slot_res= requests.request("GET", slot_url).text
#		print slot_res.text
	        slot_res= json.loads(slot_res)

		#print slot_res[0]

		check_slot1()
		#time.sleep(2)

		check_slot2()
		#time.sleep(2)

		check_slot3()
		#time.sleep(2)

		check_slot4()
		#time.sleep(2)

		check_slot5()
		#time.sleep(2)

		check_slot6()
		#time.sleep(2)

		gate_status()

		exit_gate_status()

		time.sleep(2)

except KeyboardInterrupt:
	GPIO.cleanup()
	print "goodbye"
