package ffeppdeinsatzbotv2;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DiveraAPI {
	public static String APIKEY = "HIER STEHT EIN PASSWORT";
	
	public static void loeseDiveraAus()
	{
		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI("https://www.divera247.com/api/alarm?accesskey=" + APIKEY));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
}
