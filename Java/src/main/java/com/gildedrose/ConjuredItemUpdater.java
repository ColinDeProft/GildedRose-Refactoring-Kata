package com.gildedrose;

class ConjuredItemUpdater extends AbstractItemUpdater {
    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        int degradation = (item.sellIn < 0) ? -4 : -2;
        changeQuality(item, degradation);
    }
}
