import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MAIN 
{
	
	public static void main(String[] args) 
	{
		System.out.println("Hello World!");
	 
Object[] possibilities = {"Image File", "Video File"};

String option = (String)JOptionPane.showInputDialog(
                    null,
                    "Select the File Type:\n"
                    ,
                    "Ecryption & Decryption of File",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "Text File");

//If a string was returned, say so.

if ((option != null) && (option.length() > 0)) {

 System.out.println("Your Choice="+option);   

	if (option.compareTo("Video File")==0)
	{
		GUI_MAES_vid ob1=new GUI_MAES_vid();
	}
	else 
	{
		GUI_MAES ob1=new GUI_MAES();
	}

}

else

{

System.out.println("\n<-- Exiting -->\n");

System.exit(0);

}


}
}
