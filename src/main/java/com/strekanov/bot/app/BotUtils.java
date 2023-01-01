package com.strekanov.bot.app;

import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BotUtils {
    public static Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    public static SendMessage createMessage(String text) {

        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");

        return message;
    }

    public static boolean isStart(Update update) {
        return update.hasMessage() && update.getMessage().getText().equals("/start");
    }

    public static boolean isCallbackQuery(Update update) {
        return update.hasCallbackQuery();
    }

    public static void attachButtons(SendMessage message, Map<String, String> buttons) {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String buttonName: buttons.keySet()) {

            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);

            keyboard.add(Arrays.asList(button));

        }

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

    }

    public static SendAnimation createAnimation(String name, Long chatId) {

        SendAnimation animation = new SendAnimation();

        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File("images/" + name + ".png"));

        animation.setAnimation(inputFile);
        animation.setChatId(chatId);

        return animation;

//        org.telegram.telegrambots.bots.executeAsync(animation);

    }



}
