package com.example.dropall;

import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public final class DropAllHelper {
    private DropAllHelper() {}

    /** Инвентарь открыт: выкинуть все вещи игрока (инвентарь, хотбар, вторая рука). Броня не трогается. */
    public static void dropPlayerItems() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.interactionManager == null) return;

        ScreenHandler handler = mc.player.currentScreenHandler;
        int syncId = handler.syncId;

        for (int i = 0; i < handler.slots.size(); i++) {
            Slot slot = handler.getSlot(i);
            if (slot.inventory != mc.player.getInventory()) continue;
            int idx = slot.getIndex();
            boolean wanted = (idx >= 0 && idx <= 35) || idx == 40;
            if (wanted && slot.hasStack()) {
                mc.interactionManager.clickSlot(syncId, i, 1, SlotActionType.THROW, mc.player);
            }
        }
    }

    /** Инвентарь открыт: забрать все из ячеек крафта 2x2 в инвентарь. Ячейка результата не трогается. */
    public static void takeCraftingItems() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.interactionManager == null) return;

        ScreenHandler handler = mc.player.currentScreenHandler;
        int syncId = handler.syncId;

        for (int i = 1; i <= 4 && i < handler.slots.size(); i++) {
            if (handler.getSlot(i).hasStack()) {
                mc.interactionManager.clickSlot(syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
            }
        }
    }

    /** Контейнер открыт: выкинуть всё, что лежит в контейнере. */
    public static void dropContainerItems() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.interactionManager == null) return;

        ScreenHandler handler = mc.player.currentScreenHandler;
        int syncId = handler.syncId;

        for (int i = 0; i < handler.slots.size(); i++) {
            Slot slot = handler.getSlot(i);
            if (slot.inventory == mc.player.getInventory()) continue;
            if (slot.hasStack()) {
                mc.interactionManager.clickSlot(syncId, i, 1, SlotActionType.THROW, mc.player);
            }
        }
    }

    /** Контейнер открыт: забрать всё из контейнера в инвентарь (shift-клик). */
    public static void takeContainerItems() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.interactionManager == null) return;

        ScreenHandler handler = mc.player.currentScreenHandler;
        int syncId = handler.syncId;

        for (int i = 0; i < handler.slots.size(); i++) {
            Slot slot = handler.getSlot(i);
            if (slot.inventory == mc.player.getInventory()) continue;
            if (slot.hasStack()) {
                mc.interactionManager.clickSlot(syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
            }
        }
    }
}
