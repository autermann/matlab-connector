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
package org.uncertweb.matlab.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uncertweb.matlab.MLException;
import org.uncertweb.matlab.MLHandler;
import org.uncertweb.matlab.MLRequest;
import org.uncertweb.matlab.MLResult;
import org.uncertweb.matlab.socket.DefaultSocketFactory;
import org.uncertweb.matlab.socket.SocketFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann <c.autermann@52north.org>
 */
public class MLClient {
    private static final Logger log = LoggerFactory.getLogger(MLClient.class);
    private static final int CONNECT_TIMEOUT = 10 * 1000; // 10s to connect
    private final MLHandler handler = new MLHandler();
    private final SocketFactory socketFactory;

    public MLClient(SocketFactory socketFactory) {
        this.socketFactory = Preconditions.checkNotNull(socketFactory);
    }

    public MLClient() {
        this(new DefaultSocketFactory());
    }

    /**
     * Sends a request to a MATLAB server. The server must be using the supplied
     * server.m and waiting for
     * connections on the specified port.
     *
     * @param host    the address of the MATLAB server
     * @param port    the port of the MATLAB server
     * @param request the <code>MLRequest</code> to send
     *
     * @return the <code>MLResult</code> of the function
     *
     * @throws MLException if MATLAB encountered an error during function
     *                     execution
     * @throws IOException if the connection to the MATLAB server failed
     */
    // TODO: sometimes a SocketException gets thrown (possible to do with lots of requests in a short amount of time), for now there's just three attempts if this happens
    public MLResult sendRequest(String host, int port, MLRequest request) throws
            MLException, IOException {
        SocketException thrown = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                return _sendRequest(new SocketConnection(host, port), request);
            } catch (SocketException e) {
                thrown = e;
            }
        }
        throw thrown;
    }

    public MLResult sendRequest(String mlProxyURL, MLRequest request) throws
            MLException, IOException {
        Connection connection = new ProxyUrlConnection(mlProxyURL);
        connection.getOutput().write("request=".getBytes());
        return _sendRequest(connection, request);
    }

    private MLResult _sendRequest(Connection con, MLRequest request)
            throws MLException, IOException {
        try {
            handler.outputRequest(request, con.getOutput());
            return parseResponse(con);
        } finally {
            con.close();
        }
    }

    private MLResult parseResponse(InputSupplier<? extends InputStream> in)
            throws IOException, MLException {
        String response = CharStreams.toString(CharStreams
                .newReaderSupplier(in, Charsets.UTF_8));
        // FIXME speedup, not the best
        if (response.startsWith("{\"exception\"")) {
            throw handler.parseException(response);
        } else {
            // not an error, must be result
            return handler.parseResult(response);
        }
    }

    private abstract class Connection implements InputSupplier<InputStream>,
                                                 OutputSupplier<OutputStream> {
        public void close() {
            try {
                getInput().close();
            } catch (IOException e) {
                log.error("Error closing input stream", e);
            }
            try {
                getOutput().close();
            } catch (IOException e) {
                log.error("Error closing output stream", e);
            }
        }
    }

    private class SocketConnection extends Connection {
        private final Socket socket;

        SocketConnection(String host, int port) throws IOException {
            this.socket = socketFactory
                    .createSocket(host, port, CONNECT_TIMEOUT);
        }

        @Override
        public InputStream getInput() throws IOException {
            return socket.getInputStream();
        }

        @Override
        public OutputStream getOutput() throws IOException {
            return socket.getOutputStream();
        }

        @Override
        public void close() {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("Error closing socket", e);
            }
        }
    }

    private class ProxyUrlConnection extends Connection {
        private final URLConnection connection;

        ProxyUrlConnection(String mlProxyURL) throws IOException {
            connection = new URL(mlProxyURL).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
        }

        @Override
        public InputStream getInput() throws IOException {
            return connection.getInputStream();
        }

        @Override
        public OutputStream getOutput() throws IOException {
            return connection.getOutputStream();
        }
    }
}
