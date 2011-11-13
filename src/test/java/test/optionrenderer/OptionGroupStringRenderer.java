package test.optionrenderer;

import java.util.Arrays;

import org.zkoss.fiddle.OptionBuilder;
import org.zkoss.fiddle.OptionRenderer;
import org.zkoss.fiddle.Selectbox;

public class OptionGroupStringRenderer implements OptionRenderer<String> {

	public void render(Selectbox comp,OptionBuilder builder, String data) throws Exception {
		builder.appendOption("group start");
		
		builder.appendOptionGroup(data, 
				Arrays.asList(new String[]{"child1","child2","child3","child4"})
				, new OptionRenderer<String>() {
			public void render(Selectbox comp,OptionBuilder builder, String data) throws Exception {
				builder.appendOption(data,"val_"+data);				
			}
		});
		builder.appendOption("group end");
	}
}
