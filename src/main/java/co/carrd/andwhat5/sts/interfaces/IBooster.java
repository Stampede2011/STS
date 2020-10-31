package co.carrd.andwhat5.sts.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface IBooster {
    int getMoney(NBTTagCompound paramNBTTagCompound);

    String getItemLore();
}
