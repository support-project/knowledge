package org.support.project.knowledge.parser.impl;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import org.support.project.common.exception.ParseException;
import org.support.project.knowledge.parser.SlideShowParser;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class PdfSlideShowParser implements SlideShowParser {

    @Override
    public void parse(File inputFile, File outputDir) throws ParseException {
       
        int pages = -1;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(inputFile, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                    channel.size());
            PDFFile pdfFile = new PDFFile(buf);
            pages = pdfFile.getNumPages();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
        
        for (int i = 1; i <= pages; i++) {
            PdfSlideShowParser.printImage(
                    inputFile.getAbsolutePath(),
                    outputDir.getAbsolutePath() + "/" + i + ".png",
                    i);
        }
    }

    /**
     * PDF1ページ分をImageオブジェクトに変換します
     * @param page
     * @return
     */
    public static Image pdf2image(PDFPage page) {
        int w = (int) page.getBBox().getWidth();
        int h = (int) page.getBBox().getHeight();
        // get the width and height for the doc at the default zoom
        Rectangle rect = new Rectangle(0, 0, w, h);

        // generate the image
        return page.getImage(rect.width, rect.height, // width & height
                rect, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true // block until drawing is done
                );
    }

    /**
     * PDFファイルの任意のページを抽出します。
     * @param pdf
     * @param page
     * @return
     */
    public static PDFPage getPDFPage(File pdf, int page) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(pdf, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                    channel.size());
            PDFFile pdfFile = new PDFFile(buf);
            return pdfFile.getPage(page);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
        return null;

    }
    
    /**
     * PDFファイルをPNGファイルに変換します。
     * @param pdfPath 読み込むPDFファイルのパス
     * @param outPath 出力する画像ファイルのパス
     * @return
     */
    public static int printImage(String pdfPath, String outPath, int page) {
        File f = new File(pdfPath);
        PDFPage pdfPage = getPDFPage(f, page);
        int w = (int) pdfPage.getBBox().getWidth();
        int h = (int) pdfPage.getBBox().getHeight();
        Image img = pdf2image(pdfPage);
        BufferedImage buffered = new BufferedImage(w, h, Image.SCALE_SMOOTH);
        buffered.getGraphics().drawImage(img, 0, 0, null);
        try {
            save(buffered, new File(outPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void save(BufferedImage img, File f) throws IOException {
        if (!ImageIO.write(img, "PNG", f)) {
            throw new IOException("フォーマットが対象外");
        }
    }
}
