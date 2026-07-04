package ru.logitechno.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import ru.logitechno.Pomidor;

import java.util.Arrays;
import java.util.List;

@Config(name = Pomidor.MOD_ID)
public class PomidorConfig implements ConfigData {
    // Rendering
    @ConfigEntry.Category("Rendering")
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Choices playersNameTags = Choices.DEFAULT;

    public enum Choices {
        DEFAULT,
        HIDE,
        FORCE
    }

    @ConfigEntry.Category("Rendering")
    public boolean forceShowMobNameTags = false;

    @ConfigEntry.Category("Rendering")
    public boolean lightBlockVisible = false;

    // HUDInfo
    @ConfigEntry.Category("HUDInfo")
    public boolean showHUDInfo = true;

    @ConfigEntry.Category("HUDInfo")
    public List<String> textHUDInfo = Arrays.asList(
            "&7Facing: &f{facing}",
            "&7Pitch: &f{pitch}",
            "&7Yaw: &f{yaw}",
            "&7Speed: &f{speed}",
            "&7FPS: &f{fps}",
            "&7Biome: &f{biome}",
            "&7XYZ: &f{posX} {posY} {posZ}",
            "&7Time: &f{time}",
            "&7Days: &f{days}",
            "&7Dimension: &f{dimension}",
            "&7Difficulty: &f{difficulty}",
            "&7Ping: &f{ping}",
            "&7Ping Colored: &f{pingColored}"
    );

    @ConfigEntry.Category("HUDInfo")
    public int offsetXHUDInfo = 2;

    @ConfigEntry.Category("HUDInfo")
    public int offsetZHUDInfo = 2;

    // DeathMessage
    @ConfigEntry.Category("DeathMessage")
    public boolean showDeathMessage = true;

    @ConfigEntry.Category("DeathMessage")
    public List<String> textDeathMessage = Arrays.asList(
            "&8&m        &r &c☠ You died! &8&m        ",
            "&7XYZ: &f{posX} {posY} {posZ}",
            "&7Dimension: &f{dimension}"
    );
}
