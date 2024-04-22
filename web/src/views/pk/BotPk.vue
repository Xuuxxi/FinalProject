<template>
  <PlayGround v-if="$store.state.pk.status === 'playing'" />
  <BotPkMatchBoard v-if="$store.state.pk.status === 'matching'"></BotPkMatchBoard>
  <BotPkResultBoard v-if="$store.state.pk.loser != 'none'"></BotPkResultBoard>
</template>

<script>
import PlayGround from '../../components/PlayGround.vue'
import { useStore } from 'vuex';
import { onMounted, onUnmounted } from 'vue'
import BotPkMatchBoard from '../../components/BotPkMatchBoard.vue';
import BotPkResultBoard from '../../components/BotPkResultBoard.vue';

export default {
  components: {
    PlayGround,
    BotPkMatchBoard,
    BotPkResultBoard
  },
  setup() {
    const store = useStore();
    const socketUrl = `ws://localhost:3000/websocket/${store.state.user.token}`

    let socket = null;

    onMounted(() => {
      store.commit("updateLoser", 'none');
      store.commit("updateOpponent", {
        username: 'Crazy Bot!',
        photo: "https://pic1.imgdb.cn/item/6334434516f2c2beb1594757.png",
      })

      socket = new WebSocket(socketUrl);

      socket.onopen = () => {
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
            }, 1000);

          store.commit("updateGame", data.game);

        } else if (data.event === 'result' && store.state.pk.status === 'playing') store.commit("updateRes", data);
        else if (data.event === 'roll') store.commit("updateRoll", data);
        else if (data.event === 'curMap') store.commit("updateCurMap", data);

        socket.onclose = () => {}
      }
    })

    onUnmounted(() => {
      socket.close();
      store.commit("updateStatus", "matching");
      store.commit("updateLoser", 'none');
    })

    return {
    }
  }
}
</script>

<style>

</style>