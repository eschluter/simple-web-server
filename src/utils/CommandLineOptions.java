package utils;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.MissingArgumentException;

public class CommandLineOptions {
    public static Option generateRequiredLongOption(String name, String desc, String argName) {
        Option option = OptionBuilder.withLongOpt(name)
            .withDescription(desc)
            .hasArg()
            .withArgName(argName)
            .create();

        option.setRequired(true);

        return option;
    }

    /**
     * Parse and return an ordered list of option values in options,
     * in the order specified by the arbitrary option list.
     * Doing this because there's no guarantee that options itself
     * maintains order in which items were added.
     * Catch known exceptions to print friendly error messages.
     */
    public static List<String> parseAndReturnRequiredLongOptions(
                                     String commandName, String[] args, Option... orderedOptions) {
        List<String> results = new ArrayList<>();
        Options options = new Options();
        for (Option o : orderedOptions) {
            options.addOption(o);
        }

        CommandLineParser parser = new GnuParser();

        String host = null;
        String port = null;

        try {
            CommandLine line = parser.parse(options, args);

            for (Option o : orderedOptions) {
                results.add(line.getOptionValue(o.getLongOpt()));
            }

        } catch (MissingOptionException|MissingArgumentException e) {
            System.out.println("Error - " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(commandName, options);
            return null;
        } catch (ParseException e) {
            System.out.println("Unrecoverable error - " + e.getMessage());
            return null;
        }

        return results;
    }
}
