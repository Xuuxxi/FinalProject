<template>
    <div class="matchGround">
        <div class="row">
            <div class="col-4">
                <div class="user_photo">
                    <img :src="$store.state.user.photo" alt="">
                    <div class="username">{{$store.state.user.username}}</div>
                </div>
            </div>
            <div class="col-4">
                <div class="bot_select">
                    <select v-model="selected_bot" class="form-select" aria-label="Default select example">
                        <option value="-1" selected style="text-align:center;">自己玩啦</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id" style="text-align:center">
                            {{bot.title}}出战</option>
                    </select>
                </div>
            </div>
            <div class="col-4">
                <div class="user_photo">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                    <div class="username">{{$store.state.pk.opponent_username}}</div>
                </div>
            </div>
            <div class="col-12" style="text-align: center;padding-top: 8vh">
                <button type="button" class="btn btn-warning btn-lg" @click="click_match_but">
                    <div class="info_text" style="font-size: 24px; font-weight:600">{{match_but_info}}</div>
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
import { ref } from 'vue';
import $ from 'jquery'

export default {
    setup() {
        const store = useStore();
        let match_but_info = ref("匹配!");
        let bots = ref([]);
        let selected_bot = ref("-1");
        const getBotInfo = () => {
            $.ajax({
                url: "http://localhost:3000/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success(resp) {
                    console.log(resp);
                    bots.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            });
        };
        getBotInfo();
        const click_match_but = () => {
            if (match_but_info.value === "匹配!") {
                match_but_info.value = "取消!";
                store.state.pk.socket.send(JSON.stringify({
                    event: "start_matching",
                    bot_id: selected_bot.value
                }));
            }
            else {
                match_but_info.value = "匹配!";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop_matching"
                }));
            }
        };
        return {
            match_but_info,
            bots,
            selected_bot,
            click_match_but,
            getBotInfo
        };
    }
}
</script>

<style scoped>
div.matchGround {
    border-radius: 2%;
    background-color: rgba(0, 0, 0, 0.3);
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
}

div.user_photo {
    text-align: center;
    padding-top: 10vh;
}

div.user_photo>img {
    border-radius: 15%;
    width: 22vh
}

div.username {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: white;
    padding-top: 2vh;
}

div.bot_select {
    padding-top: 20vh;
}

div.bot_select>select {
    margin: 3vh auto;
    width: 60%;
}
</style>