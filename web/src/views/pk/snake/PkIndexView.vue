<template>
  <PlayGround v-if="$store.state.pk.status === 'playing'" />
  <MatchGround v-if="$store.state.pk.status === 'matching'"></MatchGround>
  <ResultGround v-if="$store.state.pk.loser != 'none'"></ResultGround>
</template>

<script>
import PlayGround from '@/components/snake/PlayGround.vue'
import { onMounted, onUnmounted } from 'vue'
import MatchGround from '@/components/snake/MatchGround.vue';
import { useStore } from 'vuex';
import ResultGround from '@/components/snake/ResultGround.vue';

export default {
  components: {
    PlayGround,
    MatchGround,
    ResultGround
  },
  setup() {
    const store = useStore();
    const socketUrl = `ws://localhost:3000/websocket/${store.state.user.token}`

    let socket = null;

    onMounted(() => {
      // 初始化
      store.commit("updateOpponent", {
        username: '待匹配',
        photo: "https://img1.baidu.com/it/u=3373990605,1380325608&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1664470800&t=69ba588c27005cd51ffab1a02cb7d94c",
      })

      socket = new WebSocket(socketUrl);

      socket.onopen = () => {
        console.log('connected!');
        store.commit("updateSocket", socket)
      }

      socket.onmessage = msg => {
        const data = JSON.parse(msg.data);
        if (data.event === "match_success") {
          store.commit("updateOpponent", {
            username: data.opponent_username,
            photo: data.opponent_photo
          }),

            setTimeout(() => {
              store.commit("updateStatus", "playing")
            }, 200);

          store.commit("updateSnakeGame", data.game);

          console.log('match!')
        } else if (data.event === 'move') {
          const game = store.state.pk.gameObject;
          const [snake0, snake1] = game.snakes;
          snake0.set_direction(data.stepA);
          snake1.set_direction(data.stepB);
        } else if (data.event === 'result') {
          const game = store.state.pk.gameObject;
          const [snake0, snake1] = game.snakes;

          if (data.loser === "all" || data.loser === "A") {
            snake0.status = "die";
          }
          if (data.loser === "all" || data.loser === "B") {
            snake1.status = "die";
          }
          store.commit("updateLoser", data.loser);
        }

        socket.onclose = () => {
          console.log("disconnected!")
        }
      }
    })

    onUnmounted(() => {
      socket.close();
      store.commit("updateStatus", "matching");
      store.commit("updateLoser", 'none');
    })
  }
}
</script>

<style scoped>

</style>