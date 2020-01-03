package com.revtekk.social;

import com.google.gson.Gson;
import com.revtekk.social.proxy.router.Format;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Client
{
    public static void main(String[] args) throws IOException
    {
        Socket proxy = new Socket("localhost", 10002);

        OutputStream os = proxy.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        Format fmt = makeObject();
        Gson gson = new Gson();

        String raw = gson.toJson(fmt);
        System.out.println(raw);

        writeString(os, bw, raw);
        bw.close();
    }

    private static void writeString(OutputStream os, BufferedWriter bw, String str)
            throws IOException
    {
        int len = str.length();

        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(len);

        os.write(buf.array(), 0, 4);
        bw.write(str);

        bw.flush();
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
