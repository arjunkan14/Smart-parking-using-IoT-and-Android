import requests
import json
import datetime as dt

url= "http://192.168.1.8/Parking/ParkingCheck/changeSlotStatus.php"
now= dt.datetime.now()
print now.strftime("X")

payload= {'SlotNo':'four', "exit_time":now.strftime("%Y-%m-%d %X") }

response= requests.post( url, data=payload )

print response.text
