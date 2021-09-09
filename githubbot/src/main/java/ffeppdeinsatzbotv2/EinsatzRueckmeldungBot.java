package ffeppdeinsatzbotv2;

import java.util.HashMap;
import java.util.Map;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import Personen.Administrator;
import Personen.Einsatzkraft;
import Personen.EinsatzkraftImpl;
import Personen.NeuerNutzerInitialisator;

public class EinsatzRueckmeldungBot extends TelegramLongPollingBot {

	public static Map<Long, Einsatzkraft> _nutzer;
	Map<Long, NeuerNutzerInitialisator> _unberechtigte;
	Key _schluessel;
	private String _name;
	private String _token;
	public static Map<Long, Einsatzkraft> get_nutzer() {
		return _nutzer;
	}

	public static void set_nutzer(Map<Long, Einsatzkraft> _nutzer) {
		EinsatzRueckmeldungBot._nutzer = _nutzer;
	}

	public Map<Long, NeuerNutzerInitialisator> get_unberechtigte() {
		return _unberechtigte;
	}

	public void set_unberechtigte(Map<Long, NeuerNutzerInitialisator> _unberechtigte) {
		this._unberechtigte = _unberechtigte;
	}

	public Key get_schluessel() {
		return _schluessel;
	}

	public void set_schluessel(Key _schluessel) {
		this._schluessel = _schluessel;
	}

	public static long getGruppenid() {
		return _gruppenID;
	}

	public Einsatz _einsatz;
	
	public static final long _gruppenID = 0;
	
	public EinsatzRueckmeldungBot(String name, String token)
	{
		_schluessel = new Key();
		_nutzer = new HashMap<Long, Einsatzkraft>();
		_unberechtigte = new HashMap<Long, NeuerNutzerInitialisator>();
		_einsatz = null;
		_token = token;
		_name = name;
	}
	
	public EinsatzRueckmeldungBot(String name, String token, Key schluessel,  Map<Long, Einsatzkraft> nutzer)
	{
		_schluessel = schluessel;
		_nutzer = nutzer;
		_unberechtigte = new HashMap<Long, NeuerNutzerInitialisator>();
		_einsatz = null;
		_token = token;
		_name = name;
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		long userID = update.getMessage().getChatId();
		String nachricht = update.getMessage().getText();
		Integer messageID = update.getMessage().getMessageId();
		System.out.println("Nutzer: " + userID + " | " + "Nachricht: " + nachricht + " | " + "NachrichtenID: " + messageID);
		
		if(_nutzer.containsKey(userID))
		{
			berechtigteBenutzer(userID, nachricht);
		}
		else if(userID != _gruppenID)
		{
			unbekannteNutzer(userID, nachricht);
		}
	}
	

	private void unbekannteNutzer(long userID, String nachricht) {
		
			NeuerNutzerInitialisator nni;
			
			if(_unberechtigte.containsKey(userID))
			{
				nni = _unberechtigte.get(userID);
			}
			else
			{
				nni = new NeuerNutzerInitialisator(userID);
				nachrichtAusfuehren(nni.erstelleWillkommensnachricht());
			}
			
			if(nni._key != null)
			{
				if(nni._name != null)
				{
					if(nni._key.equals("A"))
					{
						Administrator admin = new Administrator(userID, nni._name);
						_nutzer.put(nni.getuserID(), admin);
						nachrichtAusfuehren(nni.erfolgreich());
						Initialisierer.speichere(admin);
						_unberechtigte.remove(userID);
						nachrichtAusfuehren(admin.zeigeMenu());

					}
					else
					{
						EinsatzkraftImpl ei = new EinsatzkraftImpl(userID, nni._name);
						_nutzer.put(nni.getuserID(), ei);
						nachrichtAusfuehren(nni.erfolgreich());
						Initialisierer.speichere(ei);
						_unberechtigte.remove(userID);
						nachrichtAusfuehren(ei.zeigeMenu());	
					}
				}
				
				
				// FIX: [A-ZÀ-ž]{1}[a-z]+[-]*[A-ZÀ-ž]*[a-z]* [A-ZÀ-ž]{1}.{1}
				// Alter REGEX : [A-Z]{1}[a-z]+ [A-Z]{1}.{1}
				else
				{
					if(nachricht.matches("[A-ZÀ-ž]{1}[a-z]+[-]*[A-ZÀ-ž]*[a-z]* [A-ZÀ-ž]{1}.{1}")) 
					{
						nni._name = nachricht;
						unbekannteNutzer(userID, nachricht);
					}
					else
					{
						nachrichtAusfuehren(new SendMessage(userID, "Achtung bitte beachte das angegebene Format! Format: Vorname und erster Buchstabe des Nachnamens ein (Bsp: Max M. oder Max-Julius R.)"));					}
				}
			}
			
		
			
			else if(nni.ueberpruefeEingabe(nachricht))
				{
					if(Key.gebeBerechtigungFuerSchluessel(nachricht) == 'A')
					{
						nni._key = "A";
					}
					if(Key.gebeBerechtigungFuerSchluessel(nachricht) == 'E')
					{
						nni._key = "E";
					}
					nachrichtAusfuehren(new SendMessage(userID, "Bitte gebe jetzt deinen Namen im Format Vorname und erster Buchstabe des Nachnamens ein (Bsp: Max M.)"));
				}
				else if(_unberechtigte.containsKey(userID))
				{
					nachrichtAusfuehren(nni.keineBerechtigungNachricht());
				}
			
			_unberechtigte.put(userID, nni);
			
		
	}

	private void berechtigteBenutzer(long userID, String nachricht) {
		Einsatzkraft aktuell = _nutzer.get(userID);
		if(aktuell instanceof EinsatzkraftImpl && _einsatz != null)
		{
			_einsatz._statusmelder.put(userID, Status.gebeStatus(nachricht));
			nachrichtAusfuehren(new SendMessage(userID, "Status gesetzt: " + nachricht));
			
		}
		
		if(aktuell instanceof Administrator)
		{
			Administrator adminaktuell = (Administrator)aktuell;
			
			switch(nachricht)
			{
				case "Zurück" : nachrichtAusfuehren(adminaktuell.zeigeMenu(0)); break;
				case "Administration": nachrichtAusfuehren(adminaktuell.zeigeMenu(1)); break;
				case "Rückmeldung geben": nachrichtAusfuehren(adminaktuell.zeigeMenu(2));  break;
			}
			if(_einsatz == null)
			{
				switch(nachricht)
				{
					case "🕓 Voralarm" : _einsatz = new Einsatz("🕓 Voralarm" , this); break;
					case "📟 Vollalarm" : _einsatz = new Einsatz("📟 Vollalarm" , this); break;
					case "❌ Abspannen" :  break;
				}
			}
			if(_einsatz != null)
			{
				switch(nachricht)
				{
					case "🕓 Voralarm" : _einsatz.aendereStichwort("🕓 Voralarm"); ; break;
					case "📟 Vollalarm" : _einsatz.aendereStichwort("📟 Vollalarm"); break;
					case "❌ Abspannen" : _einsatz.abspannen(); _einsatz = null;  break;
				}
			}
			if(Status.istStatus(nachricht))
			{
				_einsatz._statusmelder.put(userID, Status.gebeStatus(nachricht));
				nachrichtAusfuehren(new SendMessage(userID, "Status gesetzt: " + nachricht));
			}
		}
	}

	@Override
	public String getBotUsername() {
		return _name;
	}

	@Override
	public String getBotToken() {
		return _token;
	}

	public  void nachrichtAusfuehren(SendMessage sm)
	{
		try 
		{
			execute(sm);
		}
		catch (TelegramApiException e) 
		{
			e.printStackTrace();
		}
	}
	
	public int nachrichtAusfuehrenmitmessageID(SendMessage sm)
	{
		try {
			return execute(sm).getMessageId();
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void nachrichtAendern(SendMessage sm, int messageId)
	{
		//editTextMessage(messageId, 	"test");
	}
	
	}


