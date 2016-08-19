package com.aliyun.acs.demo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

public class Counter extends HttpServlet {

    public Connection getConnection() {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String passwd = System.getenv("DB_PASSWD");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, user, passwd);
        } catch (Exception e) {
            throw new RuntimeException("Error create connection to " + url, e);
        }

    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();
            s.executeUpdate("update counter set total=total+10");
            resp.setContentLength(0);
            s.close();
            c.close();
        } catch (SQLException e) {
            onError(req, resp, e);
        }
    }

    private void onError(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String msg = sw.toString();
            byte[] data = msg.getBytes("UTF-8");
            map.put("msg", sw.toString());
            resp.setContentType("application/text");
            resp.setContentLength(data.length);
            resp.getOutputStream().write(data);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select total from counter limit 1");
            rs.next();
            int total = rs.getInt("total");
            resp.getWriter().write(String.valueOf(total));
        } catch (Exception e) {
            onError(req, resp, e);
        }
    }
}
