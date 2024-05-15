<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.loser === 'all'">
            平局咯
        </div>
        <div class="result-board-text-lose" v-if="$store.state.pk.loser === 'A'">
            亚当胜利!
        </div>
        <div class="result-board-text-win" v-else>
            Win ^-^
        </div>
        <div v-if="$store.state.pk.a_score === 0 && $store.state.pk.b_score === 0">
            <div class="score_text">
                回合超时!
            </div>
        </div>
        <div v-else>
            <div class="score_text">
                你的分数: {{$store.state.pk.a_score}}
            </div>
            <div class="score_text">
                亚当分数: {{$store.state.pk.b_score}}
            </div>
        </div>
        <div class="result-board-btn">
            <button @click="restart" type="button" class="btn btn-warning btn-lg">
                再来!
            </button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();

        const restart = () => {
            store.commit("updateStatus", "matching");
            store.commit("updateLoser", "none");
            store.commit("updateOpponent", {
                username: "待匹配",
                photo: "https://pic1.imgdb.cn/item/6334434516f2c2beb1594757.png",
            })
        }

        return {
            restart
        };
    }
}
</script>

<style scoped>
div.result-board {
    height: 30vh;
    width: 30vw;
    background-color: rgba(50, 50, 50, 0.55);
    position: absolute;
    top: 30vh;
    left: 35vw;
    border-radius: 20px;
}

div.result-board-text {
    text-align: center;
    color: white;
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 2.5vh;
    padding-bottom: 1vh;
}

div.result-board-text-lose {
    text-align: center;
    color: red;
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 2.5vh;
    padding-bottom: 1vh;
}

div.result-board-text-win {
    text-align: center;
    color: green;
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 2.5vh;
    padding-bottom: 1vh;
}

div.result-board-btn {
    padding-top: 3vh;
    text-align: center;
}

div.score_text {
    text-align: center;
    color: white;
    font-size: 30px;
    font-weight: 600;
}
</style>