/*
 * Profil Klasse
 * Hier werden Namen und Avatar eines Spielers verwaltet
 * Zusätzlich erhält jeder Human Spieler noch eine ID
 * URI???
 */
package mp.texas;

import android.graphics.drawable.Drawable;

public class Profil 
{
	private String name = "Reiner Zufall II";
	private Drawable avatar;
	private String uri;
	private String id = "null";

	//Namensliste für Computergegner
	private String[] namen = {"Anton","Berta","Cäsar","Dora","Emil","Friedrich","Gustav"};

	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Drawable getAvatar() {
		return avatar;
	}
	public void setAvatar(Drawable avatar) {
		this.avatar = avatar;
	}
	
	//Erstellt Profil mit vorgefertigtem Namen
	//TASK auch gleich noch Bild einfügen
	public Profil(int id)
	{
		name=namen[id-1];
		this.id=App.selbst.getProfil().getId();
	}
	
	public Profil()
	{
		name="Mustermann";
	}
	
	//Erstellt komplettes Profil 
	public Profil(String name, Drawable bild)
	{
		this.name=name;
		this.avatar=bild;
	}

	public Profil(String name)
	{
		this.name=name;
	}
	
}
