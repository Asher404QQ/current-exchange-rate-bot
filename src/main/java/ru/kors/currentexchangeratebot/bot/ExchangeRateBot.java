package ru.kors.currentexchangeratebot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kors.currentexchangeratebot.exception.ServiceException;
import ru.kors.currentexchangeratebot.service.ExchangeRatesService;

import java.time.LocalDate;

@Component
public class ExchangeRateBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateBot.class);
    private static final String START = "/start";
    private static final String HELP = "/help";
    private static final String USD = "/usd";
    private static final String EUR = "/eur";
    private static final String AMD = "/amd";
    private static final String CNY = "/cny";
    private static final String KZT = "/kzt";
    private static final String TRY = "/try";
    @Autowired
    private ExchangeRatesService exchangeRatesService;

    public ExchangeRateBot(@Value("${bot.token}") String token) {
        super(token);
    }

    @Override
    public String getBotUsername() {
        return "your telegram bot username";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            logger.error("Нет сообщения");
            return;
        }
        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        switch (message) {
            case START -> {
                String username = update.getMessage().getChat().getUserName();
                startCommand(chatId, username);
            }
            case USD -> usdCommand(chatId);
            case EUR -> eurCommand(chatId);
            case AMD -> amdCommand(chatId);
            case CNY -> cnyCommand(chatId);
            case KZT -> kztCommand(chatId);
            case TRY -> tryCommand(chatId);
            case HELP -> helpCommand(chatId);
            default -> unknownCommand(chatId);
        }
    }


    private void startCommand(Long chatId, String username) {
        var text = """
                Привет, %s!
                
                Что может сделать этот бот?
                
                Current Exchange Rate может узнать офицальный курс валюты на сегодняшний день от Центрального Банка России.
                
                Для этого воспользуйтесь командами:
                /usd - курс доллара
                /eur - курс евро
                /amd - курс армянских драм
                /cny - курс китайского юаня
                /kzt - курс казахстанских тенге
                /try - курс турецких лир
                
                Дополнительные команды:
                /help - помощь
                """;
        var formattedText = String.format(text, username);
        sendMessage(chatId, formattedText);
    }

    private void amdCommand(Long chatId) {
        String formattedText;
        try {
            var amd = exchangeRatesService.getAMDExchangeRate();
            var text = "Курс 100 армянских драм на %s равен %s руб.";
            formattedText = String.format(text, LocalDate.now(), amd);
        } catch (ServiceException e) {
            logger.error("Ошибка при получении курса армянского драма", e);
            formattedText = "Ошибка при получении курса армянского драма, попробуйте позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void eurCommand(Long chatId) {
        String formattedText;
        try {
            var eur = exchangeRatesService.getEURExchangeRate();
            var text = "Курс евро на %s равен %s руб.";
            formattedText = String.format(text, LocalDate.now(), eur);
        } catch (ServiceException e) {
            logger.error("Ошибка при получении курса евро", e);
            formattedText = "Ошибка при получении курса евро, попробуйте позже";
        }
        sendMessage(chatId, formattedText);
    }
    private void usdCommand(Long chatId) {
        String formattedText;
        try {
            var usd = exchangeRatesService.getUSDExchangeRate();
            var text = "Курс доллара на %s равен %s руб.";
            formattedText = String.format(text, LocalDate.now(), usd);
        } catch (ServiceException e) {
            logger.error("Ошибка при получении курса доллара", e);
            formattedText = "Ошибка при получении курса доллара, попробуйте позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void cnyCommand(Long chatId) {
        String formattedText;
        try {
            var cny = exchangeRatesService.getCNYExchangeRate();
            var text = "Курс китайского юаня на %s равен %s руб.";
            formattedText = String.format(text, LocalDate.now(), cny);
        } catch (ServiceException e) {
            logger.error("Ошибка при получении курса китайского юаня", e);
            formattedText = "Ошибка при получении курса китайского юаня, попробуйте позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void kztCommand(Long chatId) {
        String formattedText;
        try {
            var kzt = exchangeRatesService.getKZTExchangeRate();
            var text = "Курс 100 казахстанских тенге на %s равен %s руб.";
            formattedText = String.format(text, LocalDate.now(), kzt);
        } catch (ServiceException e) {
            logger.error("Ошибка при получении курса казахстанских тенге", e);
            formattedText = "Ошибка при получении курса казахстанских тенге, попробуйте позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void tryCommand(Long chatId) {
        String formattedText;
        try {
            var try1 = exchangeRatesService.getTRYExchangeRate();
            var text = "Курс 10 турецких лир на %s равен %s руб.";
            formattedText = String.format(text, LocalDate.now(), try1);
        } catch (ServiceException e) {
            logger.error("Ошибка при получении курса турецких лир", e);
            formattedText = "Ошибка при получении курса турецких лир, попробуйте позже";
        }
        sendMessage(chatId, formattedText);
    }

    private void helpCommand(Long chatId) {
        var text = """
                Чтобы получить офицальный курс валюты на сегодняшний день от Центрального Банка России
                
                Воспользуйтесь командами:
                /usd - курс доллара
                /eur - курс евро
                /amd - курс армянского драма
                /cny - курс китайского юаня
                /kzt - курс казахстанских тенге
                /try - курс турецких лир
                """;
        sendMessage(chatId, text);
    }

    private void unknownCommand(Long chatId) {
        var text = "Неизвестная команда";
        sendMessage(chatId, text);
    }

    private void sendMessage(Long chatId, String message) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Ошибка при отправке сообщения", e);
        }
    }
}
