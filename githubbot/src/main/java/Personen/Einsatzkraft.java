package Personen;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ffeppdeinsatzbotv2.EinsatzRueckmeldungBot;

public abstract class Einsatzkraft {
	
	public String _name;
	public long _userid;
	public char _berechtigung;
	
	public String get_name() {
		return _name;
	}


	public void set_name(String _name) {
		this._name = _name;
	}


	public long get_userid() {
		return _userid;
	}


	public void set_userid(long _userid) {
		this._userid = _userid;
	}


	public char get_berechtigung() {
		return _berechtigung;
	}


	public void set_berechtigung(char _berechtigung) {
		this._berechtigung = _berechtigung;
	}


	public Einsatzkraft(String name, long userid, char berechtigung)
	{
		_name = name;
		_userid = userid;
		_berechtigung = berechtigung;
	}

	
	public abstract SendMessage zeigeMenu();
	public abstract void reagiereAufAlarm(String stichwort, EinsatzRueckmeldungBot nachrichtenSender);
	
	//TODO doppelten Code hierin auslagern.
}
