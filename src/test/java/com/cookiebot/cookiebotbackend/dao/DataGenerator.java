package com.cookiebot.cookiebotbackend.dao;

import com.cookiebot.cookiebotbackend.core.domains.StickerDatabase;

import java.util.UUID;

public final class DataGenerator {

    public static StickerDatabase generateSticker() {
        final var sticker = new StickerDatabase();
        sticker.setId(UUID.randomUUID().toString());

        return sticker;
    }
}
