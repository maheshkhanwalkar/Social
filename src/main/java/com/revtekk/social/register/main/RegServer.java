package com.revtekk.social.register.main;

import com.revtekk.social.config.ServerConf;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

public class RegServer
{
    private static final Logger LOG = LoggerFactory.getLogger(RegServer.class);
    private static ServerConf conf;

    public static void main(String[] args) throws IOException
    {
        LOG.info("Register Service");

        parseConfig();
        startServer();
    }

    private static void parseConfig() throws IOException
    {
        LOG.info("Reading register.yml configuration");

        Yaml yml = new Yaml(new Constructor(ServerConf.class));
        conf = yml.load(new FileInputStream("src/main/resources/config/register.yml"));
    }

    private static void startServer() throws IOException
    {
        LOG.info("Initialising server");

        Server proxy = new Server(new InetSocketAddress(conf.getIP(), conf.getPort()));
        proxy.setHandler(new RegHandler());

        try
        {
            proxy.start();
            proxy.join();
        }
        catch (Exception e)
        {
            LOG.error("Could not start server!");
            LOG.error(e.getMessage());
            LOG.error("Exiting...");

            System.exit(-1);
        }
    }
}
