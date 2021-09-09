package Personen;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ffeppdeinsatzbotv2.EinsatzRueckmeldungBot;

public class EinsatzkraftImpl extends Einsatzkraft {

	
	public EinsatzkraftImpl(long userid, String name)
	{
		super(name, userid, 'E');
	}
	
	@Override
	public SendMessage zeigeMenu() {
		return statusGeber("Menü:");
		
	}
	
	public SendMessage statusGeber(String stichwort) //TODO Stichwort vom Einsatz übernehmen!
	{
		SendMessage message = new SendMessage().setChatId(_userid).setText(stichwort);
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row = new KeyboardRow();
		// Set each button, you can also use KeyboardButton objects if you need something else than text
		row.add("Unter 10 Minuten");
		row.add("Unter 15 Minuten");
		row.add("Über 15 Minuten");
		// Add the first row to the keyboard
		keyboard.add(row);
		// Create another keyboard row
		row = new KeyboardRow();
		// Set each button for the second line
		row.add("Über 45 Minuten");
		row.add("Außer Dienst");
		row.add("Bitte Klamotten mitnehmen, komme direkt!");
		// Add the second row to the keyboard
		keyboard.add(row);
		// Set the keyboard to the markup
		keyboardMarkup.setKeyboard(keyboard);
		// Add it to the message
		message.setReplyMarkup(keyboardMarkup);
		
		return message;
	}

	@Override
	public void reagiereAufAlarm(String stichwort, EinsatzRueckmeldungBot nachrichtenSender) {
		nachrichtenSender.nachrichtAusfuehren(statusGeber(stichwort));
	
	}
	
	

}
