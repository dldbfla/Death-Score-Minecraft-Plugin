package tes.deathscore;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Deathscore extends JavaPlugin implements Listener {

    private ScoreboardManager manager;
    private Scoreboard board;
    private Objective objective;

    @Override
    public void onEnable() {
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        objective = board.registerNewObjective("죽음", "dummy", "죽음 카운터");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        String playerName = event.getEntity().getName();
        Score score = objective.getScore(playerName);
        int deaths = score.getScore();
        score.setScore(deaths + 1);
        event.getEntity().setScoreboard(board);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        Score score = objective.getScore(playerName);
        // 플레이어가 처음 접속하는 경우 죽음 카운터를 0으로 초기화
        if (score.getScore() == 0 && !score.isScoreSet()) {
            score.setScore(0);
        }
        event.getPlayer().setScoreboard(board);
    }
}
