package mp.texas;

import android.graphics.drawable.Drawable;

public class Profil 
{
	private String name = "Reiner Zufall II";
	private Drawable avatar;
	private String uri;
	private String id;

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
	

	public Profil(int id)
	{
		name=namen[id-1];
	}
	
	public Profil(String name, Drawable bild)
	{
		this.name=name;
		this.avatar=bild;
	}
	
	
}
