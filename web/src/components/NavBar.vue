<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <router-link class="navbar-brand" :to="{ name: 'home' }">ALL F IN</router-link>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link :class="route_name == 'oj' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'oj' }">随机题目对决</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'pk_user' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'pk_user' }">骰子匹配对战</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'pk_user_snake' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'pk_user_snake' }">蛇蛇匹配对战</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'pk_bot' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'pk_bot' }">骰子人机对战</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'pk_bot_snake' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'pk_bot_snake' }">蛇蛇人机对战</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'pk_local' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'pk_local' }">本地对战</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name == 'ranklist_index' ? 'nav-link active' : 'nav-link'"
              :to="{ name: 'ranklist_index' }">排行榜</router-link>
          </li>
        </ul>
        <ul class="navbar-nav" v-if="$store.state.user.is_login">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
              data-bs-toggle="dropdown" aria-expanded="false">
              {{ $store.state.user.username }}
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
              <router-link class="dropdown-item" :to="{ name: 'user_bot_index' }">我的bot</router-link>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><a class="dropdown-item" href="#" @click="toRule">游戏规则</a></li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><a class="dropdown-item" href="#" @click="logout">退出</a></li>
            </ul>
          </li>
        </ul>
        <ul class="navbar-nav" v-else>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'user_account_login' }" role="button">
              登录
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'user_account_register' }" role="button">
              注册
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import store from '@/store';
export default {
  setup() {
    const route = useRoute();
    let route_name = computed(() => route.name)
    const toRule = () => {
      window.location.replace('https://blog.csdn.net/is_Yng/article/details/127041607?csdn_share_tail=%7B%22type%22%3A%22blog%22%2C%22rType%22%3A%22article%22%2C%22rId%22%3A%22127041607%22%2C%22source%22%3A%22is_Yng%22%7D')
    }

    const logout = () => {
      store.dispatch("logout");
    }

    return {
      route_name,
      logout,
      toRule
    }
  }
}
</script>

<style scoped></style>