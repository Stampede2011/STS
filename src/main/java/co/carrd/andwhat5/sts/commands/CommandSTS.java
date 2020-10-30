package co.carrd.andwhat5.sts.commands;

import co.carrd.andwhat5.sts.STS;
import co.carrd.andwhat5.sts.ui.UISTS;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.storage.IStorageManager;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import java.io.IOException;
import java.util.Optional;
import net.minecraft.entity.player.EntityPlayerMP;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandSTS implements CommandExecutor {
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            if (args.hasAny("reload") && ((String)args.getOne("reload").get()).equalsIgnoreCase("reload")) {
                if (src.hasPermission("sts.sts.admin")) {
                    try {
                        STS.getInstance().loadConfig();
                        src.sendMessage(Text.of("[STS] Reloaded config!"));
                    }
                    catch (IOException | ObjectMappingException e) {
                        e.printStackTrace();
                    }
                    return CommandResult.success();
                }
                src.sendMessage(Text.of("[STS] You do not have permission to use this."));
                return CommandResult.success();
            }
            Player player = (Player)src;
            PlayerPartyStorage storage = Pixelmon.storageManager.getParty((EntityPlayerMP)player);
            UISTS ui = new UISTS(player, storage);
            ui.displayGUI();
        }
        return CommandResult.success();
    }
}

