// -- libraries -----------------------------------------------
#include <ESP8266WiFi.h> //esprissif's lib

// -- network variables ---------------------------------------
const char *ssidAP = "AudioLights Config Service";
const char *passAP = ""; // blank means open network

const int port = 8080;

char result[10];
// char packetBuffer[UDP_TX_PACKET_MAX_SIZE + 1];
// char SendBuffer[] = "feed me";

// ESP8266WebServer server(80);
WiFiServer TCP_SERVER(port);

void startAccessPoint() // works
{
    // wifi can be persistant between runs, stop all old connections
    WiFi.disconnect();

    Serial.println("Starting wifi access point");

    // access point and station
    WiFi.mode(WIFI_AP_STA);

    // -- setup network -----------------------------
    IPAddress local_ip(192, 168, 4, 1);
    IPAddress gateway(192, 198, 4, 1);
    IPAddress subnet(255, 255, 255, 0);
    WiFi.softAPConfig(local_ip, gateway, subnet);
    WiFi.softAP(ssidAP, passAP);

    delay(69); // give it a sec to get situated (nice)

    Serial.print("IP address: ");
    IPAddress IP = WiFi.softAPIP();
    Serial.println(IP);
    // WiFi.enableAP(true);   // unneeded?

    // start server
    TCP_SERVER.begin();
    Serial.println("Access point and server started!");
}

void stopAccessPoint() // untested
{
    Serial.println("Shutting down access point...");
    WiFi.softAPdisconnect(true); // turnoff access point
    WiFi.disconnect(true);
}

void connectToNewNetwork(char *ssid, char *pass) // untested
{
    WiFi.mode(WIFI_STA);   // no ap
    WiFi.begin(ssid, pass); // connect to new network via passed credencials
        while (WiFi.status() != WL_CONNECTED)
    {
        delay(300);
        Serial.print('.');
    }
    Serial.print("\n\n Connected with ip: ");
    Serial.println(WiFi.localIP());
}

void connectionTest() // works
{
    unsigned long tNow;

    if (TCP_SERVER.hasClient())
    {
        WiFiClient TCP_CLIENT = TCP_SERVER.available();
        TCP_CLIENT.setNoDelay(true);

        int timeout = 0;
        while (timeout < 25)
        {

            if (TCP_CLIENT)
            {
                Serial.print("Client status " + TCP_CLIENT.status());

                String msg = TCP_CLIENT.readStringUntil('\r');

                Serial.print("Received packet of size ");
                Serial.println(sizeof(msg));

                // who dun it
                Serial.print("From ");
                Serial.print(TCP_CLIENT.remoteIP());
                Serial.print(", Port ");
                Serial.println(TCP_CLIENT.remotePort());

                // contents
                Serial.print("Contents: ");
                Serial.println(msg);

                tNow = millis();
                dtostrf(tNow, 8, 0, result);

                // reply
                TCP_CLIENT.println(result);
                TCP_CLIENT.flush();
            }
            else
            {
                Serial.print('.');
                delay(20);
                timeout++;
            }
        }
    }
    // client.stop();
    // Serial.println("Client disconnected");
}

void getWifiCredentials() // needs testing
{
    // if(WiFi.getMode() != WIFI_AP || WIFI_AP_STA) {
    //     // board isn't setup for this
    //     Serial.println("wrong wifi mode");
    //     return;
    // }

    if (TCP_SERVER.hasClient())
    {
        WiFiClient TCP_CLIENT = TCP_SERVER.available();
        TCP_CLIENT.setNoDelay(true);

        Serial.println("Collecting information");

        bool waiting = true;
        while (waiting)
        {

            if (TCP_CLIENT)
            {
                // but isn't this rly insecure and bad practice to send passwords over unencripted tcp?!?!?!!
                // yes.

                // -- get name ----------------------------------------
                TCP_CLIENT.println("send: wifi's >NAME and >PASSWORD.");
                TCP_CLIENT.flush();
                String response = TCP_CLIENT.readStringUntil('\r');
                Serial.println(response);

                // -- parse string using delimiters --------------------
                int f = response.indexOf('>');        // find first
                int s = response.indexOf('>', f + 1); // find second

                // if the char before s == ' ' remove it
                int ss = (response.charAt(s - 1) == ' ') ? s - 1 : s;
                String name = response.substring(f + 1, ss);
                String pass = response.substring(s + 1);

                // -- log ssid and pass ------------------------------------
                char *newSSID = "";
                char *newPASS = "";
                Serial.println(name);
                Serial.println(pass);
                name.toCharArray(newSSID, name.length());
                pass.toCharArray(newPASS, pass.length());

                // need better if statement
                if ((newPASS != "") || (newSSID != ""))
                {
                    // might be running when not suppost to
                    Serial.println("Changing mode...");
                    stopAccessPoint();
                    TCP_SERVER.close();
                    connectToNewNetwork(newSSID, newPASS);

                    waiting = false;
                }
            }
        }
    }
    // client.stop();
    // Serial.println("Client disconnected");
}

void setup()
{
    Serial.begin(115200);
    Serial.println("booting...");
    startAccessPoint();
    getWifiCredentials();
}

void loop()
{
  
    // connectionTest();

    // probably udp connection for lights
}
