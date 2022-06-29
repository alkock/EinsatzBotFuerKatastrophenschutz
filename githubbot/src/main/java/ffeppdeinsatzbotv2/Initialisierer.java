package ffeppdeinsatzbotv2;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;

import Personen.Administrator;
import Personen.Einsatzkraft;
import Personen.EinsatzkraftImpl;

public class Initialisierer {
		public static Map<Long, Einsatzkraft> ladeEinsatzkraefte()
			{
				Map<Long, Einsatzkraft> nutzerSet = new HashMap<Long, Einsatzkraft>();			
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("HIER STEHT EINE IP","HIER STEHT EIN NUTZERNAME", "HIER STEHT EIN PASSWORT");
					Statement stmt = con.createStatement();
					ResultSet admin = stmt.executeQuery("SELECT * FROM admin");
					while(admin.next())
					{
						String name = admin.getString(1);
						Long userid = admin.getLong(2);
						Administrator admini = new Administrator(userid, name);
						nutzerSet.put(userid, admini);
					}
					
					ResultSet benutzer = stmt.executeQuery("SELECT * FROM admin");
					while(benutzer.next())
					{
						String name = benutzer.getString(1);
						Long userid = benutzer.getLong(2);
						EinsatzkraftImpl einsatzkraft = new EinsatzkraftImpl(userid, name);
						nutzerSet.put(userid, einsatzkraft);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}	
				return nutzerSet;
			}
		
		
		public static Map<Long, Einsatzkraft> ladeSchlüssel()
			{
				return null;
			}
		public static void speichere(Einsatzkraft einsatzkraft)
			{

			}
}
