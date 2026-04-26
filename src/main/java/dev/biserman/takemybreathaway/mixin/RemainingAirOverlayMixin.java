package dev.biserman.takemybreathaway.mixin;

import dev.biserman.takemybreathaway.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(value = RemainingAirOverlay.class, remap = false)
public class RemainingAirOverlayMixin {
    @Unique
    private static boolean takeMyBreathAway$isBreathlessDimension() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return false;
        return Config.DIMENSIONS.get().contains(player.level().dimension().location().toString());
    }

    // Redirects the `isAir()` call that sets the isAir local variable
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/neoforged/neoforge/fluids/FluidType;isAir()Z"
            )
    )
    private boolean redirectIsAir(FluidType fluidType) {
        if (takeMyBreathAway$isBreathlessDimension()) return false;
        return fluidType.isAir();
    }

    // Redirects the `canDrownInFluidType` call that sets the canBreathe local variable
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;canDrownInFluidType(Lnet/neoforged/neoforge/fluids/FluidType;)Z"
            )
    )
    private boolean redirectCanDrown(LocalPlayer player, FluidType fluidType) {
        if (takeMyBreathAway$isBreathlessDimension()) return true; // true = CAN drown, so !canDrown = false
        return player.canDrownInFluidType(fluidType);
    }
}
