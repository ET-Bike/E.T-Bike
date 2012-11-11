var fileManager = {
	init : function(params){

	},
	
	upload : function(multipartForm, successCallback){
		var progressDialog = $common.info('<span class="label label-info">uploading..</span>', '<img src="' + $common.progressImgSrc + '" /> wait a minute...');
		$(multipartForm).ajaxSubmit({  
			dataType: 'json',
			async:false,
			success: function(json){
				if(successCallback) successCallback(json.file);
			},
			error: function(xhr, textStatus, errorThrown) {
				setTimeout(function(){
					$(progressDialog).modal('hide');
					$common.alert('<span class="label label-warning">Fail</span>', 'failed uploading....');
				}, 3000);
			},
			complete: function(xhr, textStatus) {
				setTimeout(function(){$(progressDialog).modal('hide');}, 1000);
			}
		});	
	}
};