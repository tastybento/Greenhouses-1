package world.bentobox.greenhouses;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;

import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.configuration.Config;
import world.bentobox.bentobox.api.flags.Flag;
import world.bentobox.greenhouses.managers.GreenhouseManager;
import world.bentobox.greenhouses.managers.RecipeManager;
import world.bentobox.greenhouses.ui.user.UserCommand;

/**
 * @author tastybento
 *
 */
public class Greenhouses extends Addon {

    private GreenhouseManager manager;
    private Settings settings;
    private RecipeManager recipes;
    private final List<World> activeWorlds = new ArrayList<>();
    public static Flag GREENHOUSES = new Flag.Builder("GREENHOUSES", Material.GREEN_STAINED_GLASS).build();

    /* (non-Javadoc)
     * @see world.bentobox.bentobox.api.addons.Addon#onEnable()
     */
    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.saveResource("biomes.yml", false);
        settings = new Config<>(this, Settings.class).loadConfigObject();
        if (settings == null) {
            // Settings did no load correctly. Disable.
            logError("Settings did not load correctly - disabling Greenhouses - please check config.yml");
            this.setState(State.DISABLED);
            return;
        }
        // Save the config
        new Config<>(this, Settings.class).saveConfigObject(settings);

        // Load recipes
        recipes = new RecipeManager(this);
        // Load manager
        manager = new GreenhouseManager(this);
        // Register commands for AcidIsland and BSkyBlock
        getPlugin().getAddonsManager().getGameModeAddons().stream()
        .filter(gm -> settings.getGameModes().contains(gm.getDescription().getName()))
        .forEach(gm ->  {
            // Register command
            gm.getPlayerCommand().ifPresent(playerCmd -> new UserCommand(this, playerCmd));
            // Store active world
            activeWorlds.add(gm.getOverWorld());
            // Register protection flag with BentoBox
            GREENHOUSES.addGameModeAddon(gm);
        });
        // Register greenhouse manager
        this.registerListener(manager);
        // Register protection flag with BentoBox
        getPlugin().getFlagsManager().registerFlag(GREENHOUSES);

    }

    /* (non-Javadoc)
     * @see world.bentobox.bentobox.api.addons.Addon#onDisable()
     */
    @Override
    public void onDisable() {
        if (manager != null) {
            manager.saveGreenhouses();
        }
    }

    /**
     * @return the manager
     */
    public GreenhouseManager getManager() {
        return manager;
    }

    /**
     * @return Settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * @return the recipes
     */
    public RecipeManager getRecipes() {
        return recipes;
    }

    public List<World> getActiveWorlds() {
        return activeWorlds;
    }

}
