package Tools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageCut {

	public static void imgCut(String srcImageFile, int x, int y, int desWidth, int desHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			System.out.println("error happning here:"+srcImageFile);
			BufferedImage bi = ImageIO.read(new File(srcImageFile + "_src.jpg"));
			int srcWidth = bi.getWidth();
			int srcHeight = bi.getHeight();
			if (srcWidth >= desWidth && srcHeight >= desHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				cropFilter = new CropImageFilter(x, y, desWidth, desHeight);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(desWidth, desHeight, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null);
				g.dispose();
                System.out.println("I am storing picture to "+srcImageFile);
				ImageIO.write(tag, "JPEG", new File(srcImageFile + "_cut.jpg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
