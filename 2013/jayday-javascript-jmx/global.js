// Global configuration and functions

/* URL for local test */
var url = "http://jolokia.org/jolokia";


function mb(number) {
    return (Math.round((number*100) / (1024 * 1024)) / 100).toFixed(2) + " MB";
}

function json(text) {
    return JSON.stringify(text,null,1);
}

