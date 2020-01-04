package com.revtekk.social.proxy.main;

import com.revtekk.social.proxy.router.Router;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ProxyHandler extends AbstractHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(ProxyHandler.class);
    private static final Router router = Router.getInstance();

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException
    {
        String type = request.getContentType();
        String method = request.getMethod();

        // bad request type
        if(type == null || !type.equals("application/json") || !method.equals("POST"))
        {
            LOG.info("Bad request (malformed) from client");
            LOG.info("Discarding, sending 400 response to client...");

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

            return;
        }

        boolean good = router.process(msg);

        if(!good)
        {
            LOG.info("Wrong JSON scheme from client or internal failure");
            LOG.info("Discarding, sending 400 response to client...");

            response.sendError(400, "Bad JSON POST request");
            baseRequest.setHandled(true);

            return;
        }

        LOG.info("Sent OK response to client");

        response.setStatus(200);
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().println("OK");

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
