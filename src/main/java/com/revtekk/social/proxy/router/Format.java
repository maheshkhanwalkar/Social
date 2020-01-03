package com.revtekk.social.proxy.router;

import java.util.Map;

public class Format
{
    private String cmd;
    private Map<String, String> data;

    public String getCmd()
    {
        return cmd;
    }

    public void setCmd(String cmd)
    {
        this.cmd = cmd;
    }

    public Map<String, String> getData()
    {
        return data;
    }

    public void setData(Map<String, String> data)
    {
        this.data = data;
    }
}
