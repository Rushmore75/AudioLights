# AudioLights

Read the computer's audio stream then parse for frequencies. Data then will be sent via bluetooth to an arduino to control lights.

System audio is read by the Jack Audio Connection Kit (JACK). Program creates a input (accepting signal) client where the stream is read. Thusfor, [JACK](https://jackaudio.org/ "JACK offical website") is required to run this. You can install it on [windows](https://jackaudio.org/downloads/) or install on Ubuntu using `sudo apt-get install qjacktrl` following [this](https://youtu.be/E6LuvdDEqCA) set of videos will help. 
