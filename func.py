import json
import os
from datetime import datetime
from PIL import Image 
import io


def saveIP(ip):
  with open("stats.json","r") as f:
    data = json.load(f)
  if ip not in data:
    data[ip] = {}
    data[ip]["times_sent"] = 1
  else:
    data[ip]["times_sent"] += 1  
  with open("stats.json","w") as f:
    json.dump(data,f)

def saveImage(request):
    print("saveImage")
    try:
      ip = str(request.access_route[-1])
    except:
      ip = "unknown" 
    imageData = request.get_data()
    image = Image.open(io.BytesIO(imageData))
    if not os.path.isdir(f"images/{ip}"):
      os.mkdir(f"images/{ip}")
    now = datetime.now()
    try:
      image.save(f"images/{ip}/{now.day}-{now.month}-{now.year}_{now.hour}-{now.minute}-{now.second}.png")
    except:
      return "Error saving image",500
    return "Done",200

def saveFilesystem(request):
  ip = str(request.access_route[-1])
  with open(f"images/{ip}/data.json","r") as f:
    data = json.load(f)
  username = request.args.get('user')
  if username not in data:
    data[user] = {}
  data["fs"]

def saveData(cpu,ram,username,ip):
  print("saving...")
  ip = str(ip)
  data = {}
  data[ip] = {}
  data[ip]["specs"] = {}
  data[ip]["specs"]["ram"] = ram
  data[ip]["specs"]["cpu"] = cpu
  data[ip]["username"] = username
  with open(f"images/{ip}/data.json","w") as f:
    json.dump(data,f)