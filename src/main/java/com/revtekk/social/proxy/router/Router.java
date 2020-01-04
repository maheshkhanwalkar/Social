package com.revtekk.social.proxy.router;

import com.google.gson.Gson;
import com.revtekk.social.config.ServerConf;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.util.Map;

public class Router
{
    private Gson gson = new Gson();
    private HttpClient client = new HttpClient();

    private ServerConf regConf;

    private static final Logger LOG = LoggerFactory.getLogger(Router.class);
    private static final Router router = new Router();

    private Router()
    {
        try
        {
            client.setFollowRedirects(false);
            client.start();

            Yaml yml = new Yaml(new Constructor(ServerConf.class));
            regConf = yml.load(new FileInputStream("src/main/resources/config/register.yml"));
        }
        catch (Exception e)
        {
            LOG.error("Could not start http client");
            LOG.error(e.getMessage());
            LOG.error("Server shutting down....");

            System.exit(-1);
        }
    }

    public static Router getInstance()
    {
        return router;
    }

    public boolean process(String raw)
    {
        LOG.info("Parsing JSON");
        Format obj = gson.fromJson(raw, Format.class);

        if(obj.getCmd() == null)
        {
            LOG.info("null command, ignoring");
            return false;
        }

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

        try
        {
            String json = gson.toJson(data);

            ContentResponse resp = client.newRequest(regConf.getIP(), regConf.getPort())
                    .method(HttpMethod.POST)
                    .content(new StringContentProvider(json), "application/json")
                    .send();

            if(resp.getStatus() != 200)
            {
                LOG.warn("Bad response status from Register service");
                LOG.warn("Request will be ignored!");

                return false;
            }

            return true;
        }
        catch (Exception e)
        {
            LOG.error("Could not send to Register service");
            LOG.error(e.getMessage());

            return false;
        }
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
