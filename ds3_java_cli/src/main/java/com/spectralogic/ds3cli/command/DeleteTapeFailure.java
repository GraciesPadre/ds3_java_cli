/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3cli.exceptions.CommandException;
import com.spectralogic.ds3cli.models.DeleteResult;
import com.spectralogic.ds3cli.util.Ds3Provider;
import com.spectralogic.ds3cli.util.FileUtils;
import com.spectralogic.ds3client.commands.spectrads3.DeleteTapeFailureSpectraS3Request;
import org.apache.commons.cli.MissingArgumentException;

import java.io.IOException;
import java.util.UUID;

public class DeleteTapeFailure extends CliCommand<DeleteResult> {

    private UUID id;

    public DeleteTapeFailure(final Ds3Provider ds3Provider, final FileUtils fileUtils) {
        super(ds3Provider, fileUtils);
    }

    @Override
    public CliCommand init(final Arguments args) throws Exception {

        final String idString = args.getId();
        if (idString == null) {
            throw new MissingArgumentException("'-i' is required to delete a tape failure");
        }

        this.id = UUID.fromString(idString);
        return this;
    }

    @Override
    public DeleteResult call() throws Exception {

        try {
            this.getClient().deleteTapeFailureSpectraS3(new DeleteTapeFailureSpectraS3Request(id.toString()));
        }
        catch (final IOException e) {
            throw new CommandException("Error: Request failed with the following error: " + e.getMessage(), e);
        }

        return new DeleteResult("Success: Deleted tape failure Id: " + id.toString());
    }
}