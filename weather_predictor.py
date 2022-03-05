from geopy.exc import GeocoderTimedOut
from geopy.geocoders import Nominatim
import requests, json

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
        # print(my_json)
        printDetails(location, my_json)
    except:
        print(city, "Not Found, Please try again!!")


def printDetails(location, data):
    # Printing location details
    loc = str(location).split(", ")
    first = ', '.join([loc[i] for i in range(0, len(loc) - 2)])
    second = ', '.join([loc[i] for i in range(len(loc) - 2, len(loc))])
    print(first)
    print(second)

    print("*************Weather*************")
    # Printing Weather Details
    weather = data["weather"]
    icon = weather[0]['icon']
    description = weather[0]['description'].title()
    print("Icon =", icon)
    print("Description =", description)
    data = data['main']
    print("Temperature =", data['temp'], "but feels like", data['feels_like'])
    print("Minimum Temperature =", data['temp_min'], ", Maximum Temperature =", data['temp_max'])
    print("Pressure =", data['pressure'])
    print("Humidity =", data['humidity'])

def main():
    city = input("Enter the city: ")
    findWeather(city)

if __name__ == "__main__":
    main()