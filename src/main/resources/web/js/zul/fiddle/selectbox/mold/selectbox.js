function (out) {
	out.push('<select', this.domAttrs_(), '>');
	var s = $eval(this.items) || [] ;
	for (var i = 0, j = s.length; i < j; i++) {
		
		if (s[i][2]) {
			if(s[i][2]=="1"){
				out.push('<optgroup ',
				s[i][0]? 'label="' + zUtl.encodeXML(s[i][0]) +'" ':"",
				'>');	
			}else{
				out.push('</optgroup>');
			}
		} else {
			out.push('<option'); 
			if (this._selectedIndex > -1 && this._selectedIndex == i) out.push(' selected="selected"');
			
			if(s[i][1])
				out.push(' value ="'+zUtl.encodeXML(s[i][1])+'" ');	
						
			out.push('>', zUtl.encodeXML(s[i][0]), '</option>');
		}
	}
	out.push('</select>');
}