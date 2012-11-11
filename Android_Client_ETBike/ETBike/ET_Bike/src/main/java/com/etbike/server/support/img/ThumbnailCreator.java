package com.etbike.server.support.img;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.springframework.stereotype.Service;

@Service
public class ThumbnailCreator implements ThumbnailCreatable {
//	@Resource(name="threadPoolTaskExecutor")
//	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Override
	public void create(String sourceImagePath, String thumbnailImagePath, int width, int height){
//		try {
//			this.threadPoolTaskExecutor.execute(getJob(sourceImagePath, thumbnailImagePath, width, height));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		File thumbnailFile = new File(thumbnailImagePath);
		if(!thumbnailFile.exists()){
			try {
				createThumbnail(sourceImagePath, thumbnailFile, width, height);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	private Runnable getJob(final String sourceImagePath, final String thumbnailImagePath, final int width, final int height) {
//		Runnable job = new Runnable(){
//			public void run(){
//				File thumbnailFile = new File(thumbnailImagePath);
//				if(!thumbnailFile.exists()){
//					try {
//						createThumbnail(sourceImagePath, thumbnailFile, width, height);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		};
//		return job;
//	}

	private void createThumbnail(String sourceImagePath, File thumbnailFile, int width, int height) throws IOException {
		Image sourceImage = getImage(sourceImagePath);

		int sourceImageWidth = sourceImage.getWidth(null);
		int sourceImageHeight = sourceImage.getHeight(null);
		int thumbnailImageWidth = -1;
		int thumbnailImageHeight = -1;

		if (width < 0) {
			thumbnailImageWidth = sourceImageWidth;
		} else if (width > 0) {
			thumbnailImageWidth = width;
		}

		if (height < 0) {
			thumbnailImageHeight = sourceImageHeight * width / sourceImageWidth;
		} else if (height > 0) {
			thumbnailImageHeight = height;
		}

		Image imgTarget = sourceImage.getScaledInstance(thumbnailImageWidth, thumbnailImageHeight, Image.SCALE_SMOOTH);
		int pixels[] = new int[thumbnailImageWidth * thumbnailImageHeight];
		PixelGrabber pixelGrabber = new PixelGrabber(imgTarget, 0, 0, thumbnailImageWidth, thumbnailImageHeight, pixels, 0, thumbnailImageWidth);

		try {
			pixelGrabber.grabPixels();
		} catch (InterruptedException e) {
			throw new IOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedImage destImg = new BufferedImage(thumbnailImageWidth, thumbnailImageHeight, BufferedImage.TYPE_INT_RGB);
		destImg.setRGB(0, 0, thumbnailImageWidth, thumbnailImageHeight, pixels, 0, thumbnailImageWidth);
		ImageIO.write(destImg, "jpg", thumbnailFile);
	}

	private Image getImage(String sourceImagePath) throws IOException {
		String sourceImageileExtension = sourceImagePath.substring(sourceImagePath.lastIndexOf('.') + 1).toLowerCase();
		URL imageSourceUrl = new File(sourceImagePath).toURI().toURL();
		Image srcImg = null;
		if (sourceImageileExtension.equals("bmp") || sourceImageileExtension.equals("png") || sourceImageileExtension.equals("gif")) {
			srcImg = ImageIO.read(imageSourceUrl);
		} else {
			srcImg = new ImageIcon(imageSourceUrl).getImage();
		}

		int srcImgWidth = srcImg.getWidth(null);
		int srcImgHeight = srcImg.getHeight(null);
		if(srcImgWidth < 0 || srcImgHeight < 0){
			// TODO insert blank image_path
//			srcImg = new ImageIcon(new URL("")).getImage();
		}

		return srcImg;
	}
}
