<template>
    <LocalPlayGround v-if="$store.state.pk.status === 'playing'" />
    <LocalResult v-if="$store.state.pk.loser != 'none'"></LocalResult>
    <LocalMatchGround v-if="$store.state.pk.status === 'matching'"></LocalMatchGround>
</template>

<script>
import LocalPlayGround from '@/components/LocalPlayGround.vue';
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex';
import LocalResult from '@/components/LocalResult.vue';
import LocalMatchGround from '@/components/LocalMatchGround.vue';

export default {
  components: {
    LocalPlayGround,
    LocalResult,
    LocalMatchGround
},
  setup() {
    const store = useStore();

    onMounted(() => {
      // 初始化
      store.commit("updateStatus", "matching");
    })

    onUnmounted(() => {
      store.commit("updateStatus", "matching");
      store.commit("updateLoser", 'none');
    })
  }
}
</script>

<style>

</style>