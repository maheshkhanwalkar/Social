package com.revtekk.social.proxy.router;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Router
{
    private static final Gson gson = new Gson();
    private static final Logger LOG = LoggerFactory.getLogger(Router.class);

    public void process(String raw)
    {
        LOG.info("Parsing JSON");
        Format obj = gson.fromJson(raw, Format.class);

        switch (obj.getCmd())
        {
            case "register":
                register(obj.getData());
                break;

            default:
                LOG.warn("unknown command, ignoring");
                break;
        }
    }

    private void register(Map<String, String> data)
    {
        // TODO extract the data from the map
        LOG.info("Processing 'register' command");
    }
}
