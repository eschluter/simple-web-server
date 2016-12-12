package http;

import utils.CommonConstants;
import utils.HeaderUtils;
import http.ResponseDispositions;
import utils.RedirectMapper;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;

/**
 * Serve up a good old fashioned GET request
 *
 */
public class GETRequest extends Request {
    public GETRequest(RequestLine requestLine) {
        super(requestLine);
    }

    public byte[] getResponse() {
        Path requestedPath = this.getPath();
        Path qualifiedPath = Paths.get(CommonConstants.ROOT_DIR.toString() + "/" + requestedPath.toString());

        byte[] header = HeaderUtils.getHeader(requestedPath, qualifiedPath, this);

        if (!HeaderUtils.headerResponseOnly(requestedPath, qualifiedPath)) {
            try {
                byte[] fileContents = Files.readAllBytes(qualifiedPath);

                byte[] fullArray = new byte[header.length + fileContents.length];
                System.arraycopy(header, 0, fullArray, 0, header.length);
                System.arraycopy(fileContents, 0, fullArray, header.length, fileContents.length);

                return fullArray;
            }  catch (IOException e) {
                System.err.println("ERROR WRITING TO CLIENT " + e.getMessage());
            }
        }

        return header;
    }
}
