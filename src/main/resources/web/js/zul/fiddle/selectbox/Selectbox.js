zul.fiddle.selectbox.Selectbox = zk.$extends(zul.Widget, {
	$define: {
		selectedIndex: function (selectedIndex) {
			var n = this.$n();
			if (n)
				n.selectedIndex = selectedIndex;
		}
	},
	selectValue: function (val){
		var wgt = this;
		if (this.desktop) {
			var n = this.$n();
			jq("option", this).each(function(num) {
				if (this.value == val) {
					n.selectedIndex = num;
					wgt._doChange();
				}
			});
		}else{
			zk.afterMount(function(){
				wgt.selectValue(val);
			});
		}
	},
	bind_: function () {
		this.$supers(zul.fiddle.selectbox.Selectbox, 'bind_', arguments);
		var n = this.$n();
		this.domListen_(n, 'onChange')
			.domListen_(n, 'onFocus', 'doFocus_')
			.domListen_(n, 'onBlur', 'doBlur_');
	},
	unbind_: function () {
		var n = this.$n();
		this.domUnlisten_(n, 'onChange')
			.domUnlisten_(n, 'onFocus', 'doFocus_')
			.domUnlisten_(n, 'onBlur', 'doBlur_')
			.$supers(zul.fiddle.selectbox.Selectbox, 'unbind_', arguments);
	},
	_doChange: function (evt) {
		var n = this.$n();
		this.setSelectedIndex(n.selectedIndex);
		this.fire('onSelect', { 
			index:n.selectedIndex,
			value:n.value
		});
	}
});