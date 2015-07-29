<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script language="javascript">
function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}
var isiOS = navigator.userAgent.match('iPad') || navigator.userAgent.match('iPhone') || navigator.userAgent.match('iPod');
var isAndroid = navigator.userAgent.match('Android');
if (isiOS || isAndroid) { 
	setTimeout(function() {
	    window.location = "http://univmobile-dev.univ-paris1.fr/unm-mobileweb/?im="+getURLParameter('im')+"&poi="+getURLParameter('poi')+"&web=1";
		//window.location = "http://localhost:8080/unm-mobileweb/?im="+getURLParameter('im')+"&poi="+getURLParameter('poi')+"&web=1";
	}, 700);
	window.location = "univmobile://?im="+getURLParameter('im')+"&poi="+getURLParameter('poi');
} else {
	window.location = "http://univmobile-dev.univ-paris1.fr/unm-mobileweb/?im="+getURLParameter('im')+"&poi="+getURLParameter('poi')+"&web=1";
	//window.location = "http://localhost:8080/unm-mobileweb/?im="+getURLParameter('im')+"&poi="+getURLParameter('poi')+"&web=1";
}
</script>
</body>
</html>