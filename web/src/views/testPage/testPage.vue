<template>
    <v-ace-editor v-model:value="sqlContent" @init="editorInit" lang="javascript" :options="{
      fontSize: 14,
      tabSize: 2,
      showPrintMargin: false,
      highlightActiveLine: true,
    }" theme="monokai" style="height: 400px; width: 400px; background: #000; color: #fff">
    </v-ace-editor>
</template>

<script>
import {
    defineComponent,
    reactive,
    toRefs,
    ref,
    onMounted,
    nextTick,
} from "vue";
import * as ace from 'ace-builds';
ace.config.set('basePath', '/static/src-min-noconflict/');
import { VAceEditor } from "vue3-ace-editor";
export default defineComponent({
    name: "page-three",
    components: {
        VAceEditor,
    },
    setup() {
        const sqlContent = ref("");
        const state = reactive({
            test: "",
        });
        function editorInit() {
            require("ace-builds/src-noconflict/ext-language_tools");
            require("ace-builds/src-noconflict/snippets/sql");
            require("ace-builds/src-noconflict/mode-sql");
            require("ace-builds/src-noconflict/theme-monokai");
            require("ace-builds/src-noconflict/mode-html");
            require("ace-builds/src-noconflict/mode-html_elixir");
            require("ace-builds/src-noconflict/mode-html_ruby");
            require("ace-builds/src-noconflict/mode-javascript");
            require("ace-builds/src-noconflict/mode-python");
            require("ace-builds/src-noconflict/snippets/less");
            require("ace-builds/src-noconflict/theme-chrome");
            require("ace-builds/src-noconflict/ext-static_highlight");
            require("ace-builds/src-noconflict/ext-beautify");
        }
        onMounted(() => {
            nextTick(() => {
                editorInit;
            });
        });
        return {
            sqlContent,
            ...toRefs(state),
            editorInit,
        };
    },
});
</script>

<style scoped>
/* 修改光标颜色 */
.ace_cursor {
    color: #fff !important;
}
</style>