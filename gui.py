from email.mime import image
from tkinter import *
from tkinter import ttk
from weather_forecast import *
from tkinter.ttk import *
from PIL import ImageTk, Image  


window = Tk()
# window.resizable(False, False)
city_var = StringVar()

img = PhotoImage(file="./assets/icons/icon.png")
window.iconphoto(True, img)

# ttk Codes
style = ttk.Style()
style.configure("BW.TLabel", foreground="black", background="white")



# The parameter event inside whatsTheWeather to have it bind to the enter key.
def whatsTheWeather(event):
    city=city_var.get()
    # print("The name is : " + city)     
    city_var.set("")
    msg, image = findWeather(city)
    setOutput(msg, image)


# Setting the text inside the text_box
def setOutput(msg, image):
    image_label.config(image=image)
    output_label.config(text=msg)


# Title
window.title("Weather Forecastor")


# Canvas
window.geometry('500x700')


mainImg = ImageTk.PhotoImage(Image.open("assets/icons/main.png").resize((300, 200)))
label1 = Label(window, image = mainImg)
label1.grid(column=0, row=0, padx=10, pady=10)



# Input
entry = ttk.Entry(window, textvariable=city_var, justify="center", width=20, font=("sans-sarif", 15))
entry.focus_force()
entry.grid(column=0, row=1, padx=40, pady=10)



# Button
btn_image = Image.open("./assets/icons/btn_icon.png").resize((36, 36))
style.configure('.', font=('Helvetica', 12))
btn_PhotoImage = ImageTk.PhotoImage(btn_image)
btn = ttk.Button(window, text="What's the Weather?", image=btn_PhotoImage, compound=LEFT ,width=16, command=whatsTheWeather)
btn.grid(column=0, row=2, padx=10, pady=10)


# Binding the enter key to a function call
window.bind('<Return>', whatsTheWeather)


# Output
output_label = ttk.Label(window, width=50)
output_label.grid(column=0, row=3, padx=30, pady=10)

# # Image Output

image_label = Label(window, background='white', width=30)
image_label.grid(column=2, row=4, padx=10, pady=10)

# End
window.mainloop()