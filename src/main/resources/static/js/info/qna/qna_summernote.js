
/* summernote link */
document.write(
	'<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">'
	, '<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>' 
	, '<script src="/summernote/lang/summernote-ko-KR.js"></script>');

/* summernote  */
$(document)
	.ready(
		function() {
			$('#content')
				.summernote(
					
					{
						lang: 'ko-KR', // default: 'en-US'
						height: 300, // set editor height
						minHeight: null, // set minimum height of editor
						maxHeight: null, // set maximum height of editor
						focus: true, // set focus to editable area after initializing summernote
						toolbar: [
							// [groupName, [list of button]]
							['fontname',
								['fontname']],
							['fontsize',
								['fontsize']],
							[
								'style',
								[
									'bold',
									'italic',
									'underline',
									'strikethrough',
									'clear']],
							[
								'color',
								['forecolor',
									'color']],
							['table', ['table']],
							[
								'para',
								['ul', 'ol',
									'paragraph']],
							['height', ['height']],
							[
								'insert',
								[   'link',
									'video']],
							],
						fontNames: ['Arial',
							'Arial Black',
							'Comic Sans MS',
							'Courier New', '맑은 고딕',
							'궁서', '굴림체', '굴림', '돋움체',
							'바탕체'],
						fontSizes: ['8', '9', '10', '11',
							'12', '14', '16', '18',
							'20', '22', '24', '28',
							'30', '36', '50', '72']
							
					});
		});
