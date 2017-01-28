package org.support.project.knowledge.parser.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.support.project.common.exception.ParseException;
import org.support.project.knowledge.parser.SlideShowParser;

public class PdfSlideShowParserOnPdfbox extends AbstractSlideShowParser implements SlideShowParser {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(PdfSlideShowParserOnPdfbox.class);
    
    @Override
    public void parse(File inputFile, File outputDir) throws ParseException {
        try {
            /*
             * Solution for the 1.8 version:
            PDDocument document = PDDocument.loadNonSeq(inputFile, null);
            List<PDPage> pdPages = document.getDocumentCatalog().getAllPages();
            int page = 0;
            for (PDPage pdPage : pdPages) {
                ++page;
                BufferedImage bim = pdPage.convertToImage(BufferedImage.TYPE_INT_RGB, 300);
                ImageIOUtil.writeImage(bim, outputDir.getAbsolutePath() + "/" + page + ".png", 300);
            }
            document.close();
             */

            /*
             * Solution for the 2.0 version:
             */
            PDDocument document = PDDocument.load(inputFile);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage org = pdfRenderer.renderImageWithDPI(page, 100, ImageType.RGB);

                // suffix in filename will be used as the file format
                // ImageIOUtil.writeImage(bim, outputDir.getAbsolutePath() + "/" + (page + 1) + ".png", 300);
                super.writeImage(org, outputDir.getAbsolutePath() + "/" + (page + 1) + ".png");
            }
            document.close();
        } catch (IOException e) {
            throw new ParseException(e);
        } catch (OutOfMemoryError e) {
            LOG.error("OutOfMemoryError");
            throw e;
        }
    }

}

