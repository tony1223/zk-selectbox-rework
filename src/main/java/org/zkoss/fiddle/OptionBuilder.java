package org.zkoss.fiddle;

import java.util.List;

public interface OptionBuilder {

	public void appendOption(String text);

	public void appendOption(String text, String value);

	public void appendOptionGroup(String text, List subModel, OptionRenderer subOptionRender) throws Exception;
}
