function onSelectItemOpenForm(formId){
	if($('#'+formId).css('height') == '0px')	{
		$('#'+formId).collapse('toggle');
	}
}