package ffeppdeinsatzbotv2;

public enum Status {
	Unter10, Unter15, Über15, Über45, aD, klamotten;


	public static Status gebeStatus(String meldung)
	{
		switch(meldung)
		{
			case "Unter 10 Minuten": return Unter10; 
			case "Unter 15 Minuten": return Unter15;
			case "Über 15 Minuten": return Über15;
			case "Über 45 Minuten": return Über45;
			case "Außer Dienst": return aD;
			case "Bitte Klamotten mitnehmen, komme direkt!": return klamotten;
		}
		return null;
		
	}
	
	public static boolean istStatus(String msg)
	{
		return gebeStatus(msg) != null;
	}

	public static String gebeString(Status status)
	{
		switch(status)
		{
		case Unter10: return "Unter 10 Minuten";
		case Unter15: return "Unter 15 Minuten" ;
		case Über15: return "Über 15 Minuten" ;
		case Über45: return "Über 45 Minuten";
		case aD: return "Außer Dienst" ;
		case klamotten: return "Klamotten mitnehmen von: " ;
		default : return "";
		}
	}
}