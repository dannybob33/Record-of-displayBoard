package Jinput;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class JinputTestRunner {

	public static void main(String[] args) {
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
		ArrayList<Controller> gamePads = new ArrayList<Controller>();
		Controller input = null;

		for (int i = 0; i < ca.length; i++) {
			if (ca[i].getType() == Controller.Type.GAMEPAD) {
				input = ca[i];
			}

		}
		Component[] components = input.getComponents();
		for(Component c: components) {
			System.out.println(c);
		}
		 while(true) {
	         Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
	         if(controllers.length==0) {
	            System.out.println("Found no controllers.");
	            System.exit(0);
	         }
	         
	         for(int i=0;i<controllers.length;i++) {
	            controllers[i].poll();
	            EventQueue queue = controllers[i].getEventQueue();
	            Event event = new Event();
	            while(queue.getNextEvent(event)) {
	                StringBuffer buffer = new StringBuffer(controllers[i].getName());
	                buffer.append(" at ");
	                buffer.append(event.getNanos()).append(", ");
	                Component comp = event.getComponent();
	                buffer.append(comp.getName()).append(" changed to ");
	                float value = event.getValue(); 
	                if(comp.isAnalog()) {
	                   buffer.append(value);
	                } else {
	                   if(value==1.0f) {
	                      buffer.append("On");
	                   } else {
	                      buffer.append("Off");
	                   }
	                }
	                System.out.println(buffer.toString());
	             }
	          }
	          
	          try {
	             Thread.sleep(20);
	          } catch (InterruptedException e) {
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	          }
	       }
//		while (true) {
//			input.poll();
//			Component[] components = input.getComponents();
//			StringBuffer buffer = new StringBuffer();
//			for (int i = 0; i < components.length; i++) {
//				if (i > 0) {
//					buffer.append(", ");
//				}
//				buffer.append(components[i].getName());
//				buffer.append(": ");
//			}
//		}
		//
		// /* Get the name of the controller */
		// System.out.println(ca[i].getName());
		// System.out.println("Type: " + ca[i].getType().toString());
		//
		// /* Get this controllers components (buttons and axis) */
		// Component[] components = ca[i].getComponents();
		// System.out.println("Component Count: " + components.length);
		// for (int j = 0; j < components.length; j++) {
		//
		// /* Get the components name */
		// System.out.println("Component " + j + ": " + components[j].getName());
		// System.out.print(" ComponentType: ");
		// if (components[j].isRelative()) {
		// System.out.print("Relative");
		// } else {
		// System.out.print("Absolute");
		// }
		// if (components[j].isAnalog()) {
		// System.out.print(" Analog");
		// } else {
		// System.out.print(" Digital");
		// }
		// }
		// }
		// Controller[] controllers =
		// ControllerEnvironment.getDefaultEnvironment().getControllers();
		// Controller firstMouse = null;
		// for (int i = 0; i < controllers.length && firstMouse == null; i++) {
		// if (controllers[i].getType() == Controller.Type.MOUSE) {
		// // Found a mouse
		// firstMouse = controllers[i];
		// }
		// }
		// if (firstMouse == null) {
		// // Couldn't find a mouse
		// System.out.println("Found no mouse");
		// System.exit(0);
		// }
		//
		// System.out.println("First mouse is: " + firstMouse.getName());
		//
		// while (true) {
		// firstMouse.poll();
		// Component[] components = firstMouse.getComponents();
		// StringBuffer buffer = new StringBuffer();
		// for (int i = 0; i < components.length; i++) {
		// if (i > 0) {
		// buffer.append(", ");
		// }
		// buffer.append(components[i].getName());
		// buffer.append(": ");
		// if (components[i].isAnalog()) {
		// buffer.append(components[i].getPollData());
		// } else {
		// if (components[i].getPollData() == 1.0f) {
		// buffer.append("On");
		// } else {
		// buffer.append("Off");
		// }
		// }
		// }
		// System.out.println(buffer.toString());
		//
		// try {
		// Thread.sleep(20);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

}
