package fascinatingFighter.Nodes.Looting;

import org.powerbuddy.api.input.Mouse;
import org.powerbuddy.api.methods.Calculations;
import org.powerbuddy.api.methods.Camera;
import org.powerbuddy.api.methods.GroundItems;
import org.powerbuddy.api.methods.Menu;
import org.powerbuddy.api.methods.Npcs;
import org.powerbuddy.api.methods.Walking;
import org.powerbuddy.api.util.Time;
import org.powerbuddy.api.wrapper.GroundItem;
import org.powerbuddy.api.wrapper.Locatable;
import org.powerbuddy.api.wrapper.Npc;

import fascinatingFighter.Methods;
import fascinatingFighter.Variables;

public class LootMethod {

	public static void looting(){
		GroundItem loot = GroundItems.getNearest(Variables.lootID);
		String action = Variables.action;
		
		if(Methods.lootNow()){
			if(!loot.onScreen()){
				Variables.Status = "Finding closest loot";
				Camera.turnTo(loot);
			} else {
				Methods.openGroundItemMenu(loot, false);
				if(Menu.isMenuOpen() && Menu.optionsContains(action)){
					
						Menu.click("Take");
						Variables.Status = "Choosing " + action;
						Time.sleep(400, 900);
					
				} else if (Menu.isMenuOpen() && !Menu.actionsContains(action)){
					Menu.click("Cancel");
				}
			}
		}
	}
	
	public static void loot(){
		GroundItem loot = GroundItems.getNearest(Variables.lootID);
		
		if (loot != null) {
			if(loot.onScreen()){
				Variables.Status = "Found Loot";
						if (Calculations.distanceTo(loot) > 3) {
								Walking.walkTileMM(loot.getLocation());
						}
			
				loot.interact("Take");
			} else {
				Camera.turnTo(loot);
			}
		}
	}

	public static void interacts(Locatable a, String i){
		GroundItem loot = GroundItems.getNearest(Variables.lootID);
		
		if(loot != null){
			Mouse.move(loot.getLocation().getScreenLocation());
				if(Menu.isMenuOpen()){
					if(Menu.actionsContains("Take")){
						Mouse.click(Menu.click("Take"));
					}
				} else {
					Menu.openMenu();
				}
		}
	}
	
}