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

public class HEADRequest extends Request {
    public HEADRequest(RequestLine requestLine) {
        super(requestLine);
    }

    public byte[] getResponse() {
        Path requestedPath = this.getPath();
        Path qualifiedPath = Paths.get(CommonConstants.ROOT_DIR.toString() + "/" + requestedPath.toString());

        return HeaderUtils.getHeader(requestedPath, qualifiedPath, this);
    }
}
