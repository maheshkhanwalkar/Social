package com.revtekk.social.proxy;

public class Config
{
    private int port;

    private String mgrIP;
    private int mgrPort;

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getMgrPort()
    {
        return mgrPort;
    }

    public void setMgrPort(int mgrPort)
    {
        this.mgrPort = mgrPort;
    }

    public String getMgrIP()
    {
        return mgrIP;
    }

    public void setMgrIP(String mgrIP)
    {
        this.mgrIP = mgrIP;
    }
}
