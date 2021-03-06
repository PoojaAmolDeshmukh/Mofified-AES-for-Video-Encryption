import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;


class GUI_MAES extends JFrame implements ActionListener,FocusListener
{
	JLabel ori_image,enc_image,dec_image,lblpass;
	JLabel ori_lbl,enc_lbl,dec_lbl;
	JPasswordField pass;
	String pwd;

	JButton b1,b2,b3,exit,btnunlock,btnswitch;
	JPanel p1,p2,p3;
	private File file1,file2,file3;

	public GUI_MAES()
	{
	setSize(1024,500);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLayout(null);
	
	pass=new JPasswordField(30);

	lblpass=new JLabel("Password :");

	ori_image=new JLabel();
	ori_lbl=new JLabel("Original Image");

	enc_image=new JLabel();
	enc_lbl=new JLabel("Encrypted Image");

	dec_image=new JLabel();
	dec_lbl=new JLabel("Decrypted Image");
	pwd=new String();

	btnswitch=new JButton("Switch to Video");
	btnunlock=new JButton("Unlock");
	b1=new JButton("Change Picture");
	b2=new JButton("Encrypt Picture");
	b3=new JButton("Decrypt Picture");
	exit=new JButton("EXIT");

	p1=new JPanel();
	p2=new JPanel();
	p3=new JPanel();

	
	ori_image.setIcon(new javax.swing.ImageIcon("lenna.jpg"));
	ori_lbl.setFont(new Font("TimesNewRoman",Font.BOLD,14));
	file1=new File("lenna.jpg");
	
	enc_image.setIcon(new javax.swing.ImageIcon("Encrypted_lenna.jpg"));
	enc_lbl.setFont(new Font("TimesNewRoman",Font.BOLD,14));

	dec_image.setIcon(new javax.swing.ImageIcon("Encrypted_lenna.jpg"));
	dec_lbl.setFont(new Font("TimesNewRoman",Font.BOLD,14));

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

	p1.add(ori_image);
	p2.add(enc_image);
	p3.add(dec_image);

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
	try
	{
		if (ae.getSource()==btnswitch)
		{
			GUI_MAES_vid ob1=new GUI_MAES_vid();
				this.dispose();
		}
		if (ae.getSource()==b1 )
		{
			JFileChooser ch=new JFileChooser();
		   // ch.setCurrentDirectory(new java.io.File("d:\\ME\\project\\java"));
            ch.setDialogTitle("Open File");
	        ch.setFileSelectionMode(JFileChooser.FILES_ONLY);

			FileNameExtensionFilter ft = new FileNameExtensionFilter(
        "JPG & Images", "jpg", "jpeg");
			
			ch.setFileFilter(ft);

			if(ch.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
			{
			file1=ch.getSelectedFile();
			ori_image.setIcon(new ImageIcon(""+file1));
			enc_image.setIcon(new ImageIcon("Encrypted_lenna.jpg"));
			dec_image.setIcon(new ImageIcon("Encrypted_lenna.jpg"));
			b2.setEnabled(true);
			}
			
		}

		if(ae.getSource()==b2)
		{
		MAES ob1=new MAES();
		NoiseImage ob2=new NoiseImage();

		System.out.println("Encrypting..."+file1.getName());
		file2=new File("Encrypted_"+file1.getName());

		ob1.Encrypt(file1,pwd,file2);
		System.out.println("Success...("+file2+")");

		
		BufferedImage bf=ImageIO.read(file1);
		int w=bf.getHeight();
		int h=bf.getWidth();
		bf.flush();

		System.out.println(w+"x"+h);

		ob2.generateNoise(file2,w,h);
		dec_image.setIcon(new ImageIcon("Encrypted_lenna.jpg"));

		b3.setEnabled(true);
		b2.setEnabled(false);

		enc_image.setIcon(new ImageIcon("N_"+file2.getName()));

		}
		
		if(ae.getSource()==b3)
		{
		MAES ob1=new MAES();
		System.out.println("Decrypting..."+file2.getName());
		file3=new File("Decrypted_"+file1.getName());

		ob1.Decrypt(file2,pwd,file3);
		System.out.println("Success...("+file3.getName()+")");
		dec_image.setIcon(new ImageIcon(""+file3));
		b3.setEnabled(false);
		}
		if (ae.getSource()==btnunlock)
		{
			pass.setEnabled(true);
			btnunlock.setEnabled(false);
			b2.setEnabled(true);
			pass.setText("");
			pwd=null;
			pass.requestFocus();
		}

		if (ae.getSource()==exit)
		{
			System.exit(0);
		}

	}
	catch (Exception e)
	{
		System.out.println(e);
	}
	}

/*
	public static void main(String[] args) 
	{
		GUI_MAES ob1=new GUI_MAES();
	}*/
}
