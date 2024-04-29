<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'" />
    <MatchGround v-if="$store.state.pk.status === 'matching'"></MatchGround>
    <ResultGround v-if="$store.state.pk.loser != 'none'"></ResultGround>
</template>

<script>
import PlayGround from '@/components/oj/PlayGround.vue'
import { onMounted, onUnmounted } from 'vue'
import MatchGround from '@/components/oj/MatchGround.vue';
import { useStore } from 'vuex';
import ResultGround from '@/components/oj/ResultGround.vue';

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
                photo: "https://pic.imgdb.cn/item/662676f20ea9cb14032427a1.jpg",
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
                    }, 200);
                    
                    store.commit("updateOjGame", data.game);
                }
                // else if(data.event === 'commitResp') {
                //     console.log(data.msg);
                //     console.log(data.title);
                // }

                socket.onclose = () => { }
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

<style scoped></style>