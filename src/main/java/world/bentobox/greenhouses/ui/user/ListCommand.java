/**
 *
 */
package world.bentobox.greenhouses.ui.user;

import java.util.List;

import org.bukkit.ChatColor;

import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.greenhouses.Greenhouses;
import world.bentobox.greenhouses.greenhouse.BiomeRecipe;
import world.bentobox.greenhouses.ui.Locale;
import world.bentobox.greenhouses.util.Util;

/**
 * @author tastybento
 *
 */
public class ListCommand extends CompositeCommand {

    /**
     * @param parent
     * @param label
     * @param aliases
     */
    public ListCommand(CompositeCommand parent) {
        super(parent, "list");
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see world.bentobox.bentobox.api.commands.BentoBoxCommand#setup()
     */
    @Override
    public void setup() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see world.bentobox.bentobox.api.commands.BentoBoxCommand#execute(world.bentobox.bentobox.api.user.User, java.lang.String, java.util.List)
     */
    @Override
    public boolean execute(User user, String label, List<String> args) {
        // List all the biomes that can be made
        user.sendMessage(ChatColor.GREEN + Locale.listtitle);
        user.sendMessage(Locale.listinfo);
        int index = 0;
        for (BiomeRecipe br : ((Greenhouses)getAddon()).getBiomeRecipes()) {
            if (br.getFriendlyName().isEmpty()) {
                user.sendMessage(ChatColor.YELLOW + Integer.toString(index++) + ": " + Util.prettifyText(br.getBiome().toString()));
            } else {
                user.sendMessage(ChatColor.YELLOW + Integer.toString(index++) + ": " + br.getFriendlyName());
            }
        }
        return true;

    }

}
