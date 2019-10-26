package wolforce.mechanics.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolforce.mechanics.MConfig;
import wolforce.mechanics.Util;
import wolforce.mechanics.recipes.RecipeNetherPortal;

public class RecipePortalEvents {

	@SubscribeEvent
	public static void travelToDim(EntityTravelToDimensionEvent event) {

		if ((event.getEntity() instanceof EntityItem)) {
			EntityItem entityItem = (EntityItem) event.getEntity();
			ItemStack stackThrown = RecipeNetherPortal.getOutput(entityItem.getItem());
			if (Util.isValid(stackThrown)) {
				event.setCanceled(true);
				ItemStack newItemStack = stackThrown.copy();
				newItemStack.setCount(entityItem.getItem().getCount() * newItemStack.getCount());
				entityItem.setItem(newItemStack);
				// spawnInPlaceOf(newItemStack, entityItem);
			}
		}

		if (!MConfig.portal.allowEntitiesToTravelToTheNether && event.getDimension() == DimensionType.NETHER.getId())
			event.setCanceled(true);
	}
}
