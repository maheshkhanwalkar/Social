package com.revtekk.social.proxy.main;

import com.revtekk.social.config.ServerConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.IOException;

public class ClientProxy
{
    private static final Logger LOG = LoggerFactory.getLogger(ClientProxy.class);
    private static ServerConf conf;

    public static void main(String[] args) throws IOException
    {
        LOG.info("Client Proxy");

        parseConfig();
        startServer();
    }

    private static void parseConfig() throws IOException
    {
        LOG.info("Reading proxy.yml configuration");

        Yaml yml = new Yaml(new Constructor(ServerConf.class));
        conf = yml.load(new FileInputStream("src/main/resources/config/proxy.yml"));
    }

    private static void startServer()
    {
        LOG.info("Initialising server");
        // TODO start a server and add hooks
    }
}
