package com.gildedrose;

public class GildedRose {
    public Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItem(item);
        }
    }

    private void updateItem(Item item) {
        if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
            return; // sellIn and quality never change
        }

        switch (item.name) {
            case "Aged Brie":
                updateAgedBrie(item);
                break;
            case "Backstage passes to a TAFKAL80ETC concert":
                updateBackstagePasses(item);
                break;
            default:
                updateNormalItem(item);
                break;
        }
    }

    private void updateAgedBrie(Item item) {
        item.sellIn--;
        int delta = (item.sellIn < 0) ? 2 : 1;
        changeQuality(item, delta);
    }

    private void updateBackstagePasses(Item item) {
        item.sellIn--;
        if (item.sellIn < 0) {
            item.quality = 0;
        } else if (item.sellIn < 5) {
            changeQuality(item, 3);
        } else if (item.sellIn < 10) {
            changeQuality(item, 2);
        } else {
            changeQuality(item, 1);
        }
    }

    private void updateNormalItem(Item item) {
        item.sellIn--;
        int delta = (item.sellIn < 0) ? -2 : -1;
        changeQuality(item, delta);
    }

    private void changeQuality(Item item, int delta) {
        item.quality = Math.max(0, Math.min(50, item.quality + delta));
    }
}
