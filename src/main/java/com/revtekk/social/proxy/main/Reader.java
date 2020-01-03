package com.revtekk.social.proxy.main;

import com.revtekk.nioflex.config.ServerHooks;
import com.revtekk.nioflex.main.Client;
import com.revtekk.social.proxy.router.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Reader implements ServerHooks
{
    private static Router router = new Router();
    private static final Logger LOG = LoggerFactory.getLogger(Reader.class);

    @Override
    public boolean onAccept(Client client)
    {
        LOG.info("Accepted new client");
        return true;
    }

    @Override
    public boolean onRead(Client client)
    {
        try
        {
            LOG.info("Received data from client");

            String raw = client.readString(StandardCharsets.UTF_8);
            router.process(raw);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
