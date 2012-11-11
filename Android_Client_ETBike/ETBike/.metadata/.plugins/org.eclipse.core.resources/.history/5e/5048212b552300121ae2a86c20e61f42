var $common = {
	contextPath : '/',
	
	progressImgSrc : '',

	init : function(params){
		this.contextPath = params.contextPath;
		this.progressImgSrc = params.progressImgSrc;
	},
	
	alert : function(title, message, callback){
		var dialogId = new Date().getTime();
		var tmpl = $.tmpl('pop/alert', {dialogId:dialogId, title:title, message:message});
		var dialog = $(tmpl).on('hidden', function(){if(callback){callback();} $(this).remove();}).modal({keyboard:false, backdrop:'static'});
		return dialog;
	},
	
	confirm : function(title, message, callback){
		var dialogId = new Date().getTime();
		var tmpl = $.tmpl('pop/confirm', {dialogId:dialogId, title:title, message:message});
		var dialog = $(tmpl)
		.on('shown', function(){$("#btConfirm-" + dialogId).click(function(){if(callback) callback();});})
		.on('hidden', function(){$(this).remove();})
		.modal({keyboard:false, backdrop:'static'});
		
		return dialog;
	},	
	
	info : function(title, message, callback){
		var dialogId = new Date().getTime();
		var tmpl = $.tmpl('pop/info', {dialogId:dialogId, title:title, message:message});
		var dialog = $(tmpl).on('hidden', function(){if(callback){callback();} $(this).remove();}).modal({keyboard:false, backdrop:'static'});	
		
		return dialog;
	},

	isEmpty : function(str){
		if(str == null) return true;
		return !(str.replace(/(^\s*)|(\s*$)/g, ""));
	},
	
	getUrl : function(templateUrl, replacement){
		return templateUrl.replace(/{.*}/gi, replacement);
	}
};