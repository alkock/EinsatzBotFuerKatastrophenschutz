package ffeppdeinsatzbotv2;

import java.util.Comparator;
import java.util.LinkedList;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import Personen.Einsatzkraft;

public class ChatBotGruppe extends Thread{

	EinsatzRueckmeldungBot _nachrichtenSender;
	Einsatz _einsatz; 
	int _letzteNachricht;
	
	public ChatBotGruppe(EinsatzRueckmeldungBot nachrichtenSender, Einsatz einsatz)
	{
		_einsatz = einsatz;
		_nachrichtenSender = nachrichtenSender;
		_letzteNachricht = 0;
	}
	
	@Override
	public void run() {
		
		while(_nachrichtenSender._einsatz != null)
		{
			String u10 = "";
			String u15 = "";
			String ü15 = "";
			String ü45 = "";
			int aD = 0;
			String klamotten = Status.gebeString(Status.klamotten) + "\n" ;

			LinkedList<String> zuSortieren = new LinkedList<String>();
			
			for(long userID : _einsatz._statusmelder.keySet())
			{
				zuSortieren.add(EinsatzRueckmeldungBot._nutzer.get(userID)._name);
			}
			Comparator<String> c = Comparator.comparing(String::toString);
			
			zuSortieren.sort(c);

			while(!zuSortieren.isEmpty())
			{
				for(long userID : _einsatz._statusmelder.keySet())
				{
					if(EinsatzRueckmeldungBot._nutzer.get(userID).get_name().equals(zuSortieren.get(1)))
					{
						Status status = _einsatz._statusmelder.get(userID);
						Einsatzkraft einsatzkraft = EinsatzRueckmeldungBot._nutzer.get(userID);
						
						switch(status)
						{
							case Unter10: u10 += einsatzkraft._name + "\n"; break;
							case Unter15: u15 += einsatzkraft._name + "\n"; break;
							case Über15: ü15 += einsatzkraft._name + "\n"; break;
							case Über45: ü45 += einsatzkraft._name + "\n"; break;
							case aD: ++ aD; break;
							case klamotten: klamotten += "" + einsatzkraft._name + "\n"; break;
						}
					}
				}
			}
			
			
			for(long userID : _einsatz._statusmelder.keySet())
			{
				Status status = _einsatz._statusmelder.get(userID);
				Einsatzkraft einsatzkraft = EinsatzRueckmeldungBot._nutzer.get(userID);
				
				switch(status)
				{
					case Unter10: u10 += einsatzkraft._name + "\n"; break;
					case Unter15: u15 += einsatzkraft._name + "\n"; break;
					case Über15: ü15 += einsatzkraft._name + "\n"; break;
					case Über45: ü45 += einsatzkraft._name + "\n"; break;
					case aD: ++ aD; break;
					case klamotten: klamotten += "" + einsatzkraft._name + "\n"; break;
				}
			}
		
		if(_letzteNachricht != 0)
		{
			
		}
		
		_nachrichtenSender.nachrichtAusfuehren(new SendMessage (EinsatzRueckmeldungBot._gruppenID, "⚠️" + _einsatz._stichwort + "\n" + Status.gebeString(Status.Unter10) + ": " + u10 + "\n" +
																								Status.gebeString(Status.Unter15) + ": " + u15 + "\n" +
																								Status.gebeString(Status.Über15) + ": " + ü15 + "\n" +
																								Status.gebeString(Status.Über45) + ": " + ü45 + "\n" + 
																								Status.gebeString(Status.aD) + ": " + aD + "\n" + klamotten));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
