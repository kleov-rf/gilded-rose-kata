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

            if (isSpecialItem(item)) {
                if (item.quality < 50) {
                    increaseQualityBy1(item);
                }
                if (item.quality < 50 && isBackstagePass(item)) {
                    if (item.sellIn < 11) {
                        increaseQualityBy1(item);
                    }
                    if (item.sellIn < 6) {
                        increaseQualityBy1(item);
                    }
                }
            }

            if (!isSpecialItem(item) && item.quality > 0) {
                decreaseQualityBy1(item);
            }

            item.sellIn = item.sellIn - 1;

            if (item.sellIn >= 0) continue;

            if (isAgedBrie(item) && item.quality < 50) {
                increaseQualityBy1(item);
            }

            if (isBackstagePass(item)) {
                item.quality = 0;
            }

            if (!isSpecialItem(item) && item.quality > 0) {
                decreaseQualityBy1(item);
            }
        }
    }

    private static boolean isSpecialItem(Item item) {
        return isAgedBrie(item) || isBackstagePass(item);
    }

    private static boolean isBackstagePass(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private static boolean isAgedBrie(Item item) {
        return item.name.equals("Aged Brie");
    }

    private static void decreaseQualityBy1(Item item) {
        item.quality = item.quality - 1;
    }

    private static void increaseQualityBy1(Item item) {
        item.quality = item.quality + 1;
    }
}
