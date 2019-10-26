package wolforce.mechanics.api;

import java.util.LinkedList;

public class MakeMod {

	private LinkedList<MakeMod> mods = new LinkedList<>();

	public MakeMod() {
		mods.add(this);
	}

}
