#Importing modules
import json
import urllib2
import datetime as dt


slot_url= "http://gamerscreedapp.com/Parking/ParkingCheck/check.php"

gate_url= "http://gamerscreedapp.com/Parking/ParkingCheck/opengate.php"

data = json.load(urllib2.urlopen(slot_url))

gate= json.load(urllib2.urlopen(gate_url))

print gate[0]['status']

print data[1]['time']

time=data[1]['time']

now=dt.datetime.now()

yourdate = dt.datetime.strptime(time, '%Y-%m-%d %H:%M:%S')

if now > yourdate:
	print "greater"
