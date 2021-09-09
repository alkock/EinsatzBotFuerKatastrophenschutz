package ffeppdeinsatzbotv2;


import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import Personen.Einsatzkraft;

public class MainClass {
	
	public static void main(String[] args) {
		System.out.println("Starte.....");
    	ApiContextInitializer.init();
    	
    	System.out.println("Lese Einsatzkräfte ein....");
    	Map <Long, Einsatzkraft> test = Initialisierer.ladeEinsatzkraefte();
    	System.out.println("Konvertiere....");
    	//TODO String zu Long
    	
		Properties login = new Properties();
		try (FileReader in = new FileReader("bot.properties")) {
		    login.load(in);
		}
		catch(IOException IOE)
		{
			System.out.println("Fehler beim Laden vom Benutzernamen/Token");
		}
		String name = login.getProperty("name");
		String token = login.getProperty("token");
		    	
    	 try {
             TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
             telegramBotsApi.registerBot(new EinsatzRueckmeldungBot(name, token, new Key(), test));
         } catch (TelegramApiException e) {
             e.printStackTrace();
         }
         System.out.println("Bot erfolgreich gestartet!");

	}

}
