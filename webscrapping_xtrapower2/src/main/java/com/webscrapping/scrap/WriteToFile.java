package com.webscrapping.scrap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WriteToFile {

	private String domain = "https://xtrapowertools.com/";

	public void specifyHeadings(String subCategory) throws IOException {

		try (FileWriter fileWriter = new FileWriter("F:\\mkdistributors\\xp_data_row\\" + subCategory + ".csv", true);
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

			// headings
			csvPrinter.print("Product Name");
			csvPrinter.print("SkuCode");
			csvPrinter.print("Category");
			csvPrinter.print("Price (RS.)");
			csvPrinter.print("Quantity");
			csvPrinter.print("Description");
			csvPrinter.print("About This Item");

			for (int j = 1; j <= 5; j++)
				csvPrinter.print("image " + j);
			csvPrinter.print("");
		}
		System.out.println("Headings specified to .csv file");
	}

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
		String product = section.select("h4.pro_name").first().text();

		String skuCode = product.replaceAll(" ", "-");
		skuCode = skuCode.replaceAll("/", "by");
		skuCode = skuCode.replaceAll("\"", "In");

		List<String> imgSrcList = new ArrayList<>();
		Elements imgElements = section.select("img");
		for (Element imgElement : imgElements) {
			String imgSrc = domain + imgElement.attr("src");
			imgSrcList.add(imgSrc);
		}

		// Extract data from table
		Element descElement = section.select("table").first();
		String desc = descElement.html();

		Element section2 = null;

		try {
			// Connect to the web page and retrieve its HTML content using Jsoup
			Document document = Jsoup.connect(webpageUrl).get();

			// Extract data from the web page using the CSS selector
			section2 = document.select(".product_details_one").first();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Element aboutElement = section2.select(".col-md-6 > ul").first();
		String about = "";
		if(aboutElement != null) about = aboutElement.outerHtml();

		// Write the extracted data to .CSV file
		String csvFilePath = "F:\\mkdistributors\\xp_data_row\\" + subCategory + ".csv";
		try (FileWriter fileWriter = new FileWriter(csvFilePath, true);
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

			csvPrinter.printRecord();
			// Product details
			csvPrinter.print(product);
			csvPrinter.print(skuCode);
			csvPrinter.print(subCategory);
			csvPrinter.print(5555);
			csvPrinter.print(100);
			csvPrinter.print(desc);
			csvPrinter.print(about);

			for (int i = 0; i < 5; i++) {
				String imgSrc = i < imgSrcList.size() ? "img_site/" + "2023/" + "10/" + skuCode + "-" + (i+1) + ".jpg" : "";
				csvPrinter.print(imgSrc);
			}
		}
		System.out.println("Data saved to .csv file");
	}

}