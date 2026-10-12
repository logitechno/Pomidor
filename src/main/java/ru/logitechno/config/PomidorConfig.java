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
    public String textHUDInfo =
            "&7Facing: &f{facing}{n}" +
                    "&7Pitch: &f{pitch}{n}" +
                    "&7Yaw: &f{yaw}{n}" +
                    "&7Speed: &f{speed}{n}" +
                    "&7FPS: &f{fps}{n}" +
                    "&7Biome: &f{biome}{n}" +
                    "&7XYZ: &f{posX} {posY} {posZ}{n}" +
                    "&7Time: &f{time}{n}" +
                    "&7Days: &f{days}{n}" +
                    "&7Dimension: &f{dimension}{n}" +
                    "&7Difficulty: &f{difficulty}{n}" +
                    "&7Ping: &f{ping}{n}" +
                    "&7Ping Colored: &f{pingColored}";

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
