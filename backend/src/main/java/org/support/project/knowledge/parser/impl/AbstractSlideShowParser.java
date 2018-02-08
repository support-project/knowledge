package org.support.project.knowledge.parser.impl;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractSlideShowParser {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(AbstractSlideShowParser.class);

    private static final double SLIDE_WIDTH = 800;
    
    /**
     * 画像をファイルに保存
     * サイズが大きいものは縮小処理も含める
     * @param org 画像
     * @param path 出力するファイルパス
     * @throws IOException IOException
     */
    protected void writeImage(BufferedImage org, String path) throws IOException {
        int width = org.getWidth();
        double scale = 1.0;
        scale =  SLIDE_WIDTH / width;
        LOG.info(path + " を保存します。 scale: " + scale);
        
        ImageFilter filter = new AreaAveragingScaleFilter((int) (org.getWidth() * scale), (int) (org.getHeight() * scale));
        ImageProducer p = new FilteredImageSource(org.getSource(), filter);
        java.awt.Image dstImage = Toolkit.getDefaultToolkit().createImage(p);
        BufferedImage dst = new BufferedImage(dstImage.getWidth(null), dstImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = dst.createGraphics();
        g.drawImage(dstImage, 0, 0, null);
        g.dispose();
        ImageIO.write(dst, "png", new File(path));
    }

}
