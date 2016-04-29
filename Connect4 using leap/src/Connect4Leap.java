import java.io.IOException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.InputEvent;

import com.leapmotion.leap.*;

class LeapListner extends Listener
{
	int a1,a2;
	Connect4 x =new Connect4();
	public Robot robot;
	
	public void onInit(Controller controller)
	{
		System.out.println("initilized");
		(new Thread(x)).run();
	}
	public void onConnect(Controller controller)
	{
		System.out.println("connected to motion sensor");
		/*controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);*/				
	}
	public void onDisConnect(Controller controller)
	{
		System.out.println("motion sensor disconnected");
	}
	public void onExit(Controller controller)
	{
		System.out.println("Exited");
	}
	public void onFrame(Controller controller)
	{
		try{
		robot=new Robot();
		}
		catch(Exception e){}
		Frame frame = controller.frame();
		InteractionBox box=frame.interactionBox();
		
		//for index finger in the interaction box
		
		
		
		for(Finger f: frame.fingers())
		{
			if(f.type()==Finger.Type.TYPE_INDEX)
			{
				Vector fingerposition=f.stabilizedTipPosition();
				Vector boxFingerposition=box.normalizePoint(fingerposition);
				//Dimension screen=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				Dimension screen=x.frame.getContentPane().getSize();
				robot.mouseMove((int)(screen.width * boxFingerposition.getX()), (int)(screen.height- boxFingerposition.getY()*screen.height));												
				 a1=(int)(screen.width * boxFingerposition.getX());
				 a2=(int)(screen.height- boxFingerposition.getY()*screen.height);	
			}
			
			if(f.type()==Finger.Type.TYPE_THUMB && f.type()==Finger.Type.TYPE_INDEX )
			{
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			
					if(!x.didWin)
					{
		                if(a2>150)
		                {
		                    if (a1 > 75 && a1 < 125) 
		                    {
		                        x.addToColumn(0,x.player);
		                    }
		                    if (a1 > 125 && a1 < 175) 
		                    {
		                       x.addToColumn(1, x.player);
		                    }
		                    if (a1 > 175 && a1 < 225) 
		                    {
		                        x.addToColumn(2, x.player);
		                    }
		                    if (a1 > 225 && a1 < 275) 
		                    {
		                        x.addToColumn(3, x.player);
		                    }
		                    if (a1 > 275 && a1 < 325) 
		                    {
		                        x.addToColumn(4, x.player);		                     
		                    }
		                    if (a1 > 325 && a1 < 375) 
		                    {
		                        x.addToColumn(5, x.player);
		                    }
		                    if (a1 > 375 && a1 < 425) 
		                    {
		                        x.addToColumn(6,x. player);
		                    }
		                }
					}  
			}
			
		}		
	}
	
}
	
	public class Connect4Leap 
	{

		public static void main(String[] args)
		{
			LeapListner listener =new LeapListner();
			Controller controller=new Controller();
			
			controller.addListener(listener);
			System.out.println("press enter to quit");
			try
			{
				System.in.read();
			}
			catch(IOException e) 
			{
				e.printStackTrace();
			}
			controller.removeListener(listener);
			

		}
	}
