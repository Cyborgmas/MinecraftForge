/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated //Forge internal.
public class ExperimentalSettingsConfirmationScreen extends ConfirmScreen
{
    private CheckboxButton checkbox;
    private IBidiRenderer checkBoxText;

    public ExperimentalSettingsConfirmationScreen(BooleanConsumer callbackFunction, ITextComponent title, ITextComponent messageLine2, ITextComponent confirmText, ITextComponent cancelText)
    {
        super(callbackFunction, title, messageLine2, confirmText, cancelText);
    }

    @Override
    protected void init()
    {
        super.init();
        this.buttons.remove(0);
        this.children.remove(0);
        //remove and re-add the same button but with an extra on click action.
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 96, 150, 20, this.confirmButtonText, (button) ->
        {
            if(checkbox.isChecked())
            {
                this.getMinecraft().gameSettings.confirmExperimentalSettingsPermanently = true;
                this.getMinecraft().gameSettings.saveOptions();
            }
            this.callbackFunction.accept(true);
        }));

        checkBoxText = IBidiRenderer.func_243258_a(this.font, new TranslationTextComponent("forge.confirmgui.checkbox"), this.width - 50);
        this.checkbox = new CheckboxButton(this.width / 2 - 10, this.height / 6 + 156, 150, 20, StringTextComponent.EMPTY,false, false);
        this.addButton(checkbox);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_)
    {
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.checkBoxText.func_241863_a(p_230430_1_, this.width / 2, this.height / 6 + 131);
    }
}
