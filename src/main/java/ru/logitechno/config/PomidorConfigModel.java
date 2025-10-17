package ru.logitechno.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;
import ru.logitechno.Pomidor;

@Modmenu(modId = Pomidor.MOD_ID)
@Config(name = "pomidor-config", wrapperName = "PomidorConfig")
public class PomidorConfigModel {
    public boolean forceShowNameTags = false;
    public boolean hidePlayersNameTags = false;
    public boolean isLightBlockVisible = false;

    @SectionHeader("HUDInfo")
    public boolean toggleHUDInfo = true;
    public String textHUDInfo = "§6Biome: §f{biome}{n}§6XYZ: §f{posX} {posY} {posZ}{n}§6Time: §f{time}";
    public int intendXHUDInfo = 2;
    public int intendZHUDInfo = 2;
}
