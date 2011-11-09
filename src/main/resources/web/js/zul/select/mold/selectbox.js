function (out) {
	out.push('<select', this.domAttrs_(), '>');
	var s = $eval(this.items) || [] ;
	for (var i = 0, j = s.length; i < j; i++) {
		out.push('<option');
		if (this._selectedIndex > -1 && this._selectedIndex == i)
			out.push(' selected="selected"');
		out.push('>', s[i], '</option>');
	}
	out.push('</select>');
}