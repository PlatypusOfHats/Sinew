package com.unusualmodding.example.event;

import com.unusualmodding.example.data.example.WorldFlagData;
import com.unusualmodding.sinew.Sinew;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Sinew.MODID, value = Dist.CLIENT)
public class TestHudOverlay {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        // Only render on the HOTBAR layer or choose another target overlay
        if (!event.getOverlay().id().toString().equals("minecraft:hotbar")) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        WorldFlagData data = WorldFlagData.get(mc.level);
        GuiGraphics guiGraphics = event.getGuiGraphics();

        int x = 10;
        int y = 10;
        String message = "Client Flag: " + data.flag;
        String message2 = "Client value: " + data.value;
        guiGraphics.drawString(mc.font, message, x, y, 0xFFFFFF, true);
        guiGraphics.drawString(mc.font, message2, x, y + 10, 0xFFFFFF, true);
    }
}
