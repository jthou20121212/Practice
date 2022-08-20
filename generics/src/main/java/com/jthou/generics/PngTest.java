package com.jthou.generics;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class PngTest {

    static final String DIR = "/generics/images/";
    static final String NINE_PNG = ".9.png";
    static final String PNG_FILE_SUFFIX = ".png";

    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");
        File dir = new File(userDir, DIR);
        if (!dir.isDirectory() || dir.listFiles() == null || dir.listFiles().length == 0) return;
        for (File image : dir.listFiles()) {
            if (image.isFile() && image.getName().endsWith(PNG_FILE_SUFFIX) && !image.getName().endsWith(NINE_PNG)) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(image);
                    boolean hasAlpha = bufferedImage.getColorModel().hasAlpha();
                    boolean isAlphaPremultiplied = bufferedImage.getColorModel().isAlphaPremultiplied();
                    System.out.println(image.getName() + "：" + (hasAlpha ? "有" : "没有") + "透明通道");
                    System.out.println(image.getName() + "：" + isAlphaPremultiplied);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private void findNonAlphaPng (File file) throws IOException {
//        if (file.isDirectory()) {
//            File[] files = file.listFiles();
//            for (File tempFile : files) {
//                findNonAlphaPng(tempFile);
//            }
//        } else if (file.isFile() && file.getName().endsWith(PNG_FILE_SUFFIX) && !file.getName().endsWith(NINE_PNG)) {
//            BufferedImage bufferedImage = ImageIO.read(file);
//            if (bufferedImage != null && bufferedImage.getColorModel() != null && !bufferedImage.getColorModel().hasAlpha()) {
//                String filename = file.getAbsolutePath().substring(inputFile.getAbsolutePath().length() + 1);
//                if (entryNameMap.containsKey(filename)) {
//                    filename = entryNameMap.get(filename);
//                }
//                long size = file.length();
//                if (entrySizeMap.containsKey(filename)) {
//                    size = entrySizeMap.get(filename).getFirst();
//                }
//                if (size >= downLimitSize * K1024) {
//                    nonAlphaPngList.add(Pair.of(filename, file.length()));
//                }
//            }
//        }
//    }

//    }
}
