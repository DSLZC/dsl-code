package cn.dslcode.common.core.file;

import cn.dslcode.common.core.string.StringUtil;
import cn.dslcode.common.web.request.create.JavaURIRequest;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

/**
 * 图片工具类
 * @author 董思林 
 *  2016-07-20
 */
public class ImageUtil {

	public static final Logger log = LoggerFactory.getLogger(ImageUtil.class);
	/** 字体集合 */
	public static final List<Font> fonts = new ArrayList<Font>(100);
	/** 字体集合长度 */
	public static int fontsLen;
	static {
		// 常用字体
		String fontStrs = "Segoe Print,Kristen ITC,Harrington,Harlow Solid Italic,Gabriola,Bell MT,Baskerville Old Face,Arial Black,Algerian,Cambria,Times New Roman,Arial,仿宋,华文中宋,华文仿宋,华文宋体,华文新魏,华文楷体,华文细黑,华文隶书,宋体,幼圆,微软雅黑,微软雅黑,新宋体,方正兰亭超细黑简体,方正姚体,方正舒体,楷体,隶书,黑体";
		// 字体大小
		int size = 32;
		Arrays.asList(fontStrs.split(",")).forEach(str->{
			fonts.add(new Font(str, Font.BOLD, size));
			fonts.add(new Font(str, Font.ITALIC, size));
			fonts.add(new Font(str, Font.PLAIN, size));
//			fonts.add(new Font(str, Font.CENTER_BASELINE, size));
//			fonts.add(new Font(str, Font.LAYOUT_LEFT_TO_RIGHT, size));
//			fonts.add(new Font(str, Font.ROMAN_BASELINE, size));
//			fonts.add(new Font(str, Font.LAYOUT_NO_START_CONTEXT, size));
		});
		fontsLen = fonts.size();
	}
	/**
	 * 获得给定范围随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc,int bc) {
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
	
	/**
	 * 生成验证码图片
	 * @param captchaCode 验证码
	 * @param response
	 * @throws IOException
	 */
	public static void createCaptchaImg(String captchaCode, HttpServletResponse response) throws IOException{
		Assert.notNull(captchaCode);
		int len = captchaCode.length();
		//设置页面不缓存
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);

		// 在内存中创建图象
		int wordWidth = 14;
		int width=8+wordWidth*len, height=30;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics g = image.getGraphics();

		//生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);

		//设定字体
		g.setFont(new Font("Times New Roman",Font.PLAIN, 32));

		//画边框
		//g.setColor(new Color());
		//g.drawRect(0,0,width-1,height-1);


		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到
		for (int i=0;i<20;i++) {
			g.setColor(getRandColor(100,250));
			for (int j=0;j<len;j++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(width/3);
				int yl = random.nextInt(height/3);
				g.drawLine(x,y,x+xl,y+yl);
			}
		}
		for (int i=0;i < captchaCode.length();i++){
		    // 将认证码显示到图象中
		    g.setColor(new Color(10+random.nextInt(120),10+random.nextInt(120),10+random.nextInt(120)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.setFont(fonts.get(random.nextInt(fontsLen)));
			g.drawString(captchaCode.charAt(i)+"", wordWidth*i+2, 25);
		}
		// 图象生效
		g.dispose();

		// 输出图象到页面
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	/**
	 * 生成二维码
	 * @param text 二维码链接地址
	 * @param logo 网页上的logo图片地址，没有logo则为null
	 * @return
	 * @throws Exception
	 */
	public static InputStream QRCode(String text, String logo) throws Exception{
		String url = "http://qr.topscan.com/api.php";
		/*
		 *  bg	背景颜色	bg=颜色代码，例如：bg=ffffff
			fg	前景颜色	fg=颜色代码，例如：fg=cc0000
			gc	渐变颜色	gc=颜色代码，例如：gc=cc00000
			el	纠错等级	el可用值：h\q\m\l，例如：el=h
			w	尺寸大小	w=数值（像素），例如：w=300
			m	静区（外边距）	m=数值（像素），例如：m=30
			pt	定位点颜色（外框）	pt=颜色代码，例如：pt=00ff00
			inpt	定位点颜色（内点）	inpt=颜色代码，例如：inpt=000000
			text 接口地址  用UTF8编码格式，出现 & 符号时，请用 %26 代替, 换行符使用 %0A
			logo	logo图片	logo=图片地址，例如：logo=http://www.topscan.com/images/2013/sample.jpg
		*/
		Map<String, String> params = new HashMap<String, String>();
		params.put("w", "250");
		params.put("m", "10");
		params.put("text", text);
		if(StringUtil.isNotEmpty(logo)) params.put("logo", logo);
		InputStream is = JavaURIRequest.invoke("get", url, params);
		if(null == is) is = JavaURIRequest.invoke("get", url, params);
		return is;
	}


	/**
	 * 判断图片尺寸，宽高
	 * @param inputStream
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean imageWidthAndHeight(InputStream inputStream, int width, int height) throws Exception {
		BufferedImage image = ImageIO.read(inputStream);
		if(image == null) throw new NullPointerException("图片为空...");
		if(image.getWidth() != width) throw new Exception("图片宽度不正确...");
		if(image.getHeight() != height) throw new Exception("图片高度不正确...");
		return true;
	}

    /**
     * 判断图片尺寸，宽高
     * @param inputStream
     * @param size 图片大小
     * @param imageCompares
     * @return
     * @throws Exception
     */
	public static void imageWidthAndHeightBund(InputStream inputStream, long size, ImageCompare... imageCompares) throws Exception {
		BufferedImage image = ImageIO.read(inputStream);
		if(image == null) throw new NullPointerException("图片为空...");
		for (ImageCompare imageCompare : imageCompares) {
		    switch (imageCompare.getType()){
                case width:
                    imageCompare.setValue(image.getWidth());
                    break;
                case height:
                    imageCompare.setValue(image.getHeight());
                    break;
                case size:
                    imageCompare.setValue(size);
                    break;
            }
		    imageCompare.executeCompare();
        }
	}

	/**
	 * 图片按照尺寸缩放
	 * @param sourcePath
	 * @param targetPath
	 * @param width
	 * @param height
	 * @param isKeepAspectRatio 是否保持长宽比
	 */
	public static void scale(String sourcePath, String targetPath, int width, int height, boolean isKeepAspectRatio){
		try {
			Thumbnails.of(sourcePath).size(width, height).keepAspectRatio(isKeepAspectRatio).toFile(targetPath);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片按照尺寸缩放
	 * @param inputStream
	 * @param outputStream
	 * @param width
	 * @param height
	 * @param isKeepAspectRatio 是否保持长宽比
	 */
	public static void scale(InputStream inputStream, OutputStream outputStream, int width, int height, boolean isKeepAspectRatio){
		try {
			Thumbnails.of(inputStream).size(width, height).keepAspectRatio(isKeepAspectRatio).toOutputStream(outputStream);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片按照尺寸缩放
	 * @param bufferedImage
	 * @param outputStream
	 * @param width
	 * @param height
	 * @param isKeepAspectRatio 是否保持长宽比
	 */
	public static void scale(BufferedImage bufferedImage, OutputStream outputStream, int width, int height, boolean isKeepAspectRatio){
		try {
			Thumbnails.of(bufferedImage).size(width, height).keepAspectRatio(isKeepAspectRatio).toOutputStream(outputStream);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片按照比例缩放
	 * @param sourcePath
	 * @param targetPath
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 */
	public static void scale(String sourcePath, String targetPath, double scale){
		try {
			Thumbnails.of(sourcePath).scale(scale).toFile(targetPath);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片按照比例缩放
	 * @param inputStream
	 * @param outputStream
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 */
	public static void scale(InputStream inputStream, OutputStream outputStream, double scale){
		try {
			Thumbnails.of(inputStream).scale(scale).toOutputStream(outputStream);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片按照比例缩放
	 * @param bufferedImage
	 * @param outputStream
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 */
	public static void scale(BufferedImage bufferedImage, OutputStream outputStream, double scale){
		try {
			Thumbnails.of(bufferedImage).scale(scale).toOutputStream(outputStream);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片旋转
	 * @param sourcePath
	 * @param targetPath
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 * @param angle 旋转角度 正数：顺时针     负数：逆时针
	 */
	public static void rotate(String sourcePath, String targetPath, double scale, double angle){
		try {
			Thumbnails.of(sourcePath).scale(scale).rotate(angle).toFile(targetPath);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片旋转
	 * @param inputStream
	 * @param outputStream
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 * @param angle 旋转角度 正数：顺时针     负数：逆时针
	 */
	public static void rotate(InputStream inputStream, OutputStream outputStream, double scale, double angle){
		try {
			Thumbnails.of(inputStream).scale(scale).rotate(angle).toOutputStream(outputStream);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片旋转
	 * @param bufferedImage
	 * @param outputStream
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 * @param angle 旋转角度 正数：顺时针     负数：逆时针
	 */
	public static void rotate(BufferedImage bufferedImage, OutputStream outputStream, double scale, double angle){
		try {
			Thumbnails.of(bufferedImage).scale(scale).rotate(angle).toOutputStream(outputStream);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片添加水印
	 * @param sourcePath
	 * @param targetPath
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 * @param markPosition 添加水印的位置 Positions.BOTTOM_RIGHT Positions.CENTER_RIGHT ...........
	 * @param markInputStream 水印图片inputStream
	 * @param markOpacity 水印图片透明度  0.2f 0.5f 0.8f 1f ..................
	 */
	public static void watermark(String sourcePath, String targetPath, double scale, Position markPosition, InputStream markInputStream, float markOpacity){
		try {
			Thumbnails.of(sourcePath).scale(scale).watermark(markPosition, ImageIO.read(markInputStream), markOpacity).toFile(targetPath);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片添加水印
	 * @param inputStream
	 * @param outputStream
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 * @param markPosition 添加水印的位置 Positions.BOTTOM_RIGHT Positions.CENTER_RIGHT ...........
	 * @param markInputStream 水印图片inputStream
	 * @param markOpacity 水印图片透明度  0.2f 0.5f 0.8f 1f ..................
	 */
	public static void watermark(InputStream inputStream, OutputStream outputStream, double scale, Position markPosition, InputStream markInputStream, float markOpacity){
		try {
			Thumbnails.of(inputStream).scale(scale).watermark(markPosition, ImageIO.read(markInputStream), markOpacity).toOutputStream(outputStream);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 图片添加水印
	 * @param bufferedImage
	 * @param outputStream
	 * @param scale 缩放比例 0.2 0.5 0.8 1 1.2 ..................
	 * @param markPosition 添加水印的位置 Positions.BOTTOM_RIGHT Positions.CENTER_RIGHT ...........
	 * @param markInputStream 水印图片inputStream
	 * @param markOpacity 水印图片透明度  0.2f 0.5f 0.8f 1f ..................
	 */
	public static void watermark(BufferedImage bufferedImage, OutputStream outputStream, double scale, Position markPosition, InputStream markInputStream, float markOpacity){
		try {
			Thumbnails.of(bufferedImage).scale(scale).watermark(markPosition, ImageIO.read(markInputStream), markOpacity).toOutputStream(outputStream);
		} catch (IOException e) {
			log.error("", e);
		}
	}

	/**
	 * 裁剪，需自行实现，参考 http://rensanning.iteye.com/blog/1545708
	 * @param sourcePath
	 * @param targetPath
	 */
	public static void cut(String sourcePath, String targetPath){
		try {
			Thumbnails.of(sourcePath)
					.sourceRegion(Positions.CENTER, 100, 100)
					.size(200, 200)
					.keepAspectRatio(false)
					.toFile(targetPath);
		} catch (IOException e) {
			log.error("", e);
		}
	}

}
