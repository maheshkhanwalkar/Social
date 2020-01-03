package com.revtekk.social.proxy;

public class Config
{
    private String proxyIP;
    private int proxyPort;

    private String mgrIP;
    private int mgrPort;

    private int failCount = 5;

    // Proxy getter-setters
    public String getProxyIP()
    {
        return proxyIP;
    }

    public void setProxyIP(String proxyIP)
    {
        this.proxyIP = proxyIP;
    }

    public int getProxyPort()
    {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort)
    {
        this.proxyPort = proxyPort;
    }

    public int getFailCount()
    {
        return failCount;
    }

    public void setFailCount(int failCount)
    {
        this.failCount = failCount;
    }

    // Manager getter-setters
    public String getMgrIP()
    {
        return mgrIP;
    }

    public void setMgrIP(String mgrIP)
    {
        this.mgrIP = mgrIP;
    }

    public int getMgrPort()
    {
        return mgrPort;
    }

    public void setMgrPort(int mgrPort)
    {
        this.mgrPort = mgrPort;
    }
}
