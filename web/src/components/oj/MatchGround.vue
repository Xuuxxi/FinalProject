<template>
    <div class="matchGround">
        <div class="row">
            <div class="col-6">
                <div class="user_photo">
                    <img :src="$store.state.user.photo" alt="">
                    <div class="username">{{$store.state.user.username}}</div>
                </div>
            </div>
            <div class="col-6">
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
// import $ from 'jquery'

export default {
    setup() {
        const store = useStore();
        let match_but_info = ref("匹配!");
        let bots = ref([]);
        // const getBotInfo = () => {
        //     $.ajax({
        //         url: "http://localhost:3000/user/bot/getlist/",
        //         type: "get",
        //         headers: {
        //             Authorization: "Bearer " + store.state.user.token
        //         },
        //         success(resp) {
        //             bots.value = resp;
        //         },
        //         error(resp) {
        //             console.log(resp);
        //         }
        //     });
        // };
        const click_match_but = () => {
            if (match_but_info.value === "匹配!") {
                match_but_info.value = "取消!";
                store.state.pk.socket.send(JSON.stringify({
                    event: "start_matching",
                    game_type: "3",
                    bot_id: '-1'
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
            click_match_but
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