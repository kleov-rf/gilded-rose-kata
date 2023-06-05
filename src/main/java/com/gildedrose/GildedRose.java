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

            updateQualityBeforeSellingDate(item);

            item.sellIn = item.sellIn - 1;

            if (hasTimeUntilLimitSellingDate(item)) continue;

            updateQualityAfterSellingDate(item);
        }
    }

    private static boolean hasTimeUntilLimitSellingDate(Item item) {
        return item.sellIn >= 0;
    }

    private static void updateQualityBeforeSellingDate(Item item) {
        if (isCommonItem(item)) {
            decreaseQualityBy1(item);
            return;
        }

        increaseQualityBy(item, 1);

        if (isBackstagePass(item)) {
            increaseQualityBy(item, calculateBackstageExtraQuality(item));
        }
    }

    private static void updateQualityAfterSellingDate(Item item) {
        if (isCommonItem(item)) {
            decreaseQualityBy1(item);
            return;
        }

        if (isAgedBrie(item)) {
            increaseQualityBy(item, 1);
        }

        if (isBackstagePass(item)) {
            item.quality = 0;
        }
    }

    private static boolean isCommonItem(Item item) {
        return !isAgedBrie(item) && !isBackstagePass(item);
    }

    private static boolean isBackstagePass(Item item) {
        return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private static boolean isAgedBrie(Item item) {
        return item.name.equals("Aged Brie");
    }

    private static void decreaseQualityBy1(Item item) {
        item.quality = Math.max(item.quality - 1, 0);
    }

    private static void increaseQualityBy(Item item, int amount) {
        item.quality = Math.min(item.quality + amount, 50);
    }

    private static int calculateBackstageExtraQuality(Item item) {
        int extraPoints = 0;
        if (item.sellIn <= 10) {
            extraPoints++;
        }
        if (item.sellIn <= 5) {
            extraPoints++;
        }
        return extraPoints;
    }
}
