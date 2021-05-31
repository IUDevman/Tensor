package dev.tensor.feature.commands;

import dev.tensor.misc.imp.Command;
import net.minecraft.entity.Entity;
import net.minecraft.util.Formatting;

public final class Vanish implements Command {
	private static Entity ridden;
	
    @Override
    public String getName() {
        return "Vanish";
    }

    @Override
    public String getMarker() {
        return "(" + Formatting.YELLOW + this.getName() + Formatting.GRAY + ") ";
    }

    @Override
    public String getSyntax() {
        return "{alias} [value]";
    }

    @Override
    public String[] getAliases() {
        return new String[]{
                "vanish"
        };
    }

    @Override
    public int getID() {
        return 683;
    }

    @Override
    public void onCommand(String[] message) {
    	if(getPlayer().getVehicle() != null && ridden == null) {
    		ridden = getPlayer().getVehicle();
    		
    		getPlayer().updatePassengerForDismount(getPlayer());
    		getWorld().removeEntity(ridden.getEntityId());
    		
    		this.sendClientMessage(this.getMarker() + "Entity " + ridden.getEntityName() + " removed.", true);
    	}else {
    		if(ridden != null) {
    			ridden.removed = false;
    			getWorld().addEntity(ridden.getEntityId(), ridden);
    			getPlayer().startRiding(ridden, true);
    			
    			this.sendClientMessage(this.getMarker() + "Entity " + ridden.getEntityName() + " created.", true);
    			ridden = null;
    		}else {
    			this.sendClientMessage(this.getMarker() + "No entity is being ridden :(", true);
    		}
    	}
    }
}
