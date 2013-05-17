$(function(){
	var ds = {
		resultSections: $('.j-result-sections')
	}
	
	ds.resultSections.on('mouseenter',function(){
		tbodylist.mouseEnter($(this).children('.bs-docs-example'))
	})
	ds.resultSections.on('mouseleave',function(){
		tbodylist.mouseLeave($(this).children('.bs-docs-example'))
	})

	var tbodylist = {
		init: function(){},
		mouseEnter: function(target){
			$(target).css('background-color','#eee')
		},
		mouseLeave:function(target){
			$(target).css('background-color','#fff')
		}
	}
})
