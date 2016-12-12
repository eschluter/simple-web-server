import java.io.*;
import java.util.List;
import utils.RedirectMapper;
import utils.CommandLineOptions;
import org.apache.commons.cli.Option;

public class Server {
    public static void main(String[] args) {
        Option portOption = CommandLineOptions.generateRequiredLongOption(
                "port", "identify the port on which to listen", "port");

        Option securePortOption = CommandLineOptions.generateRequiredLongOption(
                "sslPort", "identify the secure port on which to listen", "securePort");

        List<String> userArgs = CommandLineOptions.parseAndReturnRequiredLongOptions("Server", args, portOption, securePortOption);

        if (userArgs == null) {
            System.exit(1);
        }

        // Assume if we got here, the parser did indeed provide this
        // list properly and threw any necessary exceptions otherwise
        String port 	  = userArgs.get(0);
        String securePort = userArgs.get(1);

        // Build our in-memory redirect map
        RedirectMapper.ReadAndBuildMap();

        // start listening on unsecure port
        new AcceptHandler(Integer.parseInt(port), false).start();

        // start listening on secured port
        new AcceptHandler(Integer.parseInt(securePort), true).start();
    }
}
