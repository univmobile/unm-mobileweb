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
setTimeout(function() {
  window.location = "http://univmobile-dev.univ-paris1.fr/unm-mobileweb/?im="+getURLParameter('im')+"&poi="+getURLParameter('poi')+"&web=1";
}, 700);
window.location = "univmobile://?ID="+getURLParameter('im')+"&poiID="+getURLParameter('poi');
</script>
</body>
</html>