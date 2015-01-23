/*
 * ******************************************************************************
 *   Copyright 2014 Spectra Logic Corporation. All Rights Reserved.
 *   Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *   this file except in compliance with the License. A copy of the License is located at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file.
 *   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *   CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 * ****************************************************************************
 */

package com.spectralogic.ds3cli.command;

import com.spectralogic.ds3cli.Arguments;
import com.spectralogic.ds3client.Ds3Client;
import com.spectralogic.ds3client.commands.GetServiceRequest;
import com.spectralogic.ds3client.commands.GetServiceResponse;
import com.spectralogic.ds3client.models.ListAllMyBucketsResult;
import com.spectralogic.ds3client.utils.SSLSetupException;

import java.io.IOException;
import java.security.SignatureException;

public class GetService extends CliCommand<ListAllMyBucketsResult> {


    public GetService(final Ds3Client client) {
        super(client);
    }

    @Override
    public CliCommand init(final Arguments args) throws Exception {
        return this;
    }

    @Override
    public ListAllMyBucketsResult call() throws IOException, SignatureException, SSLSetupException {
        final GetServiceResponse response = getClient().getService(new GetServiceRequest());

        final ListAllMyBucketsResult result = response.getResult();

        return result;
    }
}
