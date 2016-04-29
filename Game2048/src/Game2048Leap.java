
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

class LeapListner extends Listener
{	long gid;	
	Game2048 g;
	public void onInit(Controller controller)
	{
		g=new Game2048();
		System.out.println("initialized");		
		JFrame game = new JFrame();
	    game.setTitle("2048 Game");
	    game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    game.setSize(340, 400);
	    game.setResizable(false);
	    game.add(g);
	    game.setLocationRelativeTo(null);
	    game.setVisible(true);
	}
	
	
	public void onConnect(Controller controller)
	{
		System.out.println("connected to motion sensor");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);			
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
		controller.config().setFloat("Gesture.Swipe.MinLength", 50.0f);
		controller.config().setFloat("Gesture.Swipe.MinVelocity", 750f);
		controller.config().save();
		Frame frame = controller.frame();	    
		GestureList gestures = frame.gestures();
		for(int i = 0; i < gestures.count();i++)
		{
			//controller.enableGesture(Gesture.Type.TYPE_SWIPE);	
			Gesture gesture = gestures.get(i);
			
			if(gesture.type()==Gesture.Type.TYPE_SWIPE)
			{
				/*SwipeGesture swipe = new SwipeGesture(gesture);				
				Vector swipeDirection =swipe.direction();
				
				
				System.out.println("swipe id:"+swipe.id()+ "state:"+swipe.state()
									+"swipe start position:"+swipe.startPosition()
									+ "swipe position:"+swipe.position()
									+"swipe direction:"+swipe.direction());
				
				
				
				float x=swipeDirection.getX();
				float y=swipeDirection.getY();
				
				
				if(!g.canMove())
				{
					g.myLose=true;
				}
				
				String direc;
				if(x>y)
				{
					if(x>0)
						direc="right";
					else 
						direc="left";
				}
				else
				{
				if(y>0)
					direc="up";
				else
					direc="down";
				}
				*/
				SwipeGesture swipe=new SwipeGesture(gesture);
				if(gesture.state()==Gesture.State.STATE_START)
				{
					gid=gesture.id();
					swipe.pointable().stabilizedTipPosition();
				}
				else if(gesture.state()==Gesture.State.STATE_STOP&&gesture.id()==gid)
				{
				float xdiff=swipe.direction().getX();
				float ydiff=swipe.direction().getY();
				String direc;
				if(Math.abs(xdiff)>Math.abs(ydiff))
				{
					direc=(xdiff>0)? "right" : "left";
				}
				else
				{
					direc=(ydiff>0)? "up" : "down";
				}
				if(!g.myWin&&!g.myLose)
				{
				switch(direc){
				case "up":
						g.up();
						break;
				case "down" :
						g.down();
						break;			
				case "left": 
						g.left();
						break;
				case "right": 
						g.right();
						break;											
				}
				}}
				
				if(!g.myWin&&!g.canMove())
				{
					g.myLose=true;
				}
				g.repaint();
			}		
		}
		//g.resetGame();
	}
		
}
		




public class Game2048Leap {

	public static void main(String[] args) {
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
