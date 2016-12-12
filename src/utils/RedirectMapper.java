package utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Responsible for reading the configuration file and providing a
 * public interface to a convenient in-memory mapping of redirects.
 */
public class RedirectMapper {
    public static final Path REDIRECT_CONFIG_PATH = CommonConstants.ROOT_DIR.resolve("redirect.defs");
    public static final String ERR_NOT_FOUND = "No redirect config found. Leaving redirect map empty.";
    public static final String ERR_BAD_READ = "Error reading lines file. Redirect map may be empty or incomplete!";
    public static final String CONFIG_DELIMITER = "\\s+";
    public static final String REDIRECT_CONFIG_CHARSET = CommonConstants.CHARSET;

    public static Map<String, String> REDIRECTS_MAP = new HashMap<>();

    private static List<String> configLines = new ArrayList<>();
    
    /**
     * Build redirect information from config, if possible.
     */
    public static void ReadAndBuildMap() {
        if (Files.isReadable(REDIRECT_CONFIG_PATH)) {
            /**
             * This is unlikely to be a huge file so we'll just use
             * readAllLines, which has the extra advantage of
             * automatically closing the file handle for us.
             */
            try {
                configLines = Files.readAllLines(REDIRECT_CONFIG_PATH, Charset.forName(REDIRECT_CONFIG_CHARSET));
                for (String line : configLines) {
                    String[] pair = line.split(CONFIG_DELIMITER);
                    REDIRECTS_MAP.put(pair[0].trim(), pair[1].trim());
                }
            } catch (IOException e) {
                System.err.println(ERR_BAD_READ);
            }
        } else {
            System.err.println(ERR_NOT_FOUND);
        }
    }
}
