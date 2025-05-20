package com.mc.gaul.thepitcore.Utils;

import com.mc.gaul.thepitcore.File.ConfigX;
import com.mc.gaul.thepitcore.ThePitLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ScoreBoard {

    public void applyLobbyBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        ConfigX configX = new ConfigX(ThePitLite.getMain());
        FileConfiguration config = configX.getConfig("config");
        String title = ChatColor.translateAlternateColorCodes('&', config.getString("scoreboard.game.title"));

        Objective objective = board.registerNewObjective(title, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> rows = config.getStringList("scoreboard.lobby.rows");
        Collections.reverse(rows); // 점수 높은 게 위에 표시됨
        int score = 1;
        for (String line : rows) {
            String parsed = replacePlaceholders(line, player);
            Score s = objective.getScore(ChatColor.translateAlternateColorCodes('&', parsed));
            s.setScore(score++);
        }

        player.setScoreboard(board);
    }

    private String replacePlaceholders(String line, Player player) {
        return line
                .replace("%name%", player.getName())
                .replace("%count%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("%date%", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}
