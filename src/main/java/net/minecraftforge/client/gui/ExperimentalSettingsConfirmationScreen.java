package net.minecraftforge.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

//Forge internal.
public class ExperimentalSettingsConfirmationScreen extends ConfirmScreen
{
    private CheckboxButton checkbox;
    private IBidiRenderer checkBoxText;

    public ExperimentalSettingsConfirmationScreen(BooleanConsumer callbackFunction, ITextComponent title, ITextComponent messageLine2, ITextComponent confirmText, ITextComponent cancelText)
    {
        super(callbackFunction, title, messageLine2, confirmText, cancelText);
    }

    @Override
    protected void func_231160_c_()
    {
        super.func_231160_c_();
        this.field_230710_m_.remove(0);
        this.field_230705_e_.remove(0);
        //remove and re-add the same button but with an extra on click action.
        this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 155, this.field_230709_l_ / 6 + 96, 150, 20, this.confirmButtonText, (button) ->
        {
            if(checkbox.isChecked())
            {
                this.getMinecraft().gameSettings.confirmExperimentalSettingsPermanently = true;
                this.getMinecraft().gameSettings.saveOptions();
            }
            this.callbackFunction.accept(true);
        }));

        checkBoxText = IBidiRenderer.func_243258_a(this.field_230712_o_, new TranslationTextComponent("forge.confirmgui.checkbox"), this.field_230708_k_ - 50);
        this.checkbox = new CheckboxButton(this.field_230708_k_ / 2 - 10, this.field_230709_l_ / 6 + 156, 150, 20, ITextComponent.func_244388_a(null), false, false);
        this.func_230480_a_(checkbox);
    }

    @Override
    public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_)
    {
        super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.checkBoxText.func_241863_a(p_230430_1_, this.field_230708_k_ / 2, 185);
    }
}
