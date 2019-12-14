package ovh.ara.Lab2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import ovh.ara.Lab1.threads.IThreadService;
import ovh.ara.Lab1.threads.ThreadPoolService;
import sun.misc.Regexp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab2 {

    IThreadService service = new ThreadPoolService();

    public Lab2(){
        HttpFetcher fetcher = HttpFetcher.getInstance();
        String content = fetcher.getWebPage("http://fizyka.pw.edu.pl");
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile("<img\\s+(?:[^>]*?\\s+)?src=([\"'])(.*?)\\1")
                .matcher(content);
        while (m.find()) {
            allMatches.add(m.group(2));
        }

        List <ImageFetch> images = new ArrayList<>();
        for (int ii=0; ii<allMatches.size(); ii++){
            images.add( new ImageFetch("http://fizyka.pw.edu.pl/" + allMatches.get(ii)));
        }

        service.init(allMatches.size());
        for (int ii=0; ii<images.size(); ii++){
            images.get(ii).setLatch(service.getLatch());
            service.submit(images.get(ii));
        }

        try {
            service.await();
            service.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public static void main(String[] args) throws Exception{
       new Lab2();

    }

}
