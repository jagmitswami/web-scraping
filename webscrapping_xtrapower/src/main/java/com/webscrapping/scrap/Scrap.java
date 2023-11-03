package com.webscrapping.scrap;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrap {

	private WriteToFile writeToFile = new WriteToFile();

	private String domain = "https://xtrapowertools.com/";

	public void scrap() {
		String linkPrefix = "sub-category/";

		String[] subCategories = { "xtra-power-professional", "xtra-power-gold", "xtra-power-heavy-duty",
				"hi-max-power-tools", "pressure-washer", "xp-wet-dry-vacuum-cleaner",
				"xp-heavy-duty-wet-dry-vacuum-cleaner", "arc-welding-machine", "mig-welding-machine",
				"tig-welding-machine", "air-compressor", "spare-parts", "wa-wheel", "dc-wheel", "cut-off-wheel",
				"flap-disc", "mounted-flap-wheel", "fiber-disc", "gc-wheel", "polishing-pad", "backing-pad",
				"chain-saw-grinding-wheel", "diamond-stone", "sand-paper", "non-woven-pad", "non-woven-wheel",
				"flap-wheel", "cup-wheel-brush", "jig-saw-blade", "hole-saw", "core-bits", "tile-cutter-blade",
				"lock-installation-kit", "sds-adaptor", "core-drill", "screw-driver-bits", "magnetic-bit-holder",
				"drill-bits", "hss-step-drill-bits", "wood-flat-bit", "drill-chuck", "caulking-gun", "grease-gun",
				"spray-guns", "router-bits", "trimmer-bits", "glass-suction-cup", "chain-chain-bar", "measuring-tape",
				"sprit-level", "xtra-power-x2", "xtra-power", "tct-xtra-power-gold", "tct-awant", "-sgw-tct",
				"diamond-xtra-power", "sgw", "awant", "concut-series", "rewop", "fast-cut", "laser-cut", "b-p",
				"sand-stone", "for-granite", "glass-cutting-blade", "vacuum-welded-multi-purpose",
				"vacuum-brazed-blade", "sds-drill-bits", "chisels" };

		try {
			writeToFile.specifyHeadings();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (String subCategory : subCategories) {
			try {
				// Connect to the web page and retrieve its HTML content using Jsoup
				Document document = Jsoup.connect(domain + linkPrefix + subCategory).get();

				// Extract data from the web page using the CSS selector
				Elements links = document.select("div.cat_box > a[href]");

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
