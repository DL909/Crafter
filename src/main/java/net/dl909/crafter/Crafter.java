package net.dl909.crafter;

import net.dl909.crafter.block.CrafterBlock;
import net.dl909.crafter.block.CrafterBlockEntity;
import net.dl909.crafter.screen.CrafterScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class Crafter implements ModInitializer {

	@Override
	public void onInitialize() {
		Registry.register(Registries.BLOCK, new Identifier("minecraft","crafter"),CRAFTER_BLOCK);
		Registry.register(Registries.ITEM,new Identifier("minecraft","crafter"),CRAFTER_BLOCK_ITEM);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content-> content.add(CRAFTER_BLOCK_ITEM));

		ServerPlayNetworking.registerGlobalReceiver(
				SLOT_CHANGE_STATE,
				(server, player, handler, buf, responseSender) -> {
                    int slotId = buf.readInt();
					int syncId = buf.readInt();
					boolean enabled = buf.readBoolean();
                    if (player.isSpectator() || syncId != player.currentScreenHandler.syncId) {
						return;
					}
					Object object = player.currentScreenHandler;
					if (object instanceof CrafterScreenHandler && ((CrafterScreenHandler)object).getInputInventory() instanceof CrafterBlockEntity) {
						CrafterBlockEntity crafterBlockEntity = (CrafterBlockEntity)object;
						crafterBlockEntity.setSlotEnabled(slotId, enabled);
					}
				}
		);
	}
	public static final Block CRAFTER_BLOCK = new CrafterBlock(AbstractBlock.Settings.of(Material.STONE).strength(0.8f));
	public static final Item CRAFTER_BLOCK_ITEM = new BlockItem(CRAFTER_BLOCK,new Item.Settings());
	public static final BlockEntityType<CrafterBlockEntity> CRAFTER_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier("minecraft","crafter_block_entity"),
			FabricBlockEntityTypeBuilder.create(CrafterBlockEntity::new,CRAFTER_BLOCK).build()
	);

	public static final ScreenHandlerType<CrafterScreenHandler> CRAFTER_3X3 = ScreenHandlerRegistry.registerExtended(new Identifier("dl_ct", "general"), CrafterScreenHandler::new);

	public static final Identifier SLOT_CHANGE_STATE = new Identifier("dl_ct","slot_change_state");
}
