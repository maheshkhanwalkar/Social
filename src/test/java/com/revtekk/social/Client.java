package com.revtekk.social;

import com.revtekk.social.proxy.router.Format;

import java.util.HashMap;
import java.util.Map;

public class Client
{
    public static void main(String[] args)
    {
        // TODO implement a simple client
    }

    private static Format makeObject()
    {
        Format fmt = new Format();
        fmt.setCmd("register");

        Map<String, String> map = new HashMap<>();

        map.put("fname", "Adam");
        map.put("lname", "Smith");

        fmt.setData(map);
        return fmt;
    }
}
