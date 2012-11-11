var boardManager = {
	url : '/'
	, currentUserAccountId : 0
	, currentPage : 0
	, currentCategory : 'notice'
	, totalPage : 1
	, isLastPage : false
	, isFirstPage : false
	, editor : null
	, editorConfig : {}
	, replyManager : null
	
	, init : function(params){
		var that = this;
		that.url = params.url;
		that.imageUploadUrl = params.imageUploadUrl;
		that.currentUserAccountId = params.currentUserAccountId;
		that.replyManager = params.replyManager;
		
		that.bindEvents();
		that.load(that.currentCategory, 1);
		
		$(window).scroll(function(){
			if($(window).scrollTop() == $(document).height() - $(window).height()){
				if(!that.isLastPage && that.currentPage < that.totalPage){
					boardManager.load(that.currentCategory, that.currentPage + 1);	
				}
			}
		});
		
		that.initEditor();
	}
	
	, initEditor : function(){
		var that = this;
		that.editorConfig = {
			toolbar : [{ name: 'tools1', items : [ 'Source','Cut','Copy','Paste'] }, { name: 'tools2', items : [ 'Bold','Italic','Underline','Image','Styles','FontSize' ] }, { name: 'tools3', items : [ 'Maximize' ] }],		   
			language : 'ko', width : 520, height : 150,
			filebrowserImageUploadUrl : that.imageUploadUrl,
			image_previewText : 'image is in here.'
		};
		that.editor = that.getCKEditorInstance('contentEditor', that.editorConfig);
	}
	, getCKEditorInstance : function(editorId, config){
		if($("#"+editorId).length > 0) {
			if(!config){
				config = this.editorConfig;
			}
			return CKEDITOR.replace(editorId, config);
		}
	}
	
	, bindEvents : function(){
		var that = this;
		$("#saveBt").click(function(){
			that.save();
		});	
		
		$("#boardsSection").click(function(event){
			var target = $(event.target);
			if(target.hasClass('remove')){
				that.remove(target.attr('boardId'));
			}else if(target.hasClass('update')){
				that.update(target.attr('boardId'));
			}else if(target.hasClass('openToggle')){
				that.openView(target.attr('boardId'));
			}
		});
		
		$('#sideMenu').click(function(event){
			var target = $(event.target);
			var category = ('' + target.attr('category')).toLowerCase();
			$('.menuItem').each(function(){$(this).removeClass('active');});
			target.parent().addClass('active');
			that.resetPage();
			that.load(category, 1);
		});

		$("#btEditorClose").click(function(){
			that.resetForm();
		});
	}
	
	, resetPage : function(){
		this.currentPage = 0;
		this.totalPage = 1;
		this.isLastPage = false;
		this.isFirstPage = false;
		$("#boardsSection").html('<div id="topRow" style="display:none"></div>');
	}
	
	, openView : function(boardId){
		$('#boardContent_' + boardId).toggle('slow', function(){
			if($(this).hasClass('opened')){
				$(this).removeClass('opened').addClass('closed');
			}else{
				$(this).removeClass('closed').addClass('opened');	
				replyManager.load(boardId, 1);
			}
		});
	}
	
	, load : function(category, page){
		var that = this;
		if(!that.isLastPage && that.currentPage !== page){
			$('div#loadmoreajaxloader').show();
			$.ajax({
				type : "GET",
				url : that.url + '/' + category + '/' + page,
				dataType : "json",
				success : function(json){
					$("#boardsSection").append($.tmpl('html/board', json.page.content, {'currentUserAccountId':that.currentUserAccountId}));
					$('div#loadmoreajaxloader').hide();
					
					that.isFirstPage = json.page.firstPage;
					that.isLastPage = json.page.lastPage;
					that.totalPage = json.page.totalPages;
					that.currentPage = page;
					that.currentCategory = category;
				}
			});	
		}
	}
	
	, resetForm : function(){
		$("#boardId").val("0");
		$("#title").val("");
		this.editor.setData("");
	}
	
	, save : function(){
		var that = this;
		var params = {
			'_method' : 'PUT',
			title : $("#title").val(),
			writer : $("#writer").val(),
			content : that.editor.getData(),
			categoryValue : that.currentCategory
		};
		var isUpdate = false; 
		var boardId = $("#boardId").val();
		if(boardId !== '0'){
			params['id'] = boardId;
			isUpdate = true;
		}

		if(!$common.isEmpty(params.title) && !$common.isEmpty(params.content)){
			$.ajax({
				type : "POST",
				url : that.url,
				data : params,
				dataType : "json",
				success : function(json){
					if(json.result !== 'success'){
						alert('failed to saving.');
					}else{
						if(isUpdate){
							$("#rowTitle_"+boardId).text(json.board.title);
							$('#boardContentDetail_'+boardId).html($.tmpl('html/boardContent', json.board));
						}else{
							json.board.updatedTimestamp = '방금';
							$('#topRow').after($.tmpl('html/board', json.board, {'currentUserAccountId':that.currentUserAccountId}));	
						}
						
						$('#btEditorClose').click();
						that.resetForm();
					}
				}
			});
		}
	}
	
	, remove : function(boardId){
		var that = this;
		$common.confirm('<span class="label label-important">Important</span>', 'It will be removed. Are you sure?', function(){
			$.ajax({
				type : "POST",
				url : that.url,
				data : {'_method' : 'DELETE', id : boardId},
				dataType : "json",
				success : function(json){
					var boardRow = $('#boardRow_'+boardId); 
					boardRow.hide('slow', function(){boardRow.remove();});
				}
			});			
		});
	}
	
	, update : function(boardId){
		var that = this;
		$.ajax({
			type : "GET",
			url : that.url + "/view/" + boardId,
			dataType : "json",
			success : function(json){
				var board = json.board;
				$("#boardId").val(board.id);
				$("#title").val(board.title);
				that.editor.setData(board.content);
				
				$("#editorBt").click();
			}
		});
	}
};