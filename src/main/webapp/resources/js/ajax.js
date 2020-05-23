var request = null;
var requestComplete = null;		// 완료된 request를 판단하기 위한 Array


function ajaxCall_ASF() //비동기_한꺼번에 여러개 함수 호출
{	
	requestComplete = new Array();
	//args[0]:url
	//args[1]:param
	//args[2]:함수 호출
	//args[3]:응답순서
	//args[4]:함수파라미터
	
	var args = this.ajaxCall_ASF.arguments;
	var requestSeq = args[3];
	
	// 브라우져 호환성 검사
	if(window.XMLHttpRequest) {
		request[requestSeq] = new XMLHttpRequest();		
	} else if (window.ActiveXObject) {
		request[requestSeq] = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (request[requestSeq]) {		
		request[requestSeq].open('POST', args[0], true);		// request open
		request[requestSeq].setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");	// 헤더지정
		request[requestSeq].send(args[1]);					// 요청
		request[requestSeq].onreadystatechange =  function() {			
			for (var i=0; i<request.length; i++) {
				if(request[i].readyState == 4) {					
					if (request[i].status == 200) {
						if (requestComplete[i]) {
							continue;
						}
						requestComplete[i] = true;	// 해당 request 는 완료됨		
						var tmpStr = request[i].responseText;
						//alert(i+"-----"+requestComplete[i]);
						args[2](tmpStr,args[4]);
					}
				}
			}
		};
	} else {
		alert("request 생성 실패!!");
	}			
}

function ajaxCall(){		
	var req = null;
	var args = this.ajaxCall.arguments;
	// 브라우져 호환성 검사
	if(window.XMLHttpRequest) {
		req = new XMLHttpRequest();		
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (req) {		
		req.open('POST', args[0], true);		// request open
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");	// 헤더지정
		req.send(args[1]);					// 요청
		req.onreadystatechange =  function() {	
			if(req.readyState == 4) {					
				if(req.status == 200) {
					args[2](req.responseText, args[3], args[4]);
				}
			}
		};
		
	} else {
		alert("request 생성 실패!!");
	}	
}

function ajaxCall_formData(){
	var args = this.ajaxCall_formData.arguments;
	$.ajax({
		xhr: function() {
		    var xhr = new window.XMLHttpRequest();
		    xhr.upload.addEventListener("progress", function(evt) {
		      if (evt.lengthComputable) {
		        var percentComplete = evt.loaded / evt.total;
		        percentComplete = parseInt(percentComplete * 100);
		        var obj = top.$('#loadingTitle');
		        if(null != obj) {
			        if (percentComplete === 100) {
			        	obj.text('Processing...');
			        }else {
			        	obj.text('Uploading...'+percentComplete+'%');
			        }		        	
		        }
		      }
		    }, false);

		    return xhr;
		},
	    url: args[0],
	    processData: false,
	    contentType: false,
	    data: args[1],
	    type: 'POST',
	    success: function(result){
	       args[2](result, args[3], args[4]);
	    }
	});		
}

function ajax_formData(url, formData){
	$.ajax({
	    url: url,
	    processData: false,
	    contentType: false,
	    data: formData,
	    type: 'POST',
	    success: function(result){
	       
	    }
	});	
}


function innerHTML(data, divName){
	$("#"+divName+"").empty();
	$("#"+divName+"").html(data);
}

