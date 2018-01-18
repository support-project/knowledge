package org.support.project.knowledge.logic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.vo.SizeLimitMap;

@DI(instance = Instance.Singleton)
public class IdenticonLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());
    
    public static IdenticonLogic get() {
        return Container.getComp(IdenticonLogic.class);
    }
    
    private MessageDigest digest = null;
    public static final int ICON_SIZE = 48;
    // アイコンが登録されていない場合に、アイコンを生成するが、良く使われる人は20人までキャッシュする
    private SizeLimitMap<String, byte[]> _map = new SizeLimitMap<>(20);
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String userName = RandomUtil.randamGen(16);
        LOG.info("start");
        BufferedImage identicon = IdenticonLogic.get().generateIdenticons(userName);
        LOG.info("end");
        ImageIO.write(identicon,"PNG",new File(userName + ".png"));
    }
    
    public byte[] hash(String str) throws NoSuchAlgorithmException {
        if (digest == null) {
            digest = MessageDigest.getInstance("MD5");
        }
        byte[] hash = digest.digest(str.getBytes());
        return hash;
    }
    
    public byte[] generate(String id) throws NoSuchAlgorithmException, IOException{
        if (_map.containsKey(id)) {
            return _map.get(id);
        }
        BufferedImage img = generateIdenticons(id, ICON_SIZE, ICON_SIZE);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(img,"PNG", outputStream);
        byte[] bytes = outputStream.toByteArray();
        _map.put(id, bytes);
        return bytes;
    }
    
    
    public BufferedImage generateIdenticons(String id) throws NoSuchAlgorithmException{
        return generateIdenticons(id, ICON_SIZE, ICON_SIZE);
    }
    
    
    public BufferedImage generateIdenticons(String id, int image_width, int image_height) throws NoSuchAlgorithmException{
        int width = 5;
        int height = 5;

        byte[] hash = hash(id);

        BufferedImage identicon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = identicon.getRaster();

        int [] background = new int [] {255,255,255, 0};
        int [] foreground = new int [] {hash[0] & 255, hash[1] & 255, hash[2] & 255, 255};

        for(int x=0 ; x < width ; x++) {
            //Enforce horizontal symmetry
            int i = x < 3 ? x : 4 - x;
            for(int y=0 ; y < height; y++) {
                int [] pixelColor;
                //toggle pixels based on bit being on/off
                if((hash[i] >> y & 1) == 1)
                    pixelColor = foreground;
                else
                    pixelColor = background;
                raster.setPixel(x, y, pixelColor);
            }
        }

        BufferedImage finalImage = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);

        //Scale image to the size you want
        AffineTransform at = new AffineTransform();
        at.scale(image_width / width, image_height / height);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        finalImage = op.filter(identicon, finalImage);

        return finalImage;
    }
    
    
    
    
}
