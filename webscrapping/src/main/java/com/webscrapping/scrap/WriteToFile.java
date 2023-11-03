package com.webscrapping.scrap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WriteToFile {

	private String domain = "https://xtrapowertools.com/";

	private String previousCategory = "none";

	private Map<String, String> headings;

	public Map<String, String> specifyHeadings(String webpageUrl, String subCategory) throws IOException {

		Element section = null;

		try {
			// Connect to the web page and retrieve its HTML content using Jsoup
			Document document = Jsoup.connect(webpageUrl).get();

			// Extract data from the web page using the CSS selector
			section = document.select(".cat_section").first();

		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements tableElements = section.select("table.table-bordered td");
		List<String> tableDataList = new ArrayList<>();
		for (Element td : tableElements) {
			// Extract data from <td> elements within each <table>
			tableDataList.add(td.text());
		}

		try (FileWriter fileWriter = new FileWriter("F:\\mkdistributors\\row_wise\\" + subCategory + ".csv", true);
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
			if (!previousCategory.equals(subCategory)) {
				// headings
				headings = new LinkedHashMap<>();
				csvPrinter.print("Product Name");
				csvPrinter.print("Category");
				csvPrinter.print("Price (RS.)");
				csvPrinter.print("Quantity");

				for (int j = 1; j <= 5; j++)
					csvPrinter.print("image " + j);
				csvPrinter.print("");
			}
			for (int i = 0; i < tableDataList.size() - 1; i += 2) {
				String heading = tableDataList.get(i);
				if (!headings.containsKey(heading)) {
					headings.put(heading, "");
				}
			}
		}
		previousCategory = subCategory;
		System.out.println("Headings specified to .csv file");
		return headings;
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
		Elements tableElements = section.select("table.table-bordered td");
		List<String> tableDataList = new ArrayList<>();
		for (Element td : tableElements) {
			// Extract data from <td> elements within each <table>
			tableDataList.add(td.text());
		}

		// Save table data to map
		Map<String, String> table = new HashMap<>();
		for (int i = 0; i < tableDataList.size() - 1; i += 2) {
			String heading = tableDataList.get(i);
			String value = tableDataList.get(i + 1);
			table.put(heading, value);
		}

		// Create a CSV file and write the extracted data
		String csvFilePath = "F:\\mkdistributors\\row_wise\\" + subCategory + ".csv";
		try (FileWriter fileWriter = new FileWriter(csvFilePath, true);
				CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

			csvPrinter.printRecord();
			// Product details
			csvPrinter.print(product);
			csvPrinter.print(subCategory);
			csvPrinter.print(5555);
			csvPrinter.print(100);

			for (int i = 0; i < 5; i++) {
				String imgSrc = i < imgSrcList.size() ? imgSrcList.get(i) : "";
				csvPrinter.print(imgSrc);
			}

			for (Map.Entry<String, String> heading : headings.entrySet()) {
				String value = table.getOrDefault(heading.getKey(), "");
				csvPrinter.print(value);
			}
		}
		table.clear();
		System.out.println("Data saved to .csv file");
	}

}