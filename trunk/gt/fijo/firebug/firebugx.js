
if (!("console" in window) )
	window.console = {};
		
var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml",
			"group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];


for (var i = 0; i < names.length; ++i){
	if (! (names[i] in window.console))
		window.console[names[i]] = function() {}
	}