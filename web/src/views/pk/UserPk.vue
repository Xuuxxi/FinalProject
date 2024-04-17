<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'" />
    <MatchGround v-if="$store.state.pk.status === 'matching'"></MatchGround>
    <ResultGround v-if="$store.state.pk.loser != 'none'"></ResultGround>
</template>
  
<script>
import PlayGround from '../../components/PlayGround.vue'
import { onMounted, onUnmounted } from 'vue'
import MatchGround from '@/components/MatchGround.vue';
import { useStore } from 'vuex';
import ResultGround from '@/components/ResultGround.vue';

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
            store.commit("updateLoser", 'none'); 
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
                        }, 1000);

                    store.commit("updateGame", data.game);

                    console.log('match!')

                } else if (data.event === 'result' && store.state.pk.status === 'playing') store.commit("updateRes", data);
                else if (data.event === 'roll') store.commit("updateRoll", data);
                else if (data.event === 'curMap') store.commit("updateCurMap", data);

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