package co.carrd.andwhat5.sts;

import co.carrd.andwhat5.sts.boosters.*;
import co.carrd.andwhat5.sts.commands.CommandReload;
import co.carrd.andwhat5.sts.commands.CommandSTS;
import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.interfaces.IBooster;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Plugin(id = "sts", name = "STS", authors = {"AnDwHaT5"}, version = "8.0.0-1.1.1")
public class STS {
    private static STS instance;
    @Inject
    public PluginContainer container;
    @Inject
    private Logger logger;

    public static STS getInstance() { return instance; }

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;

    public STSConfig config;

    private CommentedConfigurationNode node;

    public static List<IBooster> boosters = new ArrayList();

    public void loadConfig() throws IOException, ObjectMappingException {
        this.node = (CommentedConfigurationNode)this.configLoader.load();
        TypeToken<STSConfig> type = TypeToken.of(STSConfig.class);
        this.config = (STSConfig)this.node.getValue(type, new STSConfig());
        this.node.setValue(type, this.config);
        this.configLoader.save(this.node);
    }



    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        boosters.add(new BoosterMoneyPerLevel());
        boosters.add(new MaxLevelBooster());
        boosters.add(new IVBooster());
        boosters.add(new PerfectIVBooster());
        boosters.add(new EVBooster());
        boosters.add(new PerfectEVBooster());
        boosters.add(new HiddenAbilityBooster());
        boosters.add(new BoosterShiny());
        boosters.add(new LegendaryBooster());
        boosters.add(new SpecialTextureBooster());
        boosters.add(new CustomTextureBooster());

        CommandSpec sts = CommandSpec.builder()
                .description(Text.of("Opens the Server Trade Station GUI."))
                .permission("sts.sts.base")
                .child(CommandReload.build(), new String[] { "reload"})
                .executor(new CommandSTS()).build();

        Sponge.getCommandManager().register(this, sts, new String[] { "sts" });
    }

    public static Logger getLogger() {
        return instance.logger;
    }
}
