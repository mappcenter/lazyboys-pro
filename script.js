
$(document).ready(function() {

    $('#cmbTag').change(function(){                
        var tagID= $(this).attr('value') ;	
        $.post("getItemPage",{
            t:tagID, 
            p:"1"
        }, function(data){	                   
            $("table.pagination").html(data);	
            regen();	
        }); 
    });
    // Fix IE z-index

    /*var zIndexNumber = 1000;
		$('div').each(function() {
			$(this).css('zIndex', zIndexNumber);
			zIndexNumber -= 10;
		});*/
		
    // Push footer down
	
    $('#container').append('<div id="push" />');

	
    // WYSIWYG
	
    $('.wysiwyg').wysiwyg();
		
    // Custom <select>
	
    $('select').wrap('<div class="my-skinnable-select" />');
	
    $(document).ready(function() {
        $('.my-skinnable-select').each(function(i) {
            selectContainer = $(this);
            selectContainer.removeClass('my-skinnable-select');
            selectContainer.addClass('skinned-select');
            selectContainer.children().before('<div class="select-text">a</div>').each(function() {
                for (var i = 0; i < this.options.length; i++) {
                    if(this.options[i].selected == true) {
                        $(this).prev().text(this.options[i].innerHTML);
                    }
                }
            });
        	
            var parentTextObj = selectContainer.children().prev();
            selectContainer.children().click(function() {
                parentTextObj.text(this.options[this.selectedIndex].innerHTML);
            })
        });
    });
  		
    // Text inside textfield
    var active_color = '#000'; // Color of user provided text
    var inactive_color = '#969696'; // Color of default text
	
    $('input[type="text"], input[type="password"]').each(function() {
        var value = $(this).parent().children('label').html();

        if($(this).attr("value") == "") {
            $(this).attr("value", value);
            $(this).css("color", inactive_color);
        }
    });
  	
    var default_values = new Array();
  		
    $('input[type="text"], input[type="password"]').focus(function() {
        if (!default_values[this.id]) {
            default_values[this.id] = this.value;
        }
        if (this.value == default_values[this.id]) {
            this.value = '';
            this.style.color = active_color;
        }
    		
        $(this).blur(function() {
            if (this.value == '') {
                this.style.color = inactive_color;
                this.value = default_values[this.id];
            }
        });
    });

    // Modal box
	
    jQuery.fn.fadeToggle = function(speed, easing, callback) { // Custom fade toggle function
        return this.animate({
            opacity: 'toggle'
        }, speed, easing, callback); 
    }; 
		
    $('a[rel="modal"]').each(function() {
        var id = $(this).attr("href");
        var modalbox = $(id).html();
        $(this).wrap('<div class="modal" />');
        $(this).parent().append(modalbox);
        $(id).remove();
    });
		
    $('a[rel="modal"]').click(function() {
        $('.modalbox').fadeOut(200);
        $(this).parent().children('.modalbox').fadeToggle(200);
        $('#overlay').show();
        return false;
    });
		
    $('body').append('<div id="overlay" />'); // Add overlay to DOM
	
    $('#overlay').click(function() { // When the overlay is clicked the mailbox will disappear
        $('#overlay').hide();
        $('.modalbox').fadeOut(200);
    });
		
    // Charts
		
    $('.bargraph').visualize({ // Create awesome charts!
        type: 'bar',
        height: '200px',
        colors: ['#005ba8','#1175c9','#92d5ea','#ee8310','#8d10ee','#5a3b16','#26a4ed','#f45a90','#e9e744'],
        appendTitle: false
    });
		
    $('.linegraph').visualize({ // Create awesome charts!
        type: 'line',
        height: '200px',
        lineWeight: 2,
        colors: ['#005ba8','#1175c9','#92d5ea','#ee8310','#8d10ee','#5a3b16','#26a4ed','#f45a90','#e9e744'],
        appendTitle: false
    });
		
    $('.areagraph').visualize({ // Create awesome charts!
        type: 'area',
        height: '200px',
        lineWeight: 1,
        colors: ['#005ba8','#1175c9','#92d5ea','#ee8310','#8d10ee','#5a3b16','#26a4ed','#f45a90','#e9e744'],
        appendTitle: false
    });
		
    $('.linegraph').hide(); // Hide original table
    $('.bargraph').hide();
    $('.areagraph').hide();

    // Navigation tabs with smooth transitions:
	
    $('#main-nav > li > ul').hide(); // Hide all subnavigation
    $('#main-nav > li > a.current').parent().children("ul").show(); // Show current subnavigation	
		
			
    $('#main-nav > li > a[href="#"]').click( // Click!
        function() {
            $(this).parent().siblings().children("a").removeClass('current'); // Remove .current class from all tabs
            $(this).addClass('current'); // Add class .current
            $(this).parent().siblings().children("ul").fadeOut(150); // Hide all subnavigation
            $(this).parent().children("ul").fadeIn(150); // Show current subnavigation
            $("#manage").html("Manage "+$('#main-nav > li > a.current').html());
            $("#task").html($('#main-nav > li > a.current').html());
                                
            return false;
        }
        );
		
    $('#main-nav > li > ul').find('a[href="#"]').click( // Click!
        function() {
			 
            $(this).parent().siblings().children("a").removeClass('current'); // Remove .current class from all tabs
            $(this).addClass('current'); // Add class .current
            $("#manage").html($(this).html());
            return false;
        }
        );
		
    // Subtitle fix
	
    var subtitle = $('#content > h2');
    $('#header').append(subtitle);
    $('#content > h2').remove();
	
    // Content tabs:
		
    $('.content-box-header ul li:first-child a').addClass('current'); // Add .current to the first class
    $('.content-box .tab-content').hide(); // Hide all .tab-content divs
    $('.content-box .tab-content:first-child').show(); // Show default tabs
	
    $('.content-box-header ul li a').click(function() {
        $(this).parent().siblings().find("a").removeClass('current'); // Remove .current from all tabs
        $(this).addClass('current'); // Set tab to current
        var tabcontent = $(this).attr('href'); // Get link to requested tab
        $(tabcontent).siblings().hide(); // Hide all other .tab-content divs
        $(tabcontent).show(); // Show content div
        return false;
    });
		
    // Alternating table rows
	
    $('tbody tr').removeClass("alt-row"); // Remove all .alt-row classes
    $('tbody tr:even').addClass("alt-row"); // Add .alt-row to even table rows
		
    // Check-all
	
    $('thead th input[type="checkbox"]').click(function() { // Click a checkbox that's in the <thead>
        $(this).parent().parent().parent().parent().find("input[type='checkbox']").attr('checked', $(this).is(':checked')); // Find all checkboxes and check them if needed
    });
                
                
		
    // Tooltip-confirmation
	
    $('.confirmation').wrap('<div class="confirm" />');
	
    $('.confirm > a').live('click',function() {
        $('.tooltip').fadeOut(200, function() { // Remove all tooltips
            $(this).remove();
        });   
        var itemID = $(this).parent().children("a").attr('rel');//get deleted item ID
        $(this).parent().append('<div class="tooltip"><p>Are you sure?</p><a href="#" onclick="deleteOneItem('+itemID+');">Yes</a> | <a href="#" class="close">No</a></div>'); 		
        $('.close').click(function() { // Closing the tooltip
            $('.tooltip').fadeOut(200, function() {
                $(this).remove();
            });
            return false;
        });
        
        $(this).parent().children('.tooltip').fadeIn(200);
        return false; // Make sure it doesn't follow the link immediately
    });
	
    // Collapse content boxes:
	
    $('.content-box-header h3').click(function() {
        if($(this).parent().parent().hasClass('closed')) {
            $(this).parent().parent().children('.content-box-content').show();
            $(this).parent().parent().removeClass('closed');
        } else {
            $(this).parent().parent().children('.content-box-content').hide();
            $(this).parent().parent().addClass('closed');
        }
    });

    // Close notifications:
	
    $('div.notification').click(function() {
        $(this).fadeOut(200, function() {
            $(this).hide();
        });
    });		  
});

function itemList(page){
    
    $(document).ready(function() {  	         
        var tagID = $('#cmbTag').attr('value') ;	
		
        $.post("getItemPage",{
            t:tagID, 
            p:page
        }, function(data){	                  
            $("#table_pagination").html(data);	
            regen();
        });
        
    }); 
}

function regen(){
    $('.confirmation').wrap('<div class="confirm" />');
        
    $('.confirm > a').live('click',function() {
        $('.tooltip').fadeOut(200, function() { // Remove all tooltips
            $(this).remove();
        });        
        var itemID = $(this).parent().children("a").attr('rel');//get deleted item ID
        $(this).parent().append('<div class="tooltip"><p>Are you sure?</p><a href="#" onclick="deleteOneItem('+itemID+');">Yes</a> | <a href="#" class="close">No</a></div>'); 		
        $('.close').click(function() { // Closing the tooltip
            $('.tooltip').fadeOut(200, function() {
                $(this).remove();
            });
            return false;
        });
        $(this).parent().children('.tooltip').fadeIn(200);
        return false; // Make sure it doesn't follow the link immediately
    });
}

function deleteItem(){
    
    var tagID = $('#cmbTag').attr('value') ;
    var page= $('#pagination > a.active').html();
    var listItemID="";
    $('input[type="checkbox"]').each(function(){
        if($(this).attr('checked')){
            //$(this).attr('checked', false);
            var itemID=$(this).attr('alt');
            listItemID+=itemID+",";      
        }                               
    });
    $.post('deleteItem',{
        listItemID:listItemID
    },function(data){        
        $.post("getItemPage",{
            t:tagID, 
            p:page
        }, function(data){
            $("#table_pagination").html(data);	
            regen();
        });
    });
    
}

function deleteOneItem(itemID){
    var listItemID=itemID+",";
    var tagID = $('#cmbTag').attr('value') ;
    var page= $('#pagination > a.active').html();
    $.post('deleteItem',{
        listItemID:listItemID
    },function(){        
        $.post("getItemPage",{
            t:tagID, 
            p:page
        }, function(data){
            $("#table_pagination").html(data);	
            regen();
        });
    });
}
 
