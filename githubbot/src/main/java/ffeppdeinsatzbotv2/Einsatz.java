package ffeppdeinsatzbotv2;

import java.util.HashMap;
import java.util.Map;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import Personen.Einsatzkraft;

public class Einsatz {

	public String _stichwort;
	Map<Long, Status> _statusmelder;
	EinsatzRueckmeldungBot _executer;
	ChatBotGruppe _cbg;
	
	public Einsatz(String stichwort, EinsatzRueckmeldungBot executer)
	{
		_stichwort = stichwort;
		_statusmelder = new HashMap<Long, Status>();
		_executer = executer;
		loeseErstmalsAus();
	}
	
	private void loeseErstmalsAus()
	{
		for(long e: EinsatzRueckmeldungBot._nutzer.keySet())
		{
			Einsatzkraft einsatzkraft = EinsatzRueckmeldungBot._nutzer.get(e);
			einsatzkraft.reagiereAufAlarm(_stichwort, _executer);
		}
			ChatBotGruppe _cbg = new ChatBotGruppe(_executer,this);
			_cbg.start();
		
	}
	
	
	public void abspannen()
	{
		_executer.nachrichtAusfuehren(new SendMessage(EinsatzRueckmeldungBot._gruppenID, "❌ Abspannen"));
		for(long e: EinsatzRueckmeldungBot._nutzer.keySet())
		{
			_executer.nachrichtAusfuehren(new SendMessage(e, "❌ Abspannen"));
		}
		
	}
	
	public void aendereStichwort(String stichwort)
	{
		_stichwort = stichwort;
	}
	
}
