package test.optionrenderer;

import org.zkoss.fiddle.OptionBuilder;
import org.zkoss.fiddle.OptionRenderer;

public class OptionTextValueStringRenderer implements OptionRenderer<String> {

	public void render(OptionBuilder builder, String data) throws Exception {
		builder.appendOption(data,"val_"+data);
	}
}
