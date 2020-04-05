import requests
import json

url= "http://gamerscreedapp.com/Parking/ParkingCheck/check.php"

response= requests.request("GET", url)
data=response.text

json_data = '{"a": 1, "b": 2, "c": 3, "d": 4, "e": 5}'

loaded_json = json.loads(json_data)
for x in loaded_json:
	print("%s: %s" % (x, loaded_json[x]))
