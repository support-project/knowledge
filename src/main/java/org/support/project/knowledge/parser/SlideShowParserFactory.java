package org.support.project.knowledge.parser;

import java.io.IOException;

import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.parser.impl.PdfSlideShowParserOnPdfbox;
import org.support.project.knowledge.parser.impl.PptxSlideShowParser;

public class SlideShowParserFactory {

    private SlideShowParserFactory() {
    }

    public static SlideShowParser getParser(String filename) throws IOException {
        String extension = StringUtils.getExtension(filename);
        if (StringUtils.isEmpty(extension)) {
            return null;
        }
        if (extension.toLowerCase().equals(".pptx")) {
            return new PptxSlideShowParser();
        } else if (extension.toLowerCase().equals(".pdf")) {
            return new PdfSlideShowParserOnPdfbox();
        } else {
            return null;
        }
    }
}
