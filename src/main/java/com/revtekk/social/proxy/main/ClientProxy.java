package com.revtekk.social.proxy.main;

import com.revtekk.nioflex.config.SecurityType;
import com.revtekk.nioflex.config.SocketType;
import com.revtekk.nioflex.impl.ServerBuilder;
import com.revtekk.nioflex.main.Server;
import com.revtekk.social.config.ServerConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;

public class ClientProxy
{
    private static final Logger LOG = LoggerFactory.getLogger(ClientProxy.class);

    private static Server proxy;
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

    private static void startServer() throws IOException
    {
        LOG.info("Initialising server");

        proxy = ServerBuilder.build(
                InetAddress.getByName(conf.getIP()), conf.getPort(), SocketType.SOCKET_TCP,
                SecurityType.SECURITY_NONE, new Reader());

        if(proxy == null)
        {
            LOG.error("Could not create server: check configuration and/or port already bound");
            System.exit(-1);
        }

        LOG.info("Starting server");
        proxy.start();
    }
}
