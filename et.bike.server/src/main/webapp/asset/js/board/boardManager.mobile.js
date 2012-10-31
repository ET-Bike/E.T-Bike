var boardManager = {
	url : '/'
	, currentUserAccountId : 0
	
	, init : function(params){
		var that = this;
		that.url = params.url;
		that.currentUserAccountId = params.currentUserAccountId;
		
		that.bindEvents();
	}
	
	, bindEvents : function(){
		var that = this;
		$('.boardList').click(function(event){
			var target = $(event.target);
			if(target.hasClass('read')){
				that.view(target.attr('boardId'));
			}
		});
	}
	
	, load : function(category, page, targetSection){
		var that = this;

		$.ajax({
			type : "GET",
			url : that.url + '/' + category + '/' + page,
			dataType : "json",
			success : function(json){
				 $(targetSection).html($.tmpl('html/board.mobile', json.page.content, {'currentUserAccountId':that.currentUserAccountId}));
			}
		});
	}
	
	, view : function(boardId){
		var that = this;
		$.ajax({
			type : "GET",
			url : that.url + "/view/" + boardId,
			dataType : "json",
			success : function(json){
				var board = json.board;
				$.tmpl('html/boardContent.mobile', board, {'currentUserAccountId':that.currentUserAccountId}).insertAfter('#boardPage');
				$.mobile.changePage('#boardViewPage_'+board.id);
			}
		});
	}
};