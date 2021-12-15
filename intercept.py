import pyaudio
import struct
import matplotlib.pyplot as plt
import numpy as np
from scipy.fftpack import fft
from tkinter import TclError

fig, freqPlot = plt.subplots(1, figsize=(15, 7))

CHUNK = int(1024 * 1)
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 44100

audio = pyaudio.PyAudio()

stream = audio.open(
                    format=FORMAT,
                    channels=CHANNELS,
                    rate=RATE,
                    input=True,
                    output=True,
                    frames_per_buffer=CHUNK
)

xf = np.linspace(0, RATE, CHUNK) #freq
line_fft, = freqPlot.plot(xf, np.random.rand(CHUNK), '-', lw=2)
freqPlot.set_xlim(20, RATE / 2)

plt.show(block=False)
while True:

    data = stream.read(CHUNK)
    data_int = struct.unpack(str(2 * CHUNK) + 'B', data)
    # data_np = np.array(data_int, dtype='b')[::2] + 128

    yf = fft(data_int)

    line_fft.set_ydata(np.abs(yf[0:CHUNK]) / (128 * CHUNK) )

    try:
        fig.canvas.draw()
        fig.canvas.flush_events()

    except TclError:
        stream.stop_stream()
        stream.close()
        audio.terminate()
        break