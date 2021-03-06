<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:loadBundle var="Message" basename="demo.Messages"/>

<html>
<head>
<title>
(Locale: 4) Test locales with single f:loadBundle
</title>
</head>
<body>

<f:view>
<h:outputText value="local=default"/><br></br>
<div id="localeText0">#{Message.hello_message}</div>
</f:view>

<f:view locale="en">
<h:outputText value="locale=en"/><br></br>
<div id="localeText1">#{Message.hello_message}</div>
</f:view>

<f:view locale="de">
<h:outputText value="locale=de"/><br></br>
<div id="localeText2">#{Message.hello_message}</div>
</f:view>

<f:view locale="en">
<h:outputText value="locale=empty"/><br></br>
<div id="localeText3">#{Message.hello_message}</div>
</f:view>

<f:view locale="en_GB">
<h:outputText value="locale=en_GB"/><br></br>
<div id="localeText">#{Message.hello_message}</div>
</f:view>

</body>

</html>
