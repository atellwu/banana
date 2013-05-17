$(function(){
	var ds = {
		advancedSearch: $('.advanced-search'),
		simpleForm: $('.simple-form'),
		advancedForm: $('.advanced-form'),
		advanceCancel: $('.advance-cancel')

	}	

	ds.advancedSearch.on('click', function(e){
		e.preventDefault()
		ds.simpleForm.addClass('hide')
		ds.advancedForm.removeClass('hide')
	})
	ds.advanceCancel.on('click', function(e){
		e.preventDefault()
		ds.advancedForm.addClass('hide')
		ds.simpleForm.removeClass('hide')
	})
})
