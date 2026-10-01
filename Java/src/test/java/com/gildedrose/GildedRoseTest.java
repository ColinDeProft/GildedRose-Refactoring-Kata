package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    private Item updateSingleItem(String name, int sellIn, int quality) {
        Item[] items = new Item[]{ new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return items[0];
    }

    @Nested
    @DisplayName("Normal Items")
    class NormalItems {

        @Test
        @DisplayName("Quality and sellIn decrease by 1 before sell-by date")
        void standardDegradation() {
            Item item = updateSingleItem("+5 Dexterity Vest", 10, 20);
            assertThat(item.sellIn).isEqualTo(9);
            assertThat(item.quality).isEqualTo(19);
        }

        @Test
        @DisplayName("Quality degrades twice as fast after sell-by date passed")
        void degradationAfterSellByDate() {
            Item item = updateSingleItem("+5 Dexterity Vest", 0, 20);
            assertThat(item.sellIn).isEqualTo(-1);
            assertThat(item.quality).isEqualTo(18);
        }

        @Test
        @DisplayName("Quality is never negative")
        void qualityNeverNegative() {
            Item item = updateSingleItem("+5 Dexterity Vest", 5, 0);
            assertThat(item.quality).isZero();
        }
    }

    @Nested
    @DisplayName("Aged Brie")
    class AgedBrie {

        @Test
        @DisplayName("Quality increases by 1 before sell-by date")
        void increasesInQuality() {
            Item item = updateSingleItem("Aged Brie", 5, 10);
            assertThat(item.sellIn).isEqualTo(4);
            assertThat(item.quality).isEqualTo(11);
        }

        @Test
        @DisplayName("Quality increases twice as fast after sell-by date")
        void increasesDoubleQualityAfterSellByDate() {
            Item item = updateSingleItem("Aged Brie", 0, 10);
            assertThat(item.sellIn).isEqualTo(-1);
            assertThat(item.quality).isEqualTo(12);
        }

        @Test
        @DisplayName("Quality is capped at 50")
        void qualityCappedAtFifty() {
            Item item = updateSingleItem("Aged Brie", 5, 50);
            assertThat(item.quality).isEqualTo(50);
        }
    }

    @Nested
    @DisplayName("Sulfuras, Hand of Ragnaros")
    class Sulfuras {

        @Test
        @DisplayName("Never decreases in quality (fixed at 80) nor has to be sold")
        void sulfurasNeverChanges() {
            Item item = updateSingleItem("Sulfuras, Hand of Ragnaros", 10, 80);
            assertThat(item.sellIn).isEqualTo(10);
            assertThat(item.quality).isEqualTo(80);
        }
    }

    @Nested
    @DisplayName("Backstage Passes")
    class BackstagePasses {

        @ParameterizedTest(name = "sellIn={0}, initialQuality={1} -> expectedQuality={2}")
        @CsvSource({
            "15, 20, 21", // > 10 days: +1
            "10, 20, 22", // <= 10 days: +2
            "5,  20, 23", // <= 5 days: +3
            "0,  20, 0"   // <= 0 days (after concert): drops to 0
        })
        @DisplayName("Quality increases based on remaining sellIn days")
        void testBackstagePasses(int sellIn, int initialQuality, int expectedQuality) {
            Item item = updateSingleItem("Backstage passes to a TAFKAL80ETC concert", sellIn, initialQuality);
            assertThat(item.quality).isEqualTo(expectedQuality);
        }

        @Test
        @DisplayName("Quality is capped at 50 even when approaching concert")
        void backstagePassesQualityCappedAtFifty() {
            Item item = updateSingleItem("Backstage passes to a TAFKAL80ETC concert", 5, 49);
            assertThat(item.quality).isEqualTo(50);
        }
    }

    @Nested
    @DisplayName("Conjured Items")
    class ConjuredItems {

        @Test
        @DisplayName("Quality degrades twice as fast as normal items before sell-by date (-2)")
        void degradesTwiceAsFast() {
            Item item = updateSingleItem("Conjured Mana Cake", 10, 20);
            assertThat(item.sellIn).isEqualTo(9);
            assertThat(item.quality).isEqualTo(18);
        }

        @Test
        @DisplayName("Quality degrades twice as fast as normal items after sell-by date (-4)")
        void degradesFourTimesAsFastAfterSellByDate() {
            Item item = updateSingleItem("Conjured Mana Cake", 0, 20);
            assertThat(item.sellIn).isEqualTo(-1);
            assertThat(item.quality).isEqualTo(16);
        }

        @Test
        @DisplayName("Quality is never negative")
        void conjuredQualityNeverNegative() {
            Item item = updateSingleItem("Conjured Mana Cake", 5, 1);
            assertThat(item.quality).isZero();
        }
    }
}
