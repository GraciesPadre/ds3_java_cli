package com.spectralogic.ds3cli.command;

import com.spectralogic.ds3cli.Arguments;
import com.spectralogic.ds3client.Ds3Client;
import com.spectralogic.ds3client.commands.GetObjectRequest;
import com.spectralogic.ds3client.commands.GetObjectResponse;
import com.spectralogic.ds3client.networking.FailedRequestException;
import com.spectralogic.ds3client.networking.NetUtils;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.InputStream;

public class GetObject extends CliCommand {

    private String bucketName;
    private String objectName;
    private String prefix;

    public GetObject(Ds3Client client) {
        super(client);
    }

    @Override
    public CliCommand init(final Arguments args) throws Exception {
        bucketName = args.getBucket();
        if (bucketName == null) {
            throw new MissingOptionException("The get object command requires '-b' to be set.");
        }
        objectName = args.getObjectName();
        if (objectName == null) {
            throw new MissingOptionException("The get object command requires '-o' to be set.");
        }
        prefix = args.getPrefix();
        if (prefix == null) {
            prefix = "";
        }
        return this;
    }

    @Override
    public String call() throws Exception {
        try {
            final GetObjectResponse response = getClient().getObject(new GetObjectRequest(bucketName, objectName));

            final String filePath = NetUtils.buildPath(prefix, objectName);

            try (final InputStream stream = response.getContent();final FileOutputStream fOut = new FileOutputStream(filePath)) {
                IOUtils.copy(stream, fOut);
            }

            return "SUCCESS: Finished downloading object.  The object was written out to: " + filePath;
        }
        catch(final FailedRequestException e) {
            if(e.getStatusCode() == 500) {
                return "Error: Cannot communicate with the remote DS3 appliance.";
            }
            else if(e.getStatusCode() == 404) {
                return "Error: Unknown bucket.";
            }
            else {
                return "Error: Encountered an unknown error of ("+ e.getStatusCode() +") while accessing the remote DS3 appliance.";
            }
        }
    }
}