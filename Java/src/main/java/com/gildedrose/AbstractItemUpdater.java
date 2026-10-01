package com.gildedrose;

/**
 * Base updater defining common bounds and operational helpers for items.
 */
abstract class AbstractItemUpdater {

    protected static final int MIN_QUALITY = 0;
    protected static final int MAX_QUALITY = 50;

    /**
     * Updates the item's sellIn and quality values for a single day.
     */
    public abstract void update(Item item);

    protected void decreaseSellIn(Item item) {
        item.sellIn--;
    }

    protected void changeQuality(Item item, int delta) {
        item.quality = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, item.quality + delta));
    }
}
