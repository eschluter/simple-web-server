package utils;

import java.util.Map;
import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Dumping ground for string constants and other goodies
 */
public class CommonConstants {

    public static final Path ROOT_DIR = Paths.get("www");
    public static final String CHARSET = "UTF-8";
    public static final String HTTP_VERSION = "HTTP/1.1";
    public static final String CRLF = "\r\n";
    public static final String REDIRECTS_PATH = "/redirect.defs";

    public static final int REQUEST_PIECES_COUNT = 3; // method, uri,
                                                      // http version

    public static Map<String, String> EXTENSION_TO_MIME_TYPE_MAP = new HashMap<String, String>(){{
            put("html", "text/html");
            put("htm", "text/html");
            put("pdf", "application/pdf");
            put("png", "image/png");
            put("jpg", "image/jpeg");
            put("txt", "text/plain");
    }};
    
}
