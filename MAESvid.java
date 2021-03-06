import java.io.*;

// AEStest: test AES encryption
public class MAESvid {


	public void encrypt(File file1,byte[] key,File file2)
	{
    
	try{
	 FileInputStream fin=new FileInputStream(file1);
	
	  FileOutputStream fpout=null;
	  
	  
	  
		 fpout=new FileOutputStream(file2);
	  
      

       AESencrypt aes = new AESencrypt(key, 4);
		
		byte[] in=new byte[16];
	  byte[] out = new byte[16];

		int l=0;
	  
	  while ((l=fin.read(in))==16)
	  {
		 
		aes.Cipher(in, out);
     
	  try
	  {
      		  fpout.write(out);
	  }
	  catch (Exception e)
	  {
		  System.out.println(e);
	  }

	  }
	   if (l!=-1)
	    {
		   //System.out.println(l);
			String temp=new String(in,0,l);
			
			for (int i=temp.length();i<16 ;i++ )
			{
				temp=temp.concat(" ");
			}
			
			in=temp.getBytes();
			
			aes.Cipher(in, out);
     
	         try
	         {
      		  fpout.write(out);
			  }
				catch (Exception e)
				{
					System.out.println("Error"+e);
				}
			}

	  fin.close();
	  fpout.close();
	  }
	  catch (Exception e)
	  {
		  System.out.println(e);
	  }
	}

	
	public void decrypt(File file2,byte[] key,File file3) throws Exception
	{
  	 FileInputStream fin=new FileInputStream(file2);

	  FileOutputStream fpout=null;
	  
	  try
	  {
		 fpout=new FileOutputStream(file3);
	  }
	  catch (Exception e)
	  {
		  System.out.println(e);
	  }
      
  
	  AESdecrypt aes = new AESdecrypt(key, 4);

 	  byte[] out = new byte[16];
	  byte[] in=new byte[16];	
	  
	  while (fin.read(in)!=-1)
	  {
	  
	  aes.InvCipher(in, out);
     
	  try
	  {
        		  fpout.write(out);
	  }
	  catch (Exception e)
	  {
		  System.out.println(e);
	  }

	  }

	  fpout.close();
	  fin.close();
	}

   /*
   public static void main(String[] args)throws Exception
	   {
      // for 128-bit key, use 16, 16, and 4 below
      // for 192-bit key, use 16, 24 and 6 below
      // for 256-bit key, use 16, 32 and 8 below
      AEStest ob1=new AEStest();

     try
     {
	    ob1.encrypt();
	  System.out.println("\nEncrypted Successfully.");
		ob1.decrypt();
		System.out.println("\nDecrypted Successfully.");	
     }
     catch (Exception e)
     {
		 System.out.println(e);
     }
	 
   }*/
}