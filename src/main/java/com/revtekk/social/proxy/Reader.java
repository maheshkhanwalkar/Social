package com.revtekk.social.proxy;

import com.revtekk.nioflex.config.ServerHooks;
import com.revtekk.nioflex.main.Client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Reader implements ServerHooks
{
    @Override
    public boolean onAccept(Client client)
    {
        return true;
    }

    @Override
    public boolean onRead(Client client)
    {
        try
        {
            String raw = client.readString(StandardCharsets.UTF_8);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
