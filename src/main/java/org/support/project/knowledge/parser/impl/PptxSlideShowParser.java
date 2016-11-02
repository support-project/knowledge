package org.support.project.knowledge.parser.impl;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.sl.draw.Drawable;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.SlideShowFactory;
import org.apache.poi.util.JvmBugs;
import org.support.project.common.exception.ParseException;
import org.support.project.knowledge.parser.SlideShowParser;

public class PptxSlideShowParser implements SlideShowParser {

    @Override
    public void parse(File inputFile, File outputDir) throws ParseException {
        float scale = 0.8f;
        String format = "png";

        try {
            SlideShow<?, ?> ss = SlideShowFactory.create(inputFile, null, true);
            List<? extends Slide<?, ?>> slides = ss.getSlides();

            Dimension pgsize = ss.getPageSize();
            int width = (int) (pgsize.width * scale);
            int height = (int) (pgsize.height * scale);

            int slideNo = 1;
            for (Slide<?, ?> slide : slides) {
                String title = slide.getTitle();
                System.out.println("Rendering slide " + slideNo + (title == null ? "" : ": " + title));

                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = img.createGraphics();
                fixFonts(graphics);

                // default rendering options
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

                graphics.scale(scale, scale);

                // draw stuff
                slide.draw(graphics);

                // save the result
                if (!"null".equals(format)) {
//                    String outname = inputFile.getName().replaceFirst(".pptx?", "");
//                    outname = String.format(Locale.ROOT, "%1$s-%2$04d.%3$s", outname, slideNo, format);
//                    File outfile = new File(outputDir, outname);
//                    ImageIO.write(img, format, outfile);
                    ImageIOUtil.writeImage(img, outputDir.getAbsolutePath() + "/" + (slideNo) + ".png", 300);
                    
                }
                slideNo++;
            }
        } catch (EncryptedDocumentException | IOException e) {
            throw new ParseException(e);
        }

    }

    @SuppressWarnings("unchecked")
    private static void fixFonts(Graphics2D graphics) {
        if (!JvmBugs.hasLineBreakMeasurerBug())
            return;
        Map<String, String> fontMap = (Map<String, String>) graphics.getRenderingHint(Drawable.FONT_MAP);
        if (fontMap == null)
            fontMap = new HashMap<String, String>();
        fontMap.put("Calibri", "Lucida Sans");
        fontMap.put("Cambria", "Lucida Bright");
        graphics.setRenderingHint(Drawable.FONT_MAP, fontMap);
    }

}
