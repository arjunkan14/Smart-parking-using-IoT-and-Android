from datetime import datetime

now = datetime.now()
print "Now: ", now
print "Today's date: ", now.strftime('%Y-%m-%d') 

print "year:", now.year
print "month:", now.month
print "day:", now.day
print "hour:", now.hour
print "minute:", now.minute
print "second:", now.second
