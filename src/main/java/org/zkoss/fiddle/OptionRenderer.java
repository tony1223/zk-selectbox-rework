package org.zkoss.fiddle;

public interface OptionRenderer<T> {
	public void render(Selectbox comp,OptionBuilder builder,T data) throws Exception;
}
