package ovh.ara.Lab2;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class HttpFetcher {

    private static HttpFetcher fetcherInstance;
    private HttpFetcher(){}

    public static synchronized HttpFetcher getInstance(){
        if (fetcherInstance == null) {
            fetcherInstance = new HttpFetcher();
        }

        return  fetcherInstance;
    }

    public String getWebPage(String url){
        HttpGet request = null;
        String content = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            request = new HttpGet(url);

            request.addHeader("User-Agent", "Apache HTTPClient");
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);


        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }

        return content;
    }

    public Image getImage(String uri){

        try {
            URL url = new URL(uri);


            Image image = ImageIO.read( url.openStream());

            return image;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }




}
