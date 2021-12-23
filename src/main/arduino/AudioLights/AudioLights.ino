// #include <WiFi.h> //arduino's lib
#include <ESP8266WiFi.h> //esprissif's lib
#include <ESP8266WebServer.h>

const char *ssidAP = "AudioLights Config Service";
const char *passAP = "your mom";

// ESP8266WebServer server(80);
WiFiServer server(80);

// const char *htmlPage = "<!DOCTYPE html> \
// <html> \
// <body> \
// <center><h1>ESP32 Soft access point</h1></center> \
// <center><h2>Web Server</h2></center> \
// <form> \
// <button name=\"LED0\" button style=\"color:green\" value=\"ON\" type=\"submit\">LED0 ON</button> \
// <button name=\"LED0\" button style=\"color=red\" value=\"OFF\" type=\"submit\">LED0 OFF</button><br><br> \
// </form> \
// </body> \
// </html>";

void startAccessPoint() // working
{
    Serial.println("Starting wifi access point");

    WiFi.softAP(ssidAP, passAP);
    IPAddress local_ip(192, 168, 4, 1);
    IPAddress gateway(0, 0, 0, 0); // "disabled"
    IPAddress subnet(255, 255, 255, 0);
    WiFi.softAPConfig(local_ip, gateway, subnet);

    Serial.print("IP address: ");
    IPAddress IP = WiFi.softAPIP();
    Serial.println(IP);

    server.begin();
}

void stopAccessPoint() // untested
{
    Serial.println("Shutting down access point...");
    WiFi.softAPdisconnect(true); // turnoff access point
}

void waitForPacket()
{
    WiFiClient client = server.available();

    if (client)
    {
        if (client.connected())
        {
            Serial.println("Client Connected");
        }

        while (client.connected())
        {
            const int BUFFER_SIZE = 16;
            char buffer[BUFFER_SIZE];
            if (client.available()) {
                // Serial.println(client.read());
                
                int rlen = client.readBytes(buffer, BUFFER_SIZE);
                for(int i =0; i < rlen; i++){
                    Serial.print(buffer[i]);
                }
                
            }
        }
        client.stop();
        Serial.println("Client disconnected");
    }
}

void setup()
{
    pinMode(0, INPUT_PULLUP);
    Serial.begin(115200);
    Serial.println("booting...");
    startAccessPoint();
}

void loop()
{
    waitForPacket();
}

// void loop()
// {
//     // put your main code here, to run repeatedly:
//     WiFiClient client = server.available();
//     if (client)
//     {
//         String request = client.readStringUntil('\r');
//         if (request.indexOf("LED0=ON"))
//         {
//             Serial.println("hit led 0");
//         }
//         if (request.indexOf("LED0=OFF"))
//         {
//             Serial.println("tim");
//         }
//         client.print(htmlPage);
//         request = "";
//     }
// }
