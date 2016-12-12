# Simple Web Server
Just a simple secure web server. Accepts connections on two ports, one of which is secured through ssl.

## How to use it

When you first get the code, make sure you build the source with "make" in the top level directory.

After that, there should be a target folder that contains the executables for the server. It is in this folder that you can put the web root, too, like the "www" folder. The server expects that the root folder will be called www, although this is configurable in the code in the constants class.

To run the server, just run 

    ./server.sh --port 8080 --sslPort 1099

Or some other port. If you look at server.sh, you will see that we designed it to make it easy to just run the server executable without having to put the one third-party dependency, the Apache command line interpreter lib, on your classpath. Again, this server does expect that the www folder will be beside its executable class, it's just that we found it easier to supply the server in this way.
