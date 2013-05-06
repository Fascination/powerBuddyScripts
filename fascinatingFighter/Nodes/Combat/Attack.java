package fascinatingFighter.Nodes.Combat;

import java.util.Arrays;

import org.powerbuddy.api.input.Mouse;
import org.powerbuddy.api.methods.Camera;
import org.powerbuddy.api.methods.GroundItems;
import org.powerbuddy.api.methods.Menu;
import org.powerbuddy.api.methods.Npcs;
import org.powerbuddy.api.methods.Players;
import org.powerbuddy.api.methods.Walking;
import org.powerbuddy.api.util.Filter;
import org.powerbuddy.api.util.Random;
import org.powerbuddy.api.util.Time;
import org.powerbuddy.api.wrapper.GroundItem;
import org.powerbuddy.api.wrapper.Locatable;
import org.powerbuddy.api.wrapper.Npc;

import fascinatingFighter.Methods;
import fascinatingFighter.Variables;


public class Attack {

	private static final Filter<Npc> NPC_FILTER = new Filter<Npc>() {
		public boolean accept(final Npc npc) {
			// Arrays.binarySearch(int[], int) will return -1 if the given array
			// doesn't
			// contain the given element
			return Arrays.binarySearch(Variables.npcToKill, npc.getId()) != -1
					&& npc.getInteracting() == null	;

			// return npc != null && npc.getId() == 1 &&
			// npc.getName().equals(Variables.npcName)
			// && npc.getInteracting() == null;

		}
	};
	
	private static final Filter<Npc> Filter = new Filter<Npc>(){

		@Override
		public boolean accept(Npc n) {
			return n.getId() == -1 && n != null;

		}
		
	};

	public static void attack(){
		Npc enemy = Npcs.getNearest(Variables.npcToKill);
		String action = Variables.action;
		
		if(enemy != null){
			if(!enemy.onScreen()){
				Variables.Status = "Finding closest npc";
				Camera.turnTo(enemy);
			} else {
				Methods.openMenu(enemy, false);
				if(Menu.isMenuOpen() && Menu.optionsContains(action)){
					if(!Methods.isInCombat()){
						Menu.click("Attack");
						Variables.Status = "Choosing action";
						Time.sleep(400, 900);
					}
				} else if (Menu.isMenuOpen() && !Menu.actionsContains(action)){
					Menu.click("Cancel");
				}
			}
			
			
		}
	}
	
	public static void attackNode() {
		Npc enemy = Npcs.getNearest(Variables.npcToKill);

		if (enemy != null) {
			if (!enemy.isInCombat() && !Players.getLocal().isMoving()) {
				if (enemy.onScreen()) {
					Variables.Status = "Attacking..";
					enemy.interact("Attack");
					//Mouse.click(enemy.getLocation().getScreenLocation());
					//interacts(enemy);

					Time.sleep(500, 600);
				} else {
					Variables.Status = "Turning Camera";
					Camera.turnTo(enemy);
				}
			}

		}

	}

	public static void attackNpc() {
		Npc a = Npcs.getNearest(41);
		/*
		 * if(a == null){ Variables.Status = "Null NPC"; } else {
		 * if(a.onScreen() && !a.isInCombat()){ Variables.Status =
		 * " Attacking NPC"; Mouse.click(a.getLocation().getScreenLocation()); }
		 * }
		 */

		a.click();
	}

	public static void attackNode_v1() {

		final Npc npc = Npcs.getNearest(NPC_FILTER);
		if (npc != null) {

			
				if (npc.onScreen() && npc.interact("Attack")) {
					if(Players.getLocal().isMoving()){
						Time.sleep(100);
					}
					Time.sleep(200);
				}
			 else {
				Walking.walkTileMM(npc.getLocation());
				final long end = System.currentTimeMillis()
						+ Random.nextInt(2000, 4000);
				while (System.currentTimeMillis() <= end) {
					if (npc.onScreen()) {
						break;
					}
				}
			}
		}
	}

	public static void attackNode_v2(){
		final Npc npc = Npcs.getNearest(Filter);
		
		if(Players.getLocal() != null && Players.getLocal().getInteracting() != null){
			npc.interact("Attack");
			Time.sleep(350, 500);
		}
	}
	
	public static void interacts(Locatable a) {
		GroundItem enemy = GroundItems.getNearest(Variables.npcToKill);

		if (enemy != null) {
			Mouse.move(enemy.getLocation().getScreenLocation());
			if (Menu.isMenuOpen()) {
				if (Menu.actionsContains("Attack")) {
					Mouse.click(Menu.click("Attack"));
				}
			} else {
				Menu.openMenu();
			}
		}
	}

}
