import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;


class MAES 
{

	 public void Encrypt(File file1,String k,File file2) throws FileNotFoundException, IOException {
    	
        FileInputStream fis = new FileInputStream(file1);
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

		byte[] buf=new byte[16];	
        
		byte[] key = k.getBytes();
		
		AESencrypt aes = new AESencrypt(key, 4);
		
		int len=0;
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
               				
				byte[] out = new byte[16];
				len+=readNum;

				aes.Cipher(buf, out);
				 bos.write(out, 0, readNum);
		
			}
        } catch (IOException ex) {
			System.out.println(ex);
        }
        byte[] bytes = bos.toByteArray();
		
		FileOutputStream fp=new FileOutputStream(file2);
		fp.write(bytes,0,len);
		fp.close();
		
		
    }

	public void Decrypt(File file1,String k,File file2) throws FileNotFoundException, IOException {
    	
 
        FileInputStream fis = new FileInputStream(file1);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[16];
	
        byte[] key = k.getBytes();
		
		AESdecrypt aes1 = new AESdecrypt(key, 4);
		int len=0;
        try {
			
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
               				
				len+=readNum;
				byte[] out1 = new byte[16];
                aes1.InvCipher(buf,out1 );
				bos.write(out1, 0, readNum);

            }
        } catch (IOException ex) {
			System.out.println(ex);
        }
        byte[] bytes = bos.toByteArray();
	

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
 
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream, it seems file is OK
 
        ImageInputStream iis = ImageIO.createImageInputStream(source);
 
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
 
        Image image = reader.read(0, param);
        //got an image file
 
 
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //bufferedImage is the RenderedImage to be written
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);
        //File imageFile = new File(file2);
        ImageIO.write(bufferedImage, "jpg", file2);
 
    }
/*
	public static void main(String[] args) throws Exception 
	{
		File file1=new File("mickey_mouse.jpg");
		
		File file2=new File("Encrypted_lenna.jpg");

		File file3=new File("Decrypted_lenna.jpg");
		
		MAES ob1=new MAES();
		
		System.out.println("Encrypting...");	
		ob1.Encrypt(file1,"key1.txt",file2);
		System.out.println("Decrypting...");
		ob1.Decrypt(file2,"key1.txt",file3);
		System.out.println("Success...");
	}*/
}
