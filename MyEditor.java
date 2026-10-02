import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class MyEditor implements ActionListener
{
JFrame jf ;
JLabel j1 ;
JTextField jtf ;
JTextArea jta , jta1 ;
JButton jbcompile ,jbrun ;
JScrollPane jsp , jsp1 ;
Runtime r ;
String str ="";
String fname ="";
String result ="";
String result1 ="";

MyEditor()
{
jf = new JFrame("My Editor");
jf.setLayout(null);
j1 = new JLabel("Enter java Class Name");
j1.setBounds(20,20,130,25);
jtf = new JTextField();
jtf.setBounds(180,20,230,25);
jta=new JTextArea(50,50);

jta.addFocusListener(new MyFocusListener(this));
jta1=new JTextArea(50,50);
jta.setFont(new Font("varinda",Font.PLAIN,15));
jta1.setFont(new Font("varinda",Font.PLAIN,15));
jsp=new JScrollPane(jta);
jsp1=new JScrollPane(jta1);
jsp.setBounds(50,60,320,150);
jsp1.setBounds(50,270,320,150);
jf.add(jsp);
jf.add(jsp1);
jbcompile=new JButton("Compile");
jbrun= new JButton("Run");
jbcompile.setBounds(100,230,80,25);
jbrun.setBounds(280,230,80,25);
jf.add(j1);
jf.add(jtf);
r=Runtime.getRuntime();
jf.add(jbcompile);
jf.add(jbrun);
jbcompile.addActionListener(this);
jbrun.addActionListener(this);
jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
jf.setSize(550,550);
jf.setVisible(true);
}

public void actionPerformed(ActionEvent e)
{
if(e.getSource() == jbcompile)
{
str="";

if(!jtf.getText().equals(""))
{

try
{
fname = jtf.getText().trim()+".java";

FileWriter fw = new FileWriter(fname);
PrintWriter pw = new PrintWriter(fw);

pw.println(jta.getText());

pw.flush();
pw.close();
fw.close();

//Process error = r.exec("C:\\Program Files\\Java\\jdk-9.0.4\\bin\\javac.exe -d . " +fname);
Process error = r.exec(new String[]{"C:\\Program Files\\Java\\jdk-26.0.1\\bin\\javac.exe","-d",".",fname});
BufferedReader err = new BufferedReader(new InputStreamReader(error.getErrorStream()));

result = "";

while(true)
{
String temp = err.readLine();
if(temp != null)
{
result +=temp;
result +="\n";
}
else break;
}

if(result.equals(""))
{
jta1.setText("Compilation Successful !" +fname);
err.close();
}
else
jta1.setText(result);
}
catch(Exception e1)
{
System.out.println(e1);
}
}
else
jta1.setText("Please Enter the java Program name !");
}

else if(e.getSource() == jbrun)
{
try
{
String fn = jtf.getText().trim();

//Process p = r.exec("C:\\Program Files\\Java\\jdk-9.0.4\\bin\\java -cp . " +fn);
Process p = r.exec(new String[ ]{"C:\\Program Files\\Java\\jdk-26.0.1\\bin\\java","-cp",".",fn});
BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

result = "";

while(true)
{
String temp=output.readLine();
if(temp !=null)
{
result +=temp;
result+="\n";
}
else
{
break;
}
}

while(true)
{
String temp=error.readLine();
if(temp != null)
{
result +=temp;
result+="\n";
}
else
{
break;
}
}

output.close();
error.close();

jta1.setText(result);
}
catch(Exception e2)
{
System.out.println(e2);
}
}
}

public static void main(String arg[ ])
{
new MyEditor();
}
}

class MyFocusListener extends FocusAdapter
{
MyEditor e;

MyFocusListener(MyEditor e)
{
this.e=e;
}

public void focusGained(FocusEvent fe)
{
String str =e.jtf.getText().trim();

e.jta.setText("public class "+str+"\n"+"{\n"+"public static void main(String...s)\n"+"{\n"+"\n"+"}\n"+"}");
}
}






