package com.eternalcode.core.command.implementations;

import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Section(route = "disposal", aliases = { "smietnik" })
@Permission("eternalcore.command.disposal")
public class DisposalCommand {

    private final MiniMessage miniMessage;
    private final LanguageManager languageManager;
    private final UserManager userManager;
    private final Server server;

    public DisposalCommand(MiniMessage miniMessage, LanguageManager languageManager, UserManager userManager, Server server) {
        this.miniMessage = miniMessage;
        this.languageManager = languageManager;
        this.userManager = userManager;
        this.server = server;
    }

    @Execute
    public void execute(Player player) {
        Language language = userManager.getUser(player.getUniqueId())
            .map(user -> user.getSettings().getLanguage())
            .orElseGet(Language.DEFAULT);

        Messages messages = languageManager.getMessages(language);
        Component component = miniMessage.deserialize(messages.other().disposalTitle());

        player.openInventory(this.server.createInventory(null, 54, component));
    }

}