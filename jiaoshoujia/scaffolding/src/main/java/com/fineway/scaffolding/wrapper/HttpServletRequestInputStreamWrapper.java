package com.fineway.scaffolding.wrapper;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class HttpServletRequestInputStreamWrapper extends HttpServletRequestWrapper {
    private ByteArrayInputStream body;

    @Override
    public ServletInputStream getInputStream() throws IOException {
        body.reset();
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return body.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new RuntimeException("not impl setReadListener");
            }

            @Override
            public int read() throws IOException {
                return body.read();
            }
        };
    }

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public HttpServletRequestInputStreamWrapper(HttpServletRequest request) {
        super(request);
        try {
            byte[] bytes = IOUtils.toByteArray(request.getInputStream());
            body = new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
