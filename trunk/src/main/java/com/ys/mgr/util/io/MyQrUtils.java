package com.ys.mgr.util.io;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 二维码工具类
 *
 *
 * Date: 2017/12/11 16:05
 */
public class MyQrUtils {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

   /* *//**
     * 生成指定url的二维码
     *
     * @param qrUrl
     * @param imgPath
     * @param width
     * @param height
     *
     * @throws WriterException
     * @throws IOException
     *//*
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void createQrScene(String qrUrl, String imgPath, int width,
                                     int height) throws WriterException, IOException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(qrUrl,
                BarcodeFormat.QR_CODE, width, height, hints);
        writeToFile(bitMatrix, "png", new File(imgPath));
    }*/


    /**
     * 把图片印刷到图片上
     *
     * @param pressImg  -- 水印文件
     * @param targetImg -- 目标文件
     * @param x         --x坐标
     * @param y         --y坐标
     * @param savePath  输出流(可以来自HttpServletReponse的输出)
     */
    public final static void pressImage(String pressImg, String targetImg, String savePath,
                                        int x, int y) {
        try {
            Image image = ImageIO.read(new File(targetImg));
            int pwidth = image.getWidth(null);
            int pheight = image.getHeight(null);

            BufferedImage buffimage = new BufferedImage(pwidth, pheight,
                    BufferedImage.TYPE_INT_BGR);
            Graphics g = buffimage.createGraphics();
            ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
            ((Graphics2D) g).setBackground(new Color(255, 255, 255));
            g.fillRect(0, 0, pwidth, pheight);
            g.drawImage(image, 0, 0, pwidth, pheight, null);

            Image logo = ImageIO.read(new File(pressImg));
            int lwidth = logo.getWidth(null);
            int lheight = logo.getHeight(null);
            g.drawImage(logo, x, y, lwidth, lheight, null);
            g.dispose();
//            FileOutputStream os = new FileOutputStream(savePath);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
//            encoder.encode(buffimage);
//            os.close();
            // TODO 待测试，没测试过
            String formatName = savePath.substring(savePath.lastIndexOf(".") + 1);
            ImageIO.write(buffimage, /*"GIF"*/ formatName /* format desired */, new File(savePath) /* target */);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* private static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        }
    }*/

    /*private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }*/

}
