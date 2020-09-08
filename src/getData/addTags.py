import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

import json


infile = open("tags.txt", "r")
cred = credentials.Certificate("eventfinder.json")
firebase_admin.initialize_app(cred)
db = firestore.client()

for x in infile:
    listdata = {}
    nametag = x.split()

    listdata["free"] = nametag[1]
    listdata["paid"] = nametag[2]
    listdata["over18"] = nametag[3]
    listdata["exercise"] = nametag[4]
    listdata["family"] = nametag[5]
    listdata["individual"] = nametag[6]
    listdata["friends"] = nametag[7]
    listdata["ticketRequired"] = nametag[8]
    listdata["mixedGroup"] = nametag[9]
    listdata["competetive"] = nametag[10]
    
    print(listdata)
    print(nametag[0])
    doc_ref = db.collection(nametag[0]).document("catagory")
    doc_ref.set(listdata)


infile.close()
