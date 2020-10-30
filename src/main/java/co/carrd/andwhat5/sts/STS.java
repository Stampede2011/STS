package co.carrd.andwhat5.sts;

import co.carrd.andwhat5.sts.boosters.*;
import co.carrd.andwhat5.sts.commands.CommandSTS;
import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

@Plugin(id="sts",
        name="STS",
        authors={"AnDwHaT5", "CraftSteamG"},
        version="1.3.0")
public class STS {
    private static STS instance;
    @Inject
    public PluginContainer container;
    @Inject
    private Logger logger;
    public static EconomyService econ;
    @Inject
    @DefaultConfig(sharedRoot=false)
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    public STSConfig config;
    private CommentedConfigurationNode node;
    public ItemType spriteItemType;
    public static List<IBooster> boosters;

    public static STS getInstance() {
        return instance;
    }

    public void loadConfig() throws IOException, ObjectMappingException {
        this.node = this.configLoader.load();
        TypeToken type = TypeToken.of(STSConfig.class);
        this.config = this.node.getValue(type, new STSConfig());
        this.node.setValue(type, this.config);
        this.configLoader.save(this.node);
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        if (this.registerEconomy()) {
            this.spriteItemType = Sponge.getGame().getRegistry().getType(ItemType.class, "pixelmon:pixelmon_sprite").get();
            instance = this;
            try {
                this.loadConfig();
            }
            catch (IOException | ObjectMappingException e) {
                e.printStackTrace();
            }
            boosters.add(new BoosterMoneyPerLevel());
            boosters.add(new BoosterShiny());
            boosters.add(new CustomTextureBooster());
            boosters.add(new HiddenAbilityBooster());
            boosters.add(new IVBooster());
            boosters.add(new LegendaryBooster());
            boosters.add(new PerfectIVBooster());
            boosters.add(new UltraBeastBooster());
            boosters.add(new PerfectEVBooster());
            boosters.add(new MaxLevelBooster());

            CommandSpec sts = CommandSpec.builder()
                    .description(Text.of("Opens the Sell to Server GUI."))
                    .permission("sts.sts.base")
                    .arguments(GenericArguments.optional(GenericArguments.string(Text.of("reload"))))
                    .executor(new CommandSTS())
                    .build();
            Sponge.getCommandManager().register(this, sts, "sts");
        }
    }

    private boolean registerEconomy()
    {
        this.logger.info("Looking for Economy Plugin...");
        Optional economyService = Sponge.getServiceManager().provide(EconomyService.class);
        if (economyService.isPresent())
        {
            econ = (EconomyService)economyService.get();
            this.logger.info("Found Economy Plugin! Starting up!");
            return true;
        }
        this.logger.warn("No Economy Plugin Found! STS will not load!");
        return false;
    }

    static {
        boosters = new ArrayList<>();
    }
}

