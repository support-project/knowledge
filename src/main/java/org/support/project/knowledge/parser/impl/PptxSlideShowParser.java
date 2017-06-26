package org.support.project.knowledge.parser.impl;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.SlideShowFactory;
import org.support.project.common.exception.ParseException;
import org.support.project.knowledge.parser.SlideShowParser;

public class PptxSlideShowParser extends AbstractSlideShowParser implements SlideShowParser {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(PptxSlideShowParser.class);

    @Override
    public void parse(File inputFile, File outputDir) throws ParseException {
        try (SlideShow<?, ?> ss = SlideShowFactory.create(inputFile, null, true)) {
            List<? extends Slide<?, ?>> slides = ss.getSlides();

            Dimension pgsize = ss.getPageSize();
            int width = (int) (pgsize.width);
            int height = (int) (pgsize.height);

            int slideNo = 1;
            for (Slide<?, ?> slide : slides) {
                String title = slide.getTitle();
                System.out.println("Rendering slide " + slideNo + (title == null ? "" : ": " + title));

                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                slide.draw(graphics);
                img.flush();
                //ImageIOUtil.writeImage(img, outputDir.getAbsolutePath() + "/" + (slideNo) + ".png", 300);
                super.writeImage(img, outputDir.getAbsolutePath() + "/" + (slideNo) + ".png");
                graphics.dispose();
                slideNo++;
            }
        } catch (EncryptedDocumentException | IOException e) {
            throw new ParseException(e);
        } catch (OutOfMemoryError e) {
            LOG.error("OutOfMemoryError");
            throw e;
        }

    }

}
