package com.gildedrose;

class BackstagePassUpdater extends AbstractItemUpdater {
    @Override
    public void update(Item item) {
        decreaseSellIn(item);

        if (item.sellIn < 0) {
            item.quality = MIN_QUALITY;
            return;
        }

        if (item.sellIn < 5) {
            changeQuality(item, 3);
        } else if (item.sellIn < 10) {
            changeQuality(item, 2);
        } else {
            changeQuality(item, 1);
        }
    }
}
