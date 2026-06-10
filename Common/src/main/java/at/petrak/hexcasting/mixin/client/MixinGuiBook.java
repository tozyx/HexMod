package at.petrak.hexcasting.mixin.client;

import at.petrak.hexcasting.api.mod.HexConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.gui.GuiBook;

@Mixin(value = GuiBook.class, remap = false)
public abstract class MixinGuiBook {
    @Shadow
    abstract void changePage(boolean left, boolean sfx);

    @Inject(method = "mouseScrolled", at = @At("HEAD"), cancellable = true)
    private void hex$mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY,
        CallbackInfoReturnable<Boolean> cir) {
        var clientConfig = HexConfig.client();
        if (clientConfig == null || !clientConfig.scrollInPatchouliBooksTurnsPages()) {
            return;
        }

        if (scrollY > 0.0) {
            changePage(true, true);
            cir.setReturnValue(true);
        } else if (scrollY < 0.0) {
            changePage(false, true);
            cir.setReturnValue(true);
        }
    }
}
