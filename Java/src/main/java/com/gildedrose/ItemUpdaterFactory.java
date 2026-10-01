package com.gildedrose;


public class ItemUpdaterFactory {

    protected static AbstractItemUpdater getUpdater(Item item) {
        if (item == null || item.name == null) {
            return new NormalItemUpdater();
        }

        if (item.name.equalsIgnoreCase("Aged Brie")) {
            return new AgedBrieUpdater();
        }
        if (item.name.startsWith("Sulfuras")) {
            return new SulfurasUpdater();
        }
        if (item.name.startsWith("Backstage passes")) {
            return new BackstagePassUpdater();
        }

        return new NormalItemUpdater();
    }
}
