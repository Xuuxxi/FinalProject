<template>
    <ContentField>
        <div class="title">
            {{ questionName }}<span class="badge text-bg-success" style="font-size: 12px; margin-left: 8px;">{{
                difficulty }}</span>
        </div>
        <br>
        <ul class="list-group list-group-flush">
            <li class="list-group-item"></li>
            <li class="list-group-item scdTitle">题目内容</li>
            <li class="list-group-item" style="white-space:pre-wrap">
                <div class="shadow-sm p-3 mb-1 bg-body-tertiary rounded text-bg-secondary" style="margin-top: 5px;">{{
                description }}</div>
            </li>
            <li class="list-group-item scdTitle">输入格式</li>
            <li class="list-group-item" style="white-space:pre-wrap">
                <div class="shadow-sm p-3 mb-1 bg-body-tertiary rounded text-bg-secondary" style="margin-top: 5px;">{{
                inputStyle }}</div>
            </li>
            <li class="list-group-item scdTitle">输出格式</li>
            <li class="list-group-item" style="white-space:pre-wrap">
                <div class="shadow-sm p-3 mb-1 bg-body-tertiary rounded text-bg-secondary" style="margin-top: 5px;">{{
                outputStyle }}</div>
            </li>
            <li class="list-group-item scdTitle">输入样例</li>
            <li class="list-group-item" style="white-space:pre-wrap">
                <div class="shadow-sm p-3 mb-1 bg-body-tertiary rounded text-bg-secondary" style="margin-top: 5px;">{{
                inputSample }}</div>
            </li>
            <li class="list-group-item scdTitle">输出样例</li>
            <li class="list-group-item" style="white-space:pre-wrap">
                <div class="shadow-sm p-3 mb-1 bg-body-tertiary rounded text-bg-secondary" style="margin-top: 5px;">{{
                outputSample }}</div>
            </li>
        </ul>

        <hr>

        <div class="card">
            <div class="card-header">
                <div class="row">
                    <div class="col-9" style="font-size: 20px; font-weight: 1000;">
                        代码编写
                    </div>
                    <div class="col-3">
                        <select class="form-select" aria-label="Default select example" style="size: 3cap;" v-model="codeType">
                            <option value="0">选择编程语言</option>
                            <option value="1">java</option>
                            <option value="2">c++</option>
                            <option value="3">python</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="card-body">
                <VAceEditor v-model:value="codeInfo.code" lang="c_cpp" theme="textmate" :options="{
                useWorker: true,
                showPrintMargin: false, // 去掉灰色的线，printMarginColumn
                highlightActiveLine: true, // 高亮行
                highlightSelectedWord: true, // 高亮选中的字符
                tabSize: 4, // tab锁进字符
                fontSize: 20, // 设置字号
                wrap: false, // 是否换行
            }" style="height: 800px; margin-top: 10px">
                </VAceEditor>
            </div>
        </div>


        <button id="submit_code_btn" class="btn btn-success"
            style="float: right; border-radius: 20px; margin: 20px 0 0 20px;" @click="commitSubmit">
            <span class="glyphicon glyphicon-cloud-upload" style="top: 1px;"></span>
            &nbsp;
            提交答案
            &nbsp;
        </button>
        <button id="run_code_btn" class="btn btn-secondary"
            style="float: right; border-radius: 20px; margin: 20px 0 0 0;" @click="commitDebug">
            <span class="glyphicon glyphicon-play-circle" style="top: 2px;"></span>
            &nbsp;
            调试代码
            &nbsp;
        </button>
        <!-- <div class="mb-3">
            <label for="botTitle" class="form-label">名称</label>
            <input type="text" class="form-control" id="botTitle" placeholder="请输入Bot名称" v-model="userCode">
        </div>
        <div class="mb-3">
            <label for="botDesc" class="form-label">简介</label>
            <textarea class="form-control" id="botDesc" rows="3" placeholder="请输入Bot简介"
                v-model="add_bot.description"></textarea>
        </div>
        <div class="mb-3">
            <label for="add-bot-code" class="form-label">代码</label>
        </div>
        <button type="button" class="btn btn-primary" @click="addBot">确定</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button> -->
    </ContentField>
</template>


<script>
import ContentField from '@/components/ContentField.vue';
import $ from 'jquery'
import { useStore } from 'vuex';
import { ref, reactive } from 'vue';
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';

export default {
    components: {
        ContentField,
        VAceEditor
    },
    setup() {
        ace.config.set(
            "basePath",
            "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")

        const store = useStore();
        let description = ref("");
        let difficulty = ref("");
        let inputSample = ref("");
        let inputStyle = ref("");
        let outputSample = ref("");
        let outputStyle = ref("");
        let questionName = ref("");
        let codeType = ref(0);

        const codeInfo = reactive({
            code: ''
        });

        const commitSubmit = () => {

        }

        const commitDebug = () => {
            
        }

        $.ajax({
            url: "http://127.0.0.1:3000/getQuestion/",
            type: "get",
            headers: {
                Authorization: "Bearer " + store.state.user.token,
            },
            success(resp) {
                console.log(resp);

                description.value = resp.description.replace(/\\n/g, '\n');
                description.value

                difficulty.value = resp.difficulty;

                inputSample.value = resp.inputSample.substring(2, resp.inputSample.length - 2).replace(/\\n/g, '\n');
                if (inputSample.value.length == 0) inputSample.value = "无需输入";

                inputStyle.value = resp.inputStyle.replace(/\\n/g, '\n');
                if (inputStyle.value == "") inputStyle.value = "无";

                outputSample.value = resp.outputSample.substring(2, resp.outputSample.length - 2).replace(/\\n/g, '\n');
                outputSample.value.replace(/\\n/g, '\n');
                if (outputSample.value.length == 0) outputSample.value = "无需输出";

                outputStyle.value = resp.outputStyle.replace(/\\n/g, '\n');
                if (outputStyle.value == "") outputStyle.value = "无";

                questionName.value = resp.questionName;
            },
            error(resp) {
                console.log(resp);
            }
        })

        return {
            ContentField,
            description,
            difficulty,
            inputSample,
            inputStyle,
            outputSample,
            outputStyle,
            questionName,
            codeInfo,
            codeType,
            commitSubmit,
            commitDebug
        }
    }
}
</script>

<style scoped>
li.scdTitle {
    font-size: 20px;
    font-weight: 800;
}

div.title {
    font-size: 40px;
    font-weight: 800;
    text-align: center;
}

.VAceEditor {
    /* ace-editor默认没有高度，所以必须设置高度，或者同时设置最小行和最大行使编辑器的高度自动增高 */
    height: 900px;
    width: 10%;
    font-size: 16px;
    border: 1px solid;
}
</style>