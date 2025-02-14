package com.eternalcode.core.teleport.command;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "tppos")
@Permission("eternalcore.tppos")
public class TeleportToPositionCommand {

    private final NoticeService noticeService;
    private final TeleportService teleportService;

    public TeleportToPositionCommand(NoticeService noticeService, TeleportService teleportService) {
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute(min = 3, max = 4)
    void execute(CommandSender sender, Viewer audience, @Arg @Name("x") Integer x, @Arg @Name("y") Integer y, @Arg @Name("z") Integer z, @Arg @By("or_sender") Player player) {
        if (sender.equals(player)) {
            this.teleport(player, x, y, z);
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.teleport().teleportedSpecifiedPlayerToCoordinates())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .viewer(audience)
            .send();
    }


    private void teleport(Player player, int x, int y, int z) {
        Location location = new Location(player.getWorld(), x, y, z);

        this.teleportService.teleport(player, location);
        this.noticeService.create()
            .notice(translation -> translation.teleport().teleportedToCoordinates())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .player(player.getUniqueId())
            .send();
    }
}
