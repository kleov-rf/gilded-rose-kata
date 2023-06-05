package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            boolean isAgedBrie = item.name.equals("Aged Brie");
            boolean isBackstagePass = item.name.equals("Backstage passes to a TAFKAL80ETC concert");
            boolean isSulfuras = item.name.equals("Sulfuras, Hand of Ragnaros");
            
            if (!isAgedBrie
                && !isBackstagePass) {
                if (item.quality > 0) {
                    if (!isSulfuras) {
                        decreaseQualityBy1(item);
                    }
                }
            } else {
                if (item.quality < 50) {
                    increaseQualityBy1(item);
                    if (isBackstagePass) {
                        handleBackstageWithQualityLessThan50(item);
                    }
                }
            }

            if (!isSulfuras) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (!isAgedBrie) {
                    if (!isBackstagePass) {
                        if (item.quality > 0 && !isSulfuras) {
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
    }

    private static void decreaseQualityBy1(Item item) {
        item.quality = item.quality - 1;
    }

    private static void handleBackstageWithQualityLessThan50(Item item) {
        if (item.quality < 50) {
            if (item.sellIn < 11) {
                increaseQualityBy1(item);
            }
            if (item.sellIn < 6) {
                increaseQualityBy1(item);
            }
        }
    }

    private static void increaseQualityBy1(Item item) {
        item.quality = item.quality + 1;
    }
}
