package com.webscrapping.scrap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloadImages {

	private String domain = "https://xtrapowertools.com/";

	public void writeData(String webpageUrl, String subCategory) throws IOException {

		Element section = null;

		try {
			// Connect to the web page and retrieve its HTML content using Jsoup
			Document document = Jsoup.connect(webpageUrl).get();

			// Extract data from the web page using the CSS selector
			section = document.select(".cat_section").first();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Extract the the product name
		String product = section.select("h4.pro_name").first().text().replaceAll(" ", "-");
		product = product.replaceAll("/", "by");
		product = product.replaceAll("\"", "In");

		Elements imgElements = section.select("img");
		int counter = 1;
		for (Element imgElement : imgElements) {
			String imgSrc = domain + imgElement.attr("src");

			// Download
			String filePath = "F:\\mkdistributors\\xp_images_new";
			CloseableHttpClient httpClient = HttpClients.createDefault();

			try {

				// Send a GET request to the image URL
				HttpGet httpGet = new HttpGet(imgSrc);
				HttpResponse response = httpClient.execute(httpGet);

				// Check if the response is successful
				if (response.getStatusLine().getStatusCode() == 200) {
					// Generate a custom name for the image
					String customFileName = product + "-" + counter + ".jpg";
					counter++;

					// Create a file object for the image
					File imageFile = new File(filePath, customFileName);

					// Create a FileOutputStream to write the image data to the file
					try (InputStream inputStream = response.getEntity().getContent();
							FileOutputStream outputStream = new FileOutputStream(imageFile)) {
						int bytesRead;
						byte[] buffer = new byte[4096];

						while ((bytesRead = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, bytesRead);
						}

						System.out.println("Image downloaded and saved as " + customFileName);
					}
				} else {
					System.out.println("Failed to download the image. HTTP status code: "
							+ response.getStatusLine().getStatusCode());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}