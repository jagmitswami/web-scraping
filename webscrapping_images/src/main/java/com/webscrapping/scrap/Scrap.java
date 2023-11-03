package com.webscrapping.scrap;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrap {

	private DownloadImages downloadImages = new DownloadImages();

	private String domain = "https://xtrapowertools.com/";

	public void scrap() {
		String linkPrefix = "sub-category/";

		String[] subCategories = { "xtra-power-professional", "xtra-power-gold", "xtra-power-heavy-duty",
				"hi-max-power-tools", "pressure-washer", "xp-wet-dry-vacuum-cleaner",
				"xp-heavy-duty-wet-dry-vacuum-cleaner", "arc-welding-machine", "mig-welding-machine",
				"tig-welding-machine", "air-compressor"};

		
		for (String subCategory : subCategories) {
			try {
				// Connect to the web page and retrieve its HTML content using Jsoup
				Document document = Jsoup.connect(domain + linkPrefix + subCategory).get();

				// Extract data from the web page using the CSS selector
				Elements links = document.select("div.cat_box > a[href]");

				for (Element link : links) {
					String href = link.attr("href");
					downloadImages.writeData(domain + href, subCategory);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
