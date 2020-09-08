import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from geopy.geocoders import Nominatim

import requests
import json
import ratelim
import geohash2


@ratelim.greedy(1, 1)
def latLongGetter(coords):
    geolocator = Nominatim(user_agent="myapp")
    return geolocator.reverse(coords)

# OSM api builder
def buildOverpassApiUrl(overpassQuery):
    bounds = '51.6693012559,-9.97708574059,55.1316222195,-5.13298539878'
    nodeQuery = 'node[' + overpassQuery + '](' + bounds + ');'
    # wayQuery = 'way[' + overpassQuery + '](' + bounds + ');'
    # relationQuery = 'relation[' + overpassQuery + '](' + bounds + ');'
    query = '?data=[out:json][timeout:25];(' + nodeQuery + ');out center;'
    baseUrl = 'http://overpass-api.de/api/interpreter'
    resultUrl = baseUrl + query
    return resultUrl

# json cache
def write_json(data, filename='addressCache.json'):
    with open(filename, 'w') as f:
        json.dump(data, f, indent=4)


infile = open("osmevents.txt", "r")
cred = credentials.Certificate("eventfinder.json")
firebase_admin.initialize_app(cred)
db = firestore.client()


for event in infile:
    # try:
    if event:
        print(event)
        overpass_query = buildOverpassApiUrl(event)
        print(overpass_query)
        response = requests.get(overpass_query)
        data = response.json()
        print('got it boy')
        events = event.split('"')

        with open("addressCache.json") as f:
            cache = json.load(f)

        with open("hashCache.json") as f:
            hashCache = json.load(f)

        for x in data["elements"]:
            try:
                # Address cache
                location = (str(x["lat"]) + ", " + str(x["lon"]))
                if location in cache:
                    x["tags"]["address"] = cache[location]
                else:
                    print("no address", x["id"])
                    longlat = latLongGetter(location)
                    cache[location] = str(longlat)
                    x["tags"]["address"] = cache[location]
                    write_json(cache)
            except:
                print("ERROR", x["id"])

            try:
                # Geohash cache
                if location in hashCache:
                    x["geohash"] = hashCache[location]
                else:
                    hashGeo = geohash2.encode(x["lat"], x["lon"])
                    hashCache[location] = hashGeo
                    x["geohash"] = hashGeo
                    write_json(hashCache, "hashCache.json")
                    print(hashGeo)
            except:
                print("Error getting hashcode for location")

            x["tags"]["review"] = []

        with open('data.txt', 'a') as outfile:
            json.dump(data["elements"], outfile)

        name = (events[-2])
        filename = ('allData/' + name + "Data.json")
        write_json(data["elements"], filename)

        # upload to database
        for x in data["elements"]:
            val = (x['id'])
            doc_ref = db.collection(name).document(str(val))
            doc_ref.set(x)

    else:
        continue

    # except:
    #   print("didnt get " + event)

infile.close()
