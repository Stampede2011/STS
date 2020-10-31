package co.carrd.andwhat5.sts.commands;

import co.carrd.andwhat5.sts.STS;
import co.carrd.andwhat5.sts.Utilities;
import co.carrd.andwhat5.sts.config.STSConfig;
import co.carrd.andwhat5.sts.ui.UISTS;
import com.pixelmongenerations.core.storage.PixelmonStorage;
import com.pixelmongenerations.core.storage.PlayerStorage;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayerMP;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;


public class CommandSTS
        implements CommandExecutor
{
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {

            Player player = (Player)src;
            PlayerStorage storage = (PlayerStorage)PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)player).orElse(null);
            if (storage == null) {

                src.sendMessage(Utilities.getMessage(STSConfig.Messages.loadError));
                return CommandResult.success();
            }
            UISTS ui = new UISTS(player, storage.partyPokemon);
            ui.displayGUI();
        }
        return CommandResult.success();
    }
}
