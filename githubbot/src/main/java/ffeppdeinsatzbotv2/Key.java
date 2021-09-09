package ffeppdeinsatzbotv2;

import java.util.HashMap;
import java.util.Map;

public class Key {
	
    private static Map<String, Character> _keyholder;
	
	public Key()
	{
		_keyholder = new HashMap<String, Character>();
		_keyholder.put("HIER STEHT EIN PASSWORT", 'A');
		_keyholder.put("HIER STEHT EIN PASSWORT", 'E');
	}
	
	public static boolean ueberpruefeBerechtigung(String zuUeberpruefen)
	{
		return _keyholder.containsKey(zuUeberpruefen);
	}
	
	public static char gebeBerechtigungFuerSchluessel(String zuUeberpruefen)
	{
		if(ueberpruefeBerechtigung(zuUeberpruefen))
		{
		return _keyholder.get(zuUeberpruefen);
		}
		return 'X';
	}
}
