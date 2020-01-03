package com.revtekk.social.proxy;

import com.revtekk.nioflex.config.SecurityType;
import com.revtekk.nioflex.config.SocketType;
import com.revtekk.nioflex.impl.ServerBuilder;
import com.revtekk.nioflex.main.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientProxy
{
    private static final Logger LOG = LoggerFactory.getLogger(ClientProxy.class);
    private static Server proxy;
    private static Config conf;

    public static void main(String[] args) throws IOException
    {
        LOG.info("Client Proxy");

        parseConfig();
        startServer();
        connectManager();
    }

    private static void parseConfig() throws IOException
    {
        LOG.info("Reading proxy.yml configuration");

        Yaml yml = new Yaml(new Constructor(Config.class));
        conf = yml.load(new FileInputStream("src/main/resources/config/proxy.yml"));
    }

    private static void startServer() throws IOException
    {
        LOG.info("Initialising server");

        proxy = ServerBuilder.build(
                InetAddress.getByName(conf.getProxyIP()), conf.getProxyPort(), SocketType.SOCKET_TCP,
                SecurityType.SECURITY_NONE, new Reader());

        if(proxy == null) {
            LOG.error("Could not create server: check configuration and/or port already bound");
            System.exit(-1);
        }

        LOG.info("Starting server");
        proxy.start();
    }

    private static void connectManager()
    {
        LOG.info("Connecting to Manager service");

        String ip = conf.getMgrIP();
        int port = conf.getMgrPort();

        int failures = 0;

        while(failures < conf.getFailCount())
        {
            try
            {
                Socket socket = new Socket(ip, port);
                InputStream is = socket.getInputStream();

                int res = is.read();

                proxy.shutdown();
                break;
            }
            catch (IOException e)
            {
                LOG.warn("Connection to Manager server broken");
                e.printStackTrace();
                LOG.info("Attempting to establish new connection");

                failures++;
            }
            catch (InterruptedException e)
            {
                LOG.error("Could not gracefully stop the server");
                LOG.error("Forcefully shutting down now....");

                System.exit(-1);
            }
        }

        LOG.warn("Failure threshold reached!");
        LOG.warn("Server cannot be automatically shutdown (must be manually terminated)");
    }
}
