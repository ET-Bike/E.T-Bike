var replyManager = {
	url : '/'
	
	, init : function(params){
		var that = this;
		that.url = params.url;
		that.bindEvents();
	}
	
	, bindEvents : function(){
		var that = this;

		$("#boardsSection").click(function(event){
			var target = $(event.target);
			if(target.hasClass('replyAdd')){
				that.save(target.attr('boardId'));
			}else if(target.hasClass('paging')){
				that.load(target.attr('boardId'), target.attr('page'));
			}else if(target.hasClass('removeReply')){
				that.remove(target.attr('replyId'),target.attr('boardId'), target.attr('page'));
			}
		});
	}
	
	, load : function(boardId, page){
		var that = this;
		$.ajax({
			type : "GET",
			url : that.url + '/' + boardId + '/' + page,
			dataType : "json",
			success : function(json){
				$("#replies_"+boardId).html($.tmpl('html/reply', json.page.content, {'boardId':boardId,'page':json.page.number}));
				$("#repliesPagination_"+boardId).html($.tmpl('html/replyPagination', json.page, {'boardId':boardId}));
			}
		});
	}
	
	, save : function(boardId){
		var that = this;
		
		var msgField = $("#boardReply_" + boardId);
		var params = {
			'_method' : 'PUT',
			'board.id' : boardId,
			writer : 'unknown',
			message : msgField.val()
		};
		if(!$common.isEmpty(params.message)){
			$.ajax({
				type : "POST",
				url : that.url,
				data : params,
				dataType : "json",
				success : function(json){
					if(json.result !== 'success'){
						alert('failed to saving.');
					}else{
						msgField.val('');
						that.load(boardId, 1);
					}
				}
			});
		}
	}
	
	, remove : function(replyId, boardId, page){
		var that = this;
		$.ajax({
			type : "POST",
			url : that.url,
			data : {'_method' : 'DELETE', id : replyId},
			dataType : "json",
			success : function(json){
				that.load(boardId, page);
			}
		});	
	}
};