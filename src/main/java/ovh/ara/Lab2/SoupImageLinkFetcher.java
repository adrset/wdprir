package ovh.ara.Lab2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SoupImageLinkFetcher implements IBatchURLFetcher{

	static 	boolean endsWithAny(String uri){
		final String DESIRED_EXTS[] = {"png", "jpg"};
		for (String key: DESIRED_EXTS){
			if (!uri.endsWith("." + key)){
				continue;
			}
			return true;
		}

		return false;
	}
	@Override
	public List<String> getContents(String url) {
		List<String> imageList = new ArrayList<>();
		try {
	        Document doc = Jsoup.connect(url).get();
            Elements img = doc.getElementsByTag("img");
            for (Element el : img) {
            	String uri = el.attr("src");
            	if (!endsWithAny(uri)){
            		continue;
				}
            	uri = el.attr("abs:src");
            	imageList.add(uri);
            }

			Elements aa = doc.getElementsByTag("a");
			for (Element el : aa) {
				String uri = el.attr("href");
				if (!endsWithAny(uri)){
					continue;
				}
				uri = el.absUrl("href");
				imageList.add(uri);
			}
		} catch (IOException ioe) {
			
		}
		
		
        
       
		return imageList;
	}

}
