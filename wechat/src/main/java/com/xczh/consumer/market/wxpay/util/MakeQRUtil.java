package com.xczh.consumer.market.wxpay.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

public final class MakeQRUtil {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private MakeQRUtil() {
		//++
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		
		return image;
	}

	public static void writeToFile(BitMatrix matrix, 
			String format, File file) 
			throws IOException {
		
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of FORMAT " + format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, 
			String format, 
			OutputStream stream) 
			throws IOException {
		
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of FORMAT " + format);
		}
	}

	public static void outputQRimgStream(String url, 
			OutputStream stream) 
			throws WriterException, 
			IOException {
		
		int width = 200+65;
		int height = 200+65;
		
		// 图片格式
		String format = "gif";
		Hashtable hints = new Hashtable();
		
		// bianm
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		
		// hints.put(EncodeHintType, 0);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
		writeToStream(deleteWhite(bitMatrix), format, stream);
	}

	public static void outputQRimg(String url) 
			throws WriterException, 
			IOException {
		
		int width = 300;
		int height = 300;
		
		// 图片格式
		String format = "png";
		Hashtable hints = new Hashtable();
		
		// bianm
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
		
		// 路径
		File outputFile = new File("d:" + File.separator + "new." + format);
		writeToFile(bitMatrix, format, outputFile);
	}

	public static String generateQRimg(String url, 
			String tradeno) 
			throws WriterException, 
			IOException {
		
		int width = 300;
		int height = 300;
		
		// 图片格式
		String format = "png";
		Hashtable hints = new Hashtable();
		
		// bianm
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
		System.out.println("图片流:" + bitMatrix.toString().getBytes());
		
		// 路径
		String fname = File.separator + "payqrimg" + File.separator + tradeno + "." + format;
		File outputFile = new File(fname);
		writeToFile(bitMatrix, format, outputFile);
		
		return fname;
	}

	public static BitMatrix deleteWhite(BitMatrix matrix) {
		
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + 1;
		int resHeight = rec[3] + 1;

		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++) {
			for (int j = 0; j < resHeight; j++) {
				if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
			}
		}
		
		return resMatrix;
	}

}