<template>
    <div class="row">
        <div class="col-2">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"
                style="width: 75%; margin: 40px 55px; background-color: rgba(50,50,50,0.6); font-weight: 800; font-size:30px; border-radius: 10px; color: white">
                匹配规则
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="margin-top: 20vh">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">匹配规则</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            ...
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-8">
            <div class="matchGround">
                <div class="row">
                    <div class="col-4">
                        <div class="user_photo">
                            <img :src="$store.state.user.photo" alt="">
                            <div class="username">{{ $store.state.user.username }}</div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="bot_select">
                            <select v-model="selected_bot" class="form-select" aria-label="Default select example">
                                <option value="-1" selected style="text-align:center;">自己玩啦</option>
                                <option v-for="bot in bots" :key="bot.id" :value="bot.id" style="text-align:center">
                                    {{ bot.title }}出战</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="user_photo">
                            <img :src="$store.state.pk.opponent_photo" alt="">
                            <div class="username">{{ $store.state.pk.opponent_username }}</div>
                        </div>
                    </div>
                    <div class="col-12" style="text-align: center;padding-top: 8vh">
                        <button type="button" class="btn btn-warning btn-lg" @click="startBotGame">
                            <div class="info_text" style="font-size: 24px; font-weight:600">{{ but_info }}</div>
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-2">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"
                style="width: 75%; margin: 40px 15px; background-color: rgba(50,50,50,0.6); font-weight: 800; font-size:30px; border-radius: 10px; color: white">
                游戏规则
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="margin-top: 20vh">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">匹配规则</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            ...
                        </div>
                    </div>
                </div>
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
        let bots = ref([]);
        let selected_bot = ref("-1");
        let but_info = ref('开始!')

        const startBotGame = () => {
            $.ajax({
                url: 'http://127.0.0.1:3000/pk/start/game/',
                type: 'post',
                data: {
                    a_id: store.state.user.id,
                    a_bot_id: selected_bot.value,
                    b_id: '114514',
                    b_bot_id: '114514'
                },
                success() {
                    but_info.value = "来了!";
                }
            })
        }

        const getBotInfo = () => {
            $.ajax({
                url: "http://localhost:3000/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success(resp) {
                    bots.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            });
        };
        getBotInfo();

        return {
            but_info,
            bots,
            selected_bot,
            getBotInfo,
            startBotGame
        };
    }
}
</script>

<style scoped>
div.matchGround {
    border-radius: 2%;
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
    background-color: rgba(0, 0, 0, 0.4);
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