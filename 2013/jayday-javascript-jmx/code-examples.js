// Setup Coder-Mirror sections:
$(function() {
    $("textarea.demo-code").each(function () {
        var demoDivId = this.id.replace("-code", "");
        var evalAsScript = $(this).data("script");
        var editor = CodeMirror.fromTextArea(this,
            {
                lineNumbers:    true,
                mode:           evalAsScript ? "javascript" : "htmlmixed",
                viewportMargin: Infinity,
                matchBrackets: true,
                autoCloseBrackets: true
            });
        editor.setValue(prepareDemo(editor.getValue()));
        var $demoDiv = $("#" + demoDivId);
        if (editor.getValue()) {
            $demoDiv.html(
                evalAsScript ?
                    "<script>" + editor.getValue() + "</script>" :
                    editor.getValue());
        }
        editor.on("change", function () {
            var unload = $demoDiv.data("unload");
            if (unload) {
                try {
                    eval(unload);
                } catch (error) { console.log(error + " -- " + $demoDiv.data("unload")); }
            }
            var value;
            if (evalAsScript) {
                value = "<script>" + editor.getValue() + "</script>";
            } else {
                value = editor.getValue();
            }
            $demoDiv.html(value);
            if (unload) {
                $demoDiv.data("unload",unload);
            }
        });

        $(".demo-sample")
            .filter(function () {
                return this.id.indexOf(demoDivId) > -1;
            })
            .each(function () {
                var templateId = this.id + "-code";
                var content = $("#" + templateId);
                $(this).click(function () {
                    var txt = prepareDemo(content.html());
                    editor.setValue(txt);
                });
            });
    });
});

function prepareDemo(txt) {
    return txt.replace(/skript/gm, "script");
}


