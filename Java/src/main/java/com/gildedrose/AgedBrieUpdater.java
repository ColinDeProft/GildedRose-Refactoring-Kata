package com.gildedrose;

class AgedBrieUpdater extends AbstractItemUpdater {
    @Override
    public void update(Item item) {
        decreaseSellIn(item);
        int increase = (item.sellIn < 0) ? 2 : 1;
        changeQuality(item, increase);
    }
}
