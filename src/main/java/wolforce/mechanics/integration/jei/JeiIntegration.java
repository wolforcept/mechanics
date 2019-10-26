package wolforce.mechanics.integration.jei;

import wolforce.mechanics.api.integration.MakeJeiIntegration;

public class JeiIntegration {

	public MakeJeiIntegration jei = new MakeJeiIntegration();

	public void register() {

		jei.add(new JeiCatPortal());
		jei.add(new JeiCatDryingTable());
	}
}
