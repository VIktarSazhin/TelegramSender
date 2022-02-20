package Bot;

import Service.PDFConvertor;
import TimeRepeater.TimeRepeater;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.*;

public class Bot extends TelegramLongPollingBot {

    private static String BOT_TOKEN = "5145412598:AAHikpj4anWuxM4g4nPped7AOyuZQPEdyJo";
    private static String BOT_NAME = "@RedCommandBot";
    //String name = "1399019417";
//    private static List<String> chatIdList = new ArrayList<>();
    private static Set<String> chatIdList = new HashSet<>();

    public Bot(DefaultBotOptions defaultBotOptions) {
    }


    public static void start() throws TelegramApiException {
        Bot bot = new Bot(new DefaultBotOptions());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        TimeRepeater.startTimer();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()){
            try {
                handleCallback(update.getCallbackQuery());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        Message message = update.getMessage();
        if (message.getText().equals("/start")){
            helloMessage(message);
        }
        createButtons(message);
    }

    public void createButtons(Message message){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Да, давай его сюда");
        inlineKeyboardButton1.setCallbackData("Yes");
        System.out.println(inlineKeyboardButton1.getCallbackData());
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Не, я подужду 17:30");
        inlineKeyboardButton2.setCallbackData("No");
        List<InlineKeyboardButton>buttons=new ArrayList<>();
        buttons.add(inlineKeyboardButton1);
        buttons.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>>lists=new ArrayList<>();
        lists.add(buttons);
        inlineKeyboardMarkup.setKeyboard(lists);
        try{
            execute(SendMessage.builder().text("Хочешь получить отчет прямо сейчас").chatId(message.getChatId().toString())
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(lists).build()).build());
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    private void handleCallback(CallbackQuery callbackQuery) throws TelegramApiException {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        if (callbackQuery.getData().equals("Yes")) {
            execute(SendMessage.builder().chatId(chatId).text("Сейчас получишь").build());
            PDFConvertor pdfConvertor = new PDFConvertor();
            File file = pdfConvertor.createPDF();
//            File file = new File("/opt/tomcat/latest/webapps/newbot/source/newPDF.pdf");
//            InputFile inputFile = new InputFile(file);
//            execute(SendDocument.builder().chatId(chatId).document(inputFile).build());
            if (file.isFile())
                sendFile(file);
        }
        if (callbackQuery.getData().equals("No")) {
            execute(SendMessage.builder().chatId(chatId).text("Правильно, лучше пусть само придет").build());
        }
    }
    public void helloMessage(Message message){
        try {
            execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Добро пожаловать в красную команду "+
                    message.getChat().getFirstName()+"!").build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
//        if (!chatIdList.contains(message.getChatId().toString())){
//            chatIdList.add(message.getChatId().toString());
//        }
        chatIdList.add(message.getChatId().toString());
    }

    public void sendFile(File file) throws TelegramApiException {
        InputFile inputFile = new InputFile(file);
        for (String str:
                chatIdList) {
            execute(SendDocument.builder().chatId(str).document(inputFile).build());
        }

    }


}
