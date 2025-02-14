package com.eternalcode.core.feature.privatechat;

import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

import java.util.UUID;

@Route(name = "spy", aliases = { "socialspy" })
@Permission("eternalcore.spy")
public class PrivateChatSocialSpyCommand {

    private final PrivateChatService privateChatService;
    private final NoticeService noticeService;

    public PrivateChatSocialSpyCommand(PrivateChatService privateChatService, NoticeService noticeService) {
        this.privateChatService = privateChatService;
        this.noticeService = noticeService;
    }

    @Execute
    void socialSpy(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.privateChatService.isSpy(uuid)) {
            this.privateChatService.disableSpy(uuid);
            this.noticeService.player(uuid, translation -> translation.privateChat().socialSpyDisable());
            return;
        }

        this.noticeService.player(uuid, translation -> translation.privateChat().socialSpyEnable());
        this.privateChatService.enableSpy(uuid);
    }

}
