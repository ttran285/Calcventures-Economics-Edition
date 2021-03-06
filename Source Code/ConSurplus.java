import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

public class ConSurplus extends JPanel
{
    int counter = 1, degree = 2, sales = 0, x = 0, choice;
    DecimalFormat maxDF = new DecimalFormat("#.###################");
    DecimalFormat df = new DecimalFormat ("0.00");
    String term2 = "", term3 = "", equation = "", correct = "";
    double sell = 0;
    double [] coeff = new double[degree + 1];
    static ArrayList<String> arr, arr2;
    
    public ConSurplus()
    {      
        //Generates Equation
        coeff[2]=Calculate.randCons(0);
        coeff[1]=Calculate.randCoef(0,coeff[2]);
        coeff[0]=Calculate.randCoef2(0,coeff[2],coeff[1]);
        if(coeff[1] != 0)
        {
            if(coeff[1] == -1)
                term2 = "-x";
            else
                term2 = maxDF.format(coeff[1])+"x"; //Bx form
        }
        if(coeff[0] != 0)
        {
            if(coeff[0] == -1)
                term3 = "-x^2";
            else
                term3 = maxDF.format(coeff[0])+"x^2 "; //Ax^2 form
        }
        equation = maxDF.format(coeff[2]) + term2 + term3;
        
        //Generates sales level
        if(coeff[0] != 0)
        {
            int x1=(int)((-coeff[1]+(Math.sqrt(Math.pow(coeff[1],2)-4*coeff[0]*coeff[2])))/(2*coeff[0]));
            int x2=(int)((-coeff[1]-(Math.sqrt(Math.pow(coeff[1],2)-4*coeff[0]*coeff[2])))/(2*coeff[0]));
            if(x2>0 && (x2 < x1 || x1 < 0))
                x = x2;
            if(x1>0 && (x1 < x2 || x2 < 0))
                x = x1;
        }
        else
            x = (int)(-coeff[2]/coeff[1]);
        sales = (int)(Math.random()*(x-10)+10);
        
        //Calculates selling price and generates 4 options
        for (int i = 0 ; i <= degree ; i++)
        {
            sell += coeff[i] * Math.pow(sales,degree-i);
        }
        sell = Double.parseDouble(df.format(sell)); //sell becomes the rounded price displayed on screen
        arr = new ArrayList<String>(Arrays.asList("$"+df.format(sell),"$" + df.format(Math.abs(sell - coeff[2])))); //puts in A and B
        if(-coeff[0]>0.001) //to dtmn C
            arr.add("$" + df.format(sell - coeff[0]*Math.pow(sales,2))); //Fake answer, didn't add Ax^2
        else if(-coeff[1]>0.001)
            arr.add("$" + df.format(sell - coeff[1]*sales)); //Fake answer, didn't add Bx
        else
            arr.add("$" + df.format(sell - 1)); //Fake answer, in case, by some magical chance, both B and C are super small and sell price is close to constant
        if((int)(Math.random()*2)==0) //to dtmn D
            arr.add("$" + df.format(sell/2)); //Fake answer, divided sell by two
        else
            arr.add("$" + df.format(sell*2)); //Fake answer, multiplied sell by two 
        for (int y = 4; y >= 1; y--)
        {
            choice = (int)(Math.random()*y);
            arr.add(arr.get(choice));
            arr.remove(choice);
        }
        
        //Calculates integral and generates 4 options
        arr2 = new ArrayList<String>(Arrays.asList("$" + df.format(Calculate.integrate(0, degree, coeff,0,sales, sell)),"$" + df.format(Calculate.integrate(0, degree, coeff,0,sales, 0)),"$" + df.format(Math.abs(Calculate.integrate(0, degree, coeff,0,sell, sell))),"$" + df.format(Math.abs(Calculate.integrate(0, degree, coeff,0,sell, sales)))));
        correct = arr2.get(0);
        for (int y = 4; y >= 1; y--)
        {
            choice = (int)(Math.random()*y);
            arr2.add(arr2.get(choice));
            arr2.remove(choice);
        }
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { 
                
                int relativeX = e.getX();
                int relativeY = e.getY();
                
                if (relativeX >= 832 && relativeX <= 916 && relativeY >= 548 && relativeY <= 601 && counter != 4 && counter != 6) //Next
                {
                    counter++;
                    repaint();
                }
                if (counter == 4) //Mission 1
                {          
                    if (relativeX >= 601 && relativeX <= 868 && relativeY >= 257 && relativeY <= 304  ) //First choice
                    {
                        if(ConSurplus.getValue(0,0).equals("$"+df.format(sell)))
                        {
                            Driver.dialogBox(1);
                            counter++;
                        }
                        else
                        {
                            Driver.dialogBox(2);
                        }
                        //if answer is correct --> output a textbox telling them they are correct, counter++, repaint();
                        //else if answer is incorrect --> output a textbox telling them they are incorrect, Driver.changeScreens("Fired");
                    }
                    else if (relativeX >= 601 && relativeX <= 868 && relativeY >= 336 && relativeY <= 382) //Second choice
                    {
                        if(ConSurplus.getValue(1,0).equals("$"+df.format(sell)))
                        {
                            Driver.dialogBox(1);
                            counter++;
                        }
                        else
                        {
                            Driver.dialogBox(2);
                        }
                    }
                    else if (relativeX >= 601 && relativeX <= 868 && relativeY >= 417 && relativeY <= 465) //Third choice
                    {
                        if(ConSurplus.getValue(2,0).equals("$"+df.format(sell)))
                        {
                            Driver.dialogBox(1);
                            counter++;
                        }
                        else
                        {
                            Driver.dialogBox(2);
                        }
                    }
                    else if (relativeX >= 601 && relativeX <= 868 && relativeY >= 498 && relativeY <= 545) //Last choice
                    {
                        if(ConSurplus.getValue(3,0).equals("$"+df.format(sell)))
                        {
                            Driver.dialogBox(1);
                            counter++;
                        }
                        else
                        {
                            Driver.dialogBox(2);
                        }
                    }
                }
                else if (counter == 6) //Mission 2
                {
                    if (relativeX >= 601 && relativeX <= 868 && relativeY >= 257 && relativeY <= 304) //First choice
                    {
                        if(ConSurplus.getValue(0,1).equals(correct))
                        {
                            Driver.dialogBox(0);
                        }
                        else
                        {
                            Driver.dialogBox(2);
                        }
                        //if answer is correct --> output a textbox telling them they are correct, Driver.changeScreens("Raise");
                        //else if answer is incorrect --> output a textbox telling them they are incorrect, Driver.changeScreens("Fired");
                    }
                    else if (relativeX >= 601 && relativeX <= 868 && relativeY >= 336 && relativeY <= 382) //Second choice
                    {
                        if(ConSurplus.getValue(1,1).equals(correct))
                        {
                            Driver.dialogBox(0);
                        }
                        else
                        {
                            Driver.dialogBox(2);
                        }
                    }
                    else if (relativeX >= 601 && relativeX <= 868 && relativeY >= 417 && relativeY <= 465) //Third choice
                    {
                        if(ConSurplus.getValue(2,1).equals(correct))
                        {
                            Driver.dialogBox(0);
                        }
                        else
                        {
                            Driver.dialogBox(2);
                        }
                    }
                    else if (relativeX >= 601 && relativeX <= 868 && relativeY >= 498 && relativeY <= 545) //Last choice
                    {
                        if(ConSurplus.getValue(3,1).equals(correct))
                        {
                            Driver.dialogBox(0);
                        }
                        else
                        {
                            Driver.dialogBox(2);
                        }
                    }
                }        
            } 
        }); 
    }
    
    public static String getValue(int index, int which)
    {
        if(which == 0)
            return arr.get(index);
        return arr2.get(index);
    }
    
    public void paintComponent (Graphics g)
    {
        MediaTracker tracker = new MediaTracker (new Frame ());    
        Image conSurplus = Toolkit.getDefaultToolkit ().getImage ("Backgrounds/ConSurplus" + counter + ".png");
        tracker.addImage (conSurplus, 0); 
        g.drawImage (conSurplus, 0, 0, null);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        g.setColor (Color.red);
        
        if (counter == 1)
        {
            g.drawString (equation, 282, 307); //output the equation of the demand curve onto the screen
        }
        else if (counter == 2)
        {
            g.drawString (""+sales, 365, 315); //output the sales level onto the screen
        }
        else if (counter == 4)
        {
            g.drawString (equation, 260, 350); //output the equation of the demand curve
            g.drawString (""+sales, 260, 447); //output the sales level
            g.drawString (arr.get(0), 677, 289); //option 1
            g.drawString (arr.get(1), 677, 368); //option 2
            g.drawString (arr.get(2), 677, 451); //option 3
            g.drawString (arr.get(3), 677, 533); //option 4
        }
        else if (counter == 6)
        {
            g.drawString (equation, 260, 350); //output the equation of the demand curve
            g.drawString (""+sales, 260, 417); //output the sales level
            g.drawString ("$"+df.format(sell), 260, 477); //output the selling price
            g.drawString (arr2.get(0), 677, 289); //option 1
            g.drawString (arr2.get(1), 677, 368); //option 2
            g.drawString (arr2.get(2), 677, 451); //option 3
            g.drawString (arr2.get(3), 677, 533); //option 4
        }
        repaint();
    }
}