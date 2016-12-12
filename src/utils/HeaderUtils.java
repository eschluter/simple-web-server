package utils;

import utils.CommonConstants;
import utils.HeaderUtils;
import http.ResponseDispositions;
import utils.RedirectMapper;
import http.Request;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;

/**
 * Assorted header construction utils that can be called in a static context.
 *
 */
public class HeaderUtils {

    private static String persistMsg = "close";

    public static byte[] getMovedHeader(Path requestedPath) {
        String redirectPath = RedirectMapper.REDIRECTS_MAP.get(requestedPath.toString());

        byte[] redirectHeader = (ResponseDispositions.MOVED_PERMANENTLY.getDispositionRepresentation() + CommonConstants.CRLF +
                "Connection: " + persistMsg + CommonConstants.CRLF +
                "Location: " + redirectPath + CommonConstants.CRLF).getBytes();
        return redirectHeader;
    }

    public static byte[] getNotFoundHeader() {
        return (ResponseDispositions.NOT_FOUND.getDispositionRepresentation() + CommonConstants.CRLF).getBytes();
    }

    public static byte[] getOKHeader(Path requestedPath, Path qualifiedPath, Request request) {
        byte[] header = null;

        try {
            header = (ResponseDispositions.OK.getDispositionRepresentation() + CommonConstants.CRLF +
                    "Connection: " + persistMsg + CommonConstants.CRLF +
                    "Content-Type: " + request.getMimeType() + CommonConstants.CRLF +
                    "Content-Length: " + Files.size(qualifiedPath) + CommonConstants.CRLF + CommonConstants.CRLF).getBytes();

        } catch (IOException e) {
            System.err.println("Could not read file contents to construct header " + e.getMessage());
        }

        return header;
    }

    public static byte[] getHeader(Path requestedPath, Path qualifiedPath, Request request) {
        byte[] fileContents;

	if (request.isPersistent()) {
		persistMsg = "keep-alive";
	} else {
		persistMsg = "close";
	}

        if (headerResponseOnly(requestedPath, qualifiedPath)) {

            if (RedirectMapper.REDIRECTS_MAP.containsKey(requestedPath.toString())) {
                return getMovedHeader(requestedPath);
            }

            return getNotFoundHeader();

        } else {

            return getOKHeader(requestedPath, qualifiedPath, request);
        }
    }

    /**
     * Determine whether we are just going to be returning a header only because there is no appropriate body.
     *
     * In this case, it includes cases in which we are given a directory name.
     *
     * @param requestedPath
     * @param qualifiedPath
     * @return
     */
    public static boolean headerResponseOnly(Path requestedPath, Path qualifiedPath) {
        return (!Files.isReadable(qualifiedPath) ||
                !Files.isRegularFile(qualifiedPath) ||
                requestedPath.toString().equals(CommonConstants.REDIRECTS_PATH));
    }

}
