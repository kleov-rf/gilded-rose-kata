package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            boolean isSulfuras = item.name.equals("Sulfuras, Hand of Ragnaros");
            if (isSulfuras) continue;

            boolean isAgedBrie = item.name.equals("Aged Brie");
            boolean isBackstagePass = item.name.equals("Backstage passes to a TAFKAL80ETC concert");

            if (isAgedBrie || isBackstagePass) {
                if (item.quality < 50) {
                    increaseQualityBy1(item);
                }
                if (item.quality < 50 && isBackstagePass) {
                    if (item.sellIn < 11) {
                        increaseQualityBy1(item);
                    }
                    if (item.sellIn < 6) {
                        increaseQualityBy1(item);
                    }
                }
            } else {
                if (item.quality > 0) {
                    decreaseQualityBy1(item);
                }
            }

            item.sellIn = item.sellIn - 1;

            if (item.sellIn >= 0) continue;

            if (!isAgedBrie) {
                if (!isBackstagePass) {
                    if (item.quality > 0) {
                        decreaseQualityBy1(item);
                    }
                } else {
                    item.quality = 0;
                }
            } else {
                if (item.quality < 50) {
                    increaseQualityBy1(item);
                }
            }
        }
    }

    private static void decreaseQualityBy1(Item item) {
        item.quality = item.quality - 1;
    }

    private static void increaseQualityBy1(Item item) {
        item.quality = item.quality + 1;
    }
}
