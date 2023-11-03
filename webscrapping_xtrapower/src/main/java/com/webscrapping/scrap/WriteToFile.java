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

	public void specifyHeadings() throws IOException {

		try (FileWriter fileWriter = new FileWriter("F:\\mkdistributors\\xpdata" + ".csv", true);
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

			// headings
			csvPrinter.print("Product Name");
			csvPrinter.print("SkuCode");
			csvPrinter.print("Category");
			csvPrinter.print("Price (RS.)");
			csvPrinter.print("Quantity");
			csvPrinter.print("Description");

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

		List<String> imgSrcList = new ArrayList<>();
		Elements imgElements = section.select("img");
		for (Element imgElement : imgElements) {
			String imgSrc = domain + imgElement.attr("src");
			imgSrcList.add(imgSrc);
		}

		// Extract data from table
		Element tableElement = section.select("table").first();
		String table = tableElement.html();

		// Write the extracted data to .CSV file
		String csvFilePath = "F:\\mkdistributors\\xpdata" + ".csv";
		try (FileWriter fileWriter = new FileWriter(csvFilePath, true);
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

			csvPrinter.printRecord();
			// Product details
			csvPrinter.print(product);
			csvPrinter.print(product);
			csvPrinter.print(subCategory);
			csvPrinter.print(5555);
			csvPrinter.print(100);
			csvPrinter.print(table);

			for (int i = 0; i < 5; i++) {
				String imgSrc = i < imgSrcList.size() ? imgSrcList.get(i) : "";
				csvPrinter.print(imgSrc);
			}
		}
		System.out.println("Data saved to .csv file");
	}

}