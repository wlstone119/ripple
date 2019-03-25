package com.xdong.ripple.crawler.image;

import java.io.File;
import java.util.UUID;
import com.google.common.io.Files;
import com.xdong.ripple.crawler.common.AbstractCrawlerClass;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class ImageCrawler extends AbstractCrawlerClass {

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (filmPattern.matcher(href).matches()) {
            return false;
        }

        if (imgPattern.matcher(href).matches()) {
            return true;
        }

        for (String domain : crawlDomains) {
            if (href.startsWith(domain)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        if (!imgPattern.matcher(url).matches()
            || !((page.getParseData() instanceof BinaryParseData) || (page.getContentData().length < (1 * 1024)))) {
            return;
        }

        String extension = url.substring(url.lastIndexOf('.'));
        String hashedName = UUID.randomUUID() + extension;

        String filename = storageFolder.getAbsolutePath() + "/" + hashedName;
        try {
            Files.write(page.getContentData(), new File(filename));
            logger.info("Stored: {}", url);
        } catch (Exception iox) {
            logger.error("Failed to write file: " + filename, iox);
        }
    }

    public static void main(String[] args) throws Exception {
        String[] crawlDomains = { "http://www.mmonly.cc/tag/kj/" };
        start(ImageCrawler.class, crawlDomains, 10, 50, null, null, null);
    }
}
