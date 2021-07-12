import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.net.*;
import javax.media.*;


class GUI_MAES_vid extends JFrame implements ActionListener,FocusListener,Runnable
{

	
	JLabel ori_lbl,enc_lbl,dec_lbl,lblpass;
	JPasswordField pass;
	
	String pwd,ext="mpg";

	JButton b1,b2,b3,exit,btnunlock,btnswitch;
	JPanel p1,p2,p3;
	
	private File file1,file2,file3;

	public GUI_MAES_vid()
	{
	setSize(1024,500);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLayout(null);
	
	pass=new JPasswordField(16);

	lblpass=new JLabel("Password :");

	ori_lbl=new JLabel("Original Video");

	enc_lbl=new JLabel("Cipher Video");

	dec_lbl=new JLabel("Decrypted Video");
	pwd=new String();

	btnswitch=new JButton("Switch to Image");
	btnunlock=new JButton("Unlock");
	b1=new JButton("Change File");
	b2=new JButton("Encrypt Video");
	b3=new JButton("Decrypt Video");
	exit=new JButton("EXIT");

	p1=new JPanel();
	p2=new JPanel();
	p3=new JPanel();

	
	ori_lbl.setBounds(125,350,125,25);
	enc_lbl.setBounds(440,350,125,25);
	dec_lbl.setBounds(760,350,125,25);
	pass.setBounds(130,10,150,25);
	lblpass.setBounds(30,10,125,25);

	b1.setBounds(115,390,125,25);
	b2.setBounds(440,390,125,25);
	b3.setBounds(760,390,125,25);
	exit.setBounds(920,420,75,30);
	btnunlock.setBounds(300,10,125,25);
	btnswitch.setBounds(440,10,125,25);

	p1.setLayout(new BorderLayout());
	p1.setSize(300,300);
	p1.setLocation(40,40);

	p2.setLayout(new BorderLayout());
	p2.setSize(300,300);
	p2.setLocation(350,40);

	p3.setLayout(new BorderLayout());
	p3.setSize(300,300);
	p3.setLocation(660,40);


	add(btnswitch);
	add(btnunlock);
	add(lblpass);
	add(pass);
	add(p1);
	add(ori_lbl);
	add(p2);
	add(enc_lbl);
	add(p3);
	add(dec_lbl);
	add(b1);
	add(b2);
	add(b3);
	add(exit);

	pass.setText("12345");
	pwd="12345~~~~~~~~~~~";
	b2.setEnabled(false);
	b3.setEnabled(false);
	pass.setEnabled(false);

	pass.addFocusListener(this);
	btnunlock.addActionListener(this);
	b1.addActionListener(this);
	b2.addActionListener(this);
	b3.addActionListener(this);
	exit.addActionListener(this);
	btnswitch.addActionListener(this);

	setVisible(true);
	}

	public void focusGained(FocusEvent fe)
	{
	
	}

	public void focusLost(FocusEvent fe)
	{
		if(fe.getSource()==pass)
	{
		String str=pass.getText();
		
		if(str.length()<5)
		{
		JOptionPane.showMessageDialog(null,"Please, Enter password having length >= 6 Your Password Length="+str.length(),"Too Small Password",JOptionPane.INFORMATION_MESSAGE);
		  System.out.println(str.length());
		
		}
		else if(str.length() < 16)
		{
			pwd=str;
			for(int i=str.length()-1;i<16;i++)
			{
			pwd=pwd.concat("~");
			}
		    System.out.println("PWD="+pwd);
			pass.setEnabled(false);
			btnunlock.setEnabled(true);
		}
	}
	}


	public void actionPerformed(ActionEvent ae)
	{

		if(ae.getSource()==btnswitch)
		{
				GUI_MAES ob1=new GUI_MAES();
				this.dispose();
		}

		if (ae.getSource()==exit)
		{
			System.exit(0);
		}

		if (ae.getSource()==b2)
		{
			try{

			MAESvid ob1=new MAESvid();
			file2=new File("cipher."+ext);
						
			
			long t1=System.currentTimeMillis();
			ob1.encrypt(file1,pwd.getBytes(),file2);
			long t2=System.currentTimeMillis();

			System.out.println("Time Taken for Encryption: "+(t2-t1));
			
			}
			catch(Exception e)
			{
				System.out.println("Error Message:"+e);
			}
			b2.setEnabled(false);
			b3.setEnabled(true);
		}

		if (ae.getSource()==b3)
		{

			try{
			MAESvid ob1=new MAESvid();
			file2=new File("cipher."+ext);
			file3=new File("plainVideo."+ext);
				
			long t1=System.currentTimeMillis();
			ob1.decrypt(file2,pwd.getBytes(),file3);
			long t2=System.currentTimeMillis();

			System.out.println("Time Taken for Decryption: "+(t2-t1));
			}catch(Exception e)
			{
							System.out.println("Error Message:"+e);	
			}

			Thread t1=new Thread(this);
			t1.setName("dec");
			t1.start();
		}

		if (ae.getSource()==btnunlock)
		{
			pass.setEnabled(true);
			btnunlock.setEnabled(false);
			b2.setEnabled(true);
			b3.setEnabled(false);

			pass.setText("");
			pwd=null;
			pass.requestFocus();
		}

		if (ae.getSource()==b1)
		{
		Thread t=new Thread(this);
        t.setName("open");
		t.start();
		}
	}

	public void displayvideo(File file,JPanel p)
	{
	URL mediaURL=null;
         try
         {
            // get the file as URL
            mediaURL = file.toURL();
         } // end try
         catch ( MalformedURLException malformedURLException )
         {
            System.err.println( "Could not create URL for the file" );
         } // end catch

         if ( mediaURL != null ) // only display if there is a valid URL
         {
            MediaPanel mediaPanel = new MediaPanel( mediaURL );
            p.add( mediaPanel );
			p.updateUI();
		 }
	}

public void run()
{
		if(Thread.currentThread().getName().equals("open"))
        {
			JFileChooser ch=new JFileChooser();
		   // ch.setCurrentDirectory(new java.io.File("d:\\ME\\project\\java"));
            ch.setDialogTitle("Open File");
	        ch.setFileSelectionMode(JFileChooser.FILES_ONLY);

			FileNameExtensionFilter ft = new FileNameExtensionFilter(
        "Video Files", "mpg");
			
			ch.setFileFilter(ft);
			if(ch.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
			{
			file1=ch.getSelectedFile();
			String str=file1.toString();
			ext=str.substring(str.lastIndexOf(".")+1);
			System.out.println("Extension:"+ext);
	
			 displayvideo(file1,p1);
         
			try{
					Thread.sleep(10);
				}
				catch(Exception e)
				{
						System.out.println(e);
				}
			b2.setEnabled(true);
			}	
		}

		if(Thread.currentThread().getName().equals("dec"))
		{
		try{
			
			displayvideo(file3,p3);
			
			Thread.sleep(10);
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
				
			b3.setEnabled(false);
		}
}
	public static void main(String[] args) 
	{
		GUI_MAES_vid ob1=new GUI_MAES_vid();
	}
}
