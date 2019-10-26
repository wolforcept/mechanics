package wolforce.mechanics.api.integration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class MakeJeiIntegration {

	// STATIC
	private static LinkedList<MakeJeiIntegration> jeis = new LinkedList<>();

	public static LinkedList<MakeJeiIntegration> getJeis() {
		return jeis;
	}

	// MAKE OBJECT

	private LinkedList<JeiCat> jeiCats;
	private HashMap<Object, String[]> infos;

	public MakeJeiIntegration() {
		jeiCats = new LinkedList<JeiCat>();
		infos = new HashMap<>();

		jeis.add(this);
	}

	// CATEGORIES

	public void add(JeiCat cat) {
		jeiCats.add(cat);
	}

	public LinkedList<JeiCat> getCats() {
		return jeiCats;
	}

	// INGREDIENT INFOS

	public void addInfo(Object object, String... infoArray) {
		infos.put(object, infoArray);
	}

	public Set<Entry<Object, String[]>> getInfos() {
		return infos.entrySet();
	}
}
