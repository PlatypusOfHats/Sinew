package com.unusualmodding.example.event;

import com.unusualmodding.example.data.example.WorldFlagData;
import com.unusualmodding.sinew.Sinew;
import com.unusualmodding.sinew.data.ClientAccessibleSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Sinew.MODID)
public class TestSavedDataEvents {

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Level level = event.getLevel();
        WorldFlagData data = WorldFlagData.get(level);

        data.flag = !data.flag;
        data.value += 10;
        data.setDirty(); // This triggers saving + syncing automatically

        player.sendSystemMessage(Component.literal("World flag is now: " + data.flag));
    }


    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = player.level();

        // Only do this on the server
        if (!(level instanceof ServerLevel serverLevel)) return;

        // Check if holding a stick in main hand
        if (player.getMainHandItem().getItem() != Items.STICK && event.getHand() == InteractionHand.MAIN_HAND) return;

        // Toggle the flag in WorldFlagData
        WorldFlagData data = WorldFlagData.get(serverLevel);
        data.flag = !data.flag;
        data.setDirty(); // This triggers save and sync

        // Feedback
        player.sendSystemMessage(Component.literal("Flag toggled to: " + data.flag));
    }


    @SubscribeEvent
    public static void playerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
            ClientAccessibleSavedData.clearClientCache();
    }
}
