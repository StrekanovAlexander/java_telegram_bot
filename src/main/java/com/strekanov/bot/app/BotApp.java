package com.strekanov.bot.app;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static com.strekanov.bot.app.BotUtils.*;

public class BotApp extends TelegramLongPollingBot {

    private Map<Long, Integer> levels = new HashMap<>();

    @Override
    public String getBotUsername() {
        return BotSettings.botUserName;
    }

    @Override
    public String getBotToken() {
        return BotSettings.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        Long chatId = getChatId(update);

        if (isStart(update)) {
            setLevel(chatId, 1);

            SendMessage message = createMessage("Press the *button*");
            message.setChatId(chatId);
            attachButtons(message, Map.of("Level 1", "level_1"));
            sendApiMethodAsync(message);
        }

        if (isCallbackQuery(update)) {
            if (update.getCallbackQuery().getData().equals("level_1") && getLevel(chatId) == 1) {
                setLevel(chatId, 2);

                SendAnimation animation = createAnimation("level-1", chatId);
                executeAsync(animation);

                SendMessage message = createMessage("Welcome to *level 1*");
                attachButtons(message, Map.of("Level 2", "level_2"));
                message.setChatId(chatId);
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("level_2") && getLevel(chatId) == 2) {
                setLevel(chatId, 3);

                SendAnimation animation = createAnimation("level-2", chatId);
                executeAsync(animation);

                SendMessage message = createMessage("Welcome to *level 2*");
                attachButtons(message, Map.of("Level 3", "level_3"));
                message.setChatId(chatId);
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("level_3") && getLevel(chatId) == 3) {

                SendAnimation animation = createAnimation("level-3", chatId);
                executeAsync(animation);

                SendMessage message = createMessage("Welcome to final *level 3*");
                message.setChatId(chatId);
                sendApiMethodAsync(message);
            }

        }

    }

    public int getLevel(Long chatId) {
        return levels.getOrDefault(chatId, 1);
    }

    public void setLevel(Long chatId, int level) {
        levels.put(chatId, level);
    }

}
