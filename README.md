# AudioLights

Read the computer's audio stream then parse for frequencies. Data then will be sent via bluetooth to an arduino to control lights.

System audio is read by the Jack Audio Connection Kit (JACK). Program creates a input (accepting signal) client where the stream is read.
