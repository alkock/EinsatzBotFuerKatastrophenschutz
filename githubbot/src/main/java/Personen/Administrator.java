package Personen;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import ffeppdeinsatzbotv2.EinsatzRueckmeldungBot;

public class Administrator extends Einsatzkraft {

    int _menustatus;

	
	public Administrator(long userid, String name)
	{
		super(name, userid, 'A');
		_menustatus = 0;
	}
	
	public SendMessage zeigeMenu(int status)
	{
		_menustatus = status;
		return zeigeMenu();
	}
	
	@Override
	public SendMessage zeigeMenu() {
		switch(_menustatus)
		{
		case 0: return switchTastatur(); 
		case 1: return alarmierungTastatur(); 
		case 2: return statusGeber("Bitte Eingabe vornehmen:"); 
		default: return switchTastatur(); 
		}
		
	}
	
	public void loeseAlarmAus()
	{
		
	}
	
	
	public SendMessage switchTastatur()
	{
		SendMessage message = new SendMessage().setChatId(_userid).setText("Bitte Eingabe vornehmen:");
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();
		row.add("Administration");
		keyboard.add(row);
		row = new KeyboardRow();
		row.add("Rückmeldung geben");
		keyboard.add(row);
		keyboardMarkup.setKeyboard(keyboard);
		message.setReplyMarkup(keyboardMarkup);
		
		return message;
		
	}
	public SendMessage alarmierungTastatur()
	{
		SendMessage message = new SendMessage().setChatId(_userid).setText("Bitte Eingabe vornehmen:");
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();
		// Set each button, you can also use KeyboardButton objects if you need something else than text
		row.add("🕓 Voralarm");
		row.add("📟 Vollalarm");
		// Add the first row to the keyboard
		keyboard.add(row);
		// Create another keyboard row
		row = new KeyboardRow();
		// Set each button for the second line
		row.add("Zurück");
		row.add("❌ Abspannen");
		// Add the second row to the keyboard
		keyboard.add(row);
		// Set the keyboard to the markup
		keyboardMarkup.setKeyboard(keyboard);
		// Add it to the message
		message.setReplyMarkup(keyboardMarkup);
		
		return message;
	}
	
	public SendMessage statusGeber(String stichwort) //TODO Stichwort vom Einsatz übernehmen!
	{
		SendMessage message = new SendMessage().setChatId(_userid).setText(stichwort);
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();
		row.add("Unter 10 Minuten");
		row.add("Unter 15 Minuten");
		row.add("Über 15 Minuten");
		keyboard.add(row);
		row = new KeyboardRow();
		row.add("Zurück");
		row.add("Über 45 Minuten");
		row.add("Außer Dienst");
		row.add("Bitte Klamotten mitnehmen, komme direkt!");
		keyboard.add(row);
		keyboardMarkup.setKeyboard(keyboard);
		message.setReplyMarkup(keyboardMarkup);
		
		return message;
	}

	@Override
	public void reagiereAufAlarm(String stichwort, EinsatzRueckmeldungBot nachrichtenSender) {
		nachrichtenSender.nachrichtAusfuehren(statusGeber(stichwort));
		
	}
}
