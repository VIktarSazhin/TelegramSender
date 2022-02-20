package TimeRepeater;

import Bot.Bot;
import Service.PDFConvertor;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeRepeater {
    public static void startTimer() {
        Calendar startCalendar = Calendar.getInstance();
        long day = 86_400_000L;
        int sec = 100000;

        if(Calendar.getInstance().getTime().getHours()<17){
//       if(Calendar.getInstance().getTime().getHours()<=23 && Calendar.getInstance().getTime().getMinutes()<=57){
            startCalendar.set(Calendar.HOUR_OF_DAY,17);
            startCalendar.set(Calendar.MINUTE,00);
            startCalendar.set(Calendar.SECOND,00);
        }else {
            startCalendar.set(Calendar.DATE,Calendar.getInstance().getTime().getDate()+1);
            startCalendar.set(Calendar.HOUR_OF_DAY,17);
            startCalendar.set(Calendar.MINUTE,00);
            startCalendar.set(Calendar.SECOND,00);
        }
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            File file;
            @Override
            public void run() {
                System.out.println("TimerTask начал свое выполнение в:" + new Date());
                PDFConvertor pdfConvertor = new PDFConvertor();
                file = pdfConvertor.createPDF();
                Bot bot = new Bot(new DefaultBotOptions());
                try {
                    bot.sendFile(file);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                System.out.println("TimerTask закончил свое выполнение в:" + new Date());
            }
        };
        timer.scheduleAtFixedRate(timerTask,startCalendar.getTime(),day );

    }
}
