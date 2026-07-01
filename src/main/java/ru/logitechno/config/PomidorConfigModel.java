package ru.logitechno.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;
import ru.logitechno.Pomidor;

@Modmenu(modId = Pomidor.MOD_ID)
@Config(name = "pomidor-config", wrapperName = "PomidorConfig")
public class PomidorConfigModel {
    public boolean forceShowMobNameTags = false;
    public Choices playersNameTags = Choices.DEFAULT;
    public static enum Choices {
        DEFAULT, HIDE, FORCE;
    }
    public boolean isLightBlockVisible = false;

    @SectionHeader("HUDInfo")
    public boolean showHUDInfo = true;
    public String textHUDInfo = "&7Facing: &f{facing}{n}&7Pitch: &f{pitch}{n}&7Yaw: &f{yaw}{n}&7Speed: &f{speed}{n}&7FPS: &f{fps}{n}&7Biome: &f{biome}{n}&7XYZ: &f{posX} {posY} {posZ}{n}&7Time: &f{time}{n}&7Days: &f{days}{n}&7Dimension: &f{dimension}{n}&7Difficulty: &f{difficulty}{n}&7Ping: &f{ping}{n}&7Ping Colored: &f{pingColored}";
    public int intendXHUDInfo = 2;
    public int intendZHUDInfo = 2;

    @SectionHeader("DeathMessage")
    public boolean showDeathMessage = true;
    public String textDeathMessage = "&8&m        &r &c☠ You died! &8&m        {n}&7XYZ: &f{posX} {posY} {posZ}{n}&7Dimension: &f{dimension}";
}
