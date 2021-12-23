# AudioLights

Read the computer's audio stream then parse for frequencies. Data then will be sent via bluetooth to an arduino to control lights.

System audio is read by the Jack Audio Connection Kit (JACK). Program creates a input (accepting signal) client where the stream is read. Thusfor, [JACK](https://jackaudio.org/ "JACK offical website") is required to run this. 

# Windows
You can install it on [windows](https://jackaudio.org/downloads/) 
By using default settings then patching in the client it seems to get the audio stream. However the Windows OS remains unaffected by JACK's exsistance. It just allows you to get a copy of the audio output.

# Ubuntu
using `sudo apt-get install qjacktrl` following [this](https://youtu.be/E6LuvdDEqCA) set of videos will help. It will take some tinkering as it over-takes the OS's whole sound system.
