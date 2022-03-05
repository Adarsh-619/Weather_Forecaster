from geopy.exc import GeocoderTimedOut
from geopy.geocoders import Nominatim
import requests, json
import urllib.request
from PIL import ImageTk, Image, ImageEnhance

icon = ''
description = ''

def findGeoCode(city):
    try:    
        geolocator = Nominatim(user_agent="Weather-App")
        return geolocator.geocode(city)
    except GeocoderTimedOut:
        return findGeoCode(city)
    except:
        return  


def findWeather(city):
    # API key
    api_key = "b9d82d47340ecf4456de5ef1ccb929ef"
    
    try:
        # Getting the geolocation for the required city
        location = findGeoCode(city)

        # Extracting the latitude and longitude from the data
        lat = location.latitude
        lon = location.longitude

        # Setting the url
        complete_url = f"http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={api_key}"
        
        # Response and formatting the response
        response = requests.get(complete_url)
        my_json = response.json()

        # printDetails(location, my_json)
        msg = printDetailsGUI(location, my_json)
        return msg

    except:
        print(city, "Not Found, Please try again!!")

# For Console
def printDetails(location, data):
    # Printing location details
    loc = str(location).split(", ")
    first = ', '.join([loc[i] for i in range(0, len(loc) - 2)])
    second = ', '.join([loc[i] for i in range(len(loc) - 2, len(loc))])
    print(first)
    print(second)

    print("*************Weather*************")
    # Printing Weather Details
    data = data['main']
    print("Temperature =", data['temp'], "but feels like", data['feels_like'])
    print("Minimum Temperature =", data['temp_min'], ", Maximum Temperature =", data['temp_max'])
    print("Pressure =", data['pressure'])
    print("Humidity =", data['humidity'])

# For GUI
def printDetailsGUI(location, data):
    global icon, description
    # Printing location details
    loc = str(location).split(", ")
    first = ', '.join([loc[i] for i in range(0, len(loc) - 2)])
    second = ', '.join([loc[i] for i in range(len(loc) - 2, len(loc))])
    weather = data["weather"]
    icon = weather[0]['icon']
    description = weather[0]['description'].title()
    data = data['main']
    msg =  str(first) + "\n" + str(second) + "\n" + "*************Weather*************" + "\n" + "Temperature = " + str(data['temp']) + " but feels like " + str(data['feels_like'])\
     + "\n" + "Minimum Temperature = " + str(data['temp_min']) + "\nMaximum Temperature = " + str(data['temp_max']) + "\n" + "Pressure = " + str(data['pressure'])\
     + "\n" + "Humidity = " + str(data['humidity'])
    image = getImage(icon)
    return msg, image



def getImage(icon):
    url = "http://openweathermap.org/img/wn/"+ icon +"@2x.png"
    urllib.request.urlretrieve(url, "assets\output\output.png")
    img = Image.open("assets\output\output.png")
    enhancer = ImageEnhance.Brightness(img)
    factor = 0.5 #darkens the image
    im_output = enhancer.enhance(factor)
    im_output.save('assets\output\output.png')
    image = ImageTk.PhotoImage(img)
    return image
    # img.show()


def main():
    city = input("Enter the city: ")
    msg = findWeather(city)
    print(msg)
    getImage(icon)

if __name__ == "__main__":
    main()