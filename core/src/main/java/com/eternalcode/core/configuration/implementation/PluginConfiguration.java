package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.database.DatabaseType;
import com.eternalcode.core.feature.afk.AfkSettings;
import com.eternalcode.core.feature.chat.ChatSettings;
import com.eternalcode.core.teleport.request.TeleportRequestSettings;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Sound;

import java.io.File;
import java.time.Duration;
import java.util.Map;

public class PluginConfiguration implements ReloadableConfig {

    @Description({
        "#",
        "# This is the main configuration file for EternalCore.",
        "#",
        "# If you need help with the configuration or have any questions related to EternalCore, join us in our Discord",
        "#",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/",
        "# Source Code: https://github.com/EternalCodeTeam/EternalCore",
        "#",
    })

    @Description({ " ", "# Database Section" })
    public Database database = new Database();

    @Description({ " ", "# Awesome sounds" })
    public Sounds sound = new Sounds();

    @Description({ " ", "# Chat Section" })
    public Chat chat = new Chat();

    @Description({ " ", "# Additional formatting options" })
    public Format format = new Format();

    @Description({ " ", "# AFK Section" })
    public Afk afk = new Afk();

    @Description({ " ", "# Other Sections" })
    public OtherSettings otherSettings = new OtherSettings();

    @Description({ " ", "# Homes Section" })
    public Homes homes = new Homes();

    @Contextual
    public static class Database {
        @Description({ "# SQL Drivers and ports:", "# MySQL (3306), MariaDB (3306), PostgresQL (5432)", "# SQLite, H2" })
        public DatabaseType databaseType = DatabaseType.SQLITE;

        public String hostname = "127.0.0.1";
        public String database = "database";
        public String username = "root";
        public String password = "U5eStr0ngP4ssw0rd";
        public int port = 3306;
    }


    @Contextual
    public static class OtherSettings implements TeleportRequestSettings {
        @Description({ "# Gamemode Creative on join Requires permission: eternalcore.staff.gamemodejoin" })
        public boolean gameModeOnJoin = false;

        @Description({ " ", "# Use unsafe enchantments? Allows you to apply custom enchants to various items" })
        public boolean unsafeEnchantments = true;

        @Description({ " ", "# Time of tpa requests expire" })
        public Duration tpaRequestExpire = Duration.ofSeconds(80);

        @Description({ " ", "# Time of teleportation time" })
        public Duration tpaTimer = Duration.ofSeconds(10);

        @Override
        public Duration teleportExpire() {
            return this.tpaRequestExpire;
        }

        @Override
        public Duration teleportTime() {
            return this.tpaTimer;
        }
    }

    @Contextual
    public static class Homes {
        @Description({ " ", "# Max homes per permission" })
        public Map<String, Integer> maxHomes = Map.of(
            "eternalcore.home.default", 1,
            "eternalcore.home.vip", 2,
            "eternalcore.home.premium", 3
        );
    }

    @Contextual
    public static class Sounds {
        @Description("# Do you want to enable sound after player join to server?")
        public boolean enabledAfterJoin = true;
        public Sound afterJoin = Sound.BLOCK_NOTE_BLOCK_PLING;
        public float afterJoinVolume = 1.8F;
        public float afterJoinPitch = 1F;

        @Description({ " ", "# Do you want to enable sound after player quit server?" })
        public boolean enableAfterQuit = true;
        public Sound afterQuit = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
        public float afterQuitVolume = 1.8F;
        public float afterQuitPitch = 1F;

        @Description({ " ", "# Do you want to enable sound after player send message on chat server?" })
        public boolean enableAfterChatMessage = true;
        public Sound afterChatMessage = Sound.ENTITY_ITEM_PICKUP;
        public float afterChatMessageVolume = 1.8F;
        public float afterChatMessagePitch = 1F;

    }

    @Contextual
    public static class Chat implements ChatSettings {

        @Description({ " ", "# Delay to send the next message under /helpop" })
        public Duration helpOpDelay = Duration.ofSeconds(60);

        @Description({ " ", "# Custom message for unknown command" })
        public boolean commandExact = false;

        @Description({ " ", "# Chat delay to send next message in chat" })
        public Duration chatDelay = Duration.ofSeconds(5);

        @Description({ " ", "# Count of lines to clear" })
        public int clearLines = 64;

        public boolean chatEnabled = true;

        @Override
        @Exclude
        public boolean isChatEnabled() {
            return this.chatEnabled;
        }

        @Override
        @Exclude
        public void setChatEnabled(boolean chatEnabled) {
            this.chatEnabled = chatEnabled;
        }

        @Override
        @Exclude
        public Duration getChatDelay() {
            return this.chatDelay;
        }

        @Override
        @Exclude
        public void setChatDelay(Duration chatDelay) {
            this.chatDelay = chatDelay;
        }

    }

    @Contextual
    public static class Format {
        public String separator = "&7, ";
    }

    @Contextual
    public static class Afk implements AfkSettings {
        @Description("# Number of interactions a player must make to have AFK status removed")
        public int interactionsCountDisableAfk = 20;

        @Description("# Number of seconds a player must be idle to be considered AFK")
        public Duration afkCommandDelay = Duration.ofSeconds(60);

        @Override
        public int interactionsCountDisableAfk() {
            return this.interactionsCountDisableAfk;
        }

        @Override
        public Duration getAfkDelay() {
            return this.afkCommandDelay;
        }

    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }
}
