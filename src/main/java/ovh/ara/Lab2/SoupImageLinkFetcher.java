package ovh.ara.Lab2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SoupImageLinkFetcher implements IBatchURLFetcher{

	@Override
	public List<String> getContents(String url) {
		List<String> imageList = new ArrayList<>();
		try {
	        Document doc = Jsoup.connect(url).get();
            Elements img = doc.getElementsByTag("img");
            for (Element el : img) {
            	String uri = el.attr("abs:src");
            	imageList.add(uri);
            }
		} catch (IOException ioe) {
			
		}
		
		
        
       
		return imageList;
	}

}
