
    require.config({ paths: { 'vs': 'vs' } });

    var editor;

    require(['vs/editor/editor.main'], function () {
        editor = monaco.editor.create(document.getElementById('container'), {
            value: "",
            language: "java",
            theme: "vs-dark",
            automaticLayout: true,
            minimap: { enabled: true },
            scrollBeyondLastLine: false,
            roundedSelection: false,
            readOnly: false,
            cursorStyle: "line"
        });
    });


    function setLanguage(lang) {
        if (editor) {
            var model = editor.getModel();
            monaco.editor.setModelLanguage(model, lang);
        }
    }

    function setTheme(theme) {
        if (editor) {
            monaco.editor.setTheme(theme);
        }
    }

    function setText(text) {
        if (editor) {
            editor.setValue(text);
        }
    }

    function getText() {
        if (editor) {
            return editor.getValue();
        }
        return "";
    }