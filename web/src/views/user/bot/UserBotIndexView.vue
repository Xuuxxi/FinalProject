<template>
  <div class="container">
    <div class="row">
      <div class="col-2">
        <img :src="$store.state.user.photo" alt="" style="width: 100%; border-radius: 25%; margin-top: 40px;">
        <button type="button" data-bs-toggle="modal" data-bs-target="#changeUserPhoto"
          style="width: 100%; margin: 10px auto; background-color: rgba(50,50,50,0.35); font-weight: 450; border-radius: 10px; color: white">更换头像</button>
        <button type="button" data-bs-toggle="modal" data-bs-target="#showExBot"
          style="width: 100%; margin: 10px auto; background-color: rgba(50,50,50,0.35); font-weight: 450; border-radius: 10px; color: white">Bot示例</button>

        <!--modal-->
        <div class="modal fade" id="changeUserPhoto" tabindex="-1" aria-labelledby="exampleModalLabel"
          aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h6 class="modal-title" id="exampleModalLabel">请输入头像图片URL</h6>
              </div>
              <div class="modal-body">
                <input type="text" class="form-control" id="userPhoto" placeholder="图片URL" v-model="userPhoto">
                <div class="modal-footer">
                  <button type="button" class="btn btn-primary" @click="updUserPhoto">确定</button>
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="modal fade" id="showExBot" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog modal-xl">
            <div class="modal-content">
              <div class="modal-header">
                <h6 class="modal-title" id="exampleModalLabel">Bot 示例</h6>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <VAceEditor v-model:value="addBotRemind" @init="editorInit" lang="java" theme="textmate"
                  style="height: 300px" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="col-10">
        <div class="card" style="margin-top: 40px">
          <div class="card-header">
            <span style="font-size: 130%">{{$store.state.user.username}}的Bot</span> <button type="button"
              class="btn btn-primary float-end" data-bs-toggle="modal" data-bs-target="#addBotBut">新增Bot</button>
          </div>
          <div class="card-body">
            <table class="table table-hover">
              <thead>
                <tr>
                  <th scope="col">名称</th>
                  <th scope="col">创建时间</th>
                  <th scope="col">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="bot in bots" :key="bot.id">
                  <td>{{bot.title}}</td>
                  <td>{{bot.createtime}}</td>
                  <td>
                    <button type="button" class="btn btn-secondary" style="margin-right: 10px" data-bs-toggle="modal"
                      :data-bs-target="'#updBotBut' + bot.id">修改</button>
                    <button type="button" class="btn btn-danger" @click="delBot(bot)">删除</button>

                    <!--modal-->
                    <div class="modal fade" :id="'updBotBut' + bot.id" tabindex="-1" aria-labelledby="exampleModalLabel"
                      aria-hidden="true">
                      <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">修改Bot: {{bot.title}}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            <div class="mb-3">
                              <label for="botTitle" class="form-label">名称</label>
                              <input type="text" class="form-control" id="botTitle" placeholder="请输入Bot名称"
                                v-model="bot.title">
                            </div>
                            <div class="mb-3">
                              <label for="botDesc" class="form-label">简介</label>
                              <textarea class="form-control" id="botDesc" rows="3" placeholder="请输入Bot简介"
                                v-model="bot.description"></textarea>
                            </div>
                            <div class="mb-3">
                              <label for="botContent" class="form-label">代码</label>
                              <VAceEditor v-model:value="bot.content" @init="editorInit" lang="java" theme="textmate"
                                style="height: 300px" />
                            </div>
                          </div>
                          <div class="modal-footer">
                            <div class="error_message">{{bot.error_message}}</div>
                            <button type="button" class="btn btn-primary" @click="updBot(bot)">确定</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>


  <!--modal-->
  <!-- Modal -->
  <div class="modal fade" id="addBotBut" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">新增Bot信息</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3">
            <label for="botTitle" class="form-label">名称</label>
            <input type="text" class="form-control" id="botTitle" placeholder="请输入Bot名称" v-model="add_bot.title">
          </div>
          <div class="mb-3">
            <label for="botDesc" class="form-label">简介</label>
            <textarea class="form-control" id="botDesc" rows="3" placeholder="请输入Bot简介"
              v-model="add_bot.description"></textarea>
          </div>
          <div class="mb-3">
            <label for="add-bot-code" class="form-label">代码</label>
            <VAceEditor v-model:value="add_bot.content" @init="editorInit" lang="java" theme="textmate"
              style="height: 300px"></VAceEditor>
          </div>
        </div>
        <div class="modal-footer">
          <div class="error_message">{{add_bot.error_message}}</div>
          <button type="button" class="btn btn-primary" @click="addBot">确定</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import { useStore } from 'vuex'
import { ref, reactive } from 'vue'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';

export default {
  components: {
    VAceEditor
  },

  setup() {
    ace.config.set(
      "basePath",
      "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")



    const addBotRemind = ref(`package com.kob.botrunningsystem.service.utils;
import java.util.List;

public class Bot implements com.kob.botrunningsystem.service.utils.BotAi {
    @Override
    public Integer nextStep(List<Integer> ownBoard, List<Integer> otherBoard, Integer figure) {
        /*
         请在此处编辑你的bot逻辑代码
         */
        
        //在此处返回下一步结果
        return null;
    }
}`);


    const store = useStore();
    let bots = ref([]);
    let userPhoto = ref('');

    const add_bot = reactive({
      title: '',
      description: '',
      content: '',
      error_message: ''
    });

    const getBotInfo = () => {
      $.ajax({
        url: 'http://localhost:3000/user/bot/getlist/',
        type: 'get',
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        success(resp) {
          bots.value = resp;
        },
        error(resp) {
          console.log(resp);
        }
      })
    }

    getBotInfo();

    const updUserPhoto = () => {
      $.ajax({
        url: 'http://localhost:3000/user/photo/',
        type: 'post',
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        data: {
          userId: store.state.user.id,
          userPhoto: userPhoto.value
        },
        success(resp) {
          if (resp.error_message === 'success') {
            store.commit("updateUserPhoto", resp.userPhoto);
          }
        }
      })
      Modal.getInstance('#changeUserPhoto').hide();
      userPhoto.value = '';
    }

    const addBot = () => {
      add_bot.error_message = '';
      $.ajax({
        url: 'http://localhost:3000/user/bot/add/',
        type: 'post',
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        data: {
          title: add_bot.title,
          description: add_bot.description,
          content: add_bot.content
        },
        success(resp) {
          if (resp.error_message === 'success') {
            add_bot.title = '',
              add_bot.description = '',
              add_bot.content = '',
              add_bot.error_message = '',
              Modal.getInstance('#addBotBut').hide();
            getBotInfo();
          } else add_bot.error_message = resp.error_message;
        }
      })
    }

    const updBot = (bot) => {
      bot.error_message = '';
      $.ajax({
        url: 'http://localhost:3000/user/bot/update/',
        type: 'post',
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        data: {
          bot_id: bot.id,
          title: bot.title,
          description: bot.description,
          content: bot.content,
        },
        success(resp) {
          if (resp.error_message === 'success') {
            bot.error_message = '';
            Modal.getInstance('#updBotBut' + bot.id).hide();
            getBotInfo();
          } else bot.error_message = resp.error_message;
        }
      })
    }

    const delBot = (bot) => {
      $.ajax({
        url: 'http://localhost:3000/user/bot/remove/',
        type: 'post',
        headers: {
          Authorization: "Bearer " + store.state.user.token
        },
        data: {
          bot_id: bot.id
        },
        success(resp) {
          if (resp.error_message === 'success') {
            getBotInfo();
          }
        }
      })
    }

    return {
      userPhoto,
      bots,
      addBotRemind,
      getBotInfo,
      add_bot,
      addBot,
      updBot,
      delBot,
      updUserPhoto
    }
  }
}
</script>

<style scoped>
div.error_message {
  color: red;
}

div.username {
  font-size: 24px;
  font-weight: 600;
  color: black;
  text-align: center;
}
</style>
