package test.optionrenderer;

import org.zkoss.fiddle.OptionBuilder;
import org.zkoss.fiddle.OptionRenderer;
import org.zkoss.fiddle.Selectbox;

public class SingleOptionTextStringRenderer implements OptionRenderer<String> {

	public void render(Selectbox comp,OptionBuilder builder, String data) throws Exception {
		builder.appendOption(data);
	}
}
