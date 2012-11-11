var templateLoader = {
	templateUrl : '/',
	
	templates : [
		'pop/alert',
		'pop/confirm',
		'pop/info',

		'html/board',
		'html/boardContent',
		'html/fileUploadInfo',
		'html/reply',
		'html/replyPagination'
	],
	
	init : function(params){
		this.templateUrl = params.templateUrl;
		this.loadTemplate();
	},
	
	loadTemplate : function(){
		var templateArray = this.templates;
		var baseUrl = this.templateUrl;
		
		for(var i=0, length=templateArray.length; i<length; i++){
			var tName = templateArray[i];
			$.ajax({
				type : "GET",
				url : baseUrl + templateArray[i],
				dataType : "html",
				async : false,
				success : function(data){$.template(tName, data);}
			});
		}		
	}
};