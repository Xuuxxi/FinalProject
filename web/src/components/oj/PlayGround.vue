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
              <option value="1">c++</option>
              <option value="2">java</option>
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
      }" style="height: 700px; margin-top: 10px">
        </VAceEditor>
      </div>
    </div>

    <div class="row">
      <div class="col-12">
        <button v-if="butShow" id="submit_code_btn" class="btn btn-success mb-2"
          style="float: right; border-radius: 20px; margin: 20px 0 0 20px;" @click="commitSubmit">
          <span class="glyphicon glyphicon-cloud-upload" style="top: 1px;"></span>
          &nbsp;
          提交代码
          &nbsp;
        </button>

        <button v-if="!butShow" class="btn btn-primary mb-2" type="button" disabled
          style="float: right; border-radius: 20px; margin: 20px 0 0 20px;">
          <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
          代码运行
        </button>
      </div>

      <div v-if="title.length != 0" class="col-12">
        <hr class="mb-3">
        <div class="card" style="">
          <div class="card-header" style="font-size: 25px; font-weight: 800; font-size:30px;">
            {{ title }}
          </div>
          <div class="card-body">
            {{ message }}
          </div>
        </div>
      </div>
    </div>
  </ContentField>
</template>


<script>
import ContentField from '@/components/ContentField.vue';
import { useStore } from 'vuex';
import { ref, reactive } from 'vue';
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';
import { onMounted } from 'vue'

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
    const socket = store.state.pk.socket;
    let description = ref(store.state.pk.description);
    let difficulty = ref(store.state.pk.difficulty);
    let inputSample = ref(store.state.pk.inputSample);
    let inputStyle = ref(store.state.pk.inputStyle);
    let outputSample = ref(store.state.pk.outputSample);
    let outputStyle = ref(store.state.pk.outputStyle);
    let questionName = ref(store.state.pk.questionName);
    let codeType = ref("0");

    const butShow = ref(true);
    let title = ref("运行结果");
    let message = ref("暂无运行结果");

    const codeInfo = reactive({
      code: ''
    });

    const commitSubmit = () => {
      if (codeType.value == "0") alert("请选择编程语言");
      else {
        butShow.value = false;
        socket.send(JSON.stringify({
          event: "commitSubmit",
          code: codeInfo.code,
          uid: store.state.user.id,
          qid: store.state.pk.qid,
          userName: store.state.user.username,
          questionName: questionName.value,
          language: codeType.value
        }));
      }
    }

    onMounted(() => {
      socket.onmessage = msg => {
        const data = JSON.parse(msg.data);
        if (data.event === 'commitResp') {
          butShow.value = true;
          title.value = data.title;
          message.value = data.message
          console.log(data.message);
        } else if (data.event === 'result') {
          store.commit("updateLoser", data.loser);
        }
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
      message,
      title,
      butShow,
      commitSubmit
    }
  }
}
</script>

<style scoped>
li.scdTitle {
  font-size: 20px;
  font-weight: 800; font-size:30px;
}

div.title {
  font-size: 40px;
  font-weight: 800; font-size:30px;
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