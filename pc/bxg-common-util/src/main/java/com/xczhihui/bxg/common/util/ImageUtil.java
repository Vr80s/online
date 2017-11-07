package com.xczhihui.bxg.common.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

/**
 * 处理图片的工具类。
 * 
 * @author liyong
 *
 */
public final class ImageUtil {

	/**
	 * 裁剪图片，裁剪后的图片为png格式。
	 * 
	 * @param inPath
	 *            源路径(包含图片)
	 * @param outPath
	 *            目标路径 null则默认为源路径
	 * @param width
	 *            剪切宽度
	 * @param height
	 *            剪切高度
	 */
	public static void cutImage(String inPath, String outPath, int width, int height) throws IOException {
		cutImage(inPath, outPath, 0, 0, width, height);
	}

	/**
	 * 裁剪图片，裁剪后的图片为png格式。
	 * 
	 * @param inPath
	 *            源路径(包含图片)
	 * @param outPath
	 *            目标路径 null则默认为源路径
	 * @param x
	 *            起点x坐标
	 * @param y
	 *            起点y左边
	 * @param width
	 *            剪切宽度
	 * @param height
	 *            剪切高度
	 */
	public static void cutImage(String inPath, String outPath, int x, int y, int width, int height) throws IOException {
		Path inImage = Paths.get(inPath);
		if (!Files.exists(inImage)) {
			throw new FileNotFoundException(inPath);
		}
		Path outImage = Paths.get(outPath);
		if (!Files.exists(outImage)) {
			Files.createFile(outImage);
		}
		String format = inPath.substring(inPath.lastIndexOf(".") + 1, inPath.length());
		try (InputStream imageInputStream = Files.newInputStream(inImage);
				OutputStream output = Files.newOutputStream(outImage)) {
			cutImage(imageInputStream, output, format, x, y, width, height);
		}
	}

	/**
	 * 裁剪图片，裁剪后的图片为png格式。
	 * 
	 * @param imageInputStream
	 *            要裁剪的图片输入流
	 * @param imageOutputStream
	 *            裁剪后的图片输出流
	 * @param format
	 *            图片格式，如：png jpg
	 * @param width
	 *            裁剪后的宽度
	 * @param height
	 *            裁剪后的高度
	 */
	public static void cutImage(InputStream imageInputStream, OutputStream imageOutputStream, String format, int width,
			int height) throws IOException {
		cutImage(imageInputStream, imageOutputStream, format, 0, 0, width, height);
	}

	/**
	 * 裁剪图片
	 * 
	 * @param imageInputStream
	 *            要裁剪的图片输入流
	 * @param imageOutputStream
	 *            裁剪后的图片输出流
	 * @param format
	 *            图片格式，如：png jpg
	 * @param x
	 *            开始坐标
	 * @param y
	 *            开始坐标
	 * @param width
	 *            裁剪后的宽度
	 * @param height
	 *            裁剪后的高度
	 * @return
	 * @throws IOException
	 */
	public static void cutImage(InputStream imageInputStream, OutputStream imageOutputStream, String format, int x,
			int y, int width, int height) throws IOException {
		BufferedImage image = ImageIO.read(imageInputStream);
		width = width > image.getWidth() ? image.getWidth() : width;
		height = height > image.getHeight() ? image.getHeight() : height;
		image = image.getSubimage(x, y, width, height);
		ImageIO.write(image, format, imageOutputStream);
	}

	/**
	 * 缩放图片
	 * 
	 * @param inputImage
	 *            源路径(包含图片)
	 * @param outImage
	 *            缩放后的图片保存位置
	 * @param width
	 *            缩放后宽度
	 * @param height
	 *            缩放后高度
	 */
	public static void scaleImage(String inputImage, String outImage, int width, int height) throws IOException {
		File imageFile = new File(inputImage);
		if (!imageFile.exists()) {
			throw new IOException("Not found the images:" + inputImage);
		}
		if (outImage == null || outImage.isEmpty())
			outImage = inputImage;
		String format = inputImage.substring(inputImage.lastIndexOf(".") + 1, inputImage.length());
		try (InputStream imageInputStream = new FileInputStream(imageFile);
				OutputStream imageOutputStream = new FileOutputStream(new File(outImage))) {
			scaleImage(imageInputStream, imageOutputStream, format, width, height);
		}
	}

	/**
	 * 缩放图片
	 * 
	 * @param imageInputStream
	 *            要缩放的图片输入流
	 * @param imageOutputStream
	 *            缩放后的图片输出流
	 * @param format
	 *            图片格式，如：png jpg
	 * @param width
	 *            缩放后宽度
	 * @param height
	 *            缩放后高度
	 */
	public static void scaleImage(InputStream imageInputStream, OutputStream imageOutputStream, String format,
			int width, int height) throws IOException {
		BufferedImage sourceImage = ImageIO.read(imageInputStream);
		int orgWidth = sourceImage.getWidth();
		int orgHeight = sourceImage.getHeight();

		int newWidth = width;
		int newHeight = height;

		double wp = (double) orgWidth / (double) width;
		double hp = (double) orgHeight / (double) height;
		double dt = 0.01;
		if ((wp - hp) > dt) {
			double rate = wp > hp ? wp : hp;
			newWidth = (int) ((double) orgWidth / rate);
			newHeight = (int) ((double) orgHeight / rate);
		}

		BufferedImage zoomImage = new BufferedImage(newWidth, newHeight, sourceImage.getType());
		Image image = sourceImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		Graphics gc = zoomImage.getGraphics();
		gc.setColor(Color.WHITE);
		gc.drawImage(image, 0, 0, null);
		gc.dispose();
		ImageIO.write(zoomImage, format, imageOutputStream);
	}
}
