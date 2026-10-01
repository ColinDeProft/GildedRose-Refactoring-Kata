package com.gildedrose;

class NormalItemUpdater extends AbstractItemUpdater {
    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        int degradation = (item.sellIn < 0) ? -2 : -1;
        changeQuality(item, degradation);
    }
}
