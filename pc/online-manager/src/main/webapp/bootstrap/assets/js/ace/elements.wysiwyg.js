/**
 <b>Wysiwyg</b>. A wrapper for Bootstrap wyswiwyg plugin.
 It's just a wrapper so you still need to include Bootstrap wysiwyg script first.
*/
(function($ , undefined) {
	$.fn.ace_wysiwyg = function($options , undefined) {
		var options = $.extend( {
			speech_button:true,
			wysiwyg:{}
        }, $options);

		var color_values = [
			'#ac725e','#d06b64','#f83a22','#fa573c','#ff7537','#ffad46',
			'#42d692','#16a765','#7bd148','#b3dc6c','#fbe983','#fad165',
			'#92e1c0','#9fe1e7','#9fc6e7','#4986e7','#9a9cff','#b99aff',
			'#c2c2c2','#cabdbf','#cca6ac','#f691b2','#cd74e6','#a47ae2',
			'#444444'
		]

		var button_defaults =
		{
			'font' : {
				values:['Arial', 'Courier', 'Comic Sans MS', 'Helvetica', 'Open Sans', 'Tahoma', 'Verdana'],
				icon:'fa fa-font',
				title:'字体'
			},
			'fontSize' : {
				values:{6:'6',5:'5',4:'4',3:'3', 2:'2', 1:'1'},
				icon:'fa fa-text-height',
				title:'字体大小'
			},
			'bold' : {
				icon : 'fa fa-bold',
				title : '粗体 (Ctrl/Cmd+B)'
			},
			'italic' : {
				icon : 'fa fa-italic',
				title : '斜体 (Ctrl/Cmd+I)'
			},
			'strikethrough' : {
				icon : 'fa fa-strikethrough',
				title : '删除线'
			},
			'underline' : {
				icon : 'fa fa-underline',
				title : '下划线'
			},
			'insertunorderedlist' : {
				icon : 'fa fa-list-ul',
				title : '子弹点列表'
			},
			'insertorderedlist' : {
				icon : 'fa fa-list-ol',
				title : '号码组列表'
			},
			'outdent' : {
				icon : 'fa fa-outdent',
				title : '减少缩进 (Shift+Tab)'
			},
			'indent' : {
				icon : 'fa fa-indent',
				title : '缩进 (Tab)'
			},
			'justifyleft' : {
				icon : 'fa fa-align-left',
				title : '居左 (Ctrl/Cmd+L)'
			},
			'justifycenter' : {
				icon : 'fa fa-align-center',
				title : '居中 (Ctrl/Cmd+E)'
			},
			'justifyright' : {
				icon : 'fa fa-align-right',
				title : '居右 (Ctrl/Cmd+R)'
			},
			'justifyfull' : {
				icon : 'fa fa-align-justify',
				title : '对齐 (Ctrl/Cmd+J)'
			},
			'createLink' : {
				icon : 'fa fa-link',
				title : '超链接',
				button_text : '添加',
				placeholder : 'URL',
				button_class : 'btn-primary'
			},
			'unlink' : {
				icon : 'fa fa-chain-broken',
				title : '移除超链接'
			},
			'insertImage' : {
				icon : 'fa fa-picture-o',
				title : '插入图片',
				button_text : '<i class="'+ ace.vars['icon'] + 'fa fa-file"></i> 选择图片 &hellip;',
				placeholder : 'Image URL',
				button_insert : '插入',
				button_class : 'btn-success',
				button_insert_class : 'btn-primary',
				choose_file: true //show the choose file button?
			},
			'foreColor' : {
				values : color_values,
				title : 'Change Color'
			},
			'backColor' : {
				values : color_values,
				title : 'Change Background Color'
			},
			'undo' : {
				icon : 'fa fa-undo',
				title : '撤销 (Ctrl/Cmd+Z)'
			},
			'redo' : {
				icon : 'fa fa-repeat',
				title : '重做 (Ctrl/Cmd+Y)'
			},
			'viewSource' : {
				icon : 'fa fa-code',
				title : '源代码模式'
			}
		}
		
		var toolbar_buttons =
		options.toolbar ||
		[
			'font',
			null,
			'fontSize',
			null,
			'bold',
			'italic',
			'strikethrough',
			'underline',
			null,
			'insertunorderedlist',
			'insertorderedlist',
			'outdent',
			'indent',
			null,
			'justifyleft',
			'justifycenter',
			'justifyright',
			'justifyfull',
			null,
			'createLink',
			'unlink',
			null,
			'insertImage',
			null,
			'foreColor',
			null,
			'undo',
			'redo',
			null,
			'viewSource'
		]


		this.each(function() {
			var toolbar = ' <div class="wysiwyg-toolbar btn-toolbar center"> <div class="btn-group"> ';

			for(var tb in toolbar_buttons) if(toolbar_buttons.hasOwnProperty(tb)) {
				var button = toolbar_buttons[tb];
				if(button === null){
					toolbar += ' </div> <div class="btn-group"> ';
					continue;
				}
				
				if(typeof button == "string" && button in button_defaults) {
					button = button_defaults[button];
					button.name = toolbar_buttons[tb];
				} else if(typeof button == "object" && button.name in button_defaults) {
					button = $.extend(button_defaults[button.name] , button);
				}
				else continue;
				
				var className = "className" in button ? button.className : 'btn-default';
				switch(button.name) {
					case 'font':
						toolbar += ' <a class="btn btn-sm '+className+' dropdown-toggle" data-toggle="dropdown" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i><i class="' + ace.vars['icon'] + 'fa fa-angle-down icon-on-right"></i></a> ';
						toolbar += ' <ul class="dropdown-menu dropdown-light dropdown-caret">';
						for(var font in button.values)
							if(button.values.hasOwnProperty(font))
								toolbar += ' <li><a data-edit="fontName ' + button.values[font] +'" style="font-family:\''+ button.values[font]  +'\'">'+button.values[font]  + '</a></li> '
						toolbar += ' </ul>';
					break;

					case 'fontSize':
						toolbar += ' <a class="btn btn-sm '+className+' dropdown-toggle" data-toggle="dropdown" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i>&nbsp;<i class="'+ ace.vars['icon'] + 'fa fa-angle-down icon-on-right"></i></a> ';
						toolbar += ' <ul class="dropdown-menu dropdown-light dropdown-caret"> ';
						for(var size in button.values)
							if(button.values.hasOwnProperty(size))
								toolbar += ' <li><a data-edit="fontSize '+size+'"><font size="'+size+'">'+ button.values[size] +'</font></a></li> '
						toolbar += ' </ul> ';
					break;

					case 'createLink':
						toolbar += ' <div class="btn-group"> <a class="btn btn-sm '+className+' dropdown-toggle" data-toggle="dropdown" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i></a> ';
						toolbar += ' <div class="dropdown-menu dropdown-caret dropdown-menu-right">\
							 <div class="input-group">\
								<input class="form-control" placeholder="'+button.placeholder+'" value="http://" type="text" data-edit="'+button.name+'" />\
								<span class="input-group-btn">\
									<button class="btn btn-sm '+button.button_class+'" type="button">'+button.button_text+'</button>\
								</span>\
							 </div>\
						</div> </div>';
					break;

					case 'insertImage':
						toolbar += ' <div class="btn-group"> <a class="btn btn-sm '+className+' dropdown-toggle" data-toggle="dropdown" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i></a> ';
						toolbar += ' <div class="dropdown-menu dropdown-caret dropdown-menu-right">\
							 <div class="input-group">\
								<input class="form-control" placeholder="'+button.placeholder+'" type="text" data-edit="'+button.name+'" />\
								<span class="input-group-btn">\
									<button class="btn btn-sm '+button.button_insert_class+'" type="button">'+button.button_insert+'</button>\
								</span>\
							 </div>';
							if( button.choose_file && 'FileReader' in window ) toolbar +=
							 '<div class="space-2"></div>\
							 <label class="center block no-margin-bottom">\
								<button class="btn btn-sm '+button.button_class+' wysiwyg-choose-file" type="button">'+button.button_text+'</button>\
								<input type="file" id="'+getRandomString()+'" data-edit="'+button.name+'" />\
							  </label>'
						toolbar += ' </div> </div>';
					break;

					case 'foreColor':
					case 'backColor':
						toolbar += ' <select class="hide wysiwyg_colorpicker" title="'+button.title+'"> ';
						$.each(button.values, function (_, color) {
                            toolbar += ' <option value="' + color + '">' + color + '</option> ';
                        });
						toolbar += ' </select> ';
						toolbar += ' <input style="display:none;" disabled class="hide" type="text" data-edit="'+button.name+'" /> ';
					break;

					case 'viewSource':
						toolbar += ' <a class="btn btn-sm '+className+'" data-view="source" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i></a> ';
					break;
					default:
						toolbar += ' <a class="btn btn-sm '+className+'" data-edit="'+button.name+'" title="'+button.title+'"><i class="'+ ace.vars['icon'] + button.icon+'"></i></a> ';
					break;
				}
			}
			toolbar += ' </div> ';
			////////////
			var speech_input;
			if (options.speech_button && 'onwebkitspeechchange' in (speech_input = document.createElement('input'))) {
				toolbar += ' <input class="wysiwyg-speech-input" type="text" data-edit="inserttext" x-webkit-speech />';
			}
			speech_input = null;
			////////////
			toolbar += ' </div> ';


			//if we have a function to decide where to put the toolbar, then call that
			if(options.toolbar_place) toolbar = options.toolbar_place.call(this, toolbar);
			//otherwise put it just before our DIV
			else toolbar = $(this).before(toolbar).prev();

			toolbar.find('a[title]').tooltip({animation:false, container:'body'});
			toolbar.find('.dropdown-menu input[type=text]').on('click', function() {return false})
		    .on('change', function() {$(this).closest('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle')})
			.on('keydown', function (e) {
				if(e.which == 27) {
					this.value = '';
					$(this).change();
				}
				else if(e.which == 13) {
					e.preventDefault();
					e.stopPropagation();
					$(this).change();
				}
			});
			
			toolbar.find('input[type=file]').prev().on(ace.click_event, function (e) { 
				$(this).next().click();
			});
			toolbar.find('.wysiwyg_colorpicker').each(function() {
				$(this).ace_colorpicker({pull_right:true}).change(function(){
					$(this).nextAll('input').eq(0).val(this.value).change();
				}).next().find('.btn-colorpicker').tooltip({title: this.title, animation:false, container:'body'})
			});
			
			
			var self = $(this);
			//view source
			var view_source = false;
			toolbar.find('a[data-view=source]').on('click', function(e){
				e.preventDefault();
				
				if(!view_source) {
					$('<textarea />')
					.css({'width':self.outerWidth(), 'height':self.outerHeight()})
					.val(self.html())
					.insertAfter(self)
					self.hide();
					
					$(this).addClass('active');
				}
				else {
					var textarea = self.next();
					self.html(textarea.val()).show();
					textarea.remove();
					
					$(this).removeClass('active');
				}
				
				view_source = !view_source;
			});


			var $options = $.extend({},{uploadType:options.uploadType}, { activeToolbarClass: 'active' , toolbarSelector : toolbar }, options.wysiwyg || {})
			$(this).wysiwyg( $options );
		});

		return this;
	}


})(window.jQuery);

