package org.zkoss.fiddle;

public interface OptionRenderer<T> {
	public void render(OptionBuilder builder,T data) throws Exception;
}
