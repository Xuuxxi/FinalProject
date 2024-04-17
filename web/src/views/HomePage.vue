<template>
    <ContentField>
      <div class="welcome" v-if="isWelcome === 0">欢迎来到我的网站, 请夸奖我的背景选择以继续 -> <button class="btn btn-primary" @click="welcomeMe()">夸奖</button></div>
      <div class="welcome" v-else>欢迎你 {{$store.state.user.username}}</div>
      
    </ContentField>
  </template>
  
  <script>
  import ContentField from '@/components/ContentField.vue';
  import { ref } from 'vue';

  export default {
      components: { ContentField },
      setup() {
        let isWelcome = ref(0);
        const welcomeMe = () => {
            isWelcome.value = 1;
            alert("谢谢 ^-^")
        }

        return {
            isWelcome,
            welcomeMe
        }
      }
  }
  </script>
  
  <style>
  div.welcome {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  </style>