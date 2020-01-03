package com.revtekk.social.proxy.router;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Router
{
    private static final Gson gson = new Gson();
    private static final Logger LOG = LoggerFactory.getLogger(Router.class);

    public boolean process(String raw)
    {
        LOG.info("Parsing JSON");
        Format obj = gson.fromJson(raw, Format.class);

        switch (obj.getCmd())
        {
            case "register":
                return register(obj.getData());
            default:
                LOG.info("unknown command, ignoring");
                return false;
        }
    }

    private boolean register(Map<String, String> data)
    {
        LOG.info("Processing 'register' command");

        // ensure all the parameters are there
        if(!check(data, "first", "last", "dob", "cell", "email"))
        {
            LOG.info("JSON is missing some entries, ignoring request");
            return false;
        }

        // TODO send this to the Register service
        return true;
    }

    private boolean check(Map<String, String> data, String... keys)
    {
        for(String key : keys)
        {
            if(!data.containsKey(key))
                return false;

            if(data.get(key) == null)
                return false;
        }

        return true;
    }
}
