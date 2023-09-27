package com.webscrapping.scrap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrap {

	private WriteToFile writeToFile = new WriteToFile();

	private String domain = "https://xtrapowertools.com/";

	public void scrap() {
		String linkPrefix = "sub-category/";

		String[] subCategories = {"wa-wheel", "dc-wheel", "cut-off-wheel",
				"flap-disc", "mounted-flap-wheel", "fiber-disc", "gc-wheel", "polishing-pad", "backing-pad",
				"chain-saw-grinding-wheel", "diamond-stone", "sand-paper", "non-woven-pad", "non-woven-wheel",
				"flap-wheel", "cup-wheel-brush", "jig-saw-blade", "hole-saw", "core-bits", "tile-cutter-blade",
				"lock-installation-kit", "sds-adaptor", "core-drill", "screw-driver-bits", "magnetic-bit-holder",
				"drill-bits", "hss-step-drill-bits", "wood-flat-bit", "drill-chuck", "caulking-gun", "grease-gun",
				"spray-guns", "router-bits", "trimmer-bits", "glass-suction-cup", "chain-chain-bar", "measuring-tape",
				"sprit-level", "xtra-power-x2", "xtra-power", "tct-xtra-power-gold", "tct-awant", "-sgw-tct",
				"diamond-xtra-power", "sgw", "awant", "concut-series", "rewop", "fast-cut", "laser-cut", "b-p",
				"sand-stone", "for-granite", "glass-cutting-blade", "vacuum-welded-multi-purpose",
				"vacuum-brazed-blade", "sds-drill-bits", "chisels"};

		for (String subCategory : subCategories) {
			try {
				// Connect to the web page and retrieve its HTML content using Jsoup
				Document document = Jsoup.connect(domain + linkPrefix + subCategory).get();

				// Extract data from the web page using the CSS selector
				Elements links = document.select("div.cat_box > a[href]");

				Map<String, String> headings = null;
				for (Element link : links) {
					String href = link.attr("href");
					headings = writeToFile.specifyHeadings(domain + href, subCategory);
				}
				try (FileWriter fileWriter = new FileWriter("F:\\mkdistributors\\Data\\" + subCategory + ".csv",
						true); CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {

					for (Map.Entry<String, String> heading : headings.entrySet()) {
						String key = heading.getKey();
						csvPrinter.print(key);
					}
				}
				for (Element link : links) {
					String href = link.attr("href");
					writeToFile.writeData(domain + href, subCategory);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
