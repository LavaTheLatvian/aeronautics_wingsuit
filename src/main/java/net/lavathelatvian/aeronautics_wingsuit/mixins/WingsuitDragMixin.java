package net.lavathelatvian.aeronautics_wingsuit.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.lavathelatvian.aeronautics_wingsuit.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LivingEntity.class)
public class WingsuitDragMixin {
    @WrapOperation(
            method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/entity/LivingEntity;isFallFlying()Z"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/phys/Vec3;multiply(DDD)Lnet/minecraft/world/phys/Vec3;",
                    ordinal = 0  // first multiply AFTER isFallFlying(), which is the 0.99/0.98/0.99 one
            )
    )
    private Vec3 wrapWingsuitGlide(Vec3 vec3, double x, double y, double z, Operation<Vec3> original) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.WINGSUIT)) {
            return original.call(vec3, 0.97, 0.96, 0.97);
        }
        return original.call(vec3, x, y, z);
    }
}
