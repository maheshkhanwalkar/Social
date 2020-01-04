package com.revtekk.social.register.main;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class RegHandler extends AbstractHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(RegHandler.class);

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException
    {
        String type = request.getContentType();
        String method = request.getMethod();

        // bad request type
        if(type == null || !type.equals("application/json") || !method.equals("POST"))
        {
            LOG.warn("Proxy is not following specifications!");
            LOG.warn("Discarding, sending 400 response");

            response.sendError(400, "Expected JSON POST request");
            baseRequest.setHandled(true);

            return;
        }

        String msg = readMessage(request.getReader(), request.getContentLength());

        if(msg == null)
        {
            LOG.info("JSON message is null, ignoring");

            response.sendError(400, "Bad JSON POST request");
            baseRequest.setHandled(true);
        }

        // TODO parse the JSON message
        response.setStatus(200);
        baseRequest.setHandled(true);
    }

    private String readMessage(BufferedReader br, int length) throws IOException
    {
        char[] raw = new char[length];
        int res = br.read(raw);

        if(res == -1)
            return null;

        return new String(raw);
    }
}
