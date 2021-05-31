package dev.tensor.feature.commands;

import dev.tensor.misc.imp.Command;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public final class HClip implements Command {

    @Override
    public String getName() {
        return "HClip";
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
                "hclip",
                "xclip",
                "tp",
                "forward"
        };
    }

    @Override
    public int getID() {
        return 682;
    }

	@Override
    public void onCommand(String[] message) {
    	final Vec3d head = direction(getPlayer().yaw);
    	
        if (message == null || message.length < 2) {
            this.sendReplaceableClientMessage(this.getMarker() + "No value inputted!", this.getID(), true);
            return;
        }

        String value = message[1];

        try {
            double clipAmount = Double.parseDouble(value);

            getPlayer().updatePosition(getPlayer().getX() + head.x * clipAmount, getPlayer().getY(), getPlayer().getZ() + head.z * clipAmount);
            this.sendReplaceableClientMessage(this.getMarker() + "Attempted to x-teleport (" + Formatting.GREEN + clipAmount + Formatting.GRAY + ") blocks!", this.getID(), true);

        } catch (NullPointerException | NumberFormatException ignored) {
            this.sendReplaceableClientMessage(this.getMarker() + "Invalid value (" + Formatting.RED + value + Formatting.GRAY + ")!", this.getID(), true);
        }
    }
    
    public static Vec3d direction(float yaw) { 
    	return new Vec3d(Math.cos(degreeToRad(yaw + 90f)), 0, Math.sin(degreeToRad(yaw + 90f)));
    }
    
    public static double degreeToRad(double deg) {
    	return deg * (float) (Math.PI / 180.0f);
    }
}
