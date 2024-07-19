/*? if Citizens {*/
package me.realized.duels.hook.hooks;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.config.Config;
import me.realized.duels.util.hook.PluginHook;
import net.citizensnpcs.*;

public class CitizensHook extends PluginHook<DuelsPlugin> {

    public static final String NAME = "Citizens";

    private final Config config;

    public CitizensHook(final DuelsPlugin plugin) {
        super(plugin, NAME);
        this.config = plugin.getConfiguration(); //error is normal dont touch it

    }
}
/*?}*/
