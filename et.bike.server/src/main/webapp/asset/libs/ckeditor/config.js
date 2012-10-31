CKEDITOR.editorConfig = function( config ) {
	config.language = 'ko';
	config.toolbar = 'WkToolbar';
	 
	config.toolbar_WkToolbar = [
		{ name: 'clipboard', items : [ 'Cut','Copy','Paste' ] },
		{ name: 'basicstyles', items : [ 'Bold','Italic','Underline'] },
		
		{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock' ] },
		{ name: 'insert', items : [ 'Image','Table','HorizontalRule', 'Link'] },
		'/',
		{ name: 'styles', items : [ 'Font','FontSize' ] },
		{ name: 'colors', items : [ 'TextColor','BGColor' ] },
		{ name: 'tools', items : [ 'Maximize', 'Source', 'Preview' ] }
	];
};
