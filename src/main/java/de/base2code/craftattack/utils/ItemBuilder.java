package de.base2code.craftattack.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

public class ItemBuilder {

    private ItemStack is;

    private ItemBuilder(Material m) {
        is = new ItemStack(m);
    }
    private ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount, (short)1);
    }
    private ItemBuilder(Material m, int amount, short durability) {
        is = new ItemStack(m, amount, durability);
    }
    private ItemBuilder(Material m, short durability) {
        is = new ItemStack(m, 1, durability);
    }

    public static ItemBuilder of(Material m){
        return new ItemBuilder(m);
    }
    public static ItemBuilder of(Material m, int amount){
        return new ItemBuilder(m, amount);
    }
    public static ItemBuilder of(Material m, int amount, short durability){
        return new ItemBuilder(m, amount, durability);
    }
    public static ItemBuilder of(Material m, short durability){
        return new ItemBuilder(m, durability);
    }

    public ItemBuilder setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, false);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level, boolean bool) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, bool);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        is.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        ItemMeta im = is.getItemMeta();
        im.setUnbreakable(true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (!lore.contains(line))
            return this;
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size())
            return this;
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemBuilder addFlag(ItemFlag... flag) {
        ItemMeta meta = is.getItemMeta();
        meta.addItemFlags(flag);
        is.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return is;
    }

}

