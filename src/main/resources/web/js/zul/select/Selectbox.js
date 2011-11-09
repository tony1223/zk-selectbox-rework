zul.select.Selectbox = zk.$extends(zul.Widget, {
	$define: {
		selectedIndex: function (selectedIndex) {
			var n = this.$n();
			if (n)
				n.selectedIndex = selectedIndex;
		}
	},
	bind_: function () {
		this.$supers(zul.select.Selectbox, 'bind_', arguments);
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
			.$supers(zul.select.Selectbox, 'unbind_', arguments);
	},
	_doChange: function (evt) {
		var n = this.$n();
		this.setSelectedIndex(n.selectedIndex);
		this.fire('onSelect', n.selectedIndex);
	}
});