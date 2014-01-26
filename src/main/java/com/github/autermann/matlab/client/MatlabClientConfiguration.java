/*
 * Copyright (C) 2012-2013 by it's authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.autermann.matlab.client;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.net.URI;

import com.github.autermann.matlab.server.MatlabInstanceConfiguration;
import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public abstract class MatlabClientConfiguration {

    public static Builder builder() {
        return new Builder();
    }

    public static class Local extends MatlabClientConfiguration {
        private final MatlabInstanceConfiguration instanceConfiguration;

        public Local(MatlabInstanceConfiguration conf) {
            this.instanceConfiguration = conf;
        }

        public MatlabInstanceConfiguration getInstanceConfiguration() {
            return this.instanceConfiguration;
        }
    }

    public static class Remote extends MatlabClientConfiguration {
        private final URI address;

        private Remote(URI address) {
            this.address = address;
        }

        public URI getAddress() {
            return address;
        }
    }

    public static class Builder {
        private URI address;
        private MatlabInstanceConfiguration instanceConfiguration;

        private Builder() {
        }

        public Builder withAddress(URI address) {
            checkNotNull(address);
            checkArgument(address.getScheme().equals("ws") ||
                          address.getScheme().equals("wss"));
            this.address = address;
            return this;
        }

        public Builder withAddress(String host, int port) {
            checkNotNull(host);
            checkArgument(port > 0);
            return withAddress(URI.create(String
                    .format("ws://%s:%s", host, port)));
        }

        public Builder withDirectory(String directory) {
            return withDirectory(new File(directory));
        }

        public Builder withDirectory(File directory) {
            checkNotNull(directory);
            checkArgument(directory.exists());
            return withInstanceConfiguration(MatlabInstanceConfiguration
                    .builder().hidden().withBaseDir(directory).build());
        }

        public Builder withInstanceConfiguration(
                MatlabInstanceConfiguration options) {
            this.instanceConfiguration = Preconditions.checkNotNull(options);
            this.address = null;
            return this;
        }

        public MatlabClientConfiguration build() {
            if (address != null) {
                return new MatlabClientConfiguration.Remote(address);
            } else {
                if (instanceConfiguration == null) {
                    instanceConfiguration = MatlabInstanceConfiguration
                            .builder().hidden().build();
                }
                return new MatlabClientConfiguration.Local(instanceConfiguration);
            }
        }
    }
}