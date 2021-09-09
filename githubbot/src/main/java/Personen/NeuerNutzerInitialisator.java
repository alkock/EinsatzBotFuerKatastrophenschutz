package Personen;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import ffeppdeinsatzbotv2.Key;

public class NeuerNutzerInitialisator {
	
	long _userid;
	public String _name;
	public String _key;
	
	public NeuerNutzerInitialisator(long userID)
	{
		_userid = userID;
		_key = null;
	}
	
	public SendMessage erstelleWillkommensnachricht()
	{
		return new SendMessage(_userid, "⚠️ Dies ist ein privater Bot. Eine Verwendung ist nur erlaubt, wenn dies der Betreiber (@ansgarkock) ausdrücklich erlaubt hat. Bitte gebe nun dein Passwort ein um fortzufahren.");
	}
	
	public boolean ueberpruefeEingabe(String nachricht)
	{
		return Key.ueberpruefeBerechtigung(nachricht);
	}
	
	public SendMessage keineBerechtigungNachricht()
	{
		return new SendMessage(_userid, "❌ Dieser Schlüssel ist falsch! Bitte erneut versuchen!");
	}
	
	public SendMessage erfolgreich()
	{
		return new SendMessage(_userid, "✅ Das Passwort ist korrekt. Du wirst nun in das System eingepflegt...");
		
	}
	
	public SendMessage gibName()
	{
		return new SendMessage(_userid, "Bitte gebe nun dein Namen in folgendenem Format an: Ansgar K.");
	}
	
	public long getuserID()
	{
		return _userid;
	}
}
