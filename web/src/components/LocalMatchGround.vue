<template>
    <div class="matchGround">
        <div class="row">
            <div class="col-4">
                <div class="user_photo">
                    <img src="https://pic1.imgdb.cn/item/6334439516f2c2beb159a7d1.jpg" alt="">
                    <div class="username">Rebecca</div>
                </div>
            </div>
            <div class="col-4">
            </div>
            <div class="col-4">
                <div class="user_photo">
                    <img src="https://pic1.imgdb.cn/item/6334439516f2c2beb159a7d4.jpg" alt="">
                    <div class="username">Lucy</div>
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

export default {
    setup() {
        const store = useStore();
        let match_but_info = ref("开始!");
        let selected_bot = ref("-1");

        const click_match_but = () => {
            store.commit("updateStatus", "playing");
        };
        return {
            match_but_info,
            selected_bot,
            click_match_but
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
    background-color: rgba(0, 0, 0, 0.3);
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