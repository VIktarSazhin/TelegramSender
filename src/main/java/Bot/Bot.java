package Bot;
import Handler.Creator.CreatorPDF;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.*;

public class Bot extends TelegramLongPollingBot {
    private static String SAZHIN_VIKTOR_ID="503930439";
    private static String ALEX_FROST_ID="737444773";
    private static String RED_TEAM_ID="-610071476";
    private static String SERGEY_PERETYAGIN_ID="1399019417";
//    private static String YURII_ID="";
    private static String [] redTeam = {SERGEY_PERETYAGIN_ID};


    public Bot(DefaultBotOptions defaultBotOptions) {
    }

    @Override
    public String getBotUsername() {
        return "@wodnyi_bot";
    }
    @Override
    public String getBotToken() {
        return "5228774882:AAE8fruDZH1uL0in-yHk-ojQkdGrePT47F4";
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

//    public static void main(String[] args) throws TelegramApiException {
    public static void start() throws TelegramApiException {
        Bot bot = new Bot(new DefaultBotOptions());
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(bot);
        File file = CreatorPDF.createPDF();
        InputFile inputFile = new InputFile(file);
        for (String str:
                redTeam) {
            bot.execute(SendDocument.builder().chatId(str).document(inputFile).build());
        }
//        bot.execute(SendDocument.builder().chatId("1399019417").document(inputFile).build());
    }
}
